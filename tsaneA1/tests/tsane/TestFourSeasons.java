package tsane;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.Enumeration;

import org.junit.Test;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Element;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.PileView;
import ks.launcher.Main;
import ks.tests.KSTestCase;

public class TestFourSeasons extends KSTestCase {
	FourSeasons fs;
	GameWindow gw;
	
	ToWasteMove twm;
	ToFoundationMove c2f;
	ToFoundationMove w2f;
	ToFoundationMove f2f;
	ToCrossPileMove c2c;
	ToCrossPileMove w2c;
	ToCrossPileMove f2c;
	
	static Pile[] crossPiles;
	static Pile[] foundations;
	
	@Override
	protected void setUp() {
		fs = new FourSeasons();
		gw = Main.generateWindow(fs, Deck.OrderBySuit);
		twm = new ToWasteMove(fs.stock, fs.waste);
		crossPiles = new Pile[] {fs.crossTop, fs.crossLeft, fs.crossMid, fs.crossRight, fs.crossBottom};
		foundations = new Pile[] {fs.heartF, fs.spadeF, fs.clubF, fs.diamondF};
	}
	
	@Override
	protected void tearDown() {
		gw.setVisible(false);
		gw.dispose();
	}
	
	@Test
	public void testInitializeModel() {
		fs.initializeModel(fs.getSeed());
		
		assertEquals(0, fs.getScore().getValue());
		assertEquals(52, fs.getNumLeft().getValue());
		assertEquals(52, fs.stock.count());
		assertTrue(fs.waste.empty());
		
		assertTrue(fs.stock.getName().equals("Stock"));
		assertTrue(fs.waste.getName().equals("Waste"));
		
		// re initialize crossPiles to contain brand new piles as opposed to those from constructor
		crossPiles = new Pile[] {fs.crossTop, fs.crossLeft, fs.crossMid, fs.crossRight, fs.crossBottom};
		foundations = new Pile[] {fs.heartF, fs.spadeF, fs.clubF, fs.diamondF};
		for(Pile p : crossPiles){
			assertTrue(p.getName().contains("Cross"));
			assertTrue(p.empty());
		}		
		for(Pile p : foundations){
			assertTrue(p.getName().contains("Foundation"));
			assertTrue(p.empty());
		}
	}

	@Test
	public void testFreshGame() {
		assertEquals(46, fs.getNumLeft().getValue());
		assertEquals(1, fs.getScoreValue());
		
		assertFalse(fs.heartF.empty());
		assertEquals(Card.HEARTS, fs.heartF.peek().getSuit());
		assertTrue(fs.spadeF.empty());
		assertTrue(fs.clubF.empty());
		assertTrue(fs.diamondF.empty());
		
		for(Pile p : crossPiles){
			assertFalse(p.empty());
		}
	}
	
	@Test
	public void testToWasteMove() {
		assertTrue(twm.valid(fs));
		Card c = fs.stock.peek();
		if(twm.doMove(fs)) fs.pushMove(twm);		
		assertEquals(45, fs.stock.count());
		assertEquals(c, fs.waste.peek());
		
		assertEquals(twm, fs.getMoves().nextElement());
		
		c = fs.waste.peek();
		twm.undo(fs);		
		assertEquals(46, fs.stock.count());
		assertEquals(c, fs.stock.peek());
		
		fs.stock.removeAll();
		assertTrue(fs.stock.empty());
		assertFalse(twm.doMove(fs));
		
		assertEquals(1, fs.getScoreValue()); // test unchanged
	}
	
	@Test
	public void testToFoundationMove() { //foundations all have same behavior, so test one = test them all
		for(Pile p : foundations) {
			f2f = new ToFoundationMove(p, p.get(), fs.heartF);
			assertFalse(f2f.valid(fs));
			assertFalse(f2f.doMove(fs));
		}
		
		Card c = fs.crossLeft.peek();
		c2f = new ToFoundationMove(fs.crossLeft, fs.crossLeft.peek(), fs.spadeF);
		assertFalse(c2f.valid(fs)); // test fail if not base rank
		
		c2f = new ToFoundationMove(fs.crossLeft, fs.crossLeft.peek(), fs.clubF);
		assertFalse(c2f.valid(fs)); // test fail if not same suit
		
		c = fs.crossTop.peek();
		c2f = new ToFoundationMove(fs.crossTop, fs.crossTop.get(), fs.spadeF);
		assertTrue(c2f.valid(fs));
		assertTrue(c2f.doMove(fs));
		fs.pushMove(c2f);
		assertEquals(c, fs.spadeF.peek());
		assertTrue(fs.crossTop.empty());
		assertEquals(2, fs.getScoreValue()); // test equals base rank
		
		assertEquals(46, fs.getNumLeft().getValue()); // test unchanged
		
		do { //get some cards into the waste pile
			twm.doMove(fs);
		} 
		while(!fs.waste.peek().isAce());
		
		c = fs.waste.peek(); // ace of spades
		w2f = new ToFoundationMove(fs.waste, fs.waste.get(), fs.spadeF);
		assertTrue(w2f.valid(fs));
		assertTrue(w2f.doMove(fs));
		fs.pushMove(w2f);
		assertEquals(c, fs.spadeF.peek());
		assertEquals(3, fs.getScoreValue()); // test wraparound King -> Ace
		
		c = fs.waste.peek(); // 2 of spades
		w2f = new ToFoundationMove(fs.waste, fs.waste.get(), fs.spadeF);
		assertTrue(w2f.valid(fs));
		assertTrue(w2f.doMove(fs));
		fs.pushMove(w2f);
		assertEquals(c, fs.spadeF.peek());
		assertEquals(4, fs.getScoreValue()); // test standard rank++ build
		
		c = fs.spadeF.peek(); // 2 of spades
		assertTrue(w2f.undo(fs));
		assertEquals(c, fs.waste.peek());
		assertEquals(3, fs.getScoreValue());
		assertTrue(fs.spadeF.peek().isAce()); //test undo waste to foundation move
		
		w2f.undo(fs); // put ace away to waste
		
		c = fs.spadeF.peek(); // king of spades
		assertTrue(c2f.undo(fs));
		assertEquals(c, fs.crossTop.peek());
		assertEquals(1, fs.getScoreValue());	
	}
	
	@Test
	public void testToCrossPileMove() {
		for(Pile p : foundations) {
			f2c = new ToCrossPileMove(p, p.get(), fs.crossTop); // some pile, fails anyway
			assertFalse(f2c.valid(fs));
		}
		
		while(fs.stock.peek().getSuit() != Card.HEARTS && fs.stock.peek().getRank() != Card.QUEEN) {
			fs.stock.get(); // discard, unnecessary
		} 
		twm = new ToWasteMove(fs.stock, fs.waste);
		twm.doMove(fs); // get queen of hearts onto waste pile
		
		Card c = fs.waste.peek(); // queen of hearts
		w2c = new ToCrossPileMove(fs.waste, fs.waste.get(), fs.crossTop);
		assertTrue(w2c.valid(fs));
		assertTrue(w2c.doMove(fs));
		fs.pushMove(w2c);
		assertEquals(c, fs.crossTop.peek()); // test suit-blind rank-based move
		
		c = fs.crossTop.peek(); // queen of hearts
		assertTrue(w2c.undo(fs));
		assertEquals(c, fs.waste.peek());
		
		c = fs.crossLeft.peek();
		ToCrossPileMove c2c2 = new ToCrossPileMove(fs.crossLeft, fs.crossLeft.get(), fs.crossTop);
		assertTrue(c2c2.valid(fs));
		assertTrue(c2c2.doMove(fs));
		fs.pushMove(c2c2);
		assertEquals(c, fs.crossTop.peek()); // test simple move
				
		c = fs.crossBottom.peek(); // 9 of spades
		c2c = new ToCrossPileMove(fs.crossBottom, fs.crossBottom.get(), fs.crossLeft);
		assertTrue(c2c.valid(fs));
		assertTrue(c2c.doMove(fs));
		fs.pushMove(c2c);
		assertEquals(c, fs.crossLeft.peek()); // test move cross to empty
		
		c = fs.crossLeft.peek();
		assertTrue(c2c.undo(fs));
		assertEquals(c, fs.crossBottom.peek()); // undo cross to empty
		
		c = fs.waste.peek(); // queen of hearts
		w2c = new ToCrossPileMove(fs.waste, fs.waste.get(), fs.crossLeft);
		assertTrue(w2c.valid(fs));
		assertTrue(w2c.doMove(fs));
		fs.pushMove(w2c);
		assertEquals(c, fs.crossLeft.peek()); // test move waste to empty
		
		c = fs.crossLeft.peek();
		assertTrue(w2c.undo(fs));
		assertEquals(c, fs.waste.peek()); // undo waste to empty
		
		c = fs.crossTop.peek();
		assertTrue(c2c2.undo(fs));
		assertEquals(c, fs.crossLeft.peek()); // undo full to full same suit
	}
}

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
		
	@Override
	protected void setUp() {
		fs = new FourSeasons();
		gw = Main.generateWindow(fs, Deck.OrderByRank);
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
		
		Pile[] crossPiles = {fs.crossTop, fs.crossLeft, fs.crossMid, fs.crossRight, fs.crossBottom};
		for(Pile p : crossPiles){
			assertTrue(p.getName().contains("Cross"));
			assertTrue(p.empty());
		}
		
		Pile[] foundations = {fs.heartF, fs.spadeF, fs.clubF, fs.diamondF};
		for(Pile p : foundations){
			assertTrue(p.getName().contains("Foundation"));
			assertTrue(p.empty());
		}
	}

	@Test
	public void testInitializeControllers() {
		
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
		
		Pile[] crossPiles = {fs.crossTop, fs.crossLeft, fs.crossMid, fs.crossRight, fs.crossBottom};
		for(Pile p : crossPiles){
			assertFalse(p.empty());
		}
	}
}

package tsane;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.Enumeration;

import org.junit.Test;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Element;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.PileView;
import ks.launcher.Main;

public class TestFourSeasons extends TestCase {
	FourSeasons fs;
	GameWindow gw;
		
	@Override
	protected void setUp() {
		fs = new FourSeasons();
		gw = Main.generateWindow(fs, 117);
	}
	
	@Override
	protected void tearDown() {
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
	public void testInitializeView() {
		Dimension d = new Dimension(769, 635); // solitaire window dimension
		assertTrue(fs.stockView.getX() + fs.stockView.getWidth() < d.getWidth());
		assertTrue(fs.stockView.getY() + fs.stockView.getHeight() < d.getHeight());		
		
		assertTrue(fs.wasteView.getX() + fs.wasteView.getWidth() < d.getWidth());
		assertTrue(fs.wasteView.getY() + fs.wasteView.getHeight() < d.getHeight());		
				
		assertEquals(fs.getCardImages().getWidth(), fs.stockView.getWidth());
		assertEquals(fs.getCardImages().getHeight(), fs.stockView.getHeight());
		assertEquals(fs.getCardImages().getWidth(), fs.wasteView.getWidth());
		assertEquals(fs.getCardImages().getHeight(), fs.wasteView.getHeight());
				
		PileView[] crossPileViews = {fs.crossTopView, fs.crossLeftView, fs.crossMidView, fs.crossRightView, fs.crossBottomView};
		for(PileView p : crossPileViews){
			assertEquals(fs.getCardImages().getWidth(), p.getWidth());
			assertEquals(fs.getCardImages().getHeight(), p.getHeight());
			assertTrue(p.getX()+p.getWidth() < d.getWidth());
			assertTrue(p.getY()+p.getHeight() < d.getHeight());
		}
		
		PileView[] foundationViews = {fs.heartFView, fs.spadeFView, fs.clubFView, fs.diamondFView};
		for(PileView p : foundationViews){
			assertEquals(fs.getCardImages().getWidth(), p.getWidth());
			assertEquals(fs.getCardImages().getHeight(), p.getHeight());
			assertTrue(p.getX()+p.getWidth() < d.getWidth());
			assertTrue(p.getY()+p.getHeight() < d.getHeight());
		}
		
	}
	
	@Test
	public void testInitializeControllers() {
		
	}
	
	
	@Test
	public void testFreshGame() {
		fs = new FourSeasons(); // calls freshGame
		assertEquals(46, fs.getNumLeft().getValue());
		assertEquals(6, fs.getScoreValue());
		
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

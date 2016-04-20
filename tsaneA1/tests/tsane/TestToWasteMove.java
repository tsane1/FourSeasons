package tsane;

import org.junit.Test;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import tsane.FourSeasons;

public class TestToWasteMove extends TestCase {
	FourSeasons fs;
	GameWindow gw;
	ToWasteMove twm;
	
	@Override
	protected void setUp() {
		fs = new FourSeasons();
		gw = Main.generateWindow(fs, Deck.OrderBySuit);
		twm = new ToWasteMove(fs.stock, fs.waste);
	}
	
	@Override
	protected void tearDown() {
		gw.setVisible(false);
		gw.dispose();
	}
	
	@Test
	public void testDoMove() {
		assertTrue(twm.valid(fs));
		Card c = fs.stock.peek();
		twm.doMove(fs);
		
		assertEquals(45, fs.stock.count());
		assertEquals(c, fs.waste.peek());
	}
	
	@Test
	public void testUndoMove() {
		twm.doMove(fs);
		Card c = fs.waste.peek();
		twm.undo(fs);
		
		assertEquals(46, fs.stock.count());
		assertEquals(c, fs.stock.peek());
	}
	
	@Test
	public void testPushMove() {
		twm.doMove(fs);
		fs.pushMove(twm);
		assertEquals(twm, fs.getMoves().nextElement());
	}
	
	@Test
	public void testInvalidMove() {
		fs.stock.removeAll();
		assertTrue(fs.stock.empty());
		assertFalse(twm.doMove(fs));
	}
}

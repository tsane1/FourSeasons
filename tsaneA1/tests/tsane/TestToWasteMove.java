package tsane;

import static org.junit.Assert.*;

import java.util.Enumeration;

import org.junit.Test;

import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.launcher.Main;
import tsane.FourSeasons;

public class TestToWasteMove extends TestCase {
	FourSeasons fs;
	GameWindow gw;
	ToWasteMove twm;
	
	@Override
	protected void setUp() {
		fs = new FourSeasons();
		gw = Main.generateWindow(fs, 117);
		twm = new ToWasteMove(fs.stock, fs.waste);
	}
	
	@Override
	protected void tearDown() {
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
		fs.initializeModel(fs.getSeed());
		while(!fs.stock.empty()) {
			twm.doMove(fs);
			fs.pushMove(twm);
		}
		assertEquals(0, fs.getNumLeft());
	}
}

package tsane;

import org.junit.Test;
import junit.framework.TestCase;
import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import tsane.FourSeasons;

@Deprecated
public class TestToFoundationMove extends KSTestCase{
	FourSeasons fs;
	GameWindow gw;
	ToFoundationMove wasteToF, crossToF;
	
	@Override
	protected void setUp() {
		fs = new FourSeasons();
		gw = Main.generateWindow(fs, Deck.OrderBySuit);
		
		ToWasteMove m = new ToWasteMove(fs.stock, fs.waste);
		m.doMove(fs);
		wasteToF = new ToFoundationMove(fs.waste, fs.waste.get(), fs.clubF);
		crossToF = new ToFoundationMove(fs.crossLeft, fs.crossLeft.get(), fs.spadeF);
	}
	
	@Override
	protected void tearDown() {
		gw.setVisible(false);
		gw.dispose();
	}
	
	@Test
	public void testDoMove() {
		assertTrue(wasteToF.valid(fs));
		assertTrue(crossToF.valid(fs));
	}
}

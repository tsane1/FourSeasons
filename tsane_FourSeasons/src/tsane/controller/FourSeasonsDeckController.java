package tsane.controller;

import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import tsane.model.ToWasteMove;


public class FourSeasonsDeckController extends java.awt.event.MouseAdapter {
	protected Solitaire fs = null;
	
	public FourSeasonsDeckController(Solitaire s) {
		super();
		fs = s;
	}
	
	public void mousePressed(java.awt.event.MouseEvent me) {
		Deck stock = (Deck) fs.getModelElement("Stock");
		Pile waste = (Pile) fs.getModelElement("Waste");
		
		if(!stock.empty()){
			Move m = new ToWasteMove(stock, waste);
			if(m.doMove(fs)) fs.pushMove(m);
			fs.refreshWidgets();
		}
	}
}

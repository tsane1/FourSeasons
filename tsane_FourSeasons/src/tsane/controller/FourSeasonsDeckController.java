package tsane.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ks.common.games.Solitaire;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import tsane.model.ToWasteMove;

public class FourSeasonsDeckController extends MouseAdapter {
	protected Solitaire fs;
	
	public FourSeasonsDeckController(Solitaire s){
		super();
		fs = s;
	}
	
	public void mousePressed (MouseEvent me){
		Deck stock = (Deck) fs.getModelElement("Stock");
		Pile waste = (Pile) fs.getModelElement("Waste");
		
		if(!stock.empty()){
			Move m = new ToWasteMove(stock, waste);
			if(m.doMove(fs)) fs.pushMove(m);
			fs.refreshWidgets();
		}
	}
}

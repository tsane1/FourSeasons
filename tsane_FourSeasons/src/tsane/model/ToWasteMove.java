package tsane.model;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToWasteMove extends Move {
	protected Deck stock;
	protected Pile waste;
	
	public ToWasteMove(Deck stock, Pile waste){
		super();
		this.stock = stock;
		this.waste = waste;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)) return false;
		else {
			waste.add(stock.get());
			game.updateNumberCardsLeft(-1);
			return true;
		}
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(stock.empty()) return false;
		else return true;
	}
	
}

package tsane;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToWasteMove extends Move {
	Deck stock;
	Pile waste;
	
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
		stock.add(waste.get());
		game.updateNumberCardsLeft(1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		return !stock.empty();
	}
}

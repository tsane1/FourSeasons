package tsane;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToCrossPileMove extends Move {
	Pile sourcePile;
	Pile targetCrossPile;
	Card c;
		
	public ToCrossPileMove(Pile sourcePile, Card c, Pile targetCrossPile) {
		super();
		this.sourcePile = sourcePile;
		this.targetCrossPile = targetCrossPile;
		this.c = c;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)) return false;
		else {
			targetCrossPile.add(c);
			return true;
		}
	}

	@Override
	public boolean undo(Solitaire game) {
		sourcePile.add(targetCrossPile.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(targetCrossPile.empty()) return !sourcePile.getName().contains("Foundation");
		else {
			return !sourcePile.getName().contains("Foundation") &&
						 (targetCrossPile.peek().compareTo(c) == 1 ||
						 	targetCrossPile.peek().isAce() && c.getRank() == Card.KING);
		}
	}
	
}

package tsane.model;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToCrossPileMove extends Move {
	protected Pile sourcePile;
	protected Pile targetCrossPile;
	protected Card c;
		
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(targetCrossPile.empty()) return !sourcePile.getName().contains("Foundation"); // blindly allow move
		else {
			return !sourcePile.getName().contains("Foundation") &&
						 (targetCrossPile.peek().compareTo(c) == 1 ||
						 	targetCrossPile.peek().isAce() && c.getRank() == 13);
		}
	}
	
}

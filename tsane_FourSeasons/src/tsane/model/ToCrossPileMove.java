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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undo(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

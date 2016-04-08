package tsane.model;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToFoundationMove extends Move {
	protected Pile sourcePile;
	protected Pile targetFoundation;
	
	public ToFoundationMove(Pile sourcePile, Pile targetFoundation){
		super();
		this.sourcePile = sourcePile;
		this.targetFoundation = targetFoundation;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)) return false;
		else {
			targetFoundation.add(sourcePile.get());
			game.updateScore(1);
			return true;
		}
	}

	@Override
	public boolean undo(Solitaire game) {
		return false;
	}

	@Override
	public boolean valid(Solitaire game) {
		return false;
	}
}

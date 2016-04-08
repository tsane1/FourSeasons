package tsane.model;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToFoundationMove extends Move {
	protected Pile sourcePile;
	protected Pile targetFoundation;
	protected Card c;
	protected int fbase;
	
	public ToFoundationMove(Pile sourcePile, Card c, Pile targetFoundation, int fbase){
		super();
		this.sourcePile = sourcePile;
		this.targetFoundation = targetFoundation;
		this.c = c;
		this.fbase = fbase;
	}
	
	@Override
	public boolean doMove(Solitaire game) {
		if(!valid(game)) return false;
		else {
			targetFoundation.add(c);
			game.updateScore(1);
			return true;
		}
	}

	@Override
	public boolean undo(Solitaire game) {
		sourcePile.add(targetFoundation.get());
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(targetFoundation.empty()) return c.getRank() == fbase;
		else {
			return !sourcePile.getName().contains("Foundation") &&
						 (targetFoundation.peek().compareTo(c) == -1 ||
						 	targetFoundation.peek().getRank() == 13 && c.isAce()) &&
						 targetFoundation.peek().sameSuit(c);
		}
	}
}

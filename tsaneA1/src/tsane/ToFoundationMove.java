package tsane;

import ks.common.games.Solitaire;
import ks.common.model.*;

public class ToFoundationMove extends Move {
	Pile sourcePile;
	Pile targetFoundation;
	Card c;
	
	public ToFoundationMove(Pile sourcePile, Card c, Pile targetFoundation){
		super();
		this.sourcePile = sourcePile;
		this.targetFoundation = targetFoundation;
		this.c = c;
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
		game.updateScore(-1);
		return true;
	}

	@Override
	public boolean valid(Solitaire game) {
		if(targetFoundation.empty()) {
			int comparator = 0;
			switch(targetFoundation.getName()) {
			case "SpadesFoundation": comparator = Card.SPADES; break;
			case "HeartsFoundation": comparator = Card.HEARTS; break;
			case "ClubsFoundation": comparator = Card.CLUBS; break;
			case "DiamondsFoundation": comparator = Card.DIAMONDS; break;
			}
			return (c.getRank() == ((FourSeasons)game).getFoundationBaseRank()) && (c.getSuit() == comparator);
		}
		else {
			return !sourcePile.getName().contains("Foundation") &&
						 (targetFoundation.peek().compareTo(c) == -1 ||
						 	targetFoundation.peek().getRank() == 13 && c.isAce()) &&
						 targetFoundation.peek().sameSuit(c);
		}
	}
}

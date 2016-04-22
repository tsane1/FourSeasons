package tsane.controller;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.PileView;
import ks.common.view.Container;
import ks.common.view.Widget;
import tsane.FourSeasons;
import tsane.ToCrossPileMove;
import tsane.ToFoundationMove;

public class PileController extends java.awt.event.MouseAdapter {
	Solitaire fs = null;
	PileView thePileView;

	public PileController(Solitaire s, PileView thePileView) {
		super();
		fs = s;
		this.thePileView = thePileView;
	}

	public void mousePressed(java.awt.event.MouseEvent me) {
		Pile toPile = (Pile) thePileView.getModelElement();
		if(toPile.getName().contains("Foundation")) return;
		if(toPile.empty()) return;
		
		CardView cv = thePileView.getCardViewForTopCard(me);
		Container c = fs.getContainer();

		c.setActiveDraggingObject (cv, me);
		c.setDragSource(thePileView);
		thePileView.redraw();
	}

	public void mouseReleased(java.awt.event.MouseEvent me) {
		Container c = fs.getContainer();
		Widget w = c.getActiveDraggingObject();

		if(w == Container.getNothingBeingDragged()) return;

		CardView cv = (CardView) w;
		Card card = (Card) cv.getModelElement();

		PileView fromPileView = (PileView) c.getDragSource();
		Pile fromPile = (Pile) fromPileView.getModelElement();
		Pile toPile = (Pile) thePileView.getModelElement();
		
		// Try to make the move
		Move m;
		if(toPile.getName().contains("Cross")) {
			m = new ToCrossPileMove(fromPile, card, toPile);
			
			if(m.doMove(fs)) fs.pushMove(m); 
			else fromPileView.returnWidget(cv);
		}
		else if(toPile.getName().contains("Foundation")) {
			m = new ToFoundationMove(fromPile, card, toPile);
			
			if(m.doMove(fs)) fs.pushMove(m);
			else fromPileView.returnWidget(cv);
		}
		else;
		
		fs.refreshWidgets(); 
		c.releaseDraggingObject();
		c.repaint();
	}
}

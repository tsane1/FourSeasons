package tsane.controller;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.PileView;
import ks.common.view.Container;
import ks.common.view.Widget;
import tsane.model.ToCrossPileMove;

public class CrossPileController extends java.awt.event.MouseAdapter {
	protected Solitaire fs = null;
	protected PileView crossPileView;
	
	public CrossPileController(Solitaire s, PileView crossPileView) {
		super();
		fs = s;
		this.crossPileView = crossPileView;
	}
	
	public void mousePressed(java.awt.event.MouseEvent me) {
		CardView cv = crossPileView.getCardViewForTopCard(me);
		Container c = fs.getContainer();
		
    c.setActiveDraggingObject (cv, me);
    c.setDragSource(crossPileView);
    crossPileView.redraw();
	}
		
	public void mouseReleased(java.awt.event.MouseEvent me) {
		Container c = fs.getContainer();
		Widget w = c.getActiveDraggingObject();
		
		if(w == Container.getNothingBeingDragged()) return;
		
		CardView cv = (CardView) w;
		Card card = (Card) cv.getModelElement();
		
		PileView fromPileView = (PileView) c.getDragSource();
		Pile fromPile = (Pile) fromPileView.getModelElement();
		Pile toPile = (Pile) crossPileView.getModelElement();
		
	// Try to make the move
			Move m = new ToCrossPileMove (fromPile, card, toPile);
			if (m.doMove(fs)) fs.pushMove(m);
			else fromPileView.returnWidget(cv);
			
			fs.refreshWidgets(); 
			c.releaseDraggingObject();
			c.repaint();
	}
}

package heineman.klondike;


import java.awt.event.MouseEvent;

import ks.common.model.BuildablePile;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.BuildablePileView;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;
import heineman.Klondike;

/**
 * Controls all actions to do with mouse events over the BuildablePile widget.
 * <p>
 * Creation date: (11/10/01 8:08:51 PM)
 * @author: George T. Heineman (heineman@cs.wpi.edu)
 */
public class BuildablePileController extends java.awt.event.MouseAdapter {

	/** The game that we are partly controlling. */
	protected Klondike theGame;

	/** The src BuildablePileView that initiated the event. */
	protected BuildablePileView src;
	/**
	 * BuildablePileController constructor comment.
	 */
	public BuildablePileController(Klondike theGame, BuildablePileView bpv) {
		super();

		this.theGame = theGame;
		this.src = bpv;
	}
	/**
	 * Try to play the faceup card directly to the foundation.
	 *
	 * @param me java.awt.event.MouseEvent
	 */
	public void mouseClicked(MouseEvent me) {

		if(me.getClickCount() > 1) {

			// Point to our underlying model element.
			BuildablePile theBP = (BuildablePile) src.getModelElement();

			// See if we can move this one card.
			boolean moveMade = false;
			for (int f = 1; f <=4; f++) {
				Pile fp = (Pile) theGame.getModelElement ("foundation" + f);
				Move m = new MoveCardToFoundationMove (theBP, null, fp);
				if (m.doMove(theGame)) {

					// Success! Add this move to our history.
					theGame.pushMove (m);

					moveMade = true;
					theGame.refreshWidgets();
					break;
				}
			}

			if (!moveMade) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				return; // announce our displeasure			
			}
		}
	}
	/**
	 * Coordinate reaction to the beginning of a Drag Event.
	 *
	 * Note: There is no way to differentiate between a press that
	 *       will become part of a double click vs. a click that will
	 *       be held and dragged. Only mouseReleased will be able to 
	 *       help us out with that one.
	 *
	 * Creation date: (10/4/01 6:05:55 PM)
	 * @param pv ks.common.view.PileView
	 * @param me java.awt.event.MouseEvent
	 */
	public void mousePressed(MouseEvent me) {

		// The container manages several critical pieces of information; namely, it
		// is responsible for the draggingObject; in our case, this would be a CardView
		// Widget managing the card we are trying to drag between two piles.
		Container c = theGame.getContainer();

		/** Return if there is no card to be chosen. */
		BuildablePile theBP = (BuildablePile) src.getModelElement();
		if (theBP.count() == 0) {
			return;
		}

		// No Face Up cards means that we must be requesting to flip a card.
		// If we get here, we must have some cards in the BuildablePile
		if (theBP.getNumFaceUp() == 0) {
			Move m = new FlipCardMove (theBP);
			if (m.doMove(theGame)) {
				theGame.pushMove (m);
				theGame.refreshWidgets();
			} else {
				// error in flip card. Not sure what to do
				System.err.println ("BuildablePileController::mousePressed(). Unexpected failure in flip card.");
			}
			return;
		}

		// Get a column of cards to move from the BuildablePileView
		// Note that this method will alter the model for BuildablePileView if the condition is met.
		ColumnView colView = src.getColumnView (me);

		// an invalid selection (either all facedown, or not in faceup region)
		if (colView == null) {
			return;
		}

		// Check conditions
		Column col = (Column) colView.getModelElement();
		if (col == null) {
			System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a ColumnView with no Column.");
			return; // sanity check, but should never happen.
		}

		// verify that Column has desired Klondike Properties to move
		if ((!col.descending()) || (!col.alternatingColors())) {
			theBP.push (col);
			java.awt.Toolkit.getDefaultToolkit().beep();
			return; // announce our displeasure
		}

		// If we get here, then the user has indeed clicked on the top card in the PileView and
		// we are able to now move it on the screen at will. For smooth action, the bounds for the
		// cardView widget reflect the original card location on the screen.
		Widget w = c.getActiveDraggingObject();
		if (w != Container.getNothingBeingDragged()) {
			System.err.println ("BuildablePileController::mousePressed(): Unexpectedly encountered a Dragging Object during a Mouse press.");
			return;
		}

		// Tell container which object is being dragged, and where in that widget the user clicked.
		c.setActiveDraggingObject (colView, me);

		// Tell container which BuildablePileView is the source for this drag event.
		c.setDragSource (src);

		// we simply redraw our source pile to avoid flicker,
		// rather than refreshing all widgets...
		src.redraw();
	}
	/**
	 * Coordinate reaction to the completion of a Drag Event.
	 * <p>
	 * A bit of a challenge to construct the appropriate move, because cards
	 * can be dragged both from the WastePile (as a CardView widget) and the 
	 * BuildablePileView (as a ColumnView widget).
	 * <p>
	 * @param me java.awt.event.MouseEvent
	 */
	public void mouseReleased(MouseEvent me) {
		Container c = theGame.getContainer();

		/** Return if there is no card being dragged chosen. */
		Widget w = c.getActiveDraggingObject();
		if (w == Container.getNothingBeingDragged()) {
			c.releaseDraggingObject();		
			return;
		}

		/** Recover the from BuildablePile OR waste Pile */
		Widget fromWidget = c.getDragSource();
		if (fromWidget == null) {
			System.err.println ("BuildablePileController::mouseReleased(): somehow no dragSource in container.");
			c.releaseDraggingObject();
			return;
		}

		// Determine the To Pile
		BuildablePile toPile = (BuildablePile) src.getModelElement();

		if (fromWidget instanceof BuildablePileView) {
			// Must be a ColumnView widget being dragged.
			ColumnView columnView = (ColumnView) w;
			Column col = (Column) columnView.getModelElement();
			if (col == null) {
				System.err.println ("BuildablePileController::mouseReleased(): somehow ColumnView model element is null.");
				return;
			}

			if (fromWidget == src) {
				toPile.push (col);   // simply put right back where it came from. No move
			} else {
				BuildablePile fromPile = (BuildablePile) fromWidget.getModelElement();
				Move m = new MoveColumnMove (fromPile, toPile, col, col.count());

				if (m.doMove (theGame)) {
					// Successful move! add move to our set of moves
					theGame.pushMove (m);
				} else {
					// Invalid move. Restore to original column. NO MOVE MADE
					fromPile.push (col);
				}
			}		
		} else {
			// Must be from the WastePile
			CardView cardView = (CardView) w;
			Card theCard = (Card) cardView.getModelElement();
			if (theCard == null) {
				System.err.println ("BuildablePileController::mouseReleased(): somehow CardView model element is null.");
				return;
			}

			Pile wastePile = (Pile) fromWidget.getModelElement();
			Move m = new MoveWasteToPileMove (wastePile, theCard, toPile);
			if (m.doMove (theGame)) {
				// Successful move! add move to our set of moves
				theGame.pushMove (m); 
			} else { 
				// Invalid move. Restore to original waste pile. NO MOVE MADE
				wastePile.add (theCard);
			}
		}
// release the dragging object, (container will reset dragSource)
		c.releaseDraggingObject();
		
		c.repaint();
	}
}

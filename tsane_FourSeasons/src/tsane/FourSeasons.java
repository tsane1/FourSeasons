package tsane;

import ks.common.games.Solitaire;
import ks.common.model.*;
import ks.common.view.*;
import ks.launcher.Main;
import ks.client.gamefactory.GameWindow;
import tsane.controller.*;

public class FourSeasons extends Solitaire {
	protected Deck stock;
	protected Pile waste;
	protected Pile clubF, spadeF, heartF, diamondF;
	protected Pile crossLeft, crossMid, crossRight, crossTop, crossBottom;
	protected int foundationBaseRank;	
	
	protected DeckView stockView;
	protected PileView wasteView;
	protected PileView clubFView, spadeFView, heartFView, diamondFView;
	protected PileView crossLeftView, crossMidView, crossRightView, crossTopView, crossBottomView;
	protected IntegerView foundationBaseRankView;
	protected IntegerView scoreView, numLeftView;
	
	public FourSeasons() {
		super();
	}
	
	@Override
	public String getName() {
		return "FourSeasons";
	}
	
	@Override
	public boolean hasWon() {
		return false;
	}

	@Override
	public void initialize() {
		initializeModel(getSeed());
		initializeView();
		initializeControllers();
	}
	
	private void initializeModel(int seed) {
		numLeft = getNumLeft();
		numLeft.setValue(52);
		score = getScore();
		score.setValue(0);
		
		// add to our model a deck, properly shuffled using the seed. 
		stock = new Deck("Stock");
		stock.create(seed);

		waste = new Pile("Waste");
		
		spadeF = new Pile("SpadesFoundation");
		clubF = new Pile("ClubsFoundation");
		heartF = new Pile("HeartsFoundation");
		diamondF = new Pile("DiamondsFoundation");
		
		crossTop = new Pile("TopCross");
		crossLeft = new Pile("LeftCross");
		crossMid = new Pile("MiddleCross");
		crossRight = new Pile("RightCross");
		crossBottom = new Pile("BottomCross");

		// add all to model
		addModelElement(stock);
		addModelElement(waste);
		addModelElement(spadeF);
		addModelElement(clubF);
		addModelElement(heartF);
		addModelElement(diamondF);
		addModelElement(crossTop);
		addModelElement(crossLeft);
		addModelElement(crossMid);
		addModelElement(crossRight);
		addModelElement(crossBottom);
	}
	
	void initializeView() {
		// Get the card artwork to be used. This is needed for the dimensions.
		CardImages ci = getCardImages();

		scoreView = new IntegerView(score);
		scoreView.setBounds(30, 276+2*ci.getHeight(), 100, 60);
		addViewWidget(scoreView);

		numLeftView = new IntegerView(numLeft);
		numLeftView.setBounds(100+ci.getWidth(), 276+2*ci.getHeight(), 100, 60);
		addViewWidget(numLeftView);
		
		stockView = new DeckView(stock);
		stockView.setBounds(30, 200, ci.getWidth(), ci.getHeight());
		addViewWidget(stockView);
		
		wasteView = new PileView(waste);
		wasteView.setBounds(30, 271+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(wasteView);
		
		heartFView = new PileView(heartF);
		heartFView.setBounds(100+ci.getWidth(), 30, ci.getWidth(), ci.getHeight());
		addViewWidget(heartFView);
		
		spadeFView = new PileView(spadeF);
		spadeFView.setBounds(450+2*ci.getWidth(), 30, ci.getWidth(), ci.getHeight());
		addViewWidget(spadeFView);
		
		clubFView = new PileView(clubF);
		clubFView.setBounds(100+ci.getWidth(), 271+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(clubFView);
		
		diamondFView = new PileView(diamondF);
		diamondFView.setBounds(450+2*ci.getWidth(), 271+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(diamondFView);
		
		crossLeftView = new PileView(crossLeft);
		crossLeftView.setBounds(139+2*ci.getWidth(), 87+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossLeftView);
		
		crossMidView = new PileView(crossMid);
		crossMidView.setBounds(159+3*ci.getWidth(), 87+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossMidView);
		
		crossRightView = new PileView(crossRight);
		crossRightView.setBounds(179+4*ci.getWidth(), 87+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossRightView);
		
		crossTopView = new PileView(crossTop);
		crossTopView.setBounds(159+3*ci.getWidth(), 67, ci.getWidth(), ci.getHeight());
		addViewWidget(crossTopView);
		
		crossBottomView = new PileView(crossBottom);
		crossBottomView.setBounds(159+3*ci.getWidth(), 107+2*ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossBottomView);
	}
	
	void initializeControllers() {
		stockView.setMouseAdapter(new DeckController(this));
		
		crossLeftView.setMouseAdapter(new CrossPileController(this, crossLeftView));
		crossMidView.setMouseAdapter(new CrossPileController(this, crossMidView));
		crossRightView.setMouseAdapter(new CrossPileController(this, crossRightView));
		crossTopView.setMouseAdapter(new CrossPileController(this, crossTopView));
		crossBottomView.setMouseAdapter(new CrossPileController(this, crossBottomView));
		
		
	}
	
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		GameWindow gw = Main.generateWindow(new FourSeasons(), 117);
		gw.setVisible(true);
	}
}

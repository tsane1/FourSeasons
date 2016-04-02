package tsane;
import ks.common.games.Solitaire;
import ks.common.model.*;
import ks.common.view.*;
import ks.launcher.Main;
import ks.client.gamefactory.GameWindow;

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
		
		spadeF = new Pile("Spades");
		clubF = new Pile("Clubs");
		heartF = new Pile("Hearts");
		diamondF = new Pile("Diamonds");
		
		crossTop = new Pile("Top");
		crossLeft = new Pile("Left");
		crossMid = new Pile("Middle");
		crossRight = new Pile("Right");
		crossBottom = new Pile("Bottom");

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
		scoreView.setBounds(992+ci.getWidth(), 57, 100, 60);
		addViewWidget(scoreView);

		numLeftView = new IntegerView(numLeft);
		numLeftView.setBounds(992+ci.getWidth(), 140, 100, 60);
		addViewWidget(numLeftView);
		
		stockView = new DeckView(stock);
		stockView.setBounds(30, 200, ci.getWidth(), ci.getHeight());
		addViewWidget(stockView);
		
		wasteView = new PileView(waste);
		wasteView.setBounds(30, 271+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(wasteView);
		
		heartFView = new PileView(heartF);
		heartFView.setBounds(178+ci.getWidth(), 30, ci.getWidth(), ci.getHeight());
		addViewWidget(heartFView);
		
		spadeFView = new PileView(spadeF);
		spadeFView.setBounds(626+2*ci.getWidth(), 30, ci.getWidth(), ci.getHeight());
		addViewWidget(spadeFView);
		
		clubFView = new PileView(clubF);
		clubFView.setBounds(178+ci.getWidth(), 241+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(clubFView);
		
		diamondFView = new PileView(diamondF);
		diamondFView.setBounds(626+2*ci.getWidth(), 241+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(diamondFView);
		
		crossLeftView = new PileView(crossLeft);
		crossLeftView.setBounds(466, 57+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossLeftView);
		
		crossMidView = new PileView(crossMid);
		crossMidView.setBounds(466+ci.getWidth(), 57+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossMidView);
		
		crossRightView = new PileView(crossRight);
		crossRightView.setBounds(466+2*ci.getWidth(), 57+ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossRightView);
		
		crossTopView = new PileView(crossTop);
		crossTopView.setBounds(466+ci.getWidth(), 57, ci.getWidth(), ci.getHeight());
		addViewWidget(crossTopView);
		
		crossBottomView = new PileView(crossBottom);
		crossBottomView.setBounds(466+2*ci.getWidth(), 57+2*ci.getHeight(), ci.getWidth(), ci.getHeight());
		addViewWidget(crossBottomView);
	}
	
	void initializeControllers() {
		
	}
	
	public static void main (String []args) {
		// Seed is to ensure we get the same initial cards every time.
		GameWindow gw = Main.generateWindow(new FourSeasons(), 117);
		gw.setVisible(true);
	}
}

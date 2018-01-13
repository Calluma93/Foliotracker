import FrontendGUI.SwingGUI;
import PortfolioTracker.FolioManager;

public class MainMethod {
	/**
	 * The main method, instantiates PortfolioTracker and FrontendGUI.
	 * Also passes reference of both to the controller to be used in actions
	 */
	public MainMethod() {
		// Create Model/FrontendGUI
		FolioManager p = new FolioManager();

		SwingGUI primaryFrame = new SwingGUI(p);

		// let PortfolioTracker know what FrontendGUI is
		p.addObserver(primaryFrame);
	}

	public static void main(String[] args) {
		new MainMethod();
	}
}

package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;

/**
 * Factory class to generate actions based on user-readable enumerators, for use in the FrontendGUI but
 * holds references to both FrontendGUI and PortfolioTracker.
 */
public final class ActionFactory {

	/**
	 * This is a factory method that provides easy creation of a requested Action without having to
	 * ever directly create an object, aids slightly in decoupling.
	 *
	 * @param action FolioAction enumerator that describes the action the user wishes to create
	 * @return An instance of the requested Action cast to an AbstractAction for use in the SwingGUI
	 */
	public static AbstractAction createAction(FolioAction action, IFolioManagerRestricted model, ISwingGUI view) {
		switch (action) {
			case NEW_FOLIO:
				return new NewFolioAction(action.toString(), action.getMnemonic(), model, view);
			case SAVE_FOLIO:
				return new SaveFolioAction(action.toString(), action.getMnemonic(), model, view);
			case LOAD_FOLIO:
				return new LoadFolioAction(action.toString(), action.getMnemonic(), model, view);
			case DELETE_FOLIO:
				return new DeleteFolioAction(action.toString(), action.getMnemonic(), model, view);
			case BUY_STOCK:
				return new BuyStockAction(action.toString(), action.getMnemonic(), model, view);
			case REMOVE_STOCK:
				return new RemoveStockAction(action.toString(), action.getMnemonic(), model, view);
			case SELL_STOCK:
				return new SellStockAction(action.toString(), action.getMnemonic(), model, view);
			case UPDATE_ALL:
				return new UpdateAllAction(action.toString(), action.getMnemonic(), model, view);
			case EXIT:
				return new ExitProgramAction(action.toString(), action.getMnemonic(), model, view);
			default:
				return null;
		}

	}

}

package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;
import quoteAPI.MethodException;
import quoteAPI.NoSuchTickerException;
import quoteAPI.WebsiteDataException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Action to buy a stock
 */
public class BuyStockAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -745778828983427237L;

	IFolioManagerRestricted model;
	ISwingGUI view;

	/**
	 * Action that represents buying a stock.
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public BuyStockAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
		this.model = model;
		this.view = view;
	}

	/**
	 * Helper function to catch exceptions thrown by the doBuyStock function and return helpful popups.
	 *
	 * @param symbol the ticker symbol of the stock to buy
	 * @param vInt   the volume of stock to buy
	 */
	private void buyWithTry(String symbol, int vInt) {

		try {

			((FolioManager) model).doBuyStock(view.getCurrentlySelectedFolio().getFolioID(), symbol, vInt);

		} catch (IOException e1) {

			JOptionPane.showMessageDialog(null,
					"Uh oh, something has gone badly wrong.");

		} catch (WebsiteDataException e1) {

			JOptionPane.showMessageDialog(null,
					"Could not parse data from server, please try again later.");

		} catch (NoSuchTickerException e1) {

			JOptionPane.showMessageDialog(null,
					"Inputted symbol does not exist on any market, please try again.");

		} catch (MethodException e1) {

			JOptionPane.showMessageDialog(null,
					"Uh oh, something has gone badly wrong.");

		} catch (NumberFormatException e1) {

			JOptionPane.showMessageDialog(null,
					"Inputted symbol does not exist on any market, please try again.");

		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		SwingWorker<Void, Void> buyWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				JTable primaryTable = view.getCurrentlySelectedFolio().getTable();

				int rowC = primaryTable.getSelectedRow();

				String symbol = "";
				String volume = "";


				if (rowC == -1) { // if row not selected init general buy
					symbol = JOptionPane.showInputDialog("Please input the ticker symbol of the stock you want to buy");

					volume = JOptionPane.showInputDialog("Please choose the volume");

				} else {
					symbol = ((String) (primaryTable.getModel()).getValueAt(rowC, 0));

					volume = JOptionPane.showInputDialog("Please choose the volume of " + symbol + " stock you wish to buy!");

				}
				int vInt = 0;

				try {
					int tInt = Integer.valueOf(volume);

					if (tInt > 0) {
						vInt = tInt;
					}

				} catch (NumberFormatException eX) {
					JOptionPane.showMessageDialog(null, "Please ensure your volume is an integer above or equal to 0!");
					return null;
				}


				buyWithTry(symbol, vInt);

				return null;
			}
		};

		buyWorker.execute();


	}

}
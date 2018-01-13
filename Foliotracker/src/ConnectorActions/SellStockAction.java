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
 * Action to sell a stock
 */
public class SellStockAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -7464905042279420275L;

	IFolioManagerRestricted model;
	ISwingGUI view;

	/**
	 * Action that represents selling a stock
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public SellStockAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
		this.model = model;
		this.view = view;
	}

	/**
	 * Helper function to catch exceptions thrown by the doSellStock function and return helpful popups.
	 *
	 * @param symbol the ticker symbol of the stock to sell
	 * @param vInt   the volume of stock to sell
	 */
	private void sellWithTry(String symbol, int vInt) {

		try {

			((FolioManager) model).doSellStock(view.getCurrentlySelectedFolio().getFolioID(), symbol, vInt);

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

		SwingWorker<Void, Void> sellWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {

				JTable primaryTable = view.getCurrentlySelectedFolio().getTable();


				int rowC = primaryTable.getSelectedRow();

				if (rowC == -1) {
					JOptionPane.showMessageDialog(null, "You must select a symbol first!");
				} else {

					String ticker = ((String) (primaryTable.getModel()).getValueAt(rowC, 0));

					String volume = JOptionPane.showInputDialog("Please choose the volume of selected stock to sell.");

					try {
						int vInt = Integer.valueOf(volume);

						if (vInt > 0) {

							sellWithTry(ticker, vInt);
						}

					} catch (NumberFormatException eX) {
						JOptionPane.showMessageDialog(null, "Please ensure your volume is an integer above or equal to 0!");
					}

				}

				return null;
			}
		};

		sellWorker.execute();

	}

}

package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to update all stocks
 */
public class UpdateAllAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -723746041645036008L;

	IFolioManagerRestricted model;
	ISwingGUI view;

	/**
	 * Action that represents updating the stock prices from the server.
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public UpdateAllAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
		this.model = model;
		this.view = view;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		SwingWorker<Void, Void> updateWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {

				((FolioManager) model).doUpdateStocks();

				return null;
			}
		};

		updateWorker.execute();


	}

}

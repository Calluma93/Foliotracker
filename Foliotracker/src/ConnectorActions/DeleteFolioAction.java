package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to delete a stock
 */
public class DeleteFolioAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 4697509418528164966L;

	IFolioManagerRestricted model;
	ISwingGUI view;

	/**
	 * Action that represents deleting a folio.
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public DeleteFolioAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int destroy = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this folio???",
				"Confirm removal.", JOptionPane.YES_NO_OPTION);
		if (destroy == JOptionPane.YES_OPTION) {
			((FolioManager) model).doRemoveFolio(view.getCurrentlySelectedFolio().getFolioID());
		}
	}

}

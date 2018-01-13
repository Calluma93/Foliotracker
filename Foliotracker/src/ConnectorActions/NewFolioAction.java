package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to create a new folio
 */
public class NewFolioAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -974559498745692035L;

	IFolioManagerRestricted model;
	ISwingGUI view;

	/**
	 * Action that represents creating a new folio
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public NewFolioAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String output = JOptionPane.showInputDialog("Choose a name for your folio");
		if (output != null && output.length() > 0) {
			((FolioManager) model).doCreateNewFolio(output);
		}
	}

}
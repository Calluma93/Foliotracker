package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action to exit the program
 */
public class ExitProgramAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 5654470003461673688L;


	/**
	 * Action that represents closing the program.
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public ExitProgramAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit. Any unsaved progress will be lost!");
		if (dialogButton == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

}

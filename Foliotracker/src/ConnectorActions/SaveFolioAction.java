package ConnectorActions;

import FrontendGUI.ISwingGUI;
import FrontendGUI.SwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Action to save a folio
 */
public class SaveFolioAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -3537716804799065254L;

	final JFileChooser fc = new JFileChooser();

	IFolioManagerRestricted model;
	ISwingGUI view;

	FileNameExtensionFilter filter = new FileNameExtensionFilter("Folio File", "folio");

	/**
	 * Action that represents saving an existing folio
	 *
	 * @param text     what the button should be called.
	 * @param mnemonic the mnemonic to be associated with this action
	 */
	public SaveFolioAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);

		fc.setFileFilter(filter);
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		int returnVal = fc.showSaveDialog(((SwingGUI) view));
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				file = new File(file.getCanonicalFile() + ".folio");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (view.getCurrentlySelectedFolio() != null) {
				((FolioManager) model).doSaveFolio(file, view.getCurrentlySelectedFolio().getFolioID());
			}
		}
	}

}
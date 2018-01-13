package ConnectorActions;

import FrontendGUI.ISwingGUI;
import FrontendGUI.SwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadFolioAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 46298644325099173L;

	final JFileChooser fc = new JFileChooser();
	IFolioManagerRestricted model;
	ISwingGUI view;
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Folio File", "folio");

	/**
	 * Action that represents loading an existing folio
	 *
	 * @param text what the button should be called.
	 */
	public LoadFolioAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);

		fc.setFileFilter(filter);
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fc.showOpenDialog(((SwingGUI) view));
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			((FolioManager) model).doLoadFolio(file);
		}
	}

}
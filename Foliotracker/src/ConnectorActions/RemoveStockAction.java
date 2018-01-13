package ConnectorActions;

import FrontendGUI.ISwingGUI;
import PortfolioTracker.FolioManager;
import PortfolioTracker.IFolioManagerRestricted;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveStockAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -6632668303495983994L;

	IFolioManagerRestricted model;
	ISwingGUI view;

	/**
	 * Action that represents removing an existing stock.
	 *
	 * @param text what the button should be called.
	 */
	public RemoveStockAction(String text, Integer mnemonic, IFolioManagerRestricted model, ISwingGUI view) {
		super(text);
		putValue(MNEMONIC_KEY, mnemonic);
		this.model = model;
		this.view = view;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JTable primaryTable = view.getCurrentlySelectedFolio().getTable();


		int rowC = primaryTable.getSelectedRow();

		if (rowC == -1) {
			JOptionPane.showMessageDialog(null, "You must select a symbol first!");
		} else {
			int destroy = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this stock???",
					"Confirm removal.", JOptionPane.YES_NO_OPTION);
			if (destroy == JOptionPane.YES_OPTION) {

				((FolioManager) model).doRemoveStock(view.getCurrentlySelectedFolio().getFolioID(), ((String) (primaryTable.getModel()).getValueAt(rowC, 0)));

			}
		}
	}

}
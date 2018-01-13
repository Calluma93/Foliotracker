package FrontendGUI;

import ConnectorActions.ActionFactory;
import ConnectorActions.FolioAction;
import PortfolioTracker.IFolioManagerRestricted;
import PortfolioTracker.IFolioRestricted;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Folio panel is a representation of the Folio object in the form of a table and labels.
 */
public class folioPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 4668226594055286694L;
	// Folio boundFolio;
	private int fID;
	private JTable primaryTable;
	private folioTableModel tModel;
	private JLabel totalValueNum;
	private JLabel totalProfitNum;

	/**
	 * Primary constructor, initialises main scheme of the GUI including layout, buttons and sets certain actions
	 *
	 * @param fID The folioid associated with this panel
	 * @param t   The tablemodel object responsible for managing the jtables values
	 */
	public folioPanel(int fID, folioTableModel t, IFolioManagerRestricted model, ISwingGUI view) {
		this.fID = fID;
		this.tModel = t;

		setLayout(new BorderLayout());

		primaryTable = new JTable(tModel);
		primaryTable.setFillsViewportHeight(true);
		primaryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		primaryTable.getColumnModel().getColumn(4).setCellRenderer(new ProfitColRenderer());
		primaryTable.setAutoCreateRowSorter(true);


		add(new JScrollPane(primaryTable), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		JPanel bottomPanelButtons = new JPanel(new FlowLayout());
		JPanel bottomPanelText = new JPanel(new BorderLayout());

		JButton buyStock = new JButton();
		buyStock.setAction(ActionFactory.createAction(FolioAction.BUY_STOCK, model, view));

		JButton sellStock = new JButton();
		sellStock.setAction(ActionFactory.createAction(FolioAction.SELL_STOCK, model, view));

		JButton removeStock = new JButton();
		removeStock.setAction(ActionFactory.createAction(FolioAction.REMOVE_STOCK, model, view));

		JButton removeFolio = new JButton();
		removeFolio.setAction(ActionFactory.createAction(FolioAction.DELETE_FOLIO, model, view));


		bottomPanelButtons.add(buyStock);
		bottomPanelButtons.add(sellStock);
		bottomPanelButtons.add(removeStock);
		bottomPanelButtons.add(removeFolio);

		// PANEL TO HOLD FOLIO VALUATION

		JPanel vPan = new JPanel(new FlowLayout());

		JLabel totalValue = new JLabel("Current Folio Valuation: ");
		totalValueNum = new JLabel("0.0");

		vPan.add(totalValue);
		vPan.add(totalValueNum);

		// PANEL TO HOLD PROFIT SUM

		JPanel pPan = new JPanel(new FlowLayout());

		JLabel totalProfit = new JLabel("Current Folio Profit: ");
		totalProfitNum = new JLabel("0.0");

		pPan.add(totalProfit);
		pPan.add(totalProfitNum);

		bottomPanelText.add(vPan, BorderLayout.NORTH);
		bottomPanelText.add(pPan, BorderLayout.SOUTH);

		bottomPanel.add(bottomPanelText, BorderLayout.WEST);
		bottomPanel.add(bottomPanelButtons, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);

	}

	/**
	 * Gets the folio id associated with this panel
	 *
	 * @return the folio id
	 */
	public int getFolioID() {
		return fID;
	}

	/**
	 * Gets the JTable stored in this object that displays the list of stocks
	 *
	 * @return The jtable of stocks
	 */
	public JTable getTable() {
		return primaryTable;
	}


	/**
	 * Passthrough method that tells the table PortfolioTracker to update itself with whatever it can pull from the PortfolioTracker
	 * Also updates the profit and value labels.
	 */
	public void notifyUpdated() {
		tModel.notifyUpdated();

		IFolioRestricted folio = tModel.getBoundFolio();

		DecimalFormat df = new DecimalFormat("0.###");

		totalValueNum.setText(df.format(folio.getFolioValue()));

		totalProfitNum.setText(df.format(folio.getFolioProfit()));

	}

}
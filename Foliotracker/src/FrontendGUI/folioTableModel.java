package FrontendGUI;

import PortfolioTracker.IFolioRestricted;
import PortfolioTracker.IStockRestricted;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Table PortfolioTracker that manages accessing PortfolioTracker objects through the restricted interfaces to update what the jtable
 * actually shows
 */
public class folioTableModel extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -3578840905007285403L;

	private String[] columnNames = {"Ticker Symbol", "Current Price", "Number of shares", "Stock Value",
			"Profit/Loss"};

	private Map<String, IStockRestricted> data;

	private IFolioRestricted boundFolio;

	/**
	 * The PortfolioTracker constructor
	 *
	 * @param modelFolio restricted reference to the folio associated with this PortfolioTracker.
	 */
	public folioTableModel(IFolioRestricted modelFolio) {
		boundFolio = modelFolio;
		data = new HashMap<>();
	}

	/**
	 * Gets the restricted folio object associated with this PortfolioTracker
	 *
	 * @return the restricted folio object
	 */
	public IFolioRestricted getBoundFolio() {
		return boundFolio;
	}

	/**
	 * Function to let the tablemodel to pull new data from the actual PortfolioTracker becuase something happened.
	 * Handles logic to figure out if it was an addition, removal or update.
	 */
	public void notifyUpdated() { // something happened but who knows what eh!!

		Map<String, IStockRestricted> cList = boundFolio.getStockList();

		if (cList.size() > getRowCount()) { // new stock

			int i = 0;
			for (Entry<String, IStockRestricted> e : cList.entrySet()) {

				IStockRestricted s = e.getValue();

				if (!data.containsKey(s.getTicker())) {
					data.put(s.getTicker(), s);
					this.fireTableRowsInserted(i, i);

				}

				i++;
			}

		} else if (cList.size() < getRowCount()) { // delete stock

			Map<String, IStockRestricted> concurCopy = new ConcurrentHashMap<>(data);

			int i = 0;
			for (Entry<String, IStockRestricted> e : concurCopy.entrySet()) { // thar
				// be
				if (boundFolio.getStock(e.getKey()) == null) {
					data.remove(e.getKey());

					this.fireTableRowsDeleted(i, i);
				}
			}

			i++;

		} else { // do an update of everything

			for (Entry<String, IStockRestricted> e : cList.entrySet()) {

				IStockRestricted s = e.getValue();

				if (data.containsKey(s.getTicker())) { // it should anyway
					data.put(s.getTicker(), s);

				}
			}

			if (cList.size() != 0) {

				this.fireTableRowsUpdated(0, cList.size() - 1);
			}
		}

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {

		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {

		int i = 0;

		for (Entry<String, IStockRestricted> e : data.entrySet()) {
			if (i == row) {

				IStockRestricted sVal = e.getValue();

				if (col == 0) { // TICKER SYMBOL
					return sVal.getTicker();
				} else if (col == 1) { // CURRENT PRICE
					return sVal.getPrice();
				} else if (col == 2) { // NUM OF SHARES
					return sVal.getVolume();
				} else if (col == 3) { // STOCK VALUE
					return sVal.getValue();
				} else if (col == 4) { // PROFIT/LOSS
					return sVal.getProfitLoss();
				}

			}
			i++;
		}
		return "";
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}


	@Override
	public Class<?> getColumnClass(int col) {
		if (col == 0) { // TICKER SYMBOL
			return String.class;
		} else if (col == 1) { // CURRENT PRICE
			return Double.class;
		} else if (col == 2) { // NUM OF SHARES
			return Integer.class;
		} else if (col == 3) { // STOCK VALUE
			return Double.class;
		} else if (col == 4) { // PROFIT/LOSS
			return Double.class;
		}

		return String.class;
	}
}
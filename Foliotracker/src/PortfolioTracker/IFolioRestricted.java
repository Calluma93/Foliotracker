package PortfolioTracker;

import java.util.Map;

/**
 * Partial Folio interface consisting of getters for View to use.
 */
public interface IFolioRestricted {

	/**
	 * Returns the profit of all the stocks stored in this folio added together
	 *
	 * @return folio profit
	 */
	double getFolioProfit();

	/**
	 * Returns the value of all the stocks stored in this folio added together
	 *
	 * @return folio value
	 */
	double getFolioValue();

	/**
	 * Returns the id of the folio object.
	 *
	 * @return the folio id
	 */
	int getID();

	/**
	 * Gets the Name assigned to the Folio
	 *
	 * @return the name of the folio.
	 */
	String getName();

	/**
	 * Gets a stock from the folio
	 *
	 * @param ticker the ID of the we want
	 * @return the Stock object we want
	 */
	IStockRestricted getStock(String ticker);

	/**
	 * Gets a list of ID for every stock in the folio
	 *
	 * @return list of Stock ID's
	 */
	Map<String, IStockRestricted> getStockList();

	/**
	 * @param ticker ticker of the stock which price should be returned.
	 * @return the price of the stock. If stock is not the set of stocks, then
	 * -1 returned.
	 */
	double getStockPrice(String ticker);

	/**
	 * Gets the quantity of a Stock held in the folio
	 *
	 * @param ticker the ID of the stock in question
	 * @return the quantity of the stock
	 */
	int getStockVolume(String ticker);

}

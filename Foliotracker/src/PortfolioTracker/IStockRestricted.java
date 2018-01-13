package PortfolioTracker;

/**
 * A restricted interface to only expose getters for use in the FrontendGUI
 */
public interface IStockRestricted {


	/**
	 * Gets the current price of the stock
	 *
	 * @return the current price of the stock
	 */
	double getPrice();

	/**
	 * Gets the current profit/loss recorded by the stock
	 *
	 * @return the profit/loss
	 */
	double getProfitLoss();

	/**
	 * Gets the ticker of the stock
	 *
	 * @return the Ticker
	 */
	String getTicker();

	/**
	 * Gets the current value of the stock
	 *
	 * @return the current value of the stock
	 */
	double getValue();

	/**
	 * Gets the current volume of the stock
	 *
	 * @return the current volume of the stock
	 */
	int getVolume();

}

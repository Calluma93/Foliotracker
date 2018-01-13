package PortfolioTracker;

import quoteAPI.MethodException;
import quoteAPI.NoSuchTickerException;
import quoteAPI.WebsiteDataException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Holds a collection of stocks to represent a portfolio
 *
 * @author Team 6
 */
public class Folio implements IFolioRestricted, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1546938311740470972L;

	private int folioID;
	private String name;
	private Map<String, Stock> stockList;

	public Folio(String name, int folioID) {
		this.name = name;
		this.folioID = folioID;
		this.stockList = new HashMap<>();

	}

	/**
	 * Adds a stock to the folio
	 *
	 * @param tickerSymbol    ticker symbol of stock which should be added
	 * @param amount        volume of stock the user wishes to add
	 * @throws NoSuchTickerException
	 * @throws WebsiteDataException
	 * @throws IOException
	 * @throws MethodException
	 */

	public void addStock(String tickerSymbol, int amount) throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {
		Stock newStock = new Stock(tickerSymbol, amount);
		if (newStock != null)
			stockList.put(tickerSymbol, newStock);

	}

	/**
	 * Removes a Stock from the folio
	 *
	 * @param ticker ticker symbol of the stock which should be removed
	 * @return true if stock removed successfully, false otherwise.
	 */
	public boolean removeStock(String ticker) {
		if (stockList.containsKey(ticker)) {
			stockList.remove(ticker);

			return true;
		}

		return false;
	}


	/**
	 * Manually sets the id of the current stock in the case of loading conflicts
	 * @param id the id to change to
	 */
	public void changeID(int id) {
		folioID = id;
	}

	@Override
	public IStockRestricted getStock(String ticker) {
		if (stockList.containsKey(ticker)) {
			return stockList.get(ticker);
		}

		return null;

	}

	@Override
	public Map<String, IStockRestricted> getStockList() {
		return new HashMap<>(stockList);
	}

	@Override
	public double getStockPrice(String ticker) {
		if (stockList.containsKey(ticker)) {
			return stockList.get(ticker).getPrice();
		}

		return -1;
	}

	@Override
	public int getStockVolume(String ticker) {
		if (stockList.containsKey(ticker)) {
			return stockList.get(ticker).getVolume();
		}

		return -1;
	}

	@Override
	public double getFolioProfit() {
		double fSum = 0;

		for (Entry<String, Stock> e : stockList.entrySet()) {
			fSum += e.getValue().getProfitLoss();
		}

		return fSum;
	}

	@Override
	public double getFolioValue() {
		double fSum = 0;

		for (Entry<String, Stock> e : stockList.entrySet()) {
			fSum += e.getValue().getValue();
		}

		return fSum;
	}

	@Override
	public int getID() {
		return folioID;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the folio
	 *
	 * @param name preferred name of folio.
	 */
	public void setName(String name) {
		this.name = name;
	}


}
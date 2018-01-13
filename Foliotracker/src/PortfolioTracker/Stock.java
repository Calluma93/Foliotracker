package PortfolioTracker;

import quoteAPI.*;

import java.io.IOException;
import java.io.Serializable;

/**
 * Stock class representing the holding of a stock position, stores held volume, current price, ticker name
 * handles updating within the object using the IQuote class. Also tracks a very basic profit/loss count
 */
public class Stock implements IStockRestricted, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -542368537712752389L;
	private String tickerSymbol;

	private int stockVolume;
	private double stockPrice;
	private double profitLossCount;

	private IQuote stockFetcher;

	/**
	 * Stock constructor
	 *
	 * @param tickerSymbol the ticker symbol
	 * @param noShares     the number of shares we with to initialise with
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 */
	public Stock(String tickerSymbol, int noShares) throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {

		this.stockVolume = noShares;

		this.stockFetcher = new Quote(false);

		this.stockFetcher.setValues(tickerSymbol);
		this.tickerSymbol = stockFetcher.getTicker();

		updatePrice();

		profitLossCount = (stockVolume * stockPrice);
	}

	/**
	 * Updates the price of the stock with the latest price from the server
	 *
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 */
	public void updatePrice() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {

		stockFetcher.setValues(tickerSymbol);


		stockPrice = stockFetcher.getLatest();

	}

	/**
	 * Purchases vol units of this stock, updates the price, profit and volume.
	 * @param vol    The volume of stock we wish to buy
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 */
	public void buyVolume(int vol) throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {

		updatePrice();

		profitLossCount += (vol * stockPrice);

		stockVolume += vol;


	}

	/**
	 * Sells vol units of this stock, updates the price, profit and volume
	 * @param vol    The volume of stock we wish to sell
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 */
	public void sellVolume(int vol) throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {

		updatePrice();

		if (stockVolume - vol < 0) {
			vol = stockVolume;
		}

		profitLossCount -= (vol * stockPrice);

		stockVolume -= vol;

	}

	@Override
	public double getValue() {
		return stockVolume * stockPrice;
	}


	// Restricted methods below

	@Override
	public int getVolume() {
		return stockVolume;
	}

	@Override
	public double getPrice() {
		return stockPrice;
	}

	@Override
	public double getProfitLoss() {
		return (getValue() - profitLossCount);
	}

	@Override
	public String getTicker() {
		return tickerSymbol;
	}

	/**
	 * Sets the stock to a new ticker symbol
	 *
	 * @param symbol the string with which you want to replace ticker.
	 */
	public void setTicker(String symbol) {
		tickerSymbol = symbol;
	}

	@Override
	public String toString() {
		return "ticker: " + getTicker()
				+ " | " + "price: " + getPrice() + " | " + "amount: " +
				getVolume() + " | " + "value: " + getValue(); }


}
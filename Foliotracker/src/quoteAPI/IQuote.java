package quoteAPI;

import java.io.IOException;

/**
 * public interface that allows the setting of and retrieval of share stock
 * values
 *
 * @author mark meiklejohn
 * @version v1 - interface added to complement <code>Quote</code> class
 *          <p>
 *          All rights reserved. Copyright: 21 Nov 2008 15:06:18
 */
public interface IQuote {

	/**
	 * This method returns the change value of the share, if the share value has
	 * risen then it will return a value of +x.xx, however if the share value
	 * has fallen it will return -x.xx
	 *
	 * @throws MethodException
	 * @effects Double change: if change == null throw MethodException else
	 * returns the change value of the share
	 */
	Double getChange() throws MethodException;

	/**
	 * This method returns the date of the share values in format mm/dd/yy
	 *
	 * @throws MethodException
	 * @effects Double date: if date == null throw MethodException else returns
	 * the date of the share values
	 */
	String getDate() throws MethodException;

	/**
	 * This method returns the latest stock price
	 *
	 * @throws MethodException
	 * @effects Double latestValue: if latestValue == null throw MethodException
	 * else return the latest stock value
	 */
	Double getLatest() throws MethodException;

	/**
	 * This method returns the days opening share prive
	 *
	 * @throws MethodException
	 * @effects Double open: if open == null throw MethodException else returns
	 * the days opening share price
	 */
	Double getOpen() throws MethodException;

	/**
	 * This method returns the days range maximum value
	 *
	 * @throws MethodException
	 * @effects Double rangeMax: if rangeMax == null throw MethodException else
	 * returns the days range maximum value
	 */
	Double getRangeMax() throws MethodException;

	/**
	 * This method returns the days range minimum value
	 *
	 * @throws MethodException
	 * @effects Double rangeMin: if rangeMin == null throw Method Exception else
	 * returns the days range minimum value
	 */
	Double getRangeMin() throws MethodException;

	/**
	 * This method returns the value of the ticker
	 *
	 * @throws MethodException
	 * @effects String ticker: if ticker == null throws MethodException else
	 * returns the ticker symbol for the company
	 */
	String getTicker() throws MethodException;

	/**
	 * This Double returns the time of the latest share transactions in format
	 * HH:MM:AM/PM
	 *
	 * @throws MethodException
	 * @effects String time: if time == null throw MethodException else returns
	 * the time of the latest share transaction
	 */
	String getTime() throws MethodException;

	/**
	 * This method returns the volume of shares available
	 *
	 * @throws MethodException
	 * @effects Double volume: if volume == null throw MethodException else
	 * returns the volume of the shares available
	 */
	Double getVolume() throws MethodException;

	/**
	 * This method gets the web page containing the share price information and
	 * sets up the data for retrieval
	 *
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 * @throws MethodException
	 * @requires: tickerSymbol != null || tickerSymbol != ""
	 */
	void setValues(String tickerSymbol)
			throws IOException, WebsiteDataException, NoSuchTickerException, MethodException;

}/// :~

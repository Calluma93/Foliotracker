package quoteAPI;
///:Quote.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Quote implements IQuote, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -516377107793550778L;
	/**
	 * Quote is a utility class that allows calling code to retrieve the latest
	 * market value of a given stock by ticker symbol. The Quote obtains the
	 * stock value by using the website, and opens a .csv (comma separated
	 * values) file: <a href="http://finance.yahoo.com">
	 * <tt>http://finance.yahoo.com/d/quotes.csv?s</tt></a> <Br>
	 * Examples of ticker symbols are <tt>MSFT</tt> and <tt>orcl</tt>. (Note
	 * that ticker symbols are not case sensitive. That is that "MSFT" and
	 * "msft" are functionally equivalent.)
	 * <p>
	 * <p>
	 * A valid ticker symbol is one that is currently registered with any world
	 * market
	 */
	private List<String> _shareDataList;
	private boolean _useProxy;

	/**
	 * Default Constructor
	 *
	 * @param useProxy true if you want to use the university proxy server else false
	 *                 not to.
	 */
	public Quote(boolean useProxy) {
		_useProxy = useProxy;
	}// end of default constructor

	// --------------------------- MAIN METHOD FOR TESTING -------------------
	public static void main(String[] args) {

		Quote q = new Quote(false);

		try {

			// pass in ticker to get the new values works for worldwide markets
			// note '.l' for UK shares
			q.setValues("hbos.l");

		} catch (Exception e) {

			System.err.println(e);

		} // end try catch

		// test to see that it works
		try {

			System.out.println("ticker: " + q.getTicker());

			System.out.println("latest value: " + q.getLatest());

			System.out.println("date: " + q.getDate());

			System.out.println("time: " + q.getTime());

			System.out.println("change: " + q.getChange());

			System.out.println("day range max: " + q.getRangeMax());

			System.out.println("day range min: " + q.getRangeMin());

			System.out.println("open: " + q.getOpen());

			System.out.println("volume: " + q.getVolume());

		} catch (MethodException e) {

			e.printStackTrace();
		} // end try catch
	}// end of main

	/**
	 * Method removePunc: removes all characters we dont't want in our string
	 *
	 * @throws MethodException
	 * @requires String str != null
	 * @effects if str == null throws method exception else returns a new string
	 * with the characters removed that we don't want
	 * @modifies str
	 */
	private static String removePunc(String str) throws MethodException {
		// use assertion to ensure not parameter value is != null
		assert str != null : "str == null";

		// handle if str == null
		if (str == null)
			throw new MethodException(str);

		// Strip out the characters we don't want namely " and +
		return Pattern.compile("[\"]").matcher(str).replaceAll("");
	}// end of removePunc

	private boolean ensureNotNull(String str) throws MethodException {
		if (str == null)
			throw new MethodException(str);
		else
			return true;
	}// :end ensureNotNull

	/**
	 * This method returns the change value of the share, if the share value has
	 * risen then it will return a value of +x.xx, however if the share value
	 * has fallen it will return x.xx
	 *
	 * @throws MethodException
	 * @effects String change: if change == null throw MethodException else
	 * returns the change value of the share
	 */
	@Override
	public Double getChange() throws MethodException {
		return ensureNotNull(_shareDataList.get(4)) ? Double.valueOf(_shareDataList.get(4)) : null;
	}// end of getChange

	/**
	 * This method returns the date of the share values in format mm/dd/yy
	 *
	 * @throws MethodException
	 * @effects String date: if date == null throw MethodException else returns
	 * the date of the share values
	 */
	@Override
	public String getDate() throws MethodException {
		return ensureNotNull(_shareDataList.get(2)) ? _shareDataList.get(2) : null;
	}// end of getDate

	/**
	 * This method returns the latest stock price
	 *
	 * @throws MethodException
	 * @effects String latestValue: if latestValue == null throw MethodException
	 * else returns the latest stock value
	 */
	@Override
	public Double getLatest() throws MethodException {
		return ensureNotNull(_shareDataList.get(1)) ? Double.valueOf(_shareDataList.get(1)) : null;
	}// end of getLatest

	/**
	 * This method returns the days opening share prive
	 *
	 * @throws MethodException
	 * @effects String open: if open == null throw MethodException else returns
	 * the days opening share price
	 */
	@Override
	public Double getOpen() throws MethodException {
		return ensureNotNull(_shareDataList.get(5)) ? Double.valueOf(_shareDataList.get(5)) : null;
	}// end of getOpen

	/**
	 * This method returns the days range maximum value
	 *
	 * @throws MethodException
	 * @effects String rangeMax: if rangeMax == null throw MethodException else
	 * returns the days range maximum value
	 */
	@Override
	public Double getRangeMax() throws MethodException {
		return ensureNotNull(_shareDataList.get(6)) ? Double.valueOf(_shareDataList.get(6)) : null;
	}// end of getRangeMax

	/**
	 * This method returns the days range minimum value
	 *
	 * @throws MethodException
	 * @effects String rangeMin: if rangeMin == null throw Method Exception else
	 * returns the days range minimum value
	 */
	@Override
	public Double getRangeMin() throws MethodException {
		return ensureNotNull(_shareDataList.get(7)) ? Double.valueOf(_shareDataList.get(7)) : null;
	}// end of getRangeMin

	/**
	 * This method returns the value of the ticker
	 *
	 * @throws MethodException
	 * @effects String ticker: if ticker == null throws MethodException else
	 * returns the ticker symbol for the company
	 */
	@Override
	public String getTicker() throws MethodException {
		return ensureNotNull(_shareDataList.get(0)) ? _shareDataList.get(0) : null;
	}// end of getTicker

	// -------------------- Private Methods --------------------

	/**
	 * This method returns the time of the latest share transactions in format
	 * HH:MM:AM/PM
	 *
	 * @throws MethodException
	 * @effects String time: if time == null throw MethodException else returns
	 * the time of the latest share transaction
	 */
	@Override
	public String getTime() throws MethodException {
		return ensureNotNull(_shareDataList.get(3)) ? _shareDataList.get(3) : null;
	}// end of getTime

	/**
	 * This method returns the voulme of shares available
	 *
	 * @throws MethodException
	 * @effects String volume: if volume == null throw MethodException else
	 * returns the volume of the shares avaliable
	 */
	@Override
	public Double getVolume() throws MethodException {
		return ensureNotNull(_shareDataList.get(8)) ? Double.valueOf(_shareDataList.get(8)) : null;
	}// end of getVolume

	/**
	 * This method gets the web page containing the share price information and
	 * sets up the data for reterival
	 *
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 * @throws MethodException
	 * @requires: tickerSymbol != null || tickerSymbol != ""
	 */
	@Override
	public void setValues(String tickerSymbol)
			throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {

		String url = "http://finance.yahoo.com/d/quotes.csv?s=";
		String fileFormat = "&f=sl1d1t1c1ohgv&e=.csv";

		if (tickerSymbol == null || tickerSymbol.equals("")) {

			throw new NoSuchTickerException(tickerSymbol);

		} else {

			BufferedReader webPage = null;

			String str = null;

			try {
				if (_useProxy) {// University proxy settings
					System.getProperties().put("proxySet", "true");

					System.getProperties().put("proxyHost", "www-cache2.strath.ac.uk");

					System.getProperties().put("proxyPort", "8080");
				} // end if

				// open web page, read the file, and buffer it for speed
				webPage = new BufferedReader(
						new InputStreamReader(new URL(url + tickerSymbol + fileFormat).openStream()));

			} catch (Exception e) {
				throw new WebsiteDataException(url);
			} // end try catch

			// read the line from the file
			try {
				str = webPage.readLine().toString();
			} catch (Exception e) {
				throw new NoSuchTickerException(tickerSymbol);
			}
			// strip the file of any punctuation
			String s = removePunc(str);

			// add the elements to an array with , as a deliminator
			_shareDataList = Arrays.asList(s.split(","));

			// close the file.
			webPage.close();

			// catches a non-existent share.
			// since the latest value on a non-existent will always be zero
			if (_shareDataList.size() == 1 || _shareDataList.get(1).equalsIgnoreCase("0.00"))
				throw new NoSuchTickerException(_shareDataList.get(0));
		} // end if else

	}// end of getLastValue

}/// :~

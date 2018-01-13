/*import PortfolioTracker.Folio;
import PortfolioTracker.FolioManager;
import PortfolioTracker.Stock;
import org.junit.Before;
import org.junit.Test;
import quoteAPI.MethodException;
import quoteAPI.NoSuchTickerException;
import quoteAPI.WebsiteDataException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestCases {


	FolioManager h = new FolioManager();

	Folio f = new Folio("A", 0);
	Folio f2 = new Folio("B", 1);

	Stock addStock;
	Stock s;


	@Before
	public void setUp() {

		try {
			s = new Stock("A", 1);
		} catch (MethodException | IOException | WebsiteDataException | NoSuchTickerException e) {
			// TODO Auto-generated catch block
		}


	}

	*//**
	 * Assert that the set ticker is changed and now equals the new one.
	 *//*
	@Test
	public void testSetTicket() {

		s.setTicker("B");
		assertEquals("B", s.getTicker());

	}

	*//**
	 * Assert that the hashcode is greater than zero and not negative.
	 *//*
	@Test
	public void testHash() {
		assertTrue(s.hashCode() >= 0 && !(s.hashCode() < 0));
	}

	*//**
	 * Assert that the Profit Loss of a sock while no stocks have been sold
	 * remains zero
	 *//*
	@Test
	public void testGetProfitLoss() {
		assertTrue(s.getProfitLoss() == 0.0);

	}

	*//**
	 * buy stocks and ensure that the volume of the stocks changes accordingly
	 *
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 *//*
	@Test
	public void testSellStock() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {
		s.buyVolume(10);
		s.sellVolume(1);
		assertEquals(10, s.getVolume());
		s.sellVolume(11);
		assertEquals(0, s.getVolume());

	}

	*//**
	 * Ensure that the string length of the toString method equals the number
	 * of characters of the stock.
	 *//*
	@Test
	public void testtoStringStock() {
		System.out.println("sssss: " + s.toString().length());
		assertEquals(51, s.toString().length());

	}

	//////////////////FOLIO//////////////////////////////

	*//**
	 * Test the getting and setters of the folio class name.
	 *//*
	@Test
	public void testIDandNameFolio() {
		// Getters//
		assertEquals(0, f.getID());
		assertEquals("A", f.getName());

		// Setters//
		f.setName("B");
		assertEquals("B", f.getName());
	}

	*//**
	 * Ensure that the stock price
	 *
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 *//*
	@Test
	public void testGetStockPrice() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {
		f.addStock("A", 1);
		assertTrue(f.getStockPrice("A") >= 0 && !(f.getStockPrice("A") < 0));
	}


	*//**
	 * Assert true that a stock is removed from a folio.
	 *
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 *//*
	@Test
	public void testRemoveStock() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {

		f.addStock("B", 10);

		assertTrue(f.removeStock("B"));
		assertFalse(f.removeStock("D"));

	}


	*//**
	 * Ensure the correct number of shares is returned by the getter StockVolume.
	 *
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 *//*
	@Test
	public void testGetStockVolume() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {
		f.addStock("A", 1);
		assertEquals(-1, f.getStockVolume("D"));
		assertTrue(f.getStockVolume("A") == 1);
	}

	*//**
	 * Assert true that a folio value will always be zero or higher and never negative.
	 *
	 * @throws MethodException
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 *//*
	@Test
	public void testGetFolioValue() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {
		f.addStock("A", 1);
		assertTrue(f.getFolioValue() >= 0 && !(f.getFolioValue() < 0));

	}

	*//**
	 * assert that when no shares have been sold the folio profit will remain at 0.
	 *//*
	@Test
	public void testGetFolioProfit() throws MethodException, IOException, WebsiteDataException, NoSuchTickerException {
		f.addStock("A", 1);

		assertTrue(f.getFolioProfit() == 0.0);
		assertFalse(f.getFolioProfit() == 3.0);

	}

	//////////////// BackEnd Handler //////////////////////////

	*//**
	 * Assert that a new folio is created and exists.
	 *//*
	@Test
	public void testCreateNewFolio() {
		h.doCreateNewFolio("a");
		assertEquals("a", h.getAllFolios().get(0).getName());

	}

	*//**
	 * Assert that you can remove a folio and number of folios will decrease by 1
	 *//*
	@Test
	public void testRemoveFolio() {
		h.doCreateNewFolio("A");
		h.doCreateNewFolio("B");

		h.doRemoveFolio(0);
		assertEquals(1, h.getAllFolios().size());


	}

	*//**
	 * Assert you can buy stock and are able to access the newly created objects
	 *
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 * @throws MethodException
	 *//*
	@Test
	public void testBuyStock() throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {
		h.doCreateNewFolio("a");

		h.doBuyStock(0, "A", 10);
		h.doBuyStock(0, "B", 5);
		h.doBuyStock(0, "B", 1);

		assertEquals("A", h.getAllFolios().get(0).getStockList().get("A").getTicker());


	}

	*//**
	 * Assert you can sell stock shares and the volume of share will decrease accordling.
	 * also will never become zero.
	 *
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 * @throws MethodException
	 *//*
	@Test
	public void testSellStockHandler()
			throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {
		h.doCreateNewFolio("a");
		h.doBuyStock(0, "A", 10);
		h.doSellStock(0, "A", 10);
		h.doSellStock(0, "A", 1);

		assertEquals(0, h.getAllFolios().get(0).getStockList().get("A").getVolume());
		assertEquals("A", h.getAllFolios().get(0).getStockList().get("A").getTicker());

	}

	*//**
	 * Assert the stocks can be fully removed and the size of the total number of stocks
	 * will decrease by 1.
	 *
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 * @throws MethodException
	 *//*
	@Test
	public void testRemoveStock2() throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {

		h.doCreateNewFolio("a");
		h.doBuyStock(0, "A", 10);
		h.doRemoveStock(0, "A");

		assertEquals(0, h.getAllFolios().get(0).getStockList().size());
		assertEquals(null, h.getAllFolios().get(0).getStockList().get("B"));
	}


	*//**
	 * Assert that the folio arraylist is not empty when new folios are creates
	 *//*
	@Test
	public void testGetAllFolios() {
		h.doCreateNewFolio("a");
		h.doCreateNewFolio("b");

		assertTrue(!h.getAllFolios().isEmpty());

	}

	*//**
	 * Assert the when the updateStock method is called stocks are refreshed.
	 *
	 * @throws MethodException
	 *//*
	@Test
	public void testUpdatePrice() throws MethodException {

		h.doCreateNewFolio("A");
		try {
			h.doBuyStock(0, "A", 10);
		} catch (IOException | WebsiteDataException | NoSuchTickerException | MethodException e) {
		}

		h.doUpdateStocks();

		assertEquals("A", h.getAllFolios().get(0).getStockList().get("A").getTicker());

	}

	*//**
	 * Create a folio and a stock. Assert that you can save said folio to a file and compare there
	 * byte streams to ensure they are the same.
	 *
	 * @throws IOException
	 * @throws WebsiteDataException
	 * @throws NoSuchTickerException
	 * @throws MethodException
	 *//*
	@Test
	public void testSaveFolio() throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {
		h.doCreateNewFolio("a");
		h.doBuyStock(0, "A", 10);
		File fileZ = new File("abc");
		File fileX = new File("cba");

		h.doSaveFolio(fileZ, 0);
		h.doSaveFolio(fileX, 0);

		FileInputStream fs = new FileInputStream(fileZ);
		FileInputStream fs2 = new FileInputStream(fileX);

		assertEquals(fs.read(), fs2.read());


	}


	*//**
	 * Load a pre-saved folio and compare them to ensure the load has been successful.
	 *//*
	@Test
	public void testLoadFolio() {

		File fileA = new File("Folio.folio");
		assertTrue(fileA.exists());
		File fileB = new File("FolioB.folio");
		File fileC = new File("test.ser");
		File fileD = new File("save.ser");
		File fileE = new File("");


		h.doLoadFolio(fileA);
		h.doLoadFolio(fileA);
		h.doLoadFolio(fileB);
		h.doLoadFolio(fileC);
		h.doLoadFolio(fileD);
		h.doLoadFolio(fileE);

		assertEquals(h.getAllFolios().get(0).getName(), h.getAllFolios().get(1).getName());
		assertEquals(h.getAllFolios().get(0).getStockList().get("A").getTicker(), h.getAllFolios().get(1).getStockList().get("A").getTicker());


	}

}
*/
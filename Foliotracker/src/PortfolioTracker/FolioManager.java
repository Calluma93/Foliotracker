package PortfolioTracker;

import quoteAPI.MethodException;
import quoteAPI.NoSuchTickerException;
import quoteAPI.WebsiteDataException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This class represents the majority of the "PortfolioTracker", it's technically the folio manager as that's its'
 * primary function (it implements a large amount of methods for stock addition/folio addition etc)
 * but that's too specific a name as it has other functionality too such as exposing
 * getters to the FrontendGUI through a restricted interface.
 *
 * @author Team6
 */
public class FolioManager extends Observable implements IFolioManagerRestricted {

	private int folioID;
	private Map<Integer, Folio> folioList;

	/**
	 * Constructor for the backendhandler class
	 */
	public FolioManager() {

		folioList = new HashMap<>();
		folioID = 0;

	}

	/**
	 * Gets the quote information for tickerSymbol and creates and adds its with
	 * the amount to the folio
	 *
	 * @param fID          the ID of the folio we would like to add to
	 * @param tickerSymbol the ticker of the shares we would like to buy
	 * @param amount       the number of shares we would like to buy
	 * @throws MethodException
	 * @throws NoSuchTickerException
	 * @throws WebsiteDataException
	 * @throws IOException
	 */
	public void doBuyStock(int fID, String tickerSymbol, int amount)
			throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {

		if (tickerSymbol != null)
			tickerSymbol = tickerSymbol.toUpperCase();

		Folio folio = folioList.get(fID);

		if (folio != null) {

			Stock cS = (Stock) folio.getStock(tickerSymbol);

			if (cS != null) {

				cS.buyVolume(amount);

			} else {

				folio.addStock(tickerSymbol, amount);

			}

			setChanged();
			notifyObservers();

		}

	}

	/**
	 * Gets the folio (if it exists) by the specified id, locates the stock with the specified ticker symbol and
	 * then executes sellVolume on the found object.
	 *
	 * @param fID        the id of the folio containing the stock
	 * @param ticker    the ticker of the stock we wish to sell
	 * @param vol        the volume we wish to sell
	 * @throws MethodException
	 * @throws NoSuchTickerException
	 * @throws WebsiteDataException
	 * @throws IOException
	 */
	public void doSellStock(int fID, String ticker, int vol)
			throws IOException, WebsiteDataException, NoSuchTickerException, MethodException {

		IFolioRestricted folio = folioList.get(fID);

		if (folio != null) {
			Stock stock = (Stock) folio.getStock(ticker);

			stock.sellVolume(vol);

			setChanged();
			notifyObservers();

		}

	}

	/**
	 * Creates a new empty folio with the name folioName and store is in the
	 * folio collection
	 *
	 * @param name this will be the name of your folio
	 */
	public void doCreateNewFolio(String name) {
		Folio newFolio = new Folio(name, folioID++);

		folioList.put(newFolio.getID(), newFolio);

		setChanged();
		notifyObservers();
	}

	/**
	 * Deletes an existing folio form the folio collection
	 *
	 * @param fID    this is the ID of the folio you would like to remove
	 */
	public void doRemoveFolio(int fID) {

		folioList.remove(fID);

		setChanged();
		notifyObservers();
	}

	/**
	 * Deletes a specified stock from a folio delimited by the given id.
	 *
	 * @param fID     The folio which should have a stock removed
	 * @param ticker The stock which should be removed
	 */
	public void doRemoveStock(int fID, String ticker) {

		Folio folio = folioList.get(fID);

		if (folio != null) {
			if (folio.getStock(ticker) != null)
				folio.removeStock(ticker);
		}

		setChanged();
		notifyObservers();
	}


	/**
	 * Updates every stock across every folio to its' latest stock value.
	 * Uses a cached threadpool to ensure that stocks are updated as fast as possible.
	 */
	public void doUpdateStocks() {

		ExecutorService tPool = Executors.newCachedThreadPool();

		for (Entry<Integer, Folio> e : folioList.entrySet()) {

			Folio folio = e.getValue();

			for (Entry<String, IStockRestricted> s : folio.getStockList().entrySet()) {

				Runnable stockUpdate = () -> {
					try {
						Stock sV = (Stock) s.getValue();

						sV.updatePrice(); // thread these
					} catch (MethodException | IOException | WebsiteDataException | NoSuchTickerException ex) {
					}
				};

				tPool.submit(stockUpdate);

			}
		}

		tPool.shutdown();

		setChanged();
		notifyObservers();
	}

	/**
	 * Saves a folio specified by id to a specified location on the users local storage
	 * @param file        a File object representing where the user wishes to store the folio
	 * @param folioID    the folio id of the folio to save
	 * @return true if successful, false if file already exists
	 */
	public boolean doSaveFolio(File file, int folioID) {

		Folio folio = folioList.get(folioID);

		try {
			if (file.exists()) {
				return false;
			}

			FileOutputStream fs = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fs);

			os.writeObject(folio);

			os.close();
			fs.close();

			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException ex) {
			return false;
		}


	}

	/**
	 * Loads a folio object saved at the specified location
	 *
	 * @param file    a File object representing where the user wishes to load the folio from
	 */
	public void doLoadFolio(File file) {
		try {

			FileInputStream fs = new FileInputStream(file);
			ObjectInputStream os = new ObjectInputStream(fs);
			Folio folio = (Folio) os.readObject();

			os.close();
			fs.close();

			if (folioList.get(folio.getID()) == null) {
				folioList.put(folio.getID(), folio);
			} else {
				int i = 0;
				while (folioList.get(i) != null) {
					i++;
				}
				folio.changeID(i);
				folioList.put(i, folio);
			}


			setChanged();
			notifyObservers();

		} catch (FileNotFoundException FNF) {
			FNF.printStackTrace();
		} catch (IOException IO) {
			IO.printStackTrace();
		} catch (ClassNotFoundException CNF) {
			CNF.printStackTrace();
		}

	}

	// Getters to expose

	@Override
	public Map<Integer, IFolioRestricted> getAllFolios() {
		return new HashMap<>(folioList);
	}

}
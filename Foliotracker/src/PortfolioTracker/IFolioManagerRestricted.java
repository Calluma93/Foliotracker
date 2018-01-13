package PortfolioTracker;

import java.util.Map;

/**
 * Restricted access to the PortfolioTracker as the FrontendGUI holds a reference to it
 */
public interface IFolioManagerRestricted {

	/**
	 * Retrieves a list of all folios currently held through a restricted format.
	 *
	 * @return the list of folios.
	 */
	Map<Integer, IFolioRestricted> getAllFolios();

}

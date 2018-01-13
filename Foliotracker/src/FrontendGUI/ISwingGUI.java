package FrontendGUI;

/**
 * Accessor interface for the view to expose certain getters to the controller.
 */
public interface ISwingGUI {

	/**
	 * Gets the foliopanel component associated with the currently selected tab
	 *
	 * @return folioPanel if a tab is selected, null if a tab is not selected or if no tabs exist.
	 */
	folioPanel getCurrentlySelectedFolio();

	/**
	 * Gets the folio panel containing the folio with the specified id
	 *
	 * @param fID the folio id to look for
	 * @return folioPanel if found, null if it could not be found
	 */
	folioPanel getFPanelWithID(int fID);
}

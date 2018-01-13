package ConnectorActions;

import java.awt.event.KeyEvent;

/**
 * Enumerator class representing user readable actions, supports storage of a label and a mnemonic to be displayed
 * on button or menu
 */
public enum FolioAction {

	NEW_FOLIO("New Folio", KeyEvent.VK_N), SAVE_FOLIO("Save Folio", KeyEvent.VK_S),
	LOAD_FOLIO("Load Folio", KeyEvent.VK_L), DELETE_FOLIO("Delete Folio", KeyEvent.VK_D),
	BUY_STOCK("Buy Stock", KeyEvent.VK_B), SELL_STOCK("Sell Stock", KeyEvent.VK_S),
	REMOVE_STOCK("Remove Stock", KeyEvent.VK_R), UPDATE_ALL("Force Update", KeyEvent.VK_F),
	NO_ACT("Do Nothing", KeyEvent.VK_0), EXIT("Exit", KeyEvent.VK_E);

	private String label;
	private Integer mnemonic;

	FolioAction(String label, Integer mnemonic) {
		this.label = label;
		this.mnemonic = mnemonic;
	}

	@Override
	public String toString() {
		return label;
	}

	/**
	 * Returns the mnemonic associated with this enum
	 *
	 * @return the mnemonic
	 */
	public Integer getMnemonic() {
		return this.mnemonic;
	}
}

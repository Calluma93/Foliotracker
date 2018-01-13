package FrontendGUI;

import ConnectorActions.ActionFactory;
import ConnectorActions.FolioAction;
import PortfolioTracker.IFolioManagerRestricted;
import PortfolioTracker.IFolioRestricted;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

/**
 * The primary GUI class that's what we actually see.
 */
public class SwingGUI extends JFrame implements Observer, ISwingGUI {

	/**
	 *
	 */
	private static final long serialVersionUID = 5621806039723607762L;
	private static int updatePeriod = 1;

	private JTabbedPane tabbedBody;
	private JSlider intervalSlider;

	private Timer updateTimer;
	private IFolioManagerRestricted model;

	/**
	 * Main constructor that manages all of the design of the gui
	 *
	 * @param be restricted reference to the PortfolioTracker to pull data from.
	 */
	public SwingGUI(IFolioManagerRestricted be) {

		// ########### BINDING ###########

		model = be;

		// ########### GENERAL GUI ###########

		setLayout(new BorderLayout());

		// ########### TOP MENU BAR ##############

		JMenuBar topMenuBar = new JMenuBar();

		// FILE MENU

		JMenu fileMenu = new JMenu("File");

		JMenuItem newFolioMenu = new JMenuItem();
		newFolioMenu.setAction(ActionFactory.createAction(FolioAction.NEW_FOLIO, model, this));

		JMenuItem saveFolioMenu = new JMenuItem();
		saveFolioMenu.setAction(ActionFactory.createAction(FolioAction.SAVE_FOLIO, model, this));

		JMenuItem loadFolioMenu = new JMenuItem();
		loadFolioMenu.setAction(ActionFactory.createAction(FolioAction.LOAD_FOLIO, model, this));

		JMenuItem exitFolioMenu = new JMenuItem();
		exitFolioMenu.setAction(ActionFactory.createAction(FolioAction.EXIT, model, this));

		fileMenu.add(newFolioMenu);
		fileMenu.add(saveFolioMenu);
		fileMenu.add(loadFolioMenu);
		fileMenu.add(exitFolioMenu);

		topMenuBar.add(fileMenu);

		// ADD MENUS

		add(topMenuBar, BorderLayout.NORTH);

		// ######### MAIN JPANEL BODY ##########

		JPanel mainBody = new JPanel(new BorderLayout());
		mainBody.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// ######### MAIN TABBED BODY ########

		tabbedBody = new JTabbedPane();

		mainBody.add(tabbedBody, BorderLayout.CENTER);

		// ######## INTERVAL PANEL #######

		JPanel intervalPanel = new JPanel(new BorderLayout());
		intervalPanel.setBorder(BorderFactory.createEtchedBorder());

		JLabel intervalLabel = new JLabel("Set update interval (seconds): ");

		intervalPanel.add(intervalLabel, BorderLayout.WEST);

		intervalSlider = new JSlider(JSlider.HORIZONTAL, 0, 60, 1);
		intervalSlider.setMajorTickSpacing(10);
		intervalSlider.setMinorTickSpacing(1);
		intervalSlider.setSnapToTicks(true);
		intervalSlider.setPaintTicks(true);
		intervalSlider.setPaintLabels(true);
		intervalSlider.addChangeListener(e -> setUpdatePeriod());

		intervalPanel.add(intervalSlider, BorderLayout.CENTER);

		mainBody.add(intervalPanel, BorderLayout.SOUTH);

		add(mainBody, BorderLayout.CENTER);

		pack();

		// GENERIC GUI SETTINGS

		setDefaultCloseOperation(SwingGUI.DO_NOTHING_ON_CLOSE);
		setTitle("CS308 - Folio Tracker");
		setSize(new Dimension(800, 400));
		setLocationRelativeTo(null);
		setVisible(true);

		// UPDATE TIMER

		updateTimer = new Timer(updatePeriod * 1000, ActionFactory.createAction(FolioAction.UPDATE_ALL, model, this));
		updateTimer.setRepeats(true);
		updateTimer.start();

		// close listener

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exitFolioMenu.doClick();
			}

		});

	}

	/**
	 * Associated with the above lambda function that sets the updatePeriod to however many seconds the user
	 * selects on the sliding scale. Also 0 pauses it.
	 */
	private void setUpdatePeriod() {
		if (intervalSlider.getValueIsAdjusting())
			return;

		updatePeriod = intervalSlider.getValue();

		if (updatePeriod == 0) {

			updateTimer.stop();

			JOptionPane.showMessageDialog(null, "The stock updater is now paused!");

		} else {

			updateTimer.stop();
			updateTimer.setInitialDelay(updatePeriod * 1000);
			updateTimer.setDelay(updatePeriod * 1000);
			updateTimer.restart();

			JOptionPane.showMessageDialog(null, "The stock update frequency is now " + updatePeriod + " seconds!");

		}

	}


	@Override
	public folioPanel getCurrentlySelectedFolio() {
		if (tabbedBody == null || tabbedBody.getTabCount() == 0)
			return null;

		int index = tabbedBody.getSelectedIndex();

		if (index != -1)
			return (folioPanel) tabbedBody.getComponentAt(index);

		return null;
	}


	@Override
	public folioPanel getFPanelWithID(int fID) {
		for (int i = 0; i < tabbedBody.getTabCount(); i++) {
			folioPanel pan = (folioPanel) tabbedBody.getComponentAt(i);
			if (pan.getFolioID() == fID) {
				return pan;
			}
		}

		return null;
	}

	// When Model changes it sends update message to all its Observers
	@Override
	public void update(Observable o, Object obj) {

		Map<Integer, IFolioRestricted> folioList = model.getAllFolios();

		if (tabbedBody.getTabCount() < folioList.size()) {

			for (Entry<Integer, IFolioRestricted> f : folioList.entrySet()) {
				IFolioRestricted modelFolio = f.getValue();
				folioPanel viewFolioPanel = getFPanelWithID(f.getKey());

				if (viewFolioPanel == null) {
					// this folio isn't found in the folioPanel list so add a
					// new tab.
					folioTableModel fM = new folioTableModel(modelFolio);
					folioPanel temp = new folioPanel(modelFolio.getID(), fM, model, this);
					tabbedBody.addTab(modelFolio.getName(), temp);

					temp.notifyUpdated();
				}
			}

		} else if (tabbedBody.getTabCount() > folioList.size()) {

			for (int i = 0; i < tabbedBody.getTabCount(); i++) {
				folioPanel pan = (folioPanel) tabbedBody.getComponentAt(i);
				if (!folioList.containsKey(pan.getFolioID())) {
					tabbedBody.remove(pan);
				}
			}

		} else { // folio size not changed, must be an update

			// note for stocks we need to do as above, if greater then add a
			// stock if less remove one but also update every stock just incase
			// the price or something has changed.

			for (Entry<Integer, IFolioRestricted> f : folioList.entrySet()) {
				folioPanel viewFolioPanel = getFPanelWithID(f.getKey());

				viewFolioPanel.notifyUpdated(); // Static code analysis says
				// that this can produce
				// NullPointExeption, need to
				// check

			}

		}

	}

}
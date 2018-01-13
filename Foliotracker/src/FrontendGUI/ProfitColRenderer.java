package FrontendGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Custom renderer to handle painting of the profit/loss column
 */
public class ProfitColRenderer extends DefaultTableCellRenderer {
	/**
	 *
	 */
	private static final long serialVersionUID = -7033585807064617611L;

	DecimalFormat df = new DecimalFormat("0.###");
	//the default green is too bright and illegible
	private Color fDarkGreen = Color.green.darker();

	// PRIVATE

	@Override
	public Component getTableCellRendererComponent(
			JTable aTable, Object aNumberValue, boolean aIsSelected,
			boolean aHasFocus, int aRow, int aColumn
	) {
	/*
	* Implementation Note :
    * It is important that no 'new' objects be present in this
    * implementation (excluding exceptions):
    * if the table is large, then a large number of objects would be
    * created during rendering.
    */

		Component c = super.getTableCellRendererComponent(
				aTable, aNumberValue, aIsSelected, aHasFocus, aRow, aColumn);

		if (c instanceof JLabel && aNumberValue instanceof Number) {

			JLabel label = (JLabel) c;
			label.setHorizontalAlignment(JLabel.RIGHT);

			Number num = (Number) aNumberValue;

			label.setText(df.format(num));

			if (aIsSelected)
				return this;

			if (num.doubleValue() < 0) {

				label.setForeground(Color.white);
				label.setBackground(Color.red);

			} else if (num.doubleValue() > 0) {

				label.setForeground(Color.white);
				label.setBackground(fDarkGreen);

			} else {
				label.setForeground(Color.black);
				label.setBackground(Color.white);
			}

		}

		return c;
	}
}

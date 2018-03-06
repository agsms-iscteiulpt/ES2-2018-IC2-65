package Output;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class Output extends JFrame {

	Label labelInfo;
	JTable jTable;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			createAndShowGUI();
		});
	}

	private static void createAndShowGUI() {
		Output myFrame = new Output();
		myFrame.setTitle("Output");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.prepareUI();
		myFrame.pack();
		myFrame.setVisible(true);
	}

	private void prepareUI() {

		JPanel vPanel = new JPanel();
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));

		MyChart myChart = new MyChart();
		myChart.setPreferredSize(new Dimension(450, 200));

		jTable = new JTable(new MyTableModel());
		jTable.getSelectionModel().addListSelectionListener(new MyRowColListener());
		jTable.getColumnModel().getSelectionModel().addListSelectionListener(new MyRowColListener());

		jTable.setFillsViewportHeight(true);
		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(600, 250));
		vPanel.add(jScrollPane);

		labelInfo = new Label();
		vPanel.add(labelInfo);

		Button buttonPrintAll = new Button("Print Results");
		buttonPrintAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println();
				for (int i = 0; i < jTable.getRowCount(); i++) {
					for (int j = 0; j < jTable.getColumnCount(); j++) {
						String val = String.valueOf(jTable.getValueAt(i, j));
					}
				}

				// Create ListArray for the first row
				// and update MyChart
				ArrayList<Integer> l = new ArrayList<>();
				for (int i = 0; i < jTable.getColumnCount(); i++) {
					l.add((Integer) jTable.getValueAt(0, i));
				}
				myChart.updateList(l);
			}
		});

		getContentPane().add(myChart, BorderLayout.PAGE_START);
		getContentPane().add(vPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPrintAll, BorderLayout.PAGE_END);
	}

	// @SuppressWarnings("serial")
	private class MyChart extends JComponent {
		ArrayList<Integer> chartList;

		public void updateList(ArrayList<Integer> l) {
			System.out.println("updateList()");

			chartList =  l;
			repaint();
		}

		@Override
		public void paint(Graphics g) {
			if (chartList != null) {
				paintMe(g);
			}
		}

		private void paintMe(Graphics g) {
			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setColor(Color.blue);

			int width = getWidth();
			int height = getHeight();

			float hDiv = (float) width / (float) (chartList.size() - 1);
			float vDiv = (float) height / (float) (Collections.max(chartList));

			for (int i = 0; i < chartList.size() - 1; i++) {

				int value1, value2;
				if (chartList.get(i) == null) {
					value1 = 0;
				} else {
					value1 = chartList.get(i);
				}
				if (chartList.get(i + 1) == null) {
					value2 = 0;
				} else {
					value2 = chartList.get(i + 1);
				}

				graphics2d.drawLine((int) (i * hDiv), height - ((int) (value1 * vDiv)), (int) ((i + 1) * hDiv),
						height - ((int) (value2 * vDiv)));
			}

			graphics2d.drawRect(0, 0, width, height);
		}

	}

	private class MyRowColListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			System.out.println("valueChanged: " + e.toString());

			if (!e.getValueIsAdjusting()) {

				int row = jTable.getSelectedRow();
				int col = jTable.getSelectedColumn();

				if (row >= 0 && col >= 0) {
					int selectedItem = (int) jTable.getValueAt(row, col);
					labelInfo.setText("MyRowListener: " + row + " : " + col + " = " + selectedItem);
				}

			}
		}
	}

	class MyTableModel extends AbstractTableModel {
		private String[] Algoritms = { "1", "2", "3", "4", "5", "6", "7" };

		private Object[][] tableData = { { 1, 9, 2, 8, 3, 7, 6 } };

		@Override
		public int getColumnCount() {
			return Algoritms.length;
		}

		@Override
		public int getRowCount() {
			return tableData.length;
		}

		@Override
		public String getColumnName(int col) {
			return Algoritms[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return tableData[row][col];
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			tableData[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}
}

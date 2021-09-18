package sql;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PostgreSQLJDBC extends JPanel {

	static JTextArea sql = new JTextArea();
	JLabel promt = new JLabel("Type your PostgreSQL statment below:");
	JButton exe = new JButton("Excute");
	JButton reset = new JButton("Reset");
	static JTable table = new JTable();
	static DefaultTableModel model = (DefaultTableModel) table.getModel();
	static Connector dc;

	public PostgreSQLJDBC(Connector conn) {
		add(promt);
		dc = conn;
		JScrollPane spane = new JScrollPane(sql);
		spane.setPreferredSize((new Dimension(750, 100)));
		add(spane);
		exe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				excute();

			}
		});
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.setColumnCount(0);
				model.setRowCount(0);
			}
		});
		add(exe);
		add(reset);

		// add(table);
		JScrollPane rspan = new JScrollPane(table);
		rspan.setPreferredSize((new Dimension(750, 100)));
		add(rspan);

	}

	private static void excute() {
		model.setColumnCount(0);
		model.setRowCount(0);
		String s = sql.getText();

		try {
			if (s.length() >= 6 && s.substring(0, 6).equalsIgnoreCase("SELECT")) {
				ResultSet rs = dc.excuteQury(s);
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					model.addColumn(rsmd.getColumnName(i));

				}
				while (rs.next()) {
					String[] data = new String[rsmd.getColumnCount()];
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						data[i - 1] = rs.getString(i);
					}
					model.addRow(data);
				}

			} else
				dc.executeUpdate(s);
		} catch (SQLException e) {
			System.out.println("Error: " + e);

		}

	}
}

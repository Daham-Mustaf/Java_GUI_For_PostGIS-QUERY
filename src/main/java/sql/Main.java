package sql;

import java.util.Properties;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame frame = new JFrame("PostGIS QUERY ");
		Properties props = new Properties();
		ConnectDialog dialog = new ConnectDialog(frame, "Database Connectro", props);
		dialog.setVisible(true);
		if (dialog.iscancelled)
			System.exit(0);
		Connector conn = new Connector(dialog.getPros(), new String(dialog.pass.getPassword()));
		if (!conn.open())
			System.exit(0);
		PostgreSQLJDBC dpanal = new PostgreSQLJDBC(conn);
		frame.setSize(800, 600);
		frame.add(dpanal);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}

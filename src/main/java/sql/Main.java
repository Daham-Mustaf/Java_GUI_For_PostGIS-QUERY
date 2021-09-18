package sql;

import java.util.Properties;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
     //Creates a JFrame with the specified title and the specified GraphicsConfiguration of a screen.
		JFrame frame = new JFrame("PostGIS QUERY ");

		//A property list that contains default values for any keys not found in this property list
		Properties props = new Properties();
		// creat an object of ConnectDialog
		ConnectDialog dialog = new ConnectDialog(frame, "Database Connectro", props);
		dialog.setVisible(true);
		//status – exit status.
		if (dialog.iscancelled)
			System.exit(0);
		//// creat an object of Connector
		Connector conn = new Connector(dialog.getPros(), new String(dialog.pass.getPassword()));
		if (!conn.open())
			System.exit(0);
		// creat an object of PostgreSQLJDBC
		PostgreSQLJDBC dpanal = new PostgreSQLJDBC(conn);
		frame.setSize(800, 600);
		//add
		frame.add(dpanal);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}

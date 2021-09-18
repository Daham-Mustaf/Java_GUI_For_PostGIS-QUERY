package sql;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConnectDialog extends JDialog implements ActionListener {

	boolean iscancelled = true;
	// create labels for host,port, databasename,user name and password
	JLabel lhost = new JLabel("Host Name");
	JTextField host = new JTextField();
	JLabel lport = new JLabel("Port");
	JTextField port = new JTextField();
	JLabel ldatabase = new JLabel("Database");
	JTextField database = new JTextField();
	JLabel lusr = new JLabel("User Name");
	JTextField user = new JTextField();
	JLabel lpass = new JLabel("User Name");
	JPasswordField pass = new JPasswordField();

	// add Buttons
	JButton ok = new JButton("Ok");
	JButton cancel = new JButton("cancel");

	// Properties object
	Properties props;

	public ConnectDialog(JFrame owner, String title, Properties p) {
		super(owner, title, true);
		setSize(300, 200);
		setLocation(250, 200);
		props = new Properties(p);
		ok.setPreferredSize(new Dimension(75, 25));
		ok.addActionListener(this);
		cancel.setPreferredSize(new Dimension(75, 25));
		cancel.addActionListener(this);
		JPanel cpanal = new JPanel();
		JPanel cpanal2 = new JPanel();

		// add grid
		cpanal.setLayout(new GridLayout(5, 2));
		cpanal.add(lhost);
		cpanal.add(host);
		cpanal.add(lport);
		cpanal.add(port);
		cpanal.add(ldatabase);
		cpanal.add(database);
		cpanal.add(lusr);
		cpanal.add(user);
		cpanal.add(lpass);
		cpanal.add(pass);

		// add to sacond panal
		cpanal2.add(ok);
		cpanal2.add(cancel);

		add(cpanal, BorderLayout.NORTH);
		add(cpanal2, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == ok)
			iscancelled = false;
		this.dispose();

	}

	public Properties getPros() {
		props.setProperty("Database", database.getText());
		props.setProperty("Host_Name", host.getText());
		props.setProperty("Port", port.getText());
		props.setProperty("User_Name", user.getText());
		return props;
	}

}

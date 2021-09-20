# Java_GUI_For_PostGIS-QUERY

# Table of contents
1. [Tha main purpose of the project:](#introduction)
2. classes:
- [`Main`:](#main)
- [`Connector`:](#conn)
- [`ConnectDialog`:](#dial)
- [`PostgreSQLJDBC`:](#post)
3. [the result of excuting PostGIS query:](#re)



## The main purpose of the project: <a name="introduction"></a>
we want to develop an application to send the quireis of POSTGIS on a friendly GUI by using Java. Swing in Java is a Graphical User Interface (GUI) toolkit that includes the GUI components. Swing provides a rich set of widgets and packages to make sophisticated GUI components for Java applications. Swing is a part of Java Foundation Classes(JFC), which is an API for Java GUI programing that provide GUI. see more information about [postGIS](https://github.com/Daham-Mustaf/PostGis).


## the class `Main`: <a name="main"></a>
contains all the configuration requirements for our project to interact with the database.
```java
/ //Creates a JFrame with the specified title and the specified GraphicsConfiguration of a screen.
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

```

## Connectin with PostgreSQL Server: <a name="conn"></a>
PostgreSQL is a powerful, open source SQL database with the object-relational structure and numerous robust features to ensure excellent performance and reliability.

```java
public class Connector {
	Connection conn;
	Statement stat;
	/*
	*connect to postgresql
	 */
	//Try to make a database connection to the given URL. The driver should return "null" if it realizes it is the wrong
	// kind of driver to connect to the given URL. This will be common, as when the JDBC driverManager is asked to connect to a given URL,
	// it passes the URL to each loaded driver in turn.

	static String url, database, username, password, hostname, port, driver;

	public Connector(Properties pros, String pass) {

		database = pros.getProperty("Database");
		/*
		 * Find Users in PostgreSQL SELECT usename FROM pg_user;
		 */
		username = pros.getProperty("User_Name");

		password = pass;

		/*
		 * SELECT boot_val,reset_val FROM pg_settings WHERE name='listen_addresses';
		 */
		hostname = pros.getProperty("Host_Name");
		/*
		 * SELECT * FROM pg_settings WHERE name = 'port';
		 */
		port = pros.getProperty("Port");
		driver = "org.postgresql.Driver";
		url = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;

	}

	public boolean open() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password);
			stat = conn.createStatement();

		} catch (Exception e) {
			System.out.println("The connection failed");
			e.printStackTrace();
			if (conn == null)
				return false;
		}
		System.out.println("Connection successful");
		return true;
	}

	public ResultSet excuteQury(String s) throws SQLException {
		// TODO Auto-generated method stub
		return stat.executeQuery(s);
	}

	public void executeUpdate(String s) throws SQLException {
		stat.executeUpdate(s);

	}

}
```

## `ConnectDialog`: <a name="dial"></a>

```java

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
```

## `PostgreSQLJDBC`: <a name="post"></a>
¡– Download PostgreSQL JDBC driver.
JDBC database URL for PostgreSQL
The syntax of database URL for PostgreSQL looks like the following forms:
jdbc:postgresql:database
jdbc:postgresql://host/database
jdbc:postgresql://host:port/database
jdbc:postgresql://host:port/database?param1=value1&param2=value2&…

j- Register JDBC driver for PostgreSQL Server and create connection

 

```java
static JTextArea sql = new JTextArea();
	JLabel promt = new JLabel("Type your PostgreSQL statement below:");
	JButton exe = new JButton("Excute");
	JButton reset = new JButton("Reset");
	static JTable table = new JTable();
	//an implementation of TableModel that uses a Vector of Vectors to store the cell value objects.
	static DefaultTableModel model = (DefaultTableModel) table.getModel();
	static Connector dc;

	public PostgreSQLJDBC(Connector conn) {
		add(promt);
		dc = conn;
		//JScrollPane manages a viewport, optional vertical and horizontal scroll bars, and optional row and column heading viewports. 
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
```
### result  <a name="re"></a>
when we run the program it shows the palce to enter the information to get the connection with data base:
<img width="317" alt="Screen Shot 2021-09-18 at 9 43 29 PM" src="https://user-images.githubusercontent.com/73963764/133906945-033b673b-840f-48e1-9c92-ef9711dcbe8d.png">

after entring the requred information the new window will be opened as follow:

<img width="800" alt="Screen Shot 2021-09-18 at 9 53 56 PM" src="https://user-images.githubusercontent.com/73963764/133906994-da94bf5a-7785-4e29-8056-5dea9f53947d.png">

excute some quries:
```j
CREATE DATABASE test;
```
CREATE a table: 
```j
CREATE TABLE point
   (
     x float8,
     y float8,
     place varchar(100),
     size float8,
     update date,
     startdate date,
     enddate date,
     title varchar(255),
     url varchar(255),
     the_geom geometry(POINT, 4326)
);
```
- quiry on data:
<img width="799" alt="Screen Shot 2021-09-18 at 10 01 10 PM" src="https://user-images.githubusercontent.com/73963764/133907200-089b8548-862a-45fe-91d5-9339fb85dc0c.png">







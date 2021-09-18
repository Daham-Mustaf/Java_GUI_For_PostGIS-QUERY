# Java_GUI_For_PostGIS-QUERY

# Table of contents
1. [Tha main purpose of the project:](#introduction)
2. classes:
- [`Main`:](#main)
- [`Connector`:](#conn)
- [`ConnectDialog`:](#dial)
- [`PostgreSQLJDBC`:](#post)



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

## Tha main purpose of the project: <a name="conn"></a>
## Tha main purpose of the project: <a name="dial"></a>
## Tha main purpose of the project: <a name="post"></a>


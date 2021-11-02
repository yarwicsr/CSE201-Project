/*
* The class "Main" houses the backbone of the program, with most of the framework building the interactive window,
* along with housing the main method that initiates the app when run.  Main also contains a constructor
* to initialize the viewable window, and check for a typing user.  The method Paint Component does the backbone of 
* the graphics of the viewable window, setting sizes and colors for the different interactive features, as well as 
* the autofill feature of the search bar, and a check to see if a user login is correct.  KeyPressed is an 
* overridden method to track the keys a user pressed, when the user has clicked on the search bar. 
* The login void method alters the boolean "loggedIn" depending on if the user-entered username and password combination
* match the stored login credentials, setting the boolean to true if there is infact a match, and leaving it false otherwise.
* 
*/

//--------------------------Various imports------------------------------
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

//-----------------------------------------------------------Start of "Main" Class-----------------------------------------------------------------
public class Main extends JPanel implements KeyListener {

  //--------------------------------------------------------------------Class Members----------------------------------------------------------------

	JFrame window = new JFrame(); //Jframe that houses the objects for the viewable window
  
	public static ArrayList<App> apps = new ArrayList<App>();// ArrayList to store the data collected from the txt file
	
  int preX, preY, whoClicked; // preX and preY store the location of the users mouse click,
  //whoClicked is currently unused
	
  int index = 0;
	
  int userstatus = 0; // stores the current credentials of a user, 2 is an admin, 1 is a moderator,
  // and 0 is a user
	
  String searchTerm = "Search: "; // searchTerm stores the string the user types into the 
  // searchbar, initially starting with "Search: " to help guide the user to search for information
  // in that location, then, "Search: " is replaced by an empty string when the user clicks on the search
  // bar, and then is filled with the string the user types

	String username = ""; // stores the username that the user types in
	String password = ""; // stores the password that the user types in 
  //both username and password are later checked against the list of acceptable logins

	char currentKey = '|'; // stores the currentKey that the user has typed

	boolean currSearching = false; // stores a boolean for if the user has clicked on the search bar
	boolean loggedIn = false; // stores a boolean for if  the user entered an acceptable login

	boolean typingUser = false; // stores a boolean for if the user is typing into the Username bar
	boolean typingPass = false; // stores a boolean for if the user is typing into the password bar

	static WordTree names = new WordTree(); // names stores all of the names derived from the data file supplied
	Set<String> startingWith = new HashSet<String>(); // stores all of the possible names, starting with the 
  //substring of the user entered string

	static Map<String, String> adminLogins = new HashMap<String, String>(); // map for holding the acceptable admin logins
	static Map<String, String> userLogins = new HashMap<String, String>(); // map for holding the acceptable user logins
	static Map<String, String> modLogins = new HashMap<String, String>(); // map for holding the acceptable moderator logins
  
  //-------------------------currently unused class members------------
	Timer tmr = null; 
	Random rnd = new Random();
  //-------------------------------------------------------------------

	
  //------------------------------------------------------------------------Methods----------------------------------------------------------------

  /*
  * This empty Constructor for the "Main" object initializes the size of the window, sets some limitations, 
  * implements a key listener, and a mouse listener.  Main then sets the windoe to visible
  */
	public Main() {
		window.setBounds(100, 100, 600, 600); // setting the bounds of the window
		window.setAlwaysOnTop(true); // making the window always ontop, viewable to the user
		window.setResizable(false); // making the window not resizable
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // making the window close when exited
		window.getContentPane().add(this); // adding the main object to the window
		window.setVisible(true); // setting the window to visible
		window.addKeyListener(this); // adding in the keyListener
		
    /*
    * addMouseListener outlines what a mouse listener should look out for, mainly checking 
    * if the user has clicked on the search bar, username bar, or password bar, then initiating 
    * the keyListener to store the characters the user wants to input.
    */
		addMouseListener(new MouseListener() {
			
      //currently unused function
      @Override
			public void mouseReleased(MouseEvent e) {}

      /*
      * mouseClicked checks where the user has clicked on the window, checking if the 
      * click is within bounds of any of the interactive sections of the window
      */
			@Override
			public void mouseClicked(MouseEvent e) {
        //storing the location of the mouseClick
				int x = e.getX();
				int y = e.getY();

        //checking if the click was located in the searchbar
				if(x >= 45 && x <= 245 && y >= 15 && y <= 30) {
					searchTerm = "";
					currSearching = true;
					repaint(); //updating the window to show the user their entered characters
				} else if(x >= 475 && x <= 525 && y >= 205 && y <= 220) {
					username = "";
					password = "";
					loggedIn = false;
					currSearching = false;
					repaint();
				} else currSearching = false;
        //checking first if the user is already loggedin, and if not, allowing the user to enter 
        // a username and password in attempt to login
				if(!loggedIn) {
          //checking if the user has clicked to enter a username
					if(x >= 100 && x <= 300 && y >= 50 && y <= 65) {
						typingUser = true;
						typingPass = false;
					} 
          //checking if the user has clicked to enter a password
          else if(x >= 100 && x <= 300 && y >= 100 && y <= 115) {
						typingPass = true;
						typingUser = false;
					} 
          //if the click was not within the username or password bars, the user isn't currently searching
          else {
						typingUser = false;
						typingPass = false;
					}
          //if the user clicks on the login button, logging them in
					if(x >= 100 && x <= 150 && y >= 150 && y <= 165) {
						if(username == "" && password == "") loggedIn = true;
						login();
					}
				}
			}

      //currently unused mousePressed method
			@Override
			public void mousePressed(MouseEvent e) {}

      //currently unused mouseEntered method
			@Override
			public void mouseEntered(MouseEvent e) {}

      //currently unused mouseExited method
			@Override
			public void mouseExited(MouseEvent e) {}
		});
	}
	
	
  /**
  * Protected method "paintComponenet" does the bulk of the graphics of the window, setting the size, and colors of various objects in the window.
  * paintComponent also updates the screen with the current characters the user has entered in to the various interactive bars in the window.
  *
  */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY); // setting the color to light gray
	    g.fillRect(10, 10, 540, 226); // setting the overall size of the window
	    g.setColor(Color.BLACK);
  
    // checking if a user is already logged in, displaying the interactive search if the user is logged in,
    // and displaying the login screen otherwise
		if(loggedIn) {
			apps.get(index).draw(g);
			g.setColor(Color.GRAY);
	    	g.drawString(searchTerm, 46, 27); // showing the user the characters they have entered so far

        //checking if the user is currently searching, and if the user has entered any characters at all
	    	if(currSearching && searchTerm.length() > 0) {

          //try catch block to catch NullPointerExceptions when searching for matching names from the data file
	    		try {
            //proceding if are names from the data file starting with the string the user has entered so far into the 
            //search bar
	    			if(names.allStartingWith(searchTerm) != null) { startingWith = names.allStartingWith(searchTerm); }
            // showing the user the names that start with the substring the user has already entered
	    			g.setColor(Color.LIGHT_GRAY);
	    			g.fillRect(45, 30, 200, 15 * startingWith.size());
	    			int currHeight = 42;
	    			g.setColor(Color.BLACK);
	    			g.drawRect(45, 30, 200, 15 * startingWith.size());
            // iterating through the startingWith hashSet, printing each name to the user directly below the searchbar,
	    			for(String s : startingWith) {
	    				g.drawString(s, 46, currHeight);
	    			currHeight += 15;
	    			}
	    		}
	    		catch (NullPointerException e) {  	}
	    	}
        
        //showing the users credentials in the bottom right corner
	    	g.fillRect(475, 205, 50, 15);
	    	g.setColor(Color.black);
	    	if(userstatus == 2) {
	    		g.drawString("User is a admin", 350, 220);
	    	}
	    	if(userstatus == 1) {
	    		g.drawString("User is a moderator", 350, 220);
	    	}
	    	if(userstatus == 0) {
	    		g.drawString("User is a user", 350, 220);
	    	}
	    	g.drawRect(475, 205, 50, 15);
	    	g.drawString("Log out", 480, 217);

		} 
    // if the user isn't currently logged in, prompting them with a login screen, containign a username
    // type-in bar, and a password type-in bar
    else {
      //username label, with input bar below
			g.drawString("Username:", 100, 40);
			g.drawRect(100, 50, 200, 15);
      //password label, with input bar below
			g.drawString("Password:", 100, 90);
			g.drawRect(100, 100, 200, 15);
      //showing the user the username that they have entered so far
			g.drawString(username, 103, 62);
      //showing the user the password that they have entered so far
			g.drawString(password, 103, 112);
			g.setColor(Color.gray);
			g.fillRect(100,150,50,15);
			g.setColor(Color.BLACK);
			g.drawRect(100, 150, 50, 15);
      //drawing the log in button
			g.drawString("Log in", 105, 162);
		}
	}
	
  //currently unused keyTyped method
	public void keyTyped(KeyEvent e) {}
  
  /*
  * Overridden keyPressed method tracks the current character that the user has entered.
  * keyPressed method then updates the current string that the user is typing into, and repaints
  * the window to show the updated string to the user.
  * 
  */
	@Override
	public void keyPressed(KeyEvent e) {
		currentKey = e.getKeyChar(); // getting the character that has been entered
    //checking if the user is currently searching through the search bar
		if(currSearching) {
      //if the user entered a backspace
			if(e.getKeyCode() == 8) {
        // if the user entered string has at least length 1,
				if(searchTerm.length() > 0) {
          //deleting the last character from that string
					searchTerm = searchTerm.substring(0, searchTerm.length() - 1);
          //repainting the updated search String to the window
					repaint();
				}
			} 
      //checking if the user has hit the enter key
      else if(e.getKeyCode() == 10) {
        //going through the apps ArrayList, checking for possible matches for the user entered string
				for(int i = 0; i < apps.size(); i++) {
					if(startingWith.size() == 1) {
            //saving the index of the first data associated with the matching name into "index" which will
            // be shown on the window
						if(apps.get(i).name.equals(startingWith.toArray()[0])) { 
							index = i;
							searchTerm = "Search: ";
							currSearching = false;
							repaint();
						}
					} 
          //saving the index of the data associated with the first name that matches the user searched term, 
          // which will be shown on the window
          else if(apps.get(i).name.equals(searchTerm)) {
						index = i;
						searchTerm = "Search: ";
						currSearching = false;
						repaint();
					}
				}
			} 
      //adding accepable characters to the string the user has entered so far, then updating it on the screen.
      else if(e.getKeyCode() < 16 || e.getKeyCode() > 18) {
				searchTerm += currentKey;
				repaint();
			}
		} 
    //checking if the user if typing in to the username login bar
		if(typingUser) {
      // checking if the user hits the backspace
			if(e.getKeyCode() == 8) {
        // checking if the string entered so far is of length greater than 1
				if(username.length() > 0) {
          // erasing the last character of the string, and updating the window with the shortened string
					username = username.substring(0, username.length() - 1);
					repaint();
				} 
       // if the user hits the enter key, try logging them in using the credentials they have typed in
        else if(e.getKeyCode() == 10) {
					login();
				}
			} 
      //if the user hits an acceptable key, add it to the string the user has entered so far, and update the
      // window with the new string
      else if(e.getKeyCode() < 16 || e.getKeyCode() > 18) {
				username += currentKey;
				repaint();
			}
		} 
		
    //checking if the user if typing in to the password login bar
		else if(typingPass) {
      // checking if the user hits the backspace
			if(e.getKeyCode() == 8) {
       // checking if the string entered so far is of length greater than 1
				if(password.length() > 0) {
        // erasing the last character of the string, and updating the window with the shortened string
					password = password.substring(0, password.length() - 1);
					repaint();
				}
			} 
      // if the user hits the enter key, try logging them in using the credentials they have typed in
      else if(e.getKeyCode() == 10) {
				login();
			} 
      //if the user hits an acceptable key, add it to the string the user has entered so far, and update the
      // window with the new string
      else if(e.getKeyCode() < 16 || e.getKeyCode() > 18) {
				password += currentKey;
				repaint();
			}
		}
	}

  // currently unused keyReleased method
	@Override
	public void keyReleased(KeyEvent e) {}

  /**
  * Login method checks the username and password that the user has entered
  * against the lists of acceptable logins accross all of the levels of users,
  * updating the loggedIn boolean if the login is accepted, then updating the window,
  * showing the user the main functions of the application
  */
	void login() {
    //checking if the username could be an admin username
		if(adminLogins.containsKey(username)) {
      //checking if the passwords match
			if(adminLogins.get(username).equals(password)) {
			userstatus = 2; // setting the user status to an admin
			loggedIn = true; // setting the loggedIn to true
			}
		} 
    //checking if the username could be a moderator username
    else if(modLogins.containsKey(username)) {
      //checking if the passwords match
			if(modLogins.get(username).equals(password)) {
				userstatus = 1; // setting the user status to a moderator
				loggedIn = true; // setting the loggedIn to true
      }
		} 
    // checking if the username could be a user's username
    else if(userLogins.containsKey(username)) {
      //checking if the passwords match
			if(userLogins.get(username).equals(password)) {
				userstatus = 0; //setting the user status to a user
				loggedIn = true; // setting the loggedIn to true
			}
		}
		repaint(); // repainting the window to show the search window
	}

  //--------------------------------------------------------------------Main Method --------------------------------------------
  /*
  * Main method to initialize a Main object, showing the window to the user and allowing for interaction.
  * The main method also reads from a supplied "apps.txt" file that houses information that the user
  * can search through in the program. Acceptable Admin, Moderator, and user logins are also added.
  */
	public static void main(String args[]) {

    //adding the acceptable logins
		adminLogins.put("Cameron", "password");
		adminLogins.put("Sophia", "password");
		modLogins.put("Brenden", "eeeee");
    userLogins.put("Johnny", "123456");
    userLogins.put("Yidong", "qwerty");

		new Main(); // initializing a "Main" object 

    //try catch block surrounding the opening of the data file
    try {
      //reading all of the lines of the data file into an arrayList
       ArrayList<String> lines = (ArrayList<String>)Files.readAllLines(Paths.get("apps.txt")); 
       // iterating through each line of the arrayList
        for(String line : lines) {
          //delimetering the words by a comma, storing them into a string array
          String[] p = line.split(",");
          //iterating through the string array
          for(int i = 0; i < p.length; i++) {
            //checking if there is a space as the first character of the strings
            if(p[i].charAt(0) == ' ') {
              p[i-1] = p[i-1] + "," + p[i]; // adding the current string into the previous string, and seperating with a comma
              for(int j = i; j < p.length - 1; j++) {
                p[j] = p[j+1];
              }
            }
          }
          apps.add(new App(p[0],p[1],p[2],p[3],p[4],p[5],p[6])); // adding the information from a single line into the arrayList of user data
          names.add(p[0]); // adding the name into the arraylist of names collected from the txt file
        }
    }
    //catch block if the file is not found
    catch (IOException e) {}
  
	}
}
//------------------------------------------------------------------ END OF CLASS -------------------------------------------------

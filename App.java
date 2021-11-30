
//-------------------various imports------------------
import java.util.ArrayList;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
//import java.awt.*;
//---------------------------------------------------Start of "App" class------------------------------------------------

/**
 * App object stores the information about a product, including its name, price, description, organization, platforms, version, and 
 * a link, which is later displayed on the window of the program, if it is selected.  
 *
 */
public class App {
	//-----------------------------------------------class members--------------------------------------------
	/**
	 *  String to store the name of the App
	 */
	public String name; 
	/**
	 *  string to store the price of the App
	 */
	public String price; 
	/**
	 * string to store the description of the app
	 */
	public String description; 
	/**
	 *  string to store the organization of the app
	 */
	public String organization; 
	/**
	 * string to store the platform the app runs on
	 */
	public String platforms; 
	/**
	 * string to store the current version the app is on
	 */
	public String version; 
	/**
	 * string to store a link to the App 
	 */
	public String link; 
	/**
	 * Image icon for each app
	 */
	public Image image; 
	/**
	 * an arraylist to store the user comments about that app
	 */
	public ArrayList<Comment> comments; 
	//-----------------------------------------------------methods-------------------------------------------------
	/**
	 * Constructor to initialize an "App" object and fill the class members accordingly
	 * @param name name of the app 
	 * @param description description of the app
	 * @param organization organization that created the app
	 * @param platforms the current platforms the app works on
	 * @param version the current version of the app
	 * @param link the false link to the app
	 * @param price the price of the app
	 */
	public App(String name, String description, String organization, String platforms, String version, String link, String price) {
		this.name = name;
		if(price.length() - price.indexOf('.') != 3) this.price = '$' + price + '0';
		else this.price = '$' + price;
		this.description = description;
		this.organization = organization;
		this.platforms = platforms;
		this.version = version;
		this.link = link;
		this.comments = new ArrayList<Comment>();
		try {
			this.image = ImageIO.read(new File("world.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * the draw method is a public void method that draws the app to the screen to show the user
	 * @param g passing the graphics object that will be altered and displayed to the user
	 */
	public void draw(Graphics g) {
		String tempPrice = price + "";

		//drawing a rectangle to houes the information about the App
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(10, 10, 540, 600);
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 541, 600);
		g.drawImage(image, 450, 30, 75, 75, null);
		g.drawRect(450, 30, 75, 75);

		//drawing the information about the app in the window
		g.drawString("Name: " + name, 50, 50);
		g.drawString("Price: " + tempPrice, 50, 75);
		g.drawString("Description: " + description, 50, 100);
		g.drawString("Organization: " + organization, 50, 125);
		g.drawString("Version: " + version, 50, 150);
		g.drawString("Platforms: " + platforms, 50, 175);
		g.setColor(Color.BLUE); // setting the color of the link to blue, to look as if it is an active link
		g.drawString("Link: " + link, 50, 200);
		g.setColor(Color.BLACK);
		g.drawString("Comments:", 50, 235);


	}
}
/*
* App object stores the information about a product, including its name, price, description, organization, platforms, version, and 
* a link, which is later displayed on the window of the program, if it is selected.  
*
*/
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
public class App {
  //-----------------------------------------------class members--------------------------------------------
  public String name; // String to store the name of the App
  public String price; // string to store the price of the App
  public String description; // string to store the description of the app
  public String organization; // string to store the organization of the app
  public String platforms; // string to store the platform the app runs on
  public String version; // string to store the current version the app is on
  public String link; // string to store a link to the App 
  public Image image;

  //-----------------------------------------------------methods-------------------------------------------------
  /*
  * Constructor to initialize an "App" object and fill the class members accordingly
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
    try {
		this.image = ImageIO.read(new File("world.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  // method to draw the window of the app, with the parameter Graphics g 
  public void draw(Graphics g) {
    String tempPrice = price + "";

    //drawing a rectangle to houes the information about the App
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(10, 10, 540, 300);
    g.setColor(Color.BLACK);
    g.drawRect(10, 10, 541, 300);
    g.drawRect(45, 15, 200, 15);
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
    
  }
}
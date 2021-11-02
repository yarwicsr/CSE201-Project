/*
* App object stores the information about a product, including its name, price, description, organization, platforms, version, and 
* a link, which is later displayed on the window of the program, if it is selected.  
*
*/
//-------------------various imports------------------
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
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

  //-----------------------------------------------------methods-------------------------------------------------
  /*
  * Constructor to initialize an "App" object and fill the class members accordingly
  */
  public App(String name, String description, String organization, String platforms, String version, String link, String price) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.organization = organization;
    this.platforms = platforms;
    this.version = version;
    this.link = link;
  }

  // method to draw the window of the app, with the parameter Graphics g 
  public void draw(Graphics g) {
    String tempPrice = price + "";

    //drawing a rectangle to houes the information about the App
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(10, 10, 540, 226);
    g.setColor(Color.BLACK);
    g.drawRect(10, 10, 541, 227);
    g.drawRect(45, 15, 200, 15);

    //drawing a rectangle to show where the Icon of the App will later be shown
    //in a future implementation
    g.drawRect(450, 25, 50, 50);
    g.drawString("App ", 452, 40);
    g.drawString("Icon", 452, 50);
    g.drawString("Here", 452, 60);

    //drawing the information about the app in the window
    g.drawString("Name: " + name, 50, 50);
    g.drawString("Price: $" + tempPrice, 50, 75);
    g.drawString("Description: " + description, 50, 100);
    g.drawString("Organization: " + organization, 50, 125);
    g.drawString("Version: " + version, 50, 150);
    g.drawString("Platforms: " + platforms, 50, 175);
    g.setColor(Color.BLUE); // setting the color of the link to blue, to look as if it is an active link
    g.drawString("Link: " + link, 50, 200);
    
  }
}
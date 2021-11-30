

import java.util.ArrayList;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Comment object is an object that stores information about a comment on an app, 
 * including the information of the comment, the username of the user that submitted 
 * the comment, and the credentials of the user that submitted the comment
 */
public class Comment {
	/**
	 * the actual contents of the comment
	 */
	public String text; 
	/**
	 * the username of the user that submitted the comment
	 */
	public String username; 
	/**
	 * the credentials of the user that submitted the comment
	 */
	public int userStatus; 
	
	/**
	 * This is the main constructor of the Comment object, taking in a parameter for the 
	 * text of the comment, along with the username of the user that submitted the comment
	 * @param text the text of the comment 
	 * @param username the user that submitted the comment
	 */
	public Comment(String text, String username) {
		this.text = text; // setting the text
		this.username = username; // setting the username
	}

}

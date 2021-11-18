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

public class Comment {
	public String text;
	public String username;
	public int userStatus;
	
	
	public Comment(String text, String username) {
		this.text = text;
		this.username = username;
	}

}

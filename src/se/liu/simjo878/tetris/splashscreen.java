package se.liu.simjo878.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class splashscreen extends JComponent
{
    private final ImageIcon icon = new ImageIcon(
	    ClassLoader.getSystemResource("images/TETRISMenu.png"));

    @Override
    public final int getHeight(){
	return icon.getIconHeight();
    }

    @Override
    public final int getWidth(){
	return icon.getIconWidth();
    }

    public void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			     RenderingHints.VALUE_ANTIALIAS_ON);

	final AffineTransform old = g2d.getTransform();

	// Steg 4: Gör ingenting (skala faktor 1)
	final AffineTransform at = AffineTransform.getScaleInstance(1, 1);


	// Steg 1:  Starta på (0,0)
	icon.paintIcon(this, g, 0, 0);

	g2d.setTransform(old);
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }


    public static void main(String[] args) {
	final JFrame frame = new JFrame("Graphics2D Test");
	frame.setLayout(new GridLayout(1,1));
	splashscreen splashscreen = new splashscreen();
	frame.add(splashscreen);
	System.out.println(splashscreen.getWidth() + " " + splashscreen.getHeight());
	frame.setVisible(true);
    }
}


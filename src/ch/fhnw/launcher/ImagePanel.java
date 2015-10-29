package ch.fhnw.launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by joel on 29.10.15.
 */
public class ImagePanel extends JPanel {
    BufferedImage img = null;
    public ImagePanel(String image) {
        super();
        loadImage(image);
        Dimension size = new Dimension(64,64);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0, 0, null);
    }

    void loadImage(String str) {
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

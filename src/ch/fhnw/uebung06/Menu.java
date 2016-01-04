package ch.fhnw.uebung06;

import ch.fhnw.util.bodys.FlyingCube;
import ch.fhnw.util.bodys.FlyingCuboid;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

/**
 * Created by joel on 29.10.15.
 */
public class Menu extends JFrame {

    MainQuboid caller;
    float r = 1, g = 1, b = 0;

    public Menu(MainQuboid caller) {
        super();
        this.caller = caller;
        draw();
    }

    public void draw() {
        setTitle("Storm - Settings");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLocation(500, 300);
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        JLabel textLabel= new JLabel("Set Speed");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        mainPanel.add(textLabel, constraints);

        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, (int) FlyingCube.SPEED * 100);
        speedSlider.addChangeListener(e -> {
            float val = ((JSlider) e.getSource()).getValue();
            val /= 100;
            FlyingCuboid.SPEED = val;
        });
        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0x"));
        labelTable.put(100, new JLabel("1x"));
        labelTable.put(500, new JLabel("5x"));
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setLabelTable(labelTable);
        constraints.gridy++;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(speedSlider, constraints);

        //--------------------------------------------------------
        JLabel whiteLabel = new JLabel(" ");
        constraints.gridy++;
        mainPanel.add(whiteLabel, constraints);

        constraints.gridy++;
        mainPanel.add(whiteLabel, constraints);
        //--------------------------------------------------------

        JLabel colorLabel = new JLabel("Set Color");
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        mainPanel.add(colorLabel, constraints);

        JSlider redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        redSlider.addChangeListener(e -> {
            r = ((JSlider)e.getSource()).getValue()/255f;
            caller.getCuboid().setColor(r, g, b);
        });
        Hashtable redTable = new Hashtable();
        redTable.put(0, new JLabel("R"));
        redSlider.setLabelTable(redTable);
        redSlider.setMajorTickSpacing(255);
        redSlider.setPaintLabels(true);
        redSlider.setPaintTicks(true);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy++;
        mainPanel.add(redSlider, constraints);

        JSlider greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        greenSlider.addChangeListener(e -> {
            g = ((JSlider) e.getSource()).getValue() / 255f;
            caller.getCuboid().setColor(r, g, b);
        });
        Hashtable greenTable = new Hashtable();
        greenTable.put(0, new JLabel("G"));
        greenSlider.setLabelTable(greenTable);
        greenSlider.setMajorTickSpacing(255);
        greenSlider.setPaintLabels(true);
        greenSlider.setPaintTicks(true);
        constraints.gridy++;
        mainPanel.add(greenSlider, constraints);

        JSlider blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        blueSlider.addChangeListener(e -> {
            b = ((JSlider) e.getSource()).getValue() / 255f;
            caller.getCuboid().setColor(r, g, b);
        });
        Hashtable blueTable = new Hashtable();
        blueTable.put(0, new JLabel("B"));
        blueSlider.setLabelTable(blueTable);
        blueSlider.setMajorTickSpacing(255);
        blueSlider.setPaintLabels(true);
        blueSlider.setPaintTicks(true);
        constraints.gridy++;
        mainPanel.add(blueSlider, constraints);

        setAlwaysOnTop(true);
        setSize(270, 300);
        setResizable(false);
        setVisible(true);
    }
}

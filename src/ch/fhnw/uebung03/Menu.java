package ch.fhnw.uebung03;

import ch.fhnw.glbase.GLBase1;
import ch.fhnw.util.bodys.FlyingCuboid;
import ch.fhnw.util.properties.IAnimatable;

import javax.media.opengl.GLBase;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

/**
 * Created by joel on 29.10.15.
 */
public class Menu extends JFrame implements ActionListener{

    MainStorm caller;

    public Menu(MainStorm caller) {
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
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel);

        JLabel textLabel = new JLabel("Set Speed");
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        mainPanel.add(textLabel, constraints);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 500, (int)FlyingCuboid.SPEED * 100);
        slider.addChangeListener(e -> {
            float val = ((JSlider) e.getSource()).getValue();
            val /= 100;
            FlyingCuboid.SPEED = val;
        });
        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0x"));
        labelTable.put(100, new JLabel("1x"));
        labelTable.put(500, new JLabel("5x"));
        slider.setMajorTickSpacing(100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(labelTable);
        constraints.gridy++;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(slider, constraints);

        setAlwaysOnTop(true);
        setSize(270, 140);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.equals(KeyEvent.VK_ENTER)) {
            caller.regenerateObjects(Integer.parseInt(((JTextField) e.getSource()).getText()));
        }
    }
}

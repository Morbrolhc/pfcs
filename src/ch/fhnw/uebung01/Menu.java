package ch.fhnw.uebung01;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joel on 21.09.15.
 */
public class Menu extends JPanel {

    MainTrack app;
    JTextField c1x;
    JTextField c1v;
    JTextField c1m;
    JTextField c2x;
    JTextField c2v;
    JTextField c2m;
    JButton restart = new JButton("Restart");
    JTextField tk;


    public Menu(MainTrack app) {
        this.app = app;
        setBackground(Color.WHITE);
        c1x = new JTextField(Double.toString(app.a.x));
        c1v = new JTextField(Double.toString(app.a.v));
        c1m = new JTextField(Double.toString(app.a.m));
        c2x = new JTextField(Double.toString(app.b.x));
        c2v = new JTextField(Double.toString(app.b.v));
        c2m = new JTextField(Double.toString(app.b.m));
        tk = new JTextField(Double.toString(app.k));
        setLayout(new GridLayout(11, 2));
        add(new JLabel("Cart 1:        "));
        add(new JLabel(""));
        add(new JLabel("Pos"));
        add(c1x);
        add(new JLabel("v"));
        add(c1v);
        add(new JLabel("m"));
        add(c1m);
        add(new JLabel("Cart 2:        "));
        add(new JLabel(""));
        add(new JLabel("Pos"));
        add(c2x);
        add(new JLabel("v"));
        add(c2v);
        add(new JLabel("m"));
        add(c2m);
        add(restart);
        restart.addActionListener(e -> {
            app.a.x = Float.parseFloat(c1x.getText());
            app.a.v = Float.parseFloat(c1v.getText());
            app.a.m = Float.parseFloat(c1m.getText());
            app.b.x = Float.parseFloat(c2x.getText());
            app.b.v = Float.parseFloat(c2v.getText());
            app.b.m = Float.parseFloat(c2m.getText());
        });
        add(new JLabel(""));
        add(new JLabel("k"));
        add(tk);
        JButton b = new JButton("Update k");
        b.addActionListener(e -> {
            app.k = Double.parseDouble(tk.getText());
        });
        add(b);

    }
}

package ch.fhnw.uebung01;

import java.awt.*;

/**
 * Created by joel on 21.09.15.
 */
public class Menu extends Panel {

    Application app;
    TextField c1x;
    TextField c1v;
    TextField c1m;
    TextField c2x;
    TextField c2v;
    TextField c2m;
    Button restart = new Button("Restart");
    TextField tk;


    public Menu(Application app) {
        this.app = app;
        setBackground(Color.WHITE);
        c1x = new TextField(Double.toString(app.a.x));
        c1v = new TextField(Double.toString(app.a.v));
        c1m = new TextField(Double.toString(app.a.m));
        c2x = new TextField(Double.toString(app.b.x));
        c2v = new TextField(Double.toString(app.b.v));
        c2m = new TextField(Double.toString(app.b.m));
        tk = new TextField(Double.toString(app.k));
        setLayout(new GridLayout(11, 2));
        add(new Label("Cart 1:        "));
        add(new Label(""));
        add(new Label("Pos"));
        add(c1x);
        add(new Label("v"));
        add(c1v);
        add(new Label("m"));
        add(c1m);
        add(new Label("Cart 2:        "));
        add(new Label(""));
        add(new Label("Pos"));
        add(c2x);
        add(new Label("v"));
        add(c2v);
        add(new Label("m"));
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
        add(new Label(""));
        add(new Label("k"));
        add(tk);
        Button b = new Button("Update k");
        b.addActionListener(e -> {
            app.k = Double.parseDouble(tk.getText());
        });
        add(b);

    }
}

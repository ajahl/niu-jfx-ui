package de.uniks.vs.ui.controller;

import de.uniks.vs.ui.ItemViewController;
import javafx.scene.paint.Color;

/**
 * Created by alex on 16/6/10.
 */
public abstract class ViewController extends ItemViewController {

    public abstract void setColor(Color color);

    public abstract void setSize(double width, double height);

    public abstract void setPosition(double posX, double posY);
}



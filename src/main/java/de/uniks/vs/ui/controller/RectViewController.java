package de.uniks.vs.ui.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;

/**
 * Created by alex on 16/6/10.
 */
public class RectViewController extends ViewController {


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
    }

    public void setPosition(double posX, double posY) {

        Rectangle rec = getShape();
        rec.setX(posX);
        rec.setY(posY);
    }

    @Override
    public Rectangle getShape() {
        return (Rectangle) super.getShape();
    }

    @Override
    public void setColor(Color color) {
        getShape().setFill(color);
    }

    @Override
    public void setSize(double width, double height) {
        getShape().setWidth(width);
        getShape().setHeight(height);
    }
}

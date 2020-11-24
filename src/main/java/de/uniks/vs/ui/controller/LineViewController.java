package de.uniks.vs.ui.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.beans.PropertyChangeEvent;

/**
 * Created by alex on 16/6/10.
 */
public class LineViewController extends ViewController {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
    }

    @Override
    public void setColor(Color color) {
        getShape().setStroke(color);
    }

    @Override
    public void setSize(double x, double y) {
        Line line = getShape();
        line.setStartX(x);
        line.setStartY(y);
    }

    @Override
    public void setPosition(double posX, double posY) {
        Line line = getShape();
        line.setEndX(posX);
        line.setEndY(posY);
    }

    @Override
    public Line getShape() {
        return (Line) super.getShape();
    }
}

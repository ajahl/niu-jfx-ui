package de.uniks.vs.ui;

import de.uniks.vs.graphmodel.GraphItemModel;
import javafx.scene.shape.Shape;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by alex on 16/6/10.
 */
public abstract class ItemViewController implements PropertyChangeListener {

    protected GraphItemModel item;
    protected Shape shape;

    public ItemViewController setItem(GraphItemModel item) {
        this.item = item;
        item.addPropertyChangeListener(this);
        return this;
    }

    public GraphItemModel getItem() {
        item.addPropertyChangeListener(this);
        return item;
    }

    public ItemViewController setShape(Shape shape) {
        Shape tmp = this.shape;
        this.shape = shape;
        propertyChange(new PropertyChangeEvent(this, "newShape", tmp, shape));
        return this;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(item + " " + evt.getPropertyName() + " " + shape.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}



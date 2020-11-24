package de.uniks.vs.ui;

import de.uniks.vs.ui.panes.GraphPane;
import javafx.scene.Group;

/**
 * Created by alex on 03/11/2016.
 */
public class GraphGroup extends Group {

    private GraphPane pane;

    public double getWidth() {

//        System.out.println("parent x " + ((JFXGraphPane)getParent()).getWidth());
//        System.out.println("scene  x " + getScene().getWidth());

        if (getParent() instanceof GraphPane && ((GraphPane)getParent()).getWidth() > 0)
            return ((GraphPane)getParent()).getWidth();

        else
            return getParentPane().getStartWidth();
    }



    public double getHeight() {

//        System.out.println("parent y " +((JFXGraphPane)getParent()).getHeight());
//        System.out.println("scene  y " +getScene().getHeight());

        getParentPane();

        if (getParent() instanceof GraphPane && ((GraphPane)getParent()).getHeight() > 0)
            return ((GraphPane)getParent()).getHeight();

        else
            return getParentPane().getStartHeight();
    }

    private GraphPane getParentPane() {
        return (GraphPane) getParent();
    }
}

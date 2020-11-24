package de.uniks.vs.ui.handler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * Created by alex on 05/12/2016.
 */
public class EnvironmentChangeHandler implements ChangeListener {

    public enum Type  {
        PANE_WIDTH,
        PANE_HEIGHT,
    }

    private InputHandler eventHandler;
    private Type type;


    public EnvironmentChangeHandler(InputHandler eventHandler, Type type) {
        this.eventHandler = eventHandler;
        this.type = type;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

        Double value = (Double) newValue;

        switch (type) {
            case PANE_WIDTH:
                eventHandler.updateBorderLocationX(value.floatValue());
                break;
            case PANE_HEIGHT:
                eventHandler.updateBorderLocationY(value.floatValue());
                break;
        }
    }
}

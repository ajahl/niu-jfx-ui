package de.uniks.vs.ui.panes;

import de.uniks.vs.ui.GraphGroup;
import de.uniks.vs.ui.handler.DragHandler;
import de.uniks.vs.ui.handler.EnvironmentChangeHandler;
import de.uniks.vs.ui.handler.InputHandler;
import de.uniks.vs.ui.handler.ModelChangeHandler;
import de.uniks.vs.ui.manager.ModelManager;
import de.uniks.vs.ui.manager.GraphModelManager;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;

/**
 * Created by alex on 03/11/2016.
 */
public class GraphPane extends Pane {

    private String title = "Graph";

    protected GraphModelManager modelGraphManager;
    protected GraphGroup rootNode;

    protected int height = 450;
    protected int width = 450;

    private Stage stage;
    protected ModelManager modelManager;
    private InputHandler eventHandler;

    public GraphPane(Node... children) {
        super(children);
    }

    public GraphPane(Stage stage) {
        this.stage = stage;
    }

    public void init() {

        modelGraphManager = GraphModelManager.getInstance();

        modelManager = modelGraphManager.getLast();

        if (modelManager == null)
            modelManager = new ModelManager();

        rootNode = new GraphGroup();
        this.getChildren().add(rootNode);
//        Scene scene = new Scene(rootNode, width, height, Color.LIGHTGREY);

        eventHandler = new InputHandler(rootNode, modelManager);
        this.addEventHandler(MouseEvent.ANY, eventHandler);
        DragHandler dragHandler = new DragHandler(rootNode, modelManager, this);
        EnvironmentChangeHandler wigthChangeHandler = new EnvironmentChangeHandler(eventHandler, EnvironmentChangeHandler.Type.PANE_WIDTH);
        EnvironmentChangeHandler heightChangeHandler = new EnvironmentChangeHandler(eventHandler, EnvironmentChangeHandler.Type.PANE_HEIGHT);
        this.setOnDragOver(dragHandler);
        this.setOnDragExited(dragHandler);
        this.widthProperty().addListener(wigthChangeHandler);
        this.heightProperty().addListener(heightChangeHandler);
        ModelChangeHandler changeHandler = new ModelChangeHandler(modelManager, eventHandler);
        changeHandler.handle();

    }

    public Stage getStage() {
        return stage;
    }

    public ModelManager getModelManager() {
        return modelManager;
    }

    public void addChangeListener(PropertyChangeListener listener) {
        modelManager.addPropertyChangListener(listener);
    }

    public int getStartWidth() {
        return width;
    }

    public int getStartHeight() {
        return height;
    }

    public ReadOnlyDoubleProperty fitToWidthProperty() {
        return this.widthProperty();
    }

    public ReadOnlyDoubleProperty fitToHeightProperty() {
        return this.heightProperty();
    }
}

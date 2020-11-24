package de.uniks.vs.ui.handler;

import de.uniks.vs.importer.GraphImporter;
import de.uniks.vs.ui.GraphGroup;
import de.uniks.vs.ui.manager.ModelManager;
import de.uniks.vs.ui.panes.GraphPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

import java.awt.*;
import java.io.File;

/**
 * Created by alex on 16/6/8.
 */
public class DragHandler implements  EventHandler<DragEvent>, ChangeListener {

    private final GraphGroup rootNode;
    private final ModelManager modelManager;
    private final GraphImporter importer;
    private String filePath;
    private GraphPane pane;

    public DragHandler(GraphGroup rootNode, ModelManager modelManager) {
        this.rootNode = rootNode;
        this.modelManager = modelManager;
        importer = new GraphImporter(modelManager);
    }

    public DragHandler(GraphGroup rootNode, ModelManager modelManager, GraphPane pane) {
        this(rootNode, modelManager);
        this.pane = pane;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

    }

    @Override
    public void handle(DragEvent event) {
        EventType<DragEvent> eventType = event.getEventType();
        switch (eventType.getName()) {
            case "DRAG_OVER":
                PointerInfo a2 = MouseInfo.getPointerInfo();
                Bounds bounds = pane.localToScene(pane.getBoundsInLocal());

                double minX = bounds.getMinX();
                double minY = bounds.getMinY();
                double x2 = (a2.getLocation().getX() - pane.getStage().getX() - minX);
                double y2 = (a2.getLocation().getY() - pane.getStage().getY() - minY);
//                double x2 = (a2.getLocation().getX() - pane.getStage().getX() - pane.getLayoutX());
//                double y2 = (a2.getLocation().getY() - pane.getStage().getY() - pane.getLayoutY());
                System.out.println(x2+ ", " +y2);

                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasFiles()) {
                    success = true;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                    }
                }
//                event.setDropCompleted(success);
                event.consume();
                break;
            case "DRAG_EXITED":
                PointerInfo a = MouseInfo.getPointerInfo();
//                double x1 = a.getLocation().getX();
//                double y1 = a.getLocation().getY();
//
//                double x3 = pane.getStage().getX();
//                double y3 = pane.getStage().getY();
//
//                double layoutX = pane.getLayoutX();
//                double layoutY = pane.getLayoutY();
//
//                double width = pane.getWidth();
//                double height = pane.getHeight();

                bounds = pane.localToScene(pane.getBoundsInLocal());

                minX = bounds.getMinX();
                minY = bounds.getMinY();

                double x = (a.getLocation().getX() - pane.getStage().getX() - minX);
                double y = (a.getLocation().getY() - pane.getStage().getY() - minY);

                if (x < 0 || x > pane.getWidth() || y < 24 || y > pane.getHeight()) {
                    return;
                }

                System.out.println(x+", " + y + "   " + filePath);
                importer.handle(filePath);
                event.consume();
                break;
        }

    }


}



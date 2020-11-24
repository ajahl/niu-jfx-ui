package de.uniks.vs.ui.panes;

import de.uniks.vs.ui.GraphGroup;
import de.uniks.vs.ui.handler.DragHandler;
import de.uniks.vs.ui.handler.InputHandler;
import de.uniks.vs.ui.handler.ModelChangeHandler;
import de.uniks.vs.ui.handler.ModelRuleChangeHandler;
import de.uniks.vs.ui.manager.ModelManager;
import de.uniks.vs.ui.manager.GraphModelManager;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by alex on 03/11/2016.
 */
public class GraphEditorPane extends GraphPane {

    public GraphEditorPane(Stage stage) {
        super(stage);
    }

    public void init() {

        modelGraphManager = GraphModelManager.getInstance();

        modelManager = modelGraphManager.getLast();

        if (modelManager == null)
            modelManager = new ModelManager();


        rootNode = new GraphGroup();
        this.getChildren().add(rootNode);

        InputHandler eventHandler = new InputHandler(rootNode, modelManager);
        DragHandler dragHandler = new DragHandler(rootNode, modelManager, this);
        this.addEventHandler(MouseEvent.ANY, eventHandler);
        this.setOnDragOver(dragHandler);
        this.setOnDragExited(dragHandler);
        ModelChangeHandler changeHandler = new ModelRuleChangeHandler(modelManager, eventHandler);
        changeHandler.handle();

    }
}

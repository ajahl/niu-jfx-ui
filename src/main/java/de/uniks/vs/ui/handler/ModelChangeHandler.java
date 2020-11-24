package de.uniks.vs.ui.handler;

import de.uniks.vs.ui.manager.ModelManager;
import de.uniks.vs.graphmodel.GraphEdgeModel;
import de.uniks.vs.graphmodel.GraphModel;
import de.uniks.vs.graphmodel.GraphNodeModel;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Created by alex on 16/6/10.
 */
public class ModelChangeHandler implements PropertyChangeListener {
    
	private ModelManager modelManager;
	protected InputHandler eventHandler;
	protected GraphModel model;

    public ModelChangeHandler(ModelManager modelManager) {
        this.modelManager = modelManager;
        this.modelManager.addPropertyChangListenerToModels(this);
        this.modelManager.addPropertyChangListener(this);
    }

    public ModelChangeHandler(ModelManager modelManager, InputHandler eventHandler) {
		this.modelManager = modelManager;
        this.modelManager.addPropertyChangListenerToModels(this);
        this.modelManager.addPropertyChangListener(this);
		this.eventHandler = eventHandler;
		this.eventHandler.setModelHandler(this);
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

//        System.out.println("ModelChangeHandler::propertyChange "+evt.getPropertyName());

        switch (evt.getPropertyName()) {

            case "addModel":
                GraphModel model = (GraphModel) evt.getNewValue();
                System.out.println("ModelChangeHandler::propertyChange addModel " + model.getName());

                Platform.runLater(() -> {
                    handle(model);
                });
                break;

            case "addNode":
                Platform.runLater(() -> {
                    GraphNodeModel graphNode = (GraphNodeModel) evt.getNewValue();
                    GraphModel graphModel = this.modelManager.getLastModel();

                    if (graphModel == null)
                        return;

                    if (graphModel.getType() == GraphModel.Type.DG)
                        eventHandler.createNode(graphNode, InputHandler.ROUND);
                    else
                        eventHandler.createNode(graphNode);
                });
                break;

            case "setStatus":
                handle(evt.getSource());
                break;

            case "newGraphItem":
                break;

            case "addOutEdge":
                // System.err.println("addOutEdge " + ((GraphEdge) evt.getNewValue()).getId());
                break;

            case "addInEdge":
                Platform.runLater(() -> {
                    GraphEdgeModel graphEdge = (GraphEdgeModel) evt.getNewValue();
                    GraphModel graphModel = this.modelManager.getLastModel();

                    if (graphModel == null)
                        return;

                    //System.err.println("addInEdge " + graphEdge.getId());
                    eventHandler.createEdge(graphEdge);
                });
                break;

            case "flashNode":
                GraphNodeModel graphNode = (GraphNodeModel) evt.getNewValue();
                eventHandler.flashNode(graphNode, Color.YELLOW);
                break;

            default:
                System.out.println("JFXModelChangeHandler::propertyChange " + evt.getPropertyName());

        }
    }

    private void handle(Object source) {

        if (source instanceof GraphNodeModel) {
            eventHandler.updateNode((GraphNodeModel)source);
        }

        else if (source instanceof GraphEdgeModel) {
            eventHandler.updateEdge((GraphEdgeModel)source);
        }
    }

    public void handle() {
		GraphModel model = this.modelManager.getLastModel();

        if (model != null)
		    handle(model);
	}
	
	public void handle(GraphModel model) {
		eventHandler.resetPane();
		this.model = model;
		ArrayList<GraphNodeModel> nodes = this.model.getNodes();

		for (GraphNodeModel graphNode : nodes) {
			if (model.getType() == GraphModel.Type.DG)
				eventHandler.createNode(graphNode, InputHandler.ROUND);
			else
				eventHandler.createNode(graphNode);

		}

		ArrayList<GraphEdgeModel> edges = model.getEdges();

		for (GraphEdgeModel edge : edges) {
			eventHandler.createEdge(edge);
		}
	}
	
	public ModelManager getModelManager() {
		return modelManager;
	}
}

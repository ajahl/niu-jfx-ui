package de.uniks.vs.ui.handler;

import de.uniks.vs.ui.manager.ModelManager;
import de.uniks.vs.graphmodel.GraphEdgeModel;
import de.uniks.vs.graphmodel.GraphModel;
import de.uniks.vs.graphmodel.GraphNodeModel;

import java.util.ArrayList;

/**
 * Created by alex on 03/11/2016.
 */
public class ModelRuleChangeHandler extends ModelChangeHandler {

    public ModelRuleChangeHandler(ModelManager modelManager, InputHandler eventHandler) {
        super(modelManager, eventHandler);
    }

    public void handle(GraphModel model) {
        this.model = model;
        ArrayList<GraphNodeModel> nodes = this.model.getNodes();

        for (GraphNodeModel graphNode : nodes) {
            if (model.getType() == GraphModel.Type.DG)
                eventHandler.createNode(graphNode, InputHandler.ROUND);
            else
                eventHandler.createNode(graphNode);

        }

        ArrayList<GraphEdgeModel> edges = this.model.getEdges();

        for (GraphEdgeModel edge : edges) {
            eventHandler.createEdge(edge);
        }

        if ( this.model.getModels().size() > 0)

            for (GraphModel graphModel : this.model.getModels()) {
                handle(graphModel);
            }
    }
}

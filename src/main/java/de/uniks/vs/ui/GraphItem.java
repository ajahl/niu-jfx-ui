package de.uniks.vs.ui;

import de.uniks.vs.ui.manager.GraphItemManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class GraphItem extends Group {

    protected String graphItemID;
    protected Text text;

    public Text getText() {
        return text;
    }

    public String getGraphItemID() {
        return graphItemID;
    }

    public void setGraphItemID(String graphItemID) {
        this.graphItemID = graphItemID;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Node getItem(String id) {

        for (Node node : this.getChildren()) {

            if (node instanceof GraphItem && id.equals(((GraphItem) node).getGraphItemID()))
                return node;
        }
        return null;
    }

    @Override
    public ObservableList<Node> getChildren() {
//        try {
//            throw new Exception();
//        } catch (Exception e) {
//            String[] stack = Arrays.toString(e.getStackTrace()).split(",");
//            System.out.println("---------------------------------------------------------");
//            System.out.println("1" + stack[1]);
//            System.out.println("2" + stack[2]);
//        }
        return super.getChildrenUnmodifiable();
    }

    public void removeItem(Integer id) {
        Node item = null;
        ObservableList<Node> children = this.getChildrenUnmodifiable();

        for (Node node : children) {

            if (node instanceof GraphNode
                    && id.equals(Integer.valueOf(((GraphItem) node).getGraphItemID()))) {
                item = node;
            }
        }
        if (item != null) {
            Node finalItem = item;
//            Platform.runLater(() -> {
                this.removeChild(finalItem);
//            });
            GraphItemManager.getInstance().free(finalItem);
        }
    }

    private void removeChild(Node item) {
        Platform.runLater(() -> {
            super.getChildren().remove(item);
        });
    }

    public void addChild(Node node) {
        Platform.runLater(() -> {
            super.getChildren().add(node);
        });
    }
}

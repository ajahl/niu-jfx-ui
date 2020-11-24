package de.uniks.vs.ui.handler;

import de.uniks.vs.ui.*;
import de.uniks.vs.ui.manager.ModelManager;
import de.uniks.vs.ui.panes.GraphPane;
import de.uniks.vs.graphmodel.GraphEdgeModel;
import de.uniks.vs.graphmodel.GraphItemModel;
import de.uniks.vs.graphmodel.GraphNodeModel;
import de.uniks.vs.graphmodel.VisualProperties;
import javafx.animation.StrokeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

/**
 * Created by alex on 16/6/8.
 */
public class InputHandler implements EventHandler<MouseEvent>, ChangeListener {

    public  static final double DEFAULT_WIDTH 	= 16;
    public  static final double DEFAULT_HEIGHT	= 16;

    public static final String STANDARD 	= "standard";
    public static final String ROUND 		= "round";

    private final GraphGroup rootNode;
    private ModelManager modelManager;
    private ModelChangeHandler handler;
    private Node node;
//    private Physics physics 	= new Physics();
    private boolean drag 		= false;
    private boolean linking 	= false;

    private boolean  entered 	= false;



    public InputHandler(GraphGroup rootNode) {
        this.rootNode = rootNode;
        Parent parent = rootNode.getParent();

        if (parent instanceof GraphPane) {
        GraphPane pane = (GraphPane) parent;
//        Scene scene = rootNode.getScene();
//        parent.addEventHandler(MouseEvent.ANY, this);
//        scene.widthProperty().addListener(this);
        pane.widthProperty().addListener(this);
        }
        else if (parent instanceof StackPane) {
            StackPane pane = (StackPane) parent;
            pane.widthProperty().addListener(this);
        }
    }

    public InputHandler(GraphGroup rootNode, ModelManager modelManager) {
    	this(rootNode);
//        this.rootNode = rootNode;
        this.modelManager = modelManager;

//        physics = new Physics()
//        		.withRootNode(this.rootNode)
//        		.withModelManager(this.modelManager)
//        		.withSpeed(2);

//        PhysicUpdater physicUpdater = new PhysicUpdater(physics);
//        physicUpdater.start();
    }

	@Override
    public void handle(MouseEvent event) {

        String eventType = event.getEventType().toString();
		switch (eventType) {
		 	case "MOUSE_ENTERED":
		 		createCursorNode(event.getX(), event.getY());
		 		break;
		 	case "MOUSE_EXITED":
		 		removeCursorNode();
		 		break;
		 	case "MOUSE_MOVED":
		 		updateCursorNode(event.getX(), event.getY());
//		 		physics.setRemanence(0.1);
		 		break;
            case "MOUSE_PRESSED":
                if (event.getButton() == MouseButton.PRIMARY)
                    handleLeftMouseButtonPressed(event);
                else if (event.getButton() == MouseButton.SECONDARY)
                    handleRightMouseButtonPressed(event);
                break;
            case "MOUSE_DRAGGED":
                if (event.getButton() == MouseButton.PRIMARY)
                    handleLeftMouseButtonDragged(event);
                else if (event.getButton() == MouseButton.SECONDARY)
                    handleRightMouseButtonDragged(event);
                break;
            case "MOUSE_RELEASED":
                if (event.getButton() == MouseButton.PRIMARY)
                    handleLeftMouseButtonReleased(event);
                else if (event.getButton() == MouseButton.SECONDARY)
                    handleRightMouseButtonReleased(event);
                break;
        }
    }

    private void handleRightMouseButtonPressed(MouseEvent event) {
        Node node = getPickedNode(event);
        if (!(node instanceof Rectangle && !linking))
            return;

        linking = true;
        Rectangle rectangle = (Rectangle) node;
        double posX = rectangle.getX() + DEFAULT_WIDTH/2;
        double posY = rectangle.getY() + DEFAULT_HEIGHT/2;
        this.node = createEdge(posX, posY, event.getX(),event.getY());
        this.rootNode.getChildren().add(this.node);
    }

    private void handleRightMouseButtonReleased(MouseEvent event) {
        rootNode.getChildren().remove(this.node);

        Node node = getPickedNode(event);

        if(linking && node instanceof Rectangle) {
            //TODO: add assoc to GraphModel
            //  get source and target
            //  create assoc
            //  add source and target
            linking = false;
        }
        this.node = null;
    }

    private void handleRightMouseButtonDragged(MouseEvent event) {

        if( linking && this.node instanceof Line) {
            Line line = (Line) this.node;
            line.setEndX(event.getX());
            line.setEndY(event.getY());
        }
    }

    private void handleLeftMouseButtonReleased(MouseEvent event) {
        if( drag ) {
            this.node = null;
            drag = false;
        }
        else {
            if (this.node == null)
                return;

            if(node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) this.node;
                rootNode.getChildren().remove(this.node);

                if (event.getX() - rectangle.getX() > 3 && event.getY() - rectangle.getY() > 3) {
                    //TODO: add new activity to GraphModel
                    //  create activity
//                    modelManager.createActivity();
                }

                this.node = null;
            }
        }
    }

    private Node getPickedNode(MouseEvent event) {
        PickResult pickResult = event.getPickResult();
        return pickResult.getIntersectedNode();
    }

    private void handleLeftMouseButtonDragged(MouseEvent event) {
        if(drag && node != null){

            if(node instanceof Rectangle) {
                Rectangle rectangle = (Rectangle) this.node;
                rectangle.setX(event.getX() - DEFAULT_WIDTH / 2);
                rectangle.setY(event.getY() - DEFAULT_HEIGHT / 2);
            }
        }
        else if(node instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) this.node;
            rectangle.setWidth( event.getX() - rectangle.getX());
            rectangle.setHeight( event.getY() - rectangle.getY());
        }
    }

    private void handleLeftMouseButtonPressed(MouseEvent event) {
        if (drag)
            return;

        node = createRectangle(event.getX(), event.getY());
        this.rootNode.getChildren().add(node);
    }
    
    
    
    private GraphCursor createCursorNode(double x, double y) {
    	GraphCursor cursorNode = new GraphCursor();
    	cursorNode.setId("cursor");
    	cursorNode.setX(x);
    	cursorNode.setY(y);
//    	this.rootNode.getChildren().add(cursorNode);
//    	PhysicsItem physicsItem = physics.createPhysics(cursorNode, 15.0f);
    	return cursorNode;
    }
    
    
    
    private void updateCursorNode(double x, double y) {
//    	Cursor item = (Cursor)physics.getFromPhysics("cursor");
//    	item.setPosition(x, y);
    }
    
    private void removeCursorNode() {
//    	JFXGraphNode cursorNode = null;
//    	for (Node node : this.rootNode.getChildren()) {
//			if ("cursor".equals(node.getId())) {
//				cursorNode = (JFXGraphNode)node;
//			}
//		}
//		this.rootNode.getChildren().remove(cursorNode);
//    	physics.removeFromPhysics("cursor");
    }

    private Rectangle createRectangle(double x, double y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(1);
        rectangle.setHeight(1);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(Color.RED);
        return rectangle;
    }


    private Line createEdge(double posX, double posY, double x, double y) {
        Line line = new Line();
        line.setStartX(posX);
        line.setStartY(posY);
        line.setEndX(x);
        line.setEndY(y);
        line.setStroke(Color.WHITESMOKE);
        line.setStrokeWidth(3.0);
        return line;
    }

    private Rectangle createRectangle(double x, double y, String type) {
    	Rectangle rectangle = new Rectangle();
        rectangle.setWidth(DEFAULT_WIDTH);
        rectangle.setHeight(DEFAULT_HEIGHT);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(Color.WHITESMOKE);
        
        if (STANDARD.equals(type)) {
        	rectangle.setArcWidth(20);
        	rectangle.setArcHeight(20);
        }
        else if (ROUND.equals(type)) {
        	rectangle.setWidth(30);
            rectangle.setHeight(20);
        	rectangle.setArcWidth(30);
        	rectangle.setArcHeight(20);
        }
        return rectangle;
    }
    
    public void createNode(GraphNodeModel graphNode) {
    	createNode(graphNode, STANDARD);
    }

    public void createNode(GraphNodeModel graphNode, String type) {
		long id = graphNode.getGraphItemModelID();
		Rectangle node = createRectangle(20+ id*10, 20+ id*10, type);
		node.setId(""+graphNode.getGraphItemModelID());

		if (graphNode.getType().equals("g")
				|| graphNode.getType().equals("dg_entry")) {
			node.setStroke(Color.DARKGRAY);
			node.setStrokeWidth(5);
		}
		else if (graphNode.getType().equals("e") 
				|| graphNode.getType().equals("dg_exit")) {
			node.setStroke(Color.BLACK);
			node.setStrokeWidth(5);
		}
		else  {
			node.setStroke(graphNode.getProperties().color );
			node.setStrokeWidth(5);
		}


		if (graphNode.getStatus().equals(GraphItemModel.NEW)) {
			node.setStroke(Color.LIGHTGREEN);
			node.setStrokeWidth(5);
		}
		else if (graphNode.getStatus().equals(GraphItemModel.DELETED)) {
			node.setStroke(Color.RED);
			node.setStrokeWidth(5);
		}

		if (modelManager.getLastModel().getName().startsWith("DG")
                || graphNode.getProperties().type == VisualProperties.Type.ROUND)
			node.setStrokeWidth(2);

	//	Text text = new Text(node.getX(), node.getY(), graphNode.getProperties().label!=null ? graphNode.getProperties().label :""+graphNode.getNodeName());
		Text text = new Text(node.getX(), node.getY(), graphNode.getProperties().label!=null ? graphNode.getProperties().label : "");
//		Text text = new Text(node.getX(), node.getY(), ""+graphNode.getId());
		text.setBoundsType(TextBoundsType.VISUAL);

		GraphNode jfxGraphNode = new GraphNode();
        jfxGraphNode.setId( graphNode.getGraphModel().getName() + "_" +id);
		jfxGraphNode.addItem(node, text);

        this.rootNode.getChildren().add(jfxGraphNode);
//        PhysicsItem physicsItem = this.physics.createPhysics(jfxGraphNode, 5.0f);
//
//        physicsItem.setMass(graphNode.getOrder()*2);
//
//        this.physics.setRemanence(0.1);
    }

    public void updateNode(GraphNodeModel graphNode) {
        long id = graphNode.getGraphItemModelID();
        String jfxGraphNodeId = graphNode.getGraphModel().getName() + "_" + id;

        GraphNode jfxGraphNode = getChildWithID(jfxGraphNodeId);

        Rectangle rectangle = jfxGraphNode.getRectangle();

        if (graphNode.getStatus().equals(GraphItemModel.NEW)) {
            rectangle.setStroke(Color.LIGHTGREEN);
            rectangle.setStrokeWidth(5);
        }
		else if (graphNode.getStatus().equals(GraphItemModel.DELETED)) {
            rectangle.setStroke(Color.RED);
            rectangle.setStrokeWidth(5);
        }
    }

	public void createEdge(GraphEdgeModel edge) {
		GraphNodeModel source = edge.getSource();
		GraphNodeModel target = edge.getTarget();

        long id = edge.getGraphItemModelID();

		GraphNode sourceNode = getChildWithID(source.getGraphModel().getName()+"_"+source.getGraphItemModelID());
		GraphNode targetNode = getChildWithID(source.getGraphModel().getName()+"_"+target.getGraphItemModelID());
		
		Line line = createEdge(
				sourceNode.getX() + DEFAULT_WIDTH/2, 
				sourceNode.getY() + DEFAULT_HEIGHT/2, 
				targetNode.getX() + DEFAULT_WIDTH/2, 
				targetNode.getY() + DEFAULT_HEIGHT/2);
		
		line.setId(""+id);
		
		if (GraphItemModel.NEW.equals(edge.getStatus()))
			line.setStroke(Color.LIGHTGREEN);
		
		else if (GraphItemModel.DELETED.equals(edge.getStatus()))
			line.setStroke(Color.RED);
		
		Text text = new Text(sourceNode.getX(), sourceNode.getY(),
                                edge.getProperties().label!=null ? edge.getProperties().label :""+edge.getGraphItemModelID());
		text.setBoundsType(TextBoundsType.VISUAL);
		
		if ("DD on".equals(edge.getStatus())) {
			line.getStrokeDashArray().addAll(20d, 10d);
			text.setText(id + " (DD)");
		}
		
		GraphEdge jfxGraphEdge = new GraphEdge();
        jfxGraphEdge.setId(edge.getGraphModel().getName() + "_" + id);
		jfxGraphEdge.addItem(line, text);
		this.rootNode.getChildren().add(jfxGraphEdge);
	}

    public void updateEdge(GraphEdgeModel graphEdge) {
        long id = graphEdge.getGraphItemModelID();
        String jfxGraphEdgeId = graphEdge.getGraphModel().getName() + "_" + id;
        GraphEdge jfxGraphEdge = getEdgeWithID(jfxGraphEdgeId);
        Line line = jfxGraphEdge.getLine();

        if (graphEdge.getStatus().equals(GraphItemModel.NEW))
            line.setStroke(Color.LIGHTGREEN);

        else if (graphEdge.getStatus().equals(GraphItemModel.DELETED))
            line.setStroke(Color.RED);
    }

	public GraphNode getChildWithID(String id) {
        synchronized (this.rootNode) {
            for (Node node : this.rootNode.getChildren()) {

                String nodeID = ((GraphItem)node).getGraphItemID();

                if (node instanceof GraphNode && nodeID.equals(id))
                    return (GraphNode) node;
            }
        }
		return null;
	}

	public GraphEdge getEdgeWithID(String id) {
		for (Node node : this.rootNode.getChildren()) {

			String nodeID = ((GraphItem)node).getGraphItemID();

			if (node instanceof GraphEdge && nodeID.equals(id))
				return (GraphEdge) node;
		}
		return null;
	}

	public void setModelHandler(ModelChangeHandler handler) {
		this.handler = handler;
	}

	@Override
	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//        physics.setRemanence(0.1);
	}


    public void resetPane() {
        this.rootNode.getChildren().clear();
//        physics.reset();
        createCursorNode(0, 0);
    }

    public void updateBorderLocationX(float value) {
//        ArrayList<Border> borders = physics.getBorders();
//
//        for (Border border:borders) {
//
//            if ("right".equals(border.getId()))
//                border.updateLocationX(value);
//        }
    }

    public void updateBorderLocationY(float value) {
//        ArrayList<Border> borders = physics.getBorders();
//
//        for (Border border:borders) {
//
//            if ("bottom".equals(border.getId()))
//                border.updateLocationY(value);
//        }
    }

    public void flashNode(GraphNodeModel graphNode, Color flashColor) {
        long id = graphNode.getGraphItemModelID();
        String jfxGraphNodeId = graphNode.getGraphModel().getName() + "_" + id;
        Color color = graphNode.getProperties().color;

//        Thread r = new Thread() {
//
//            @Override
//            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GraphNode jfxGraphNode = getChildWithID(jfxGraphNodeId);
//                        stroke[0] = jfxGraphNode.getRectangle().getStroke();

                        Rectangle rectangle = jfxGraphNode.getRectangle();
//                        Paint paint = rectangle.getStroke();
//                        Color strokeColor = jfxGraphNode.getStrokeColor();
//                        rectangle.setStroke(Color.YELLOW);
//                        Color color = Color.valueOf(paint.toString());

                        StrokeTransition strokeTransition = new StrokeTransition(Duration.millis(200), rectangle, color, flashColor);
                        strokeTransition.setCycleCount(2);
                        strokeTransition.setAutoReverse(true);

                        strokeTransition.play();
//                        SequentialTransition seqTransition = new SequentialTransition (
//                                new PauseTransition(Duration.millis(1000)),
//                        );
//                      seqTransition.play();
                    }
                });

//                try {
//                    sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        JFXGraphNode jfxGraphNode = getChildWithID(jfxGraphNodeId);
//                        jfxGraphNode.getRectangle().setStroke(Color.GREEN);
//                    }
//                });
//            }
//        };
//
//        r.start();

    }
}


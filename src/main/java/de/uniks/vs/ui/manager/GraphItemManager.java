package de.uniks.vs.ui.manager;

import de.uniks.vs.ui.GraphItem;
import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GraphItemManager {

    private static GraphItemManager instance;
    private ConcurrentHashMap<Node, Class> freeNodes;
    private ConcurrentHashMap<Node, Class> assignedNodes;

    private GraphItemManager() {
        freeNodes = new ConcurrentHashMap<>();
        assignedNodes = new ConcurrentHashMap<>();
    }

    public static GraphItemManager getInstance() {
        if (instance == null)
            instance = new GraphItemManager();
        return instance;
    }

    public Node get(Class clazz) {
        Optional<Node> item = freeNodes.entrySet().stream().filter(entry -> clazz.equals(entry.getValue()))
                .map(Map.Entry::getKey).findFirst();
        if (item.isPresent()) {
            freeNodes.remove(item.get());
            assignedNodes.put(item.get(), item.getClass());
            return item.get();
        }
        return null;
    }

    public GraphItem create(Class clazz) {
        GraphItem item = null;
        try {
            item = (GraphItem) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        assignedNodes.put(item, item.getClass());
        return item;
    }

    public void free(Node node) {
        assignedNodes.remove(node);
        freeNodes.put(node, node.getClass());
        System.out.println("----------------------freeNodes---------------------------");
        System.out.println("freeNodes: " + freeNodes.size());
        System.out.println("AssignedNodes: " + assignedNodes.size());
        System.out.println("---------------------------------------------------------");
    }

    public void assigned(Node node) {
        freeNodes.remove(node);
        assignedNodes.put(node, node.getClass());
        System.out.println("------------------assignedNodes---------------------------");
        System.out.println("AssignedNodes: " + assignedNodes.size());
        System.out.println("freeNodes: " + freeNodes.size());
        System.out.println("---------------------------------------------------------");
    }
}

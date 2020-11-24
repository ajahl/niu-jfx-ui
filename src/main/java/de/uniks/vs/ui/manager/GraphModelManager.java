package de.uniks.vs.ui.manager;

import java.util.ArrayList;

public class GraphModelManager {

    private static GraphModelManager instance = new GraphModelManager();

    private GraphModelManager() { }

    public static GraphModelManager getInstance() {
        return instance;
    }


    ArrayList<ModelManager> models = new ArrayList<>();

    public void add(ModelManager modelManager) {
        models.add(modelManager);
    }

    public ModelManager get(int index) {
        return models.get(index);
    }

    public ModelManager getLast() {
        for (ModelManager modelManager : models) {
            System.out.println("model: " + modelManager.getLastModelID() + "   " + models.size());
        }
        return models.size()>0 ? models.get(models.size()-1) : null;
    }
}

package de.uniks.vs.ui.panes;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.PrintStream;

public class InfoPane extends Pane {

    private Stage stage;

    public InfoPane(Stage stage) {
        this.stage = stage;
    }

    public void init() {
        TextArea textArea = new TextArea();

        this.getChildren().add(textArea);

        System.setOut(new PrintStream(System.out) {
            @Override
            public void write(byte[] buf, int off, int len) {
                super.write(buf, off, len);

                String msg = new String(buf, off, len);

                textArea.setText(textArea.getText() + msg);
            }
        });

        System.out.println("Console running ...");
    }
}

package de.uniks.vs.ui;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphEdge extends GraphItem {
	
	private Line line;

	public void addItem(Line line, Text text) {
		this.line = line;
		this.text = text;
		this.addChild(line);
		//this.getChildren().add(text);
	}
	
	public Line getLine() {
		return line;
	}

	public void setEndX(double endX) {
		line.setEndX(endX);
		 text.setX((endX - line.getStartX())/2 + line.getStartX());
	}

	public void setEndY(double endY) {
		line.setEndY(endY);
		text.setY((endY - line.getStartY())/2 + line.getStartY());
	}

	public void setStartX(double startX) {
		line.setStartX(startX);
	}

	public void setStartY(double startY) {
		line.setStartY(startY);
	}
}

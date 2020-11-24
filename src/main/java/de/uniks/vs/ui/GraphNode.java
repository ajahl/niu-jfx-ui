package de.uniks.vs.ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GraphNode extends GraphItem {
	
	private Rectangle rectangle;

	public double getX() {
		return rectangle.getX();
	}

	public double getY() {
		return rectangle.getY();
	}

	public void addItem(Rectangle rectangle, Text text) {
		this.rectangle = rectangle;
		this.rectangle.setStroke(Color.BLACK);
		this.text = text;
		this.addChild(rectangle);
		this.addChild(text);
	}

	public void setX(double x) {
		 rectangle.setX(x);
		 text.setX( x - text.getBoundsInLocal().getWidth() * text.getScaleX()/2);
	}

	public void setY(double y) {
		rectangle.setY(y);
		text.setY(y + this.getHeight()*2);
	}

    public double getWidth() {
		return rectangle.getWidth();
	}

	public double getHeight() {
		return rectangle.getHeight();
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setColor(Color color) {
		rectangle.setFill(color);
	}
}

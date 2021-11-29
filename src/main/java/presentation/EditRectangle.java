package presentation;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class EditRectangle {
    private Point2D start;
    private Point2D end;

    private Rectangle rectangle;
    private Circle leftUpCornerCircle;
    private Circle rightBottomCornerCircle;
    private int radius = 5;

    public EditRectangle(Point2D start, Point2D end){
        this.start = start;
        this.end = end;
        setUpShapes();
    }

    public EditRectangle(Point2D start){
        this.start = start;
        this.end = start;
        setUpShapes();
    }

    private void setUpShapes(){
        rectangle = new Rectangle(start.getX(),start.getY(),Math.abs(end.getX()-start.getX()),Math.abs(end.getY()-start.getY()));
        leftUpCornerCircle = new Circle(start.getX(),start.getY(),radius, Color.TRANSPARENT);
        rightBottomCornerCircle = new Circle(end.getX(),end.getY(),radius, Color.TRANSPARENT);

        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);
    }

    public void moveBottomCorner(Point2D bottomCorner){
        //We set up the start and end point (corner up left & bottom right)
        setStartAndEnd(start,bottomCorner);

        updatePosition();

    }

    private void updatePosition(){
        // We move the rectangle
        rectangle.setX(start.getX());
        rectangle.setY(start.getY());
        rectangle.setWidth(Math.abs(start.getX())-end.getX());
        rectangle.setHeight(Math.abs(start.getY()-end.getY()));

        //We move the editReactive circle
        leftUpCornerCircle.setCenterX(start.getX());
        leftUpCornerCircle.setCenterY(start.getY());

        rightBottomCornerCircle.setCenterX(end.getX());
        rightBottomCornerCircle.setCenterY(end.getY());
    }

    public void moveTopCorner(Point2D TopCorner){
        setStartAndEnd(TopCorner,end);
        updatePosition();
    }

    private void setStartAndEnd(Point2D point1, Point2D point2){
        start = new Point2D(Math.min(point1.getX(), point2.getX()),Math.min(point1.getY(), point2.getY()));
        end = new Point2D(Math.max(point1.getX(), point2.getX()),Math.max(point1.getY(), point2.getY()));

    }

    public void addToScene(Pane pane){
        pane.getChildren().add(rectangle);
        pane.getChildren().add(leftUpCornerCircle);
        pane.getChildren().add(rightBottomCornerCircle);
    }

    public void enableEdit(boolean b){
        leftUpCornerCircle.setDisable(!b);
        rightBottomCornerCircle.setDisable(!b);
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

}

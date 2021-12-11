package abstraction;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BirdBox {
    private Point2D start;
    private Point2D end;
    private int id;
    private int birdSpeciesId;

    // %%%%%%%%%%%%%% Shapes %%%%%%%%%%%%%%
    private Rectangle rectangleBox;
    private Circle topCircle;
    private Circle bottomCircle;

    private double radius = 5;

    private static final Color normalStrokeColor = Color.RED;
    private static final Color highlightStrokeColor = Color.BLUE;

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    public BirdBox(Point2D start, Point2D end){
        this.start = start;
        this.end = end;

        double minX = Math.min(start.getX(),end.getX());
        double maxX = Math.max(start.getX(),end.getX());
        double minY = Math.min(start.getY(),end.getY());
        double maxY = Math.max(start.getY(),end.getY());


        rectangleBox = new Rectangle(minX,minY, Math.abs(start.getX()-end.getX()),Math.abs(start.getY()-end.getY()));
        rectangleBox.setStroke(normalStrokeColor);
        rectangleBox.setFill(Color.TRANSPARENT);

        topCircle = new Circle(minX,minY,radius);
        bottomCircle = new Circle(maxX,maxY,radius);

        topCircle.setStroke(normalStrokeColor);
        bottomCircle.setStroke(normalStrokeColor);
        topCircle.setFill(normalStrokeColor);
        bottomCircle.setFill(normalStrokeColor);

        topCircle.setVisible(false);
        bottomCircle.setVisible(false);
    }

    public void setBirdSpecies(int id){
        birdSpeciesId = id;
    }

    // Might be usefull for species section on the software
    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public Shape[] getShapes(){return new Shape[]{rectangleBox,topCircle,bottomCircle};}

    /***
     * Edit the position of the box and the associated circles.
     * @param end
     */
    public void editBox(Point2D end) {
        this.end = end;
        double minX = Math.min(start.getX(),end.getX());
        double maxX = Math.max(start.getX(),end.getX());
        double minY = Math.min(start.getY(),end.getY());
        double maxY = Math.max(start.getY(),end.getY());

        rectangleBox.setX(minX);
        rectangleBox.setY(minY);
        rectangleBox.setWidth(Math.abs(minX-maxX));
        rectangleBox.setHeight(Math.abs(minY-maxY));

        topCircle.setCenterX(minX);
        topCircle.setCenterY(minY);
        bottomCircle.setCenterY(maxY);
        bottomCircle.setCenterX(maxX);
    }

    public void highlightBox(boolean b){
        for (Shape a: getShapes()) {
            a.setStroke(b ? highlightStrokeColor : normalStrokeColor);
        }
        topCircle.setVisible(b);
        bottomCircle.setVisible(b);
    }


}

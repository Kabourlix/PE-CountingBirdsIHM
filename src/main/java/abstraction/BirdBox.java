package abstraction;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Objects;

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
        //this.start = start;
        //this.end = end;

        double minX = Math.min(start.getX(),end.getX());
        double maxX = Math.max(start.getX(),end.getX());
        double minY = Math.min(start.getY(),end.getY());
        double maxY = Math.max(start.getY(),end.getY());

        this.start = new Point2D(minX,minY);
        this.end = new Point2D(maxX,maxY);

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
        birdSpeciesId = -1; //initally set to an impossible value.
    }

    public int getBirdSpeciesId(){return birdSpeciesId;}
    public void setBirdSpecies(int id){
        birdSpeciesId = id;
    }

    // Might be usefull for species section on the software
    public int getId(){return id;}
    public void setId(int id){this.id=id;}

    public Shape[] getShapes(){return new Shape[]{rectangleBox,topCircle,bottomCircle};}

    private void resetStartEnd(Point2D pos1, Point2D pos2){
        double minX = Math.min(pos1.getX(),pos2.getX());
        double maxX = Math.max(pos1.getX(),pos2.getX());
        double minY = Math.min(pos1.getY(),pos2.getY());
        double maxY = Math.max(pos1.getY(),pos2.getY());

        start = new Point2D(minX,minY);
        end = new Point2D(maxX,maxY);
    }

    private void setupPosition(){
        double minX = start.getX();
        double maxX = end.getX();
        double minY = start.getY();
        double maxY = end.getY();

        rectangleBox.setX(minX);
        rectangleBox.setY(minY);
        rectangleBox.setWidth(Math.abs(minX-maxX));
        rectangleBox.setHeight(Math.abs(minY-maxY));

        topCircle.setCenterX(minX);
        topCircle.setCenterY(minY);
        bottomCircle.setCenterY(maxY);
        bottomCircle.setCenterX(maxX);
    }

    /***
     * Edit the position of the box and the associated circles.
     * @param end
     */
    public void editBox(Point2D end) {

        resetStartEnd(start,end);
        setupPosition();
    }

    /***
     * This edit the box from up left corner (can be merged with the previous function easily)
     * @param start
     */
    public void editBoxTop(Point2D start) {
        resetStartEnd(start,end);
        setupPosition();
    }

    /***
     * This function move the box to another place. (and the related circle for edit)
     * @param oldCursor
     * @param newCursor
     */
    public void moveBox(Point2D oldCursor, Point2D newCursor){
        double x_move = newCursor.getX()-oldCursor.getX();
        double y_move = newCursor.getY()-oldCursor.getY();

        rectangleBox.setX(rectangleBox.getX() + x_move);
        rectangleBox.setY(rectangleBox.getY() + y_move);

        topCircle.setCenterX(topCircle.getCenterX() + x_move);
        topCircle.setCenterY(topCircle.getCenterY() + y_move);
        bottomCircle.setCenterY(bottomCircle.getCenterY() + y_move);
        bottomCircle.setCenterX(bottomCircle.getCenterX() + x_move);
    }

    /***
     * Shed a light on the box (if true) by coloring it in blue.
     * @param b
     */
    public void highlightBox(boolean b){
        for (Shape a: getShapes()) {
            a.setStroke(b ? highlightStrokeColor : normalStrokeColor);
        }
    }

    /***
     * Make the circle related to edit on the box and set the color to blue.
     * @param b
     */
    public void highlightEditModeBox(boolean b){
        highlightBox(true); // We make sure the selection is displayed for the user when he wants to edit a box.
        topCircle.setVisible(b);
        bottomCircle.setVisible(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirdBox birdBox = (BirdBox) o;
        return id == birdBox.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

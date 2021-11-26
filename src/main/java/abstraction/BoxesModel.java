package abstraction;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class BoxesModel {

    private Map<Integer, List<Box>> boxesPerImage;
    private Integer currentBox = null;

    public BoxesModel(int nbOfImages) {
        // Init the Map containing all the box information relative to each images.
        boxesPerImage = new HashMap<>();
        for(int i=0;i<nbOfImages;i++)
        {
            boxesPerImage.put(i,new ArrayList<Box>());
        }
    }

    public void addBox(int imageID, Point2D start, Point2D end, int birdID){
        boxesPerImage.get(imageID).add(new Box(start,end));
        currentBox =  boxesPerImage.get(imageID).size()-1;
    }

    /***
     * This function enables us to get the current selected Box.
     * @param imageID : the current image we are dealing with.
     * @param mousePos : The position of the mouse in the image referentiel (upper-left corner is the origin)
     */
    public void getBox(int imageID, Point2D mousePos){
        int nbBox = boxesPerImage.get(imageID).size();
        if(currentBox != null) //We deselect the previous box if there was one.
        {
            boxesPerImage.get(imageID).get(currentBox).isSelected(false);
            currentBox = null; // We reinit current box.
        }
        for(int k=0;k<nbBox;k++){
            Box b= boxesPerImage.get(imageID).get(k);
            if(b.getRectangleBox().contains(mousePos)){
                currentBox = k;
                b.isSelected(true);
                break;
            }

        }

    }

    /***
     * This function enables us to edit the current selected Box. This can be useful for drawing boxes and editing them.
     * @param imageID : the current image we are dealing with.
     * @param mousePos : The position of the mouse in the image referentiel (upper-left corner is the origin)
     */
    public void editBox(int imageID, Point2D mousePos){
        if(currentBox != null){
            boxesPerImage.get(imageID).get(currentBox).setRectangleBox(mousePos);
        }

    }

    public Rectangle getRectangleBox(int imageID){
        return boxesPerImage.get(imageID).get(currentBox).getRectangleBox();
    }



    private class Box{
        private Point2D start;
        private Point2D end;
        private int birdSpeciesId;
        private Rectangle rectangleBox;

        public Box(Point2D start, Point2D end){
            this.start = start;
            this.end = end;

            double minX = Math.min(start.getX(),end.getX());
            double minY = Math.min(start.getY(),end.getY());
            rectangleBox = new Rectangle(minX,minY, Math.abs(start.getX()-end.getX()),Math.abs(start.getY()-end.getY()));
            rectangleBox.setStroke(Color.RED);
            rectangleBox.setFill(Color.TRANSPARENT);
        }

        public void setBirdSpecies(int id){
            birdSpeciesId = id;
        }

        public Rectangle getRectangleBox() {
            return rectangleBox;
        }

        public void setRectangleBox(Point2D end) {
            this.end = end;
            double x0 = start.getX();
            double x1 = end.getX();
            double y0 = start.getY();
            double y1 = end.getY();
            rectangleBox.setX(Math.min(x0,x1));
            rectangleBox.setY(Math.min(y0,y1));
            rectangleBox.setWidth(Math.abs(x0-x1));
            rectangleBox.setHeight(Math.abs(y0-y1));
        }

        private void highlightBox(boolean b){
            if(b)
            {
                rectangleBox.setFill(new Color(255f, 0f, 0f, 0.2f));
            }
            else{
                rectangleBox.setFill(null);
            }
        }

        public void isSelected(boolean b){
            this.highlightBox(b);
        }
    }
}

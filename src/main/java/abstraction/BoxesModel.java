package abstraction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.*;

public class BoxesModel {

    private Map<Integer, List<Box>> boxesPerImage;
    private IntegerProperty currentBox;
    public IntegerProperty currentBoxProperty(){return currentBox;}
    public int getCurrentBox(){return currentBox.get();}
    public void setCurrentBox(int value){currentBox.set(value);}


    public BoxesModel(int nbOfImages) {
        // Init the Map containing all the box information relative to each images.
        boxesPerImage = new HashMap<>();
        for(int i=0;i<nbOfImages;i++)
        {
            boxesPerImage.put(i,new ArrayList<Box>());
        }
        currentBox = new SimpleIntegerProperty();
        currentBox.set(-1); // -1 is a false value telling nothing is selected
    }

    /***
     * This functions add a box to the model and to a correspond image
     * @param imageID
     * @param start : the position of the up left corner
     * @param end : the position of the bottom right corner
     */
    public void addBox(int imageID, Point2D start, Point2D end){
        boxesPerImage.get(imageID).add(new Box(start,end));
        currentBox.set(boxesPerImage.get(imageID).size()-1);
    }

    public void setBird(int imageID, int birdID){boxesPerImage.get(imageID).get(currentBox.get()).setBirdSpecies(birdID);}

    /***
     * This function enables us to get the current selected Box.
     * @param imageID : the current image we are dealing with.
     * @param mousePos : The position of the mouse in the image referentiel (upper-left corner is the origin)
     */
    public void getBox(int imageID, Point2D mousePos){
        int nbBox = boxesPerImage.get(imageID).size();
        boolean isFound = false;
        System.out.println("For the moment we have " + boxesPerImage.get(imageID).size());
        for (int k = 0; k < nbBox; k++) {
            Box b = boxesPerImage.get(imageID).get(k);
            if (b.getRectangleBox().contains(mousePos)) {
                if(currentBox.get() != -1) boxesPerImage.get(imageID).get(currentBox.get()).isSelected(false);
                currentBox.set(k);
                isFound = true;
                b.isSelected(true);
                break;
            }
        }

        if(!isFound){
            boxesPerImage.get(imageID).get(currentBox.get()).isSelected(false);
            currentBox.set(-1);
        } // Otherwise, it is the correct value
    }

    public Box getBox2(int imageID, Point2D mousePos){
        return null;
    }


    private void setBoxManually(int imageID,int k){ //!Must be modified due to property
        if(currentBox.get() != -1){boxesPerImage.get(imageID).get(currentBox.get()).isSelected(false);} //We deselect the last on
        currentBox.set(k); // We don't select it
        //TODO : Check if we need to select it.
    }

    /***
     * This function enables us to edit the current selected Box. This can be useful for drawing boxes and editing them.
     * @param imageID : the current image we are dealing with.
     * @param mousePos : The position of the mouse in the image referentiel (upper-left corner is the origin)
     */
    public void editBox(int imageID, Point2D mousePos){
        if(currentBox.get() != -1){
            boxesPerImage.get(imageID).get(currentBox.get()).setRectangleBox(mousePos);
        }

    }

    public Rectangle getRectangleBox(int imageID){
        return boxesPerImage.get(imageID).get(currentBox.get()).getRectangleBox();
    }

    public Shape[] getShapes(int imageID){
        return boxesPerImage.get(imageID).get(currentBox.get()).getShapes();
    }


    /// Reading .csv files functions

    /***
     * Get the boxes from the .csv files from localisation algorithm.
     * Format : IdPhoto IdBox UpLeftCorner (vector2) BottomRightCorner (vector2)
     * example: i1,i2,x1,y1,x2,y2
     * @param path : the path of the produced .csv file
     * @throws FileNotFoundException if the file cannot be loaded.
     */
    public void getBoxesFromCSV(String path) throws FileNotFoundException {
        try {
            BufferedReader csv = new BufferedReader(new FileReader(path));
            String row;
            while ((row = csv.readLine()) != null){
                String[] data = row.split(",");
                addBox(Integer.parseInt(data[0]),new Point2D(Float.parseFloat(data[2]),Float.parseFloat(data[3])),new Point2D(Float.parseFloat(data[4]),Float.parseFloat(data[5])));
            }
            System.out.println("The file hase been correctly loaded.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * Get the bird species of each box of each images
     * Format  : IdPhoto IdBox PredIndex PredBirdName
     * example : i1,i2,3,"MouetteRieuse"
     * @param path
     * @throws FileNotFoundException
     */
    public void getBirdSpeciesFromCSV(String path) throws FileNotFoundException {
        try{
            BufferedReader csv = new BufferedReader(new FileReader(path));
            String row;
            while ((row = csv.readLine()) != null){
                String[] data = row.split(",");
                int imageID = Integer.parseInt(data[0]);
                setBoxManually(imageID,Integer.parseInt(data[1]));
                setBird(imageID,Integer.parseInt(data[2]));
            }
            System.out.println("The file hase been correctly loaded.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private class Box{
        private Point2D start;
        private Point2D end;
        private int id;
        private int birdSpeciesId;
        private Rectangle rectangleBox;

        private Circle topCircle;
        private Circle bottomCircle;

        private double radius = 5;

        public Box(Point2D start, Point2D end){
            this.start = start;
            this.end = end;

            double minX = Math.min(start.getX(),end.getX());
            double maxX = Math.max(start.getX(),end.getX());
            double minY = Math.min(start.getY(),end.getY());
            double maxY = Math.max(start.getY(),end.getY());


            rectangleBox = new Rectangle(minX,minY, Math.abs(start.getX()-end.getX()),Math.abs(start.getY()-end.getY()));
            rectangleBox.setStroke(Color.RED);
            rectangleBox.setFill(Color.TRANSPARENT);

            topCircle = new Circle(minX,minY,radius);
            bottomCircle = new Circle(maxX,maxY,radius);

            topCircle.setStroke(Color.RED);
            bottomCircle.setStroke(Color.RED);
            topCircle.setFill(Color.RED);
            bottomCircle.setFill(Color.RED);
        }

        public void setBirdSpecies(int id){
            birdSpeciesId = id;
        }

        public Rectangle getRectangleBox() {
            return rectangleBox;
        }

        public Shape[] getShapes(){return new Shape[]{rectangleBox,topCircle,bottomCircle};}

        public void setRectangleBox(Point2D end) {
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

        private void highlightBox(boolean b){
            if(b)
            {
                rectangleBox.setFill(new Color(1f, 0f, 0f, 0.2f));
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

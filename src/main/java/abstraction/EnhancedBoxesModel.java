package abstraction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EnhancedBoxesModel {

    private List<List<BirdBox>> boxesPerImage;

    public EnhancedBoxesModel(int nbOfImage){
        boxesPerImage = new ArrayList<>();

        for (int i =0; i<nbOfImage; i++){
            boxesPerImage.add(new ArrayList<BirdBox>());
        }
    }

    /***
     * This method add a box to the model associated to the correct image.
     * @param imageID : the id of the image to which the box will be added.
     * @param start : up left corner of the box.
     * @param end : bottom right corner of the box.
     */
    public BirdBox addBox(int imageID, Point2D start, Point2D end){
        BirdBox addedBox = new BirdBox(start,end);
        boxesPerImage.get(imageID).add(addedBox);
        return addedBox;
    }

    /***
     * This method return the box associated to the position of the mouse on the ImagePane.
     * @param imageID
     * @param mousePos
     * @return
     */
    public BirdBox getBox(int imageID, Point2D mousePos){
        for(BirdBox b : boxesPerImage.get(imageID)){ // It returns the top box.
            if(b.getShapes()[0].contains(mousePos)) return b;
        }
        return null;
    }

    /***
     * Return all the boxes from a picture.
     * @param imageID
     * @return List<BirdBox> containing each boxes. Return an empty list if there is no box.
     */
    public List<BirdBox> getAllBoxes(int imageID){
        return boxesPerImage.get(imageID);
    }


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
                //TODO : Edit the box bird ID;
                //setBoxManually(imageID,Integer.parseInt(data[1]));
                //setBird(imageID,Integer.parseInt(data[2]));
            }
            System.out.println("The file hase been correctly loaded.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

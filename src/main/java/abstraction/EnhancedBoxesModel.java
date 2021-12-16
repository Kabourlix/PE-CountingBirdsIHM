package abstraction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.effect.Light;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * This class contain the boxes model and how they interact with the interface. It also stores the data of all our boxes related
 * to each pictures.
 * It is a key element of the application.
 */
public class EnhancedBoxesModel {

    private List<List<BirdBox>> boxesPerImage;
    private Map<Integer,String> speciesList;
    private int[] birdsAmount; // This count the ammount of birds on each picture

    public int getAmountForPicture(int i){return birdsAmount[i];}

    public EnhancedBoxesModel(int nbOfImage){
        boxesPerImage = new ArrayList<>();
        birdsAmount = new int[nbOfImage];
        for (int i =0; i<nbOfImage; i++){
            boxesPerImage.add(new ArrayList<BirdBox>());
            birdsAmount[i] = 0; //We initialize the amount of birds per picture
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
        addedBox.setId(boxesPerImage.get(imageID).size());
        birdsAmount[imageID] += 1;
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
     * This methods deletes a selected box
     * @param imageID : the image related.
     * @param boxToDelete : the box to delete.
     */
    public void deleteBox(int imageID,BirdBox boxToDelete){
        boxesPerImage.get(imageID).remove(boxToDelete);
        birdsAmount[imageID] -= 1;
    }

    /***
     * This function provides the species name of bird according to an associated index. (They are stored in this class)
     * @param index
     * @return string : bird species name.
     */
    public String getSpeciesName(int index){
        if(index ==-1){
            return "Unspecified";
        }else{
            return speciesList.get(index);
        }
    }

    /***
     * This function provides the species index according to an associated name.
     * @param speciesName
     * @return int : species index.
     */
    public int getSpeciesIndex(String speciesName){
        int id = -1;
        for(int i : speciesList.keySet()){
            id = speciesList.get(i).equalsIgnoreCase(speciesName) ? i : id;
        }
        if(id==-1){
            id = speciesList.size(); // We had a new id (since they are created ordonnaly starting by 0)
            speciesList.put(id, speciesName);
        }

        return id;
    }

    /***
     * This function must run the python script associated with the machine learning algorithms.
     * For the moment, it only test a "hello world" script.
     * @param localisationPath
     * @param classificationPath
     * @throws FileNotFoundException
     */
    public void runPythonCSVScripts(String localisationPath, String classificationPath) throws FileNotFoundException {
        // This is a sample code, and it works.
        try{
            ProcessBuilder pb = new ProcessBuilder("python","test.py");
            pb.directory(new File("/Users/hdamaia/IdeaProjects/PE_CountingSoftware/src/main/python"));
            pb.start();
        }catch(Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
        //getBoxesFromCSV(localisationPath);
        //getBirdSpeciesFromCSV(classificationPath);
    }


    /***
     * Get the boxes from the .csv files from localisation algorithm.
     * Format : IdPhoto IdBox UpLeftCorner (vector2) BottomRightCorner (vector2)
     * example: i1,i2,x1,y1,x2,y2
     * @param path : the path of the produced .csv file
     * @throws FileNotFoundException if the file cannot be loaded.
     */
    private void getBoxesFromCSV(String path) throws FileNotFoundException {
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
    private void getBirdSpeciesFromCSV(String path) throws FileNotFoundException {
        try{
            BufferedReader csv = new BufferedReader(new FileReader(path));
            String row;
            while ((row = csv.readLine()) != null){
                String[] data = row.split(",");
                int imageID = Integer.parseInt(data[0]);

                int birdID = Integer.parseInt(data[2]);
                isIdInSpeciesList(imageID,data[3]);
                boxesPerImage.get(imageID).get(Integer.parseInt(data[1])).setBirdSpecies(birdID);
            }
            System.out.println("The file hase been correctly loaded.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * This function checks if the id given by the user exists in our database. Otherwise, it adds it.
     * @param idToCheck
     * @param birdName
     */
    private void isIdInSpeciesList(int idToCheck,String birdName){
        for(Integer id : speciesList.keySet()){
            if(idToCheck == id) return;
        }
        // If we get off the for loop, it means the id hasn't been found
        speciesList.put(idToCheck, birdName);
    }


}

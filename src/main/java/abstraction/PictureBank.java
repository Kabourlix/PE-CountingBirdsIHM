package abstraction;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This class is aimed at loading and selecting the current picture displayed on the application.
 */
public class PictureBank {

    private Image[] picturesBank;
    private int nbPicturesFailedToLoad = 0;

    private IntegerProperty currentIndex;
    public void setCurrentIndex(int i){currentIndex.set(i);}
    public int getCurrentIndex(){return currentIndex.get();}
    public IntegerProperty getCurrentIndexProperty(){return currentIndex;}

    public int getImagesLength(){return picturesBank.length;}
    public PictureBank(String _path){
        LoadPictureBank(_path);

    }

    private void LoadPictureBank(String bankPath) {
        final File dir = new File(bankPath);
        final String[] extension = {"png","jpg","jpeg"};
        final FilenameFilter image_filter = new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                for(final String ext : extension){
                    if(name.endsWith("."+ext)){
                        return (true);
                    }
                }
                return false;
            }
        };
        try{
            if(dir.isDirectory()){ //We make sure we are in a directory
                File[] files = dir.listFiles(image_filter); // We create the list of all the file to extract
                if(files != null){
                    picturesBank = new Image[files.length]; //We init the pictureBank list.

                    for(int k = 0 ; k<files.length;k++){
                        try {
                            picturesBank[k] = new Image(files[k].toURI().toURL().toExternalForm()); // We add the picture to the list
                        }catch (Exception e){
                            e.printStackTrace();
                            nbPicturesFailedToLoad++;
                        }
                    }
                    System.out.println("The bank was loaded. Failed : " + nbPicturesFailedToLoad +". It contains " + picturesBank.length + "images.");
                }
            }else{
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Image getImage(int index){
        if(index >= 0 && index<picturesBank.length) // if the given index is correct
        {
            return picturesBank[index];
        }else{
            System.out.println("The given index is incorrect.");
            return null;

        }

    }

    public static void main(String[] args) { // ça marche
        System.out.println("--- Start testing ---");
        PictureBank pb = new PictureBank("/Users/hdamaia/IdeaProjects/PE_CountingSoftware/pictures");
    }
}

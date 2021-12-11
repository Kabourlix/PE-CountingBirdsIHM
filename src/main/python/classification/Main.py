import sys
sys.path.append(r"C:\Users\selma\Documents\IMT Atlantique\Projet entreprise\Classification\src")

from Model import Model
from Training import Training
from DeviceDataLoader import DeviceDataLoader
from Data import Data
from ConfusionMatrix import ConfusionMatrix

class Main:
    """
    Classe principale du projet, permettant la gestion du réseau de neurones.
    """
    
    def __init__(self):
        """
        Constructeur de la classe Main.
        """
        
        self.data = Data()
        
        self.train_dataloader = DeviceDataLoader(self.data.train_dataloader)
        self.valid_dataloader = DeviceDataLoader(self.data.valid_dataloader)
        
        self.device = self.train_dataloader.get_device()
        
        self.training = Training()
        
        self.model = self.train_dataloader.to_device(
            Model(num_classes=self.data.number_of_species), self.device)
        
        self.confusion_matrix = ConfusionMatrix(self.model, self.data,
                                                self.train_dataloader,
                                                self.device)
        
    def launch_training(self):
        """
        Lance l'entraînement du réseau de neurones.
        """
        
        self.training.train_model(self.model, self.train_dataloader,
                                  self.valid_dataloader)
        
    def show_confusion_matrix(self):
        """
        Affiche la matrice de confusion du modèle.
        """
        
        self.confusion_matrix.show_confusion_matrix()
      
    def save_model(self):
        """
        Sauvegarde le modèle.
        """  
        
        self.model.save_model()
        
    def load_model(self):
        """
        Charge un modèle sauvegardé.
        """
        
        self.model.load_model()
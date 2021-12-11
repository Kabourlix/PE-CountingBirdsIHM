# Ce script permet de générer le fichier csv des prédictions.

import torchvision.transforms as T
import torch

from PIL import Image

import os
import csv

from Main import Main

if __name__ == '__main__':
    
    if os.path.isfile('predictions.csv'):
        os.remove('predictions.csv')
    
    pred_dict = {
        0 : 'BecasseauSanderling',
        1 : 'BernacheCravant',
        2 : 'GoelandArgente',
        3 : 'MouetteRieuse',
        4 : 'PluvierArgente'}

    transform = T.Compose([
        T.Resize((128,128)),
        T.ToTensor()])
    
    main = Main()
    
    main.load_model()
    
    rows = []
    header = ['id1', 'id2', 'prediction index', 'prediction']
    rows.append(header)
    
    for img_name in os.listdir('img'):
        row = list(map(int, img_name.split('.')[0].split('_')[1:]))
        
        img = Image.open('img/' + img_name)
        img = transform(img)
        
        xb = main.train_dataloader.to_device(img.unsqueeze(0), main.device)
        out = main.model(xb)
        _, pred = torch.max(out, dim=1)
        
        pred = pred.item()
        
        row.append(pred)
        row.append(pred_dict[pred])
        
        rows.append(row)
        
    with open('predictions.csv', 'w', encoding='UTF8', newline='') as f:
        writer = csv.writer(f)
            
        writer.writerows(rows)
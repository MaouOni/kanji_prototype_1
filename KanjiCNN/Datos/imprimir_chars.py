import os
from configparser import ConfigParser

# Cargar el archivo de configuración
config = ConfigParser()
config.read("/content/Project/Proyecto/New/config.ini")

# Rutas definidas en el archivo de configuración
RUTA_MODELO = config.get("Rutas", "MODEL_PATH")
RUTA_CLASES = config.get("Rutas", "CLASS_PATH")
RUTA_PREDICCION = config.get("Rutas", "PREDICT_PATH")
RUTA_IMAGENES_ENTRENAMIENTO = config.get("Rutas", "TRAINING_IMAGES_PATH")
RUTA_IMAGENES_NO_PROCESADAS = config.get("Rutas", "UNPROCESSED_IMAGES_PATH")

# Iterar sobre las etiquetas en la ruta de imágenes de entrenamiento
for etiqueta in sorted(os.listdir(RUTA_IMAGENES_ENTRENAMIENTO)):
    print(chr(int(etiqueta, 16)), end=" ")

# Mostrar el total de caracteres
print("\nTotal: " + str(len(os.listdir(RUTA_IMAGENES_ENTRENAMIENTO))) + " caracteres")

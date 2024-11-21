import tensorflow as tf
from tensorflow import keras
from keras.layers import RandomZoom, RandomTranslation, RandomRotation

import os
from configparser import ConfigParser

# Configuración de rutas a partir del archivo
config = ConfigParser()
config.read("/content/Project/Proyecto/New/config.ini")
RUTA_CLASES = config["Rutas"]["RUTA_CLASES"]
RUTA_PREDICCION = config["Rutas"]["RUTA_PREDICCION"]
RUTA_MODELO = config["Rutas"]["RUTA_MODELO"]
RUTA_IMAGENES_NO_PROCESADAS = config["Rutas"]["RUTA_IMAGENES_NO_PROCESADAS"]
RUTA_IMAGENES_ENTRENAMIENTO = config["Rutas"]["RUTA_IMAGENES_ENTRENAMIENTO"]

TAMANO_LOTE = 128  # Definir el tamaño del lote

# Crear archivo CSV para etiquetas
claves_clases = sorted(os.listdir(RUTA_IMAGENES_ENTRENAMIENTO))
with open(RUTA_CLASES, "w") as archivo_clases:
    archivo_clases.write(",".join(claves_clases))

# Imprimir el inicio del proceso de creación del conjunto de entrenamiento
print("\nCreación del conjunto de entrenamienton\n")

# Secuencia para la aumentación de datos
aumentacion_datos = keras.Sequential([
    RandomZoom(0, 0.25),
    RandomTranslation((-0.1, 0.1), (-0.1, 0.1)),
    RandomRotation(0.02)
])

# Cargar el conjunto de entrenamiento
conjunto_entrenamiento = keras.preprocessing.image_dataset_from_directory(
    RUTA_IMAGENES_ENTRENAMIENTO,
    labels="inferred",
    label_mode="int",
    color_mode="grayscale",
    batch_size=TAMANO_LOTE,
    image_size=(64, 64),
    shuffle=True,
    seed=420,
    validation_split=0.10,
    subset="training"
)

# Aplicar aumentación de datos
conjunto_entrenamiento = conjunto_entrenamiento.map(
    lambda x, y: (aumentacion_datos(x, training=True), y)
)

# Imprimir el inicio del proceso de creación del conjunto de validación
print("\n================= Creación del conjunto de validación =================\n")

# Cargar el conjunto de validación
conjunto_validacion = keras.preprocessing.image_dataset_from_directory(
    RUTA_IMAGENES_ENTRENAMIENTO,
    labels="inferred",
    label_mode="int",
    color_mode="grayscale",
    batch_size=TAMANO_LOTE,
    image_size=(64, 64),
    shuffle=True,
    seed=420,
    validation_split=0.10,
    subset="validation"
)

# Aplicar aumentación de datos en el conjunto de validación
conjunto_validacion = conjunto_validacion.map(
    lambda x, y: (aumentacion_datos(x, training=True), y)
)
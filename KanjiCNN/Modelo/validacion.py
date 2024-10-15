import tensorflow as tf
import keras

import cv2
import numpy as np

from configparser import ConfigParser
config = ConfigParser()
config.read("/content/Project/Proyecto/New/config.ini")
RUTA_MODELO = config.get("Rutas", "MODEL_PATH")
RUTA_CLASES = config.get("Rutas", "CLASS_PATH")
RUTA_PREDICCION = config.get("Rutas", "PREDICT_PATH")
RUTA_IMAGENES_ENTRENAMIENTO = config.get("Rutas", "TRAINING_IMAGES_PATH")
RUTA_IMAGENES_NO_PROCESADAS = config.get("Rutas", "UNPROCESSED_IMAGES_PATH")

# Configurar el crecimiento de memoria
gpus = tf.config.list_physical_devices('GPU')
try:
    tf.config.experimental.set_memory_growth(gpus[0], True)
except:
    pass

# Abrir la lista de clases generada por dataset_creation.py
archivo_clases = open(RUTA_CLASES, "r")
claves_clases = archivo_clases.read().split(",")
archivo_clases.close()

print("\n====================================================================")
print("Procesando imagen\n")

im = cv2.imread(RUTA_PREDICCION)
im = cv2.cvtColor(im, cv2.COLOR_BGR2GRAY)

for y in range(0, im.shape[0]):
    for x in range(0, im.shape[1]):
        if im[y][x] < 237:
            im[y][x] = 1
        else:
            im[y][x] = 0

im = np.atleast_3d(im)
im = tf.image.resize(im, [64, 64])
im = np.expand_dims(im, 0)

print("\n====================================================================")
print("Cargando modelo\n")

modelo = keras.models.load_model(RUTA_MODELO)

salida = np.squeeze(modelo(im, training=False))

# Imprimir la salida del modelo
print("Salida del modelo:", salida)

print("Mejores predicciones")
for i in np.flipud(np.argsort(salida)[-5:]):
    print(chr(int(claves_clases[i], 16)))

print("Mejor predicción")
print(chr(int(claves_clases[np.argmax(salida)], 16)))

import tensorflow as tf
import keras
import cv2
import numpy as np
import matplotlib.pyplot as plt
from configparser import ConfigParser

config = ConfigParser()
config.read("/home/jvralph/Vídeos/Proyecto/New/config.ini")
RUTA_MODELO = config.get("Rutas", "RUTA_MODELO")
RUTA_CLASES = config.get("Rutas", "RUTA_CLASES")
RUTA_PREDICCION = config.get("Rutas", "RUTA_PREDICCION")

# Lista de Kanji generada por dataset_creation.py
archivo_clases = open(RUTA_CLASES, "r")
claves_clases = archivo_clases.read().split(",")
archivo_clases.close()

print("Procesar Imagen\n")

# Leer la imagen (fondo blanco, trazo negro)
im = cv2.imread(RUTA_PREDICCION, cv2.IMREAD_GRAYSCALE)

# Invertir los colores de la imagen: fondo blanco -> negro, trazo negro -> blanco
im = cv2.bitwise_not(im)

# Kernel pequeño para la dilatación
#kernel = np.ones((5, 5), np.uint8)  
#im = cv2.dilate(im, kernel, iterations=1)

# Redimensionar la imagen con interpolación adecuada para preservar detalles
im = cv2.resize(im, (64, 64), interpolation=cv2.INTER_AREA)

# Asegurar que los colores se mantengan y ajustar formato de entrada
im = np.atleast_3d(im)
im = np.expand_dims(im, -1)  # Agregar la dimensión de canal
im = np.expand_dims(im, 0)   # Agregar dimensión para el batch


# Mostrar la imagen procesada para verificar que los colores se invirtieron correctamente
plt.imshow(np.squeeze(im), cmap='gray')
plt.show()

print("\n Modelo\n")


# Cargar el modelo entrenado
modelo = keras.models.load_model(RUTA_MODELO)

modelo.summary()

# Realizar la predicción
salida = np.squeeze(modelo(im, training=False))

print("Mejores predicciones")
for i in np.flipud(np.argsort(salida)[-7:]):
    probabilidad = salida[i] * 100
    print(f"{chr(int(claves_clases[i], 16))} (Clase: {claves_clases[i]}), Probabilidad: {probabilidad:.2f}%")

# Mostrar la mejor predicción
print("Mejor predicción")
mejor_clase = np.argmax(salida)
probabilidad_mejor = salida[mejor_clase] * 100
print(f"{chr(int(claves_clases[mejor_clase], 16))} (Clase: {claves_clases[mejor_clase]}), Probabilidad: {probabilidad_mejor:.2f}%")
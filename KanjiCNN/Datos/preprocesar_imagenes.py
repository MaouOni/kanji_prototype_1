import os
import cv2

# Definir rutas
RUTA_IMAGENES_NO_PROCESADAS = '/home/jvralph/Proyecto/Nuevo/Datos/ETL_DATABASE/imagenes/ETL9G/'
RUTA_IMAGENES_PROCESADAS = '/home/jvralph/Proyecto/Nuevo/Datos/ETL_DATABASE/imagenes_procesadas/'

# Crear el directorio de salida si no existe
if not os.path.exists(RUTA_IMAGENES_PROCESADAS):
    os.makedirs(RUTA_IMAGENES_PROCESADAS)

# Función para preprocesar una imagen
def preprocesar_imagen(ruta_imagen):
    # Cargar la imagen en escala de grises
    imagen = cv2.imread(ruta_imagen, cv2.IMREAD_GRAYSCALE)

    # Binarización: aplicar un umbral para convertir en blanco y negro
    _, imagen_binaria = cv2.threshold(imagen, 237, 255, cv2.THRESH_BINARY_INV)

    return imagen_binaria

# Función para procesar todas las imágenes del dataset
def procesar_imagenes(ruta_entrada, ruta_salida):
    # Iterar sobre todas las carpetas (caracteres) en el dataset
    for carpeta_kanji in os.listdir(ruta_entrada):
        ruta_carpeta_kanji = os.path.join(ruta_entrada, carpeta_kanji)
        
        if os.path.isdir(ruta_carpeta_kanji):  # Asegurarse de que sea un directorio
            carpeta_salida_kanji = os.path.join(ruta_salida, carpeta_kanji)
            
            # Crear la carpeta para las imágenes procesadas si no existe
            if not os.path.exists(carpeta_salida_kanji):
                os.makedirs(carpeta_salida_kanji)
            
            # Procesar todas las imágenes dentro del directorio del kanji
            for archivo_imagen in os.listdir(ruta_carpeta_kanji):
                if archivo_imagen.endswith('.png'):
                    ruta_imagen = os.path.join(ruta_carpeta_kanji, archivo_imagen)
                    
                    # Preprocesar la imagen
                    imagen_procesada = preprocesar_imagen(ruta_imagen)
                    
                    # Guardar la imagen procesada
                    ruta_imagen_salida = os.path.join(carpeta_salida_kanji, archivo_imagen)
                    cv2.imwrite(ruta_imagen_salida, imagen_procesada)
                    
                    print(f"Imagen procesada guardada: {ruta_imagen_salida}")

# Llamar a la función para procesar las imágenes
procesar_imagenes(RUTA_IMAGENES_NO_PROCESADAS, RUTA_IMAGENES_PROCESADAS)

print("Procesamiento de imágenes completado.")

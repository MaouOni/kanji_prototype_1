import os
from PIL import Image

# Ruta de la carpeta con las imágenes originales
ruta_entrada = '/content/Project/Proyecto/New/Modelo/Validacion/128'
# Ruta de la carpeta para guardar las imágenes redimensionadas
ruta_salida = '/content/Project/Proyecto/New/Modelo/Validacion/64'


# Recorrer todas las imágenes en la carpeta de entrada
for nombre_imagen in os.listdir(ruta_entrada):

    if nombre_imagen.endswith(('.png')):

        ruta_imagen_original = os.path.join(ruta_entrada, nombre_imagen)
        imagen = Image.open(ruta_imagen_original)
        imagen_redimensionada = imagen.resize((64, 64))
        ruta_guardado = os.path.join(ruta_salida, nombre_imagen)
        imagen_redimensionada.save(ruta_guardado)
        print(f"Imagen redimensionada guardada en: {ruta_guardado}")
        
print("Proceso de redimensionamiento completado.")

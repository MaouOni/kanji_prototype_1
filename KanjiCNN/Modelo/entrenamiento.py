import tensorflow as tf
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten, Dropout, BatchNormalization, PReLU
from keras.callbacks import EarlyStopping,ModelCheckpoint

from configparser import ConfigParser
from creacion_data import conjunto_entrenamiento, conjunto_validacion

# Leer archivo de configuración
config = ConfigParser()
config.read("/content/Project/Proyecto/New/config.ini")

# Parámetros y rutas desde el archivo de configuración
RUTA_MODELO = config["Rutas"]["RUTA_MODELO"]
RUTA_IMAGENES_ENTRENAMIENTO = config["Rutas"]["RUTA_IMAGENES_ENTRENAMIENTO"]

TAM_LOTE = 124  # Tamaño de lote optimizado para la GPU
EPOCAS = 20  # Número de épocas para entrenar

print("\nConstrucción del modelo")

# Crear el modelo secuencial con capas
modelo = Sequential([
    Conv2D(16, (5, 5), activation='relu', input_shape=(64, 64, 1)), 
    #Entrada: 16 filtros de 5x5 (probar 7x7) , activacion relu para no linealidad , imagenes de 64x64 en escala de grises
    #Salida: Tensor de dimensiones 60x60x16
    BatchNormalization(),#Valores por defecto, momentum=0.99,epsilon, center=True, scale=True
    MaxPooling2D(),#Valores por defecto, poolsize(2x2), strides=None -> esto quiere decir que son igual al poolsize,padding=valid, dataformat=none
    Dropout(0.1),#Desechar 10% de neuronas 

    Conv2D(32, (3, 3), activation='relu'),
    #Entrada: 32 filtros de 3x3
    BatchNormalization(),
    MaxPooling2D(),
    Dropout(0.1),

    Flatten(), # Solo pasa las capas anteriores a 1 dimension para la capa densa
    Dense(512, activation='relu'), # Capa densa de 512 de 512 neuronas
    Dropout(0.3), #Desechar 30% de neuronas
    
    Dense(2965, activation='softmax')#Capa densa de 2965 neuronas , cada una representa 1 clase (Kanji), softmax para convertir valores a probabilidades
])

# Configurar la compilación del modelo
modelo.compile(
    loss='sparse_categorical_crossentropy',
    optimizer="adam",
    metrics=['accuracy']
)

# Resumen del modelo
modelo.summary()

# Callbacks configurados
checkpoint = ModelCheckpoint( #Callback para poder retomar el modelo cuando termine tiempo en VM
    filepath=RUTA_MODELO,
    monitor='val_accuracy',
    save_best_only=True,
    verbose=1,
    mode='max'
)

early_stopping = EarlyStopping( #Callback para parar entrenamiento si los valores de val_loss son iguales durante 2 epocas
    monitor='val_loss',
    patience=2,
    verbose=1,
    restore_best_weights=True
)

print("\nIniciando entrenamiento")

# Iniciar el entrenamiento y guardar el historial
historial = modelo.fit(
    conjunto_entrenamiento, #Imagenes de entrenamiento
    validation_data=conjunto_validacion, # Imagenes de validacion
    epochs=EPOCAS, #Epocas de entrenamiento
    batch_size=TAM_LOTE, # Tamaño del lote 
    callbacks=[checkpoint, early_stopping] #Callbacks
)

# Guardar el modelo final
modelo.save(RUTA_MODELO)

# Imprimir las claves del historial para ver las métricas disponibles
print(historial.history.keys())
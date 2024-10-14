import tensorflow as tf
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten, Dropout, BatchNormalization, PReLU
from keras.callbacks import EarlyStopping, ModelCheckpoint

from configparser import ConfigParser
from creacion_data import conjunto_entrenamiento, conjunto_validacion

# Leer archivo de configuración
config = ConfigParser()
config.read("/content/Project/Proyecto/New/config.ini")

# Parámetros y rutas desde el archivo de configuración
RUTA_MODELO = config["Rutas"]["MODEL_PATH"]
RUTA_IMAGENES_ENTRENAMIENTO = config["Rutas"]["TRAINING_IMAGES_PATH"]

TAMANIO_LOTE = 124  # Tamaño de lote optimizado para la GPU
EPOCAS = 20  # Número de épocas para entrenar

print("\n================= Construcción del modelo =================")

# Crear el modelo secuencial con capas
modelo = Sequential([
    Conv2D(16, (5, 5), activation='relu', input_shape=(64, 64, 1)),
    BatchNormalization(),
    PReLU(),
    MaxPooling2D(),
    Dropout(0.1),

    Conv2D(32, (3, 3), activation='relu'),
    BatchNormalization(),
    PReLU(),
    MaxPooling2D(),
    Dropout(0.1),

    Flatten(),
    Dense(6000, activation='relu'),
    Dropout(0.1),
    
    Dense(2965, activation='softmax')
])

# Configurar la compilación del modelo
modelo.compile(
    loss=tf.losses.SparseCategoricalCrossentropy(),
    optimizer="adam",
    metrics=['accuracy']
)

# Resumen del modelo
modelo.summary()

# Callbacks configurados
checkpoint = ModelCheckpoint(
    filepath=RUTA_MODELO, 
    monitor='val_accuracy', 
    save_best_only=True, 
    verbose=1, 
    mode='max'
)

early_stopping = EarlyStopping(
    monitor='val_loss', 
    patience=2, 
    verbose=1, 
    restore_best_weights=True
)

print("\n================= Iniciando entrenamiento =================")

# Iniciar el entrenamiento
modelo.fit(
    conjunto_entrenamiento, 
    epochs=EPOCAS, 
    validation_data=conjunto_validacion, 
    batch_size=TAMANIO_LOTE,
    callbacks=[checkpoint, early_stopping]
)

# Guardar el modelo final
modelo.save(RUTA_MODELO)

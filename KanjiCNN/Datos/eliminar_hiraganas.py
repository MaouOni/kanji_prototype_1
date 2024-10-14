import os
import shutil

for imagen in os.listdir("Datos/ETL_DATABASE/imagenes/ETL9G"):
    if int(imagen, 16) >= 12353 and int(imagen, 16) <= 12436:
        print("eliminando " + imagen + " " + chr(int(imagen, 16)))
        shutil.rmtree("Datos/ETL_DATABASE/imagenes/ETL9G/" + imagen)

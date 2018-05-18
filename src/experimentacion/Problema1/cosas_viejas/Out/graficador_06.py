import matplotlib.pyplot as plt
import csv, sys
import numpy as np

#-----Formato esperado
#	A, C, TIEMPO
#	0, 1, 1928731823
#---------------------

inputFile = sys.argv[1]
#outputFile = sys.argv[2]

#---variables generales
cant_repeticiones = 19
#nombresCasos = np.array(["Iguales", "Algunas Diferencias", "Pocas Diferencias", "Uno Muy Diferente"])
nombresCasos = np.array(["Iguales", "Algunas Diferencias", "Pocas Diferencias"])
posicionCaso = np.zeros(len(nombresCasos))

#--abro el archivo de entrada (CSV)
entrada = csv.reader(open(inputFile, "rb"))

#--inicializo la matriz de datos
datos = np.zeros((len(nombresCasos), cant_repeticiones))

#--recorro el archivo de entrada y voy llenando la matriz de datos
for row in entrada:
	datos[int(row[0])][posicionCaso[int(row[0])]] = row[1]
	posicionCaso[int(row[0])] = posicionCaso[int(row[0])] + 1

x_pos = np.arange(len(nombresCasos))

plt.bar(x_pos, datos[:,0] , align='center', alpha=0.4)
plt.xticks(x_pos, nombresCasos)
plt.ylabel('Tiempo de ejecucion')
plt.xlabel('Caso de entrada')
plt.title('0 Arq y 6 Can')
plt.show()
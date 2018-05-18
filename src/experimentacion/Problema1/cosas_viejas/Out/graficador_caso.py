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
casos = np.array([[0, 1], [0, 2], [0, 3], [0, 4], [0, 5], [1, 0], [1, 1], [2, 0], [2, 1], [2, 2], [3, 0], [3, 1], [3, 2], [3, 3], [4, 0], [4, 1], [4, 2], [5, 0], [5, 1]])
nombresCasos = np.array(["0,1", "0,2", "0,3", "0,4", "0,5", "1,0", "1,1", "2,0", "2,1", "2,2", "3,0", "3,1", "3,2", "3,3", "4,0", "4,1", "4,2", "5,0", "5,1"])
hashCasos = np.zeros((7,7))
posicionCaso = np.zeros(len(casos))

#---lleno la matriz de hash de casos
k = 0
for i in xrange(0,len(casos)):
	hashCasos[casos[i][0], casos[i][1]] = k
	k = k+1

#--abro el archivo de entrada (CSV)
entrada = csv.reader(open(inputFile, "rb"))

#--inicializo la matriz de datos
datos = np.zeros((len(casos), cant_repeticiones))

#--recorro el archivo de entrada y voy llenando la matriz de datos
for row in entrada:
	indiceCaso = hashCasos[row[0], row[1]]
	datos[indiceCaso][posicionCaso[indiceCaso]] = row[2]
	posicionCaso[indiceCaso] = posicionCaso[indiceCaso] + 1


datosGraph = np.zeros(len(casos))

#--calculo la mediana
for i in xrange(0,len(datos)):
	datosGraph[i] = np.median(datos[i][0:posicionCaso[i]:1])

#plt.yscale('log')
#plt.plot(datos[:,0], nombresCasos, marker='o', linestyle = ':')
#plt.xlabel("Estado")
#plt.ylabel("Error")
#plt.title("Grafico de convergencia del error")
#plt.legend()
#plt.savefig(outputFile)
#plt.show()

x_pos = np.arange(len(casos))

plt.bar(x_pos, datosGraph , align='center', alpha=0.4)
plt.xticks(x_pos, nombresCasos)
plt.ylabel('Tiempo de ejecucion')
plt.xlabel('Cantidad de personas [Arqueologos, Canibales]')
plt.title('Caso Uno Muy Diferente')
plt.show()
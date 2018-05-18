import matplotlib.pyplot as plt
import sys, csv, math
import numpy as np

#-----Formato esperado-------------------------------
#--filas, columnas, paredes, num_repeticion, tiempo--
#----------------------------------------------------

entrada_bu = csv.reader(open("salida_bu.csv", "rb"))
entrada_td = csv.reader(open("salida_td.csv", "rb"))

labelx = "#Total de tesoros x Capacidad total"
labely = "Tiempo de ejecucion (ms)"
titulo = "#Tesoros y Capacidad total vs Tiempo de ejecucion"

ejex = np.empty((0))
cotaLineal = np.empty((0))
ejey1 = np.empty((0))
ejey2 = np.empty((0))

maximo = 0;

cant_actual = -1
datos_actual = np.empty((0))
for row in entrada_bu:
	if cant_actual != int(row[1])*int(row[2]):
		if cant_actual!=-1:
			datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
			datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
			ejex = np.append(ejex, np.array([cant_actual]), axis=0)
			if datos_actual[np.argmax(datos_actual)] > maximo:
				maximo = datos_actual[np.argmax(datos_actual)]
			cotaLineal = np.append(cotaLineal, np.array([200 + float(cant_actual)/4]), axis=0)
			ejey1 = np.append(ejey1, np.array([np.mean(datos_actual)]), axis=0)
		cant_actual = int(row[1])*int(row[2])
		datos_actual = np.empty((0))
	datos_actual = np.append(datos_actual, [float(row[4])])

if cant_actual!=-1:
	datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
	datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
	ejex = np.append(ejex, np.array([cant_actual]), axis=0)
	if datos_actual[np.argmax(datos_actual)] > maximo:
		maximo = datos_actual[np.argmax(datos_actual)]
	cotaLineal = np.append(cotaLineal, np.array([200 + float(cant_actual)/4]), axis=0)
	ejey1 = np.append(ejey1, np.array([np.mean(datos_actual)]), axis=0)



cant_actual = -1
datos_actual = np.empty((0))
for row in entrada_td:
	if cant_actual != int(row[1])*int(row[2]):
		if cant_actual!=-1:
			datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
			datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
			if datos_actual[np.argmax(datos_actual)] > maximo:
				maximo = datos_actual[np.argmax(datos_actual)]
			ejey2 = np.append(ejey2, np.array([np.mean(datos_actual)]), axis=0)
		cant_actual = int(row[1])*int(row[2])
		datos_actual = np.empty((0))
	datos_actual = np.append(datos_actual, [float(row[4])])

if cant_actual!=-1:
	datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
	datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
	if datos_actual[np.argmax(datos_actual)] > maximo:
		maximo = datos_actual[np.argmax(datos_actual)]
	ejey2 = np.append(ejey2, np.array([np.mean(datos_actual)]), axis=0)


plt.plot(ejex, ejey1)
plt.plot(ejex, ejey2)
plt.plot(ejex, cotaLineal)

x1,x2,y1,y2 = plt.axis()
plt.axis((x1,x2,0,maximo * 1.05))

plt.xlabel(labelx)
plt.ylabel(labely)
plt.title(titulo)
plt.legend(["Bottom Up","Top Down", "f(n) = 200 + 1/4 * n"], loc='lower right')

plt.savefig("salida.png")
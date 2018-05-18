import matplotlib.pyplot as plt
import csv, sys
import numpy as np

#-----Formato esperado
#	A, C, TIEMPO
#	0, 1, 1928731823
#---------------------

#---variables generales
estrategias = np.array([0,1,2])
nombresEstrategias = np.array(["Estrategia 1", "Estrategia 2", "Estrategia 3"])

width = 0.2
color = np.array(['y','r','g'])

for j in estrategias:
	entrada = csv.reader(open("salida_"+str(j)+".csv", "rb"))

	can_act = -1
	arq_act = -1
	ejex = np.empty((0))
	ejey = np.empty((0))
	for row in entrada:
		if arq_act != row[0] or can_act != row[1]:
			if arq_act != -1 and can_act != -1:
				datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
				datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
				ejex = np.append(ejex, np.array([str(arq_act)+","+str(can_act)]), axis=0)
				ejey = np.append(ejey, np.array([np.mean(datos_actual)]), axis=0)
			arq_act = row[0]
			can_act = row[1]
			datos_actual = np.empty((0))
		datos_actual = np.append(datos_actual, [float(row[2])])
	if arq_act != -1 and can_act != -1:
		datos_actual = np.delete(datos_actual, np.argmax(datos_actual))
		datos_actual = np.delete(datos_actual, np.argmin(datos_actual))
		ejex = np.append(ejex, np.array([str(arq_act)+","+str(can_act)]), axis=0)
		ejey = np.append(ejey, np.array([np.mean(datos_actual)]), axis=0)

	plt.bar(np.arange(len(ejey))+(width*j), ejey, width, color=color[j])

plt.xticks(np.arange(len(ejex)),ejex)
plt.ylabel('Tiempo de ejecucion (ms)')
plt.xlabel('Cantidad de personas [Arqueologos, Canibales]')
plt.title('Caso Algunas Diferencias')
plt.legend(nombresEstrategias, loc='upper center')
plt.yscale('symlog')
plt.savefig("algunas_diferencias.png")
plt.show()

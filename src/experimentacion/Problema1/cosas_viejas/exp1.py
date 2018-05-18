import numpy as np
import csv, commands, time, sys

path = "/home/laucha/laucha/algo3segundocuatrimestre/"

casos = np.array([[0, 1], [0, 2], [0, 3], [0, 4], [0, 5], [0, 6], [1, 0], [1, 1], [2, 0], [2, 1], [2, 2], [3, 0], [3, 1], [3, 2], [3, 3], [4, 0], [4, 1], [4, 2], [5, 0], [5, 1], [6, 0]]);

cant_repeticiones = 10

archivos_in = np.array([ 'iguales', 'algunas_diferencias', 'uno_muy_diferente', 'pocas_diferencias' ])
estrategias = np.array([0, 1, 2])

for estr_indice in xrange(0, len(estrategias)):

	for arch_indice in xrange(0,len(archivos_in)):

		sys.stdout.write("\n")
		sys.stdout.write("\n")
		sys.stdout.write("--------------------------------------------------------------\n")
		sys.stdout.write("-------------INICIA ARCHIVO: "+str(archivos_in[arch_indice])+" - ESTRATEGIA: "+str(estrategias[estr_indice])+"\n")
		sys.stdout.flush()

		salida = csv.writer(open('Out/%s.csv' % str(archivos_in[arch_indice])+str(estrategias[estr_indice]), "wb"))

		fo = open('In/%s.in' % str(archivos_in[arch_indice]), "r")

		lineas = fo.readlines()
		entrada = ""
		i = 0
		k = 0
		for j in xrange(0, len(lineas)):
			if i < 2:
				entrada = entrada + lineas[j]
				i = i + 1
			elif i == 2:

				sys.stdout.write("----Inicia caso: "+str(casos[k][0])+"A - "+str(casos[k][1])+"C\n")
				sys.stdout.flush()
				for repeticion in xrange(1,cant_repeticiones):
					sys.stdout.write(str(repeticion)+"/"+str(cant_repeticiones)+"\n");
					sys.stdout.flush()
					tiempo = 0
					asd, tiempo = commands.getstatusoutput("echo '"+entrada+"' | java -cp "+path+"tp1Nuevo/src/ soluciones/Ejercicio1 "+str(estrategias[estr_indice]))
					salida.writerow([casos[k][0], casos[k][1], tiempo])

				i = i + 1
				k = k+1
				entrada = ""
			elif i == 3:
				i = 0
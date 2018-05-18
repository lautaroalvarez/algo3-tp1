import numpy as np
import csv, commands, time, sys

path = "/home/laucha/laucha/algo3segundocuatrimestre/"

casos = np.array([[0, 1], [0, 2], [0, 3], [0, 4], [0, 5], [0, 6], [1, 0], [1, 1], [2, 0], [2, 1], [2, 2], [3, 0], [3, 1], [3, 2], [3, 3], [4, 0], [4, 1], [4, 2], [5, 0], [5, 1], [6, 0]]);

cant_repeticiones = 10

estrategias = np.array([0, 1, 2])

for estr_indice in xrange(0, len(estrategias)):

	sys.stdout.write("\n")
	sys.stdout.write("\n")
	sys.stdout.write("--------------------------------------------------------------\n")
	sys.stdout.write("-------------INICIA ESTRATEGIA: "+str(estrategias[estr_indice])+"\n")
	sys.stdout.flush()

	salida = csv.writer(open('salida_%s.csv' % str(estrategias[estr_indice]), "wb"))

	fo = open('entrada.in', "r")

	lineas = fo.readlines()
	entrada = str(estrategias[estr_indice])+" "
	i = 0
	k = 0
	for j in xrange(0, len(lineas)):
		if i < 3:
			entrada = entrada + lineas[j]
			i = i + 1
		elif i == 3:

			sys.stdout.write("----Inicia caso: "+str(casos[k][0])+"A - "+str(casos[k][1])+"C\n")
			sys.stdout.flush()
			for repeticion in xrange(1,cant_repeticiones):
				sys.stdout.write(str(repeticion)+"/"+str(cant_repeticiones)+"\n");
				sys.stdout.flush()
				tiempo = 0
				asd, tiempo = commands.getstatusoutput("echo '"+entrada+"' | java -cp "+path+"tp1Nuevo/src/ soluciones/Ejercicio1")
				salida.writerow([casos[k][0], casos[k][1], tiempo])

			i = 0
			k = k+1
			entrada = str(estrategias[estr_indice])+" "
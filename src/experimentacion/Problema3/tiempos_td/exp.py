import numpy as np
import csv, commands, sys, math

path = "/home/laucha/laucha/algo3segundocuatrimestre/"

archivo_salida = path + "tp1Nuevo/src/experimentacion/Problema3/tiempos_td/salida"

#--constantes
mochilas = 1
maximo_tesoros = 30
cant_repeticiones = 10
ciclo = 3

sys.stdout.write("\n")
sys.stdout.write("\n")
sys.stdout.write("---------------------------------------------------------\n")
sys.stdout.write("-------------MEDIR TIEMPOS TOP DOWN----------------------\n")
sys.stdout.flush()

#--variables
capacidades = 3
tesoros = 1

salida = csv.writer(open('%s.csv' % str(archivo_salida), "wb"))

cont = 0
cont2 = 0
while tesoros < maximo_tesoros:
	for i in xrange(0,cant_repeticiones):
		sys.stdout.write("--- tesoros: "+str(tesoros)+" capacidades: "+str(capacidades)+" rep: "+str(i)+"/"+str(cant_repeticiones)+"\n")
		sys.stdout.flush()
		
		commands.getstatusoutput("python "+path+"tp1Nuevo/src/experimentacion/Problema3/generadorCasos.py "+path+"tp1Nuevo/src/experimentacion/Problema3/caso.in "+str(int(mochilas))+" "+str(int(capacidades))+" "+str(int(tesoros)))

		status, tiempo = commands.getstatusoutput("cat "+path+"tp1Nuevo/src/experimentacion/Problema3/caso.in | java -cp "+path+"tp1Nuevo/src/ soluciones/Ej3td")

		salida.writerow([int(mochilas), int(capacidades), int(tesoros), int(i), tiempo])

	if cont == ciclo:
		cont = 0
		ciclo = ciclo + 1
		tesoros = tesoros + 1
	else:
		cont = cont + 1
		if cont2 == 1:
			cont2 = 0
			capacidades = capacidades + 1
		else:
			cont2 = cont2 + 1
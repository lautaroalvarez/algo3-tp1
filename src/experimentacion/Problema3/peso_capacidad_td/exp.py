import numpy as np
import csv, commands, sys, math

path = "/home/laucha/laucha/algo3segundocuatrimestre/"

archivo_salida = path + "tp1Nuevo/src/experimentacion/Problema3/peso_capacidad_td/salida"

#--constantes
mochilas = 1
maximo_tesoros = 500
cant_repeticiones = 10

sys.stdout.write("\n")
sys.stdout.write("\n")
sys.stdout.write("-------------------------------------------------------\n")
sys.stdout.write("----------------PESO CAPACIDAD TOP DOWN----------------\n")
sys.stdout.flush()

#--variables
capacidades = 1
tesoros = 1

salida = csv.writer(open('%s.csv' % str(archivo_salida), "wb"))

cont = 0
while tesoros < maximo_tesoros:
	for i in xrange(0,cant_repeticiones):
		sys.stdout.write("--- tesoros: "+str(tesoros)+" capacidades: "+str(capacidades)+" rep: "+str(i)+"/"+str(cant_repeticiones)+"\n")
		sys.stdout.flush()
		
		commands.getstatusoutput("python "+path+"tp1Nuevo/src/experimentacion/Problema3/generadorCasos.py "+path+"tp1Nuevo/src/experimentacion/Problema3/caso.in "+str(int(mochilas))+" "+str(int(capacidades))+" "+str(int(tesoros))+" 2")

		status, tiempo = commands.getstatusoutput("cat "+path+"tp1Nuevo/src/experimentacion/Problema3/caso.in | java -cp "+path+"tp1Nuevo/src/ soluciones/Ej3td")

		salida.writerow([int(mochilas), int(capacidades), int(tesoros), int(i), tiempo])

	tesoros = tesoros + math.ceil(tesoros * 0.15)
	capacidades = tesoros
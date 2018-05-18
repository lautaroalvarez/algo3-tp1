import sys
import numpy as np
from random import randint


archivo_salida = sys.argv[1]
cant_mochilas = int(sys.argv[2])
capacidad_mochilas = int(sys.argv[3])
cant_tesoros = int(sys.argv[4])
caso = 0
if len(sys.argv) > 5:
	caso = int(sys.argv[5])

f = open(archivo_salida, 'w')

cant_tipos_tesoros = randint(1,cant_tesoros)

#-----ESCRIBE CANTIDADES INICIALES
f.write(str(cant_mochilas)+" "+str(cant_tipos_tesoros)+"\n")

#-----COMPLETA MOCHILAS
maxima_capacidad = 0
cant_capacidades_restantes = capacidad_mochilas
for i in xrange(1, cant_mochilas+1):
	if i == cant_mochilas:
		nueva_capac = cant_capacidades_restantes
	else:
		nueva_capac = randint(1, cant_capacidades_restantes - (cant_mochilas-i))
		cant_capacidades_restantes = cant_capacidades_restantes - nueva_capac

	if nueva_capac > maxima_capacidad:
		maxima_capacidad = nueva_capac

	if i > 1:
		f.write(str(" "))
	f.write(str(nueva_capac))

f.write("\n")


#-----COMPLETA TESOROS
cant_tesoros_restantes = cant_tesoros
for i in xrange(1, cant_tipos_tesoros+1):
	if i == cant_tipos_tesoros:
		nueva_cant = cant_tesoros_restantes
	else:
		nueva_cant = randint(1, cant_tesoros_restantes - (cant_tipos_tesoros-i))
		cant_tesoros_restantes = cant_tesoros_restantes - nueva_cant

	nuevo_valor = randint(1, 10000)
	if caso == 1:
		nuevo_peso = 1
	elif caso == 2:
		nuevo_peso = maxima_capacidad + 1
	else:
		nuevo_peso = randint(1, maxima_capacidad)

	f.write(str(nueva_cant)+" "+str(nuevo_peso)+" "+str(nuevo_valor)+"\n")

f.close()
import os, sys
import time
import matplotlib.pyplot as plt
import numpy as np
import pylab
import random
import subprocess

#from scipy.optimize import curve_fit


lista_tiempos = []
promedios = [0]
desviaciones = [0]

j = 2
while j<160:
    sting1 = '2 1'
    sting2 = str(j/2) + ' ' + str(j/2)
    sting3 = str(j) + ' 1' + ' 1'
    os.system('echo ' + sting1 + ' > a')
    os.system('echo ' + sting2 + ' >> a')
    os.system('echo ' + sting3 + ' >> a')
    for i in range(1000):
        aux = subprocess.check_output('java -Xmx2048m soluciones.Ejercicio3 < a', shell=True)
        lista_tiempos.append(int(aux))
    #print('La lista de tiempos de ' + str(j))
    #print(lista_tiempos)
    #promedios.append(np.mean(lista_tiempos))
    print(str(j) + ', ' + str(np.mean(lista_tiempos)) + ', '+ str(np.std(lista_tiempos)))
    #desviaciones.append(np.std(lista_tiempos))
    lista_tiempos = []
    j = j+10


#x = [x+1 for x in range(3)]
#y = np.array(promedios)
#e = np.array(desviaciones)

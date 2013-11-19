#!/usr/bin/python

from numpy import math

clusters = [[40.0, 20.0],[10.0, 30.0]]
#clusters=[[35.0, 15.0],[5.0, 5.0],[10.0, 30.0]]
#clusters=[[50.0, 0.0],[0.0, 50.0]]

N = sum( [sum(cluster) for cluster in clusters] )

h1 = 0.0
for row in clusters:
	h1 += -1.0 * sum( row ) / N * math.log( sum(row) / N, 2 )

classSums=[0.0 for x in range( len( clusters[0] ) )]

h2 = 0.0
for i in range( len( clusters[0] ) ):
	for row in clusters:
		classSums[i] += row[i]
	h2 += -1.0 * classSums[i] / N * math.log( classSums[i] / N, 2 )

print "Summed", classSums
print "H1:", h1, "H2:", h2

numerator = 0.0
for row in clusters:
	for i, val in enumerate( row ):
		if val != 0.0:
			numerator += ( val / N ) * math.log( ( val * N ) / ( sum ( row ) * classSums[i] ), 2 )

print "NMI:", ( 2 * numerator ) / ( h1 + h2 )

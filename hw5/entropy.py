#!/usr/bin/python

from numpy import math

#clusters = [[40.0, 20.0],[10.0, 30.0]]
#clusters=[[35.0, 15.0],[5.0, 5.0],[10.0, 30.0]]
clusters=[[50.0, 0.0],[0.0, 50.0]]

entropy = 0.0
for row in clusters:
	rowTotal = 0.0
	for i in row:
		if i != 0:
			rowTotal += -1 * (i/sum(row)) * math.log( (i/sum(row)), 2 )
	entropy += ( sum(row) / 100.0 ) * rowTotal

print "Entropy:", entropy

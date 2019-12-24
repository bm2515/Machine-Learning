import sys
for line in sys.stdin:
	val = line.strip().split('\t')
	(Time, Precipitation) = (val[0],val[5])
		
	print "%s\t%s" % (Time,Precipitation)
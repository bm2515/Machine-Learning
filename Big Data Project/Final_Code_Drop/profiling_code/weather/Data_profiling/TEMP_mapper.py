import sys
for line in sys.stdin:
	val = line.strip().split('\t')
	(Time, TEMP) = (val[0],val[4])		
	print "%s\t%s" % (Time,TEMP)
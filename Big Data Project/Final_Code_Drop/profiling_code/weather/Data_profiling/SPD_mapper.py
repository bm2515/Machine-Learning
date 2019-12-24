import sys
for line in sys.stdin:
	val = line.strip().split('\t')
	(Time, SPD) = (val[0],val[1])		
	print "%s\t%s" % (Time,SPD)
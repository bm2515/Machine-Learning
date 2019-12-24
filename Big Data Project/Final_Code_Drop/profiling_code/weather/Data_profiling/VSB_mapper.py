import sys
for line in sys.stdin:
	val = line.strip().split('\t')
	(Time, VSB) = (val[0],val[2])
	if VSB != '****':		
		print "%s\t%s" % (Time,VSB)
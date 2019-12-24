import sys

(max_val,min_val) = (-sys.maxint, sys.maxint)
for line in sys.stdin:
	(key,val) = line.strip().split('\t')
	min_val = min(int(val),min_val)
	max_val = max(int(val),max_val)

	
print '%s\t%s' %(max_val, min_val)
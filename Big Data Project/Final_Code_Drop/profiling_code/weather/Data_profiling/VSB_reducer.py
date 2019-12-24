import sys

(max_val,min_val) = (-float("inf"), float("inf"))

for line in sys.stdin:
	(key,val) = line.strip().split('\t')
	min_val = min(float(val),min_val)
	max_val = max(float(val),max_val)
	
print '%s\t%s' %(max_val, min_val)
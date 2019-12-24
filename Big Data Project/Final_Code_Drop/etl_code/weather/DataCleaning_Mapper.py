import sys
infile = sys.stdin
next(infile)
for line in sys.stdin:
	val = line.strip()
	#extract only the columns I'm interested
	(YR_MODAHRMN,SPD,VSB,Weather_code,TEMP,PCP01)\
	= (val[13:25],val[30:33],val[52:56],val[69:71],val[83:87],val[121:126])
	#filter out the rows with missing important wind speed or temperature values
	if SPD != '***' and TEMP != '****':
		#fill in the missing Weather_code with 00
		if Weather_code == '**':
			Weather_code = '00'
		#fill in the missing precipitation with 0.00:
		if PCP01 == '*****':
			PCP01 = ' 0.00 '
		
		print "%s\t%s\t%s\t%s\t%s\t%s" % (YR_MODAHRMN,SPD,VSB,Weather_code,TEMP,PCP01)

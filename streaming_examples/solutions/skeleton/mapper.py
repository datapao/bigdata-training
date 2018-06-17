#!/usr/bin/env python3

import sys

for line in sys.stdin:
    line = line.rstrip('\n')
    d = line.split(',')

    key = d[0] 
    value = d[1]

    print(key + "\t" + value)

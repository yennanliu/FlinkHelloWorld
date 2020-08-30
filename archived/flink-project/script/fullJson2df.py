import pandas as pd 
import json
from pandas.io.json import json_normalize    

with open('data/Sidewalk_201912.GeoJSON') as data_file:    
    data = json.load(data_file)  

tmp = []
for item in data['features']:
    if item['properties']['PSTART'] and item['properties']['PEND']:
        tmp.append([ item['properties']['COUNTY_NA'],
                     item['properties']['VILL_NAME'],
                     item['properties']['PSTART'].replace('\n',''),
                     item['properties']['PEND'].replace('\n',''),
                     item['properties']['Shape_STLe']])

columns = ['COUNTY_NA', 'VILL_NAME', 'PSTART','PEND', 'Shape_STLe']
df = pd.DataFrame(tmp, columns=columns)
print (df.columns)
print (">>> save to data/sidewalk.csv")
df.to_csv('data/sidewalk_no_header.csv', sep="|", header=False)
df.to_csv('data/sidewalk.csv', sep="|")

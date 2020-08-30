import pandas as pd 
import json
from pandas.io.json import json_normalize    

with open('data/sample2.json') as data_file:    
    data = json.load(data_file)  

df = json_normalize(data['features'])

print (df.columns)

df_ = df[
         ['properties.COUNTY_NA',
         'properties.VILL_NAME', 
         'properties.PSTART',
         'properties.PEND',
         'properties.Shape_STLe']
         ]
df_.columns = ['COUNTY_NA', 'VILL_NAME', 'PSTART','PEND', 'Shape_STLe']

print (">>> save to data/sample2.csv")
df_.to_csv('data/sample2.csv', header=False)
df_.to_csv('data/sample3.csv')
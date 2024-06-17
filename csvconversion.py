import pandas as pd
from pandas import json_normalize
import json
 
# Load the JSON file
json_file_path = 'code-scanning-alerts.json'
with open(json_file_path, 'r') as f:
    data = json.load(f)
 
# Normalize the nested JSON
df = json_normalize(data)
 
# Convert the DataFrame to a CSV file
csv_file_path = 'code-scanning-alerts.csv'
df.to_csv(csv_file_path, index=False)
 
print(f"Nested JSON file has been converted to CSV file at: {csv_file_path}")

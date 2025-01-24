import pandas as pd
import os

def get_csvfile(filename):
    # Build the file path relative to the Python script
    current_dir = os.path.dirname(__file__)  # Directory of the current Python file: /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app
    filepath = os.path.join(current_dir, filename)
    print(f"Constructed file path: {filepath}") # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/dataset.csv

    # Check if the file exists at the constructed path
    if not os.path.exists(filepath):
        return f"Error: File not found at {filepath}"
    try:
        df = pd.read_csv(filepath)
        datatype = type(df)
        print(f"the data type of this is: {datatype}")

        df_short = df.head(3)
        print(df_short)

        return df_short, filepath
    except Exception as e:
        return f"Error reading file: {str(e)}"



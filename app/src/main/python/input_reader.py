import pandas as pd
import os

def get_csvfile(filename):
    """
    input = csv file
    the input will be converted to pandas dataframe
    output = first 3 rows of the dataframe and file path
    """

    # Build the file path relative to the Python script
    current_dir = os.path.dirname(__file__)  # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app
    filepath = os.path.join(current_dir, filename) # input csv file name is passed to here
    print(f"Constructed file path: {filepath}") # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/dataset.csv

    # Check if the file exists at the constructed path
    if not os.path.exists(filepath):
        return f"Error: File not found at {filepath}"
    try:
        df = pd.read_csv(filepath)

        df_short = df.head(3)
        print(df_short)

        return df_short, filepath
    except Exception as e:
        return f"Error reading file: {str(e)}"
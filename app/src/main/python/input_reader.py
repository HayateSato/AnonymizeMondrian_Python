import pandas as pd
import os

def get_csvfile(filename):
    # Build the file path relative to the Python script
    current_dir = os.path.dirname(__file__)  # Directory of the current Python file
    filepath = os.path.join(current_dir, filename)

    print(f"Current directory: {current_dir}")  # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/dataset.csv
    print(f"Constructed file path: {filepath}")
    absolute_dataset_path = os.path.abspath(filepath)
    print(f"Dataset absolute path: {absolute_dataset_path}")

    if not os.path.exists(filepath):
        return f"Error: File not found at {filepath}"

    try:

        df = pd.read_csv(filepath)
        # return df.head(5)
        print(f"the data type of this is: {type(df)}")


        df_short = df.head(3)
        print(df_short)

        return df_short, filepath
    except Exception as e:
        return f"Error reading file: {str(e)}"



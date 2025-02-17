import os
import pandas as pd
import time
import algorithm.hierarchy_tree as h_tree

def summarized(partition, dim, qi_list):
    for qi in qi_list:
        partition.sort_values(by=qi, inplace=True)
        if partition[qi].iloc[0] != partition[qi].iloc[-1]:
            s = f"{partition[qi].iloc[0]}-{partition[qi].iloc[-1]}"
            partition[qi] = s
    return partition

def anonymize(partition, ranks, k, qi_list):
    dim = ranks[0][0]
    partition.sort_values(by=dim, inplace=True)
    si = len(partition)
    mid = si // 2
    left_partition = partition.iloc[:mid]
    right_partition = partition.iloc[mid:]
    if len(left_partition) >= k and len(right_partition) >= k:
        return pd.concat([anonymize(left_partition, ranks, k, qi_list), anonymize(right_partition, ranks, k, qi_list)])
    return summarized(partition, dim, qi_list)

def mondrian(partition, qi_list, k):
    ranks = {qi: partition[qi].nunique() for qi in qi_list}
    ranks = sorted(ranks.items(), key=lambda item: item[1], reverse=True)
    return anonymize(partition, ranks, k, qi_list)

def map_text_to_num(df, qi_list, hierarchy_tree_dict):
    for column in qi_list:
        hierarchy_tree = hierarchy_tree_dict[column]
        mapping = {leaf.value: leaf_id for leaf_id, leaf in hierarchy_tree.leaf_id_dict.items()}
        df[column] = df[column].map(mapping)
    return df

def map_num_to_text(df, qi_list, hierarchy_tree_dict):
    for column in qi_list:
        hierarchy_tree = hierarchy_tree_dict[column]
        df[column] = df[column].apply(lambda x: hierarchy_tree.leaf_id_dict[str(x)].value if str(x).isdigit() else hierarchy_tree.find_common_ancestor(*map(str, x.split('-'))).value)
    return df
def check_k_anonymity(df, qi_list, k):
    return all(len(group) >= k for _, group in df.groupby(qi_list))
def run_anonymize(qi_list, data_file, hierarchy_file_dir, k=5):
    df = pd.read_csv(data_file, usecols=qi_list)
    hierarchy_tree_dict = h_tree.build_all_hierarchy_tree(hierarchy_file_dir)
    df = map_text_to_num(df, qi_list, hierarchy_tree_dict)
    if df.isnull().values.any():
        df.fillna(1, inplace=True)
        print("Error: NaN values found in the data frame. Please remove or handle them before anonymizing the data.")
    df = mondrian(df, qi_list, k)
    if not check_k_anonymity(df, qi_list, k):
        raise Exception("Not all partitions are k-anonymous")
    df = map_num_to_text(df, qi_list, hierarchy_tree_dict)
    return df

def anonymize_execute(k_value):
    tic = time.time()
    print("running anonymization")
    k = k_value
    print(f"run_anonymize executing with K = {k}")

    current_dir = os.path.dirname(__file__)  # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/algorithm
    parent_dir = os.path.dirname(current_dir)  # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app

    input_dir = os.path.join(parent_dir, "input/") # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/input
    input_path = os.path.join(input_dir, "dataset.csv")

    quasi_identifiers = ['sex', 'age', 'race', 'marital-status', 'education', 'native-country', 'workclass', 'occupation']
    sensitive_attributes = ['salary-class']
    identifiers = ['ID', 'soc_sec_id', 'given_name', 'surname']

    hierarchy_file_path = os.path.join(current_dir, "hierarchy/")  # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/algorithm/hierarchy
    data_frame = run_anonymize(quasi_identifiers, input_path, hierarchy_file_path, k)

    output_dir = os.path.join(parent_dir, "output/anonymized/") # /data/data/com.example.pythoncalculation/files/chaquopy/AssetFinder/app/output/anonymized
    output_file_path = os.path.join(output_dir, f'k_{k}_anonymized_dataset.csv')
    try:
        data_frame.to_csv(output_file_path, index=False)
        print(f"Anonymized data saved to: {output_file_path}")
    except Exception as e:
        print(f"Error saving file: {str(e)}")

    toc = time.time()
    execution_time = toc - tic
    print(f"Execution time: {execution_time:.2f} seconds")

    df_short = data_frame.iloc[850:890]
    print(df_short)
    return df_short
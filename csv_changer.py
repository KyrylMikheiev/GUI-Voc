import pandas as pd

# Define the file paths
input_file = 'LatinVocabParser/VocabAPI/voc_list.csv'
output_file = 'output.csv'

# Load the CSV file into a DataFrame
df = pd.read_csv(input_file, delimiter=';', header=None, names=['Latein', 'Deutsche Übersetzung', 'Wortart', 'Lektion'])

# Add an 'id' column
df['id'] = range(0, len(df))
df = df.drop(0)

# Save the modified DataFrame to a new CSV file
df.to_csv(output_file, index=False, sep=';')

print("New CSV file with 'id' column has been created successfully!")

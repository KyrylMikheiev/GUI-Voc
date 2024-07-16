import pandas as pd

# Define the file paths
input_file = 'LatinVocabParser/VocabAPI/voc_list.csv'
output_file = 'output.csv'

# Load the CSV file into a DataFrame
df = pd.read_csv(input_file, delimiter=';', header=None, names=['Latein', 'Deutsche Übersetzung', 'Wortart', 'Lektion', 'id'])

# Add an 'id' column
#df['id'] = range(0, len(df))
#df = df.drop(0)


#print all rows where id is 1
x = df[df['Lektion'] == "1"]
print(x)

# Save the modified DataFrame to a new CSV file
#df.to_csv(output_file, index=False, sep=';')

import mysql.connector

# Connect to MySQL
try:
    connection = mysql.connector.connect(
        host="localhost",
        user="username",
        password="password",
        database="vocabdb"
    )

    if connection.is_connected():
        print("Connected to MySQL database")

    # Create a cursor object
    cursor = connection.cursor()

    # Execute a query
    query = "SELECT * FROM vocabs"
    cursor.execute(query)

    # Fetch the results
    rows = cursor.fetchall()

    # Process the results
    for row in rows:
        print(row)  # Print each row

except mysql.connector.Error as e:
    print("Error connecting to MySQL:", e)

finally:
    # Close the cursor and connection
    if 'connection' in locals() and connection.is_connected():
        cursor.close()
        connection.close()
        print("MySQL connection is closed")

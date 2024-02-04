import requests

BASE_URL = "https://vt.jo-dev.net/"

def create_account(firstName, lastName, email, password, modePreference, userClass):
    url = BASE_URL + "?action=createAccount"
    data = {
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "password": password,
        "modePreference": modePreference,
        "class": userClass
    }
    response = requests.post(url, json=data)
    return response.status_code

def login(email, password):
    url = BASE_URL + "?action=login"
    data = {
        "email": email,
        "password": password
    }
    response = requests.post(url, json=data)
    return response.json()

# Example usage:
if __name__ == "__main__":
    # Create account
    account_info = create_account("John", "Doe", "john.doe@example.com", "password123", 1, 1)
    print("Create account response:", account_info)

    # Login
    login_info = login("johnsdoe@example.com", "password123")
    print("Login response:", login_info)

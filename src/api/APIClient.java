package src.api;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import src.auth.AuthManager;
import src.auth.User;

public class APIClient {

    private static final String BASE_URL = "https://vt.jonah-emme.de/";

    public static boolean createUserAccount(String firstName, String lastName, String email, String password, int modePreference, int userClass) {
        try {
            URI uri = URI.create(BASE_URL + "?action=createAccount");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            System.out.println(firstName + "/" + lastName + "/" + email +"/" +password +"/"+ modePreference +"/"+ userClass);
            String requestBody = parseToJson(Map.of("firstName", firstName, "lastName", lastName, "email", email, "password", password, "modePreference", String.valueOf(modePreference), "class", String.valueOf(userClass)));
            //String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"modePreference\":%d,\"class\":%d}",
            //            firstName, lastName, email, password, modePreference, userClass);


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Account created successfully");
                return true;
            } else {
                System.out.println("Failed to create account. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User login(String email, String password) {
        try {
            URI uri = URI.create(BASE_URL + "?action=login");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String requestBody = parseToJson(Map.of("email", email, "password", password));

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.lines().collect(Collectors.joining());
                System.out.println("Login successful. (APIclient) ");
                JSONObject data = JSONParser.parse(response.toString());
                JSONObject userData = data.get("userData");
                System.out.println(userData);
                return new User(data.get("token").getString(), userData.get("userID").getInt(), userData.get("verified").getBool(), userData.get("firstName").getString(), userData.get("lastName").getString(), userData.get("email").getString(), userData.get("modePreference").getInt(), userData.get("class").getInt());
            } else {
                System.out.println("Login failed. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User verifyAccount(String email, String code) {
        try {
            URI uri = URI.create(BASE_URL + "?action=verifyAccount");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String requestBody = parseToJson(Map.of("email", email, "code", code));
            //String.format("{\"email\":\"%s\",\"code\":\"%s\"}", email, code);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.lines().collect(Collectors.joining());
                System.out.println("Verification successful.");
                JSONObject data = JSONParser.parse(response.toString());
                JSONObject userData = data.get("userData");
                return new User(data.get("token").getString(), userData.get("userID").getInt(), userData.get("verified").getBool(), userData.get("firstName").getString(), userData.get("lastName").getString(), userData.get("email").getString(), userData.get("modePreference").getInt(), userData.get("class").getInt());
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Verification failed.");
            } else {
                System.out.println("Verification failed. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteAccount(String password) {
        try {
            URI uri = URI.create(BASE_URL + "?action=deleteAccount");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Auth", AuthManager.getToken()); // Set the Auth header with the token
            connection.setDoOutput(true);

            String requestBody = parseToJson(Map.of("password", password));
            //String.format("{\"password\":\"%s\"}", password);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Account deleted successfully");
                return true;
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Failed to delete account. Invalid login credentials.");
            } else {
                System.out.println("Failed to delete account. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean initiatePasswordReset(String email) {
        try {
            URI uri = URI.create(BASE_URL + "?action=initiatePasswordReset");
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"email\": \"" + email + "\"}";

            OutputStream os = con.getOutputStream();
            os.write(jsonInputString.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            con.disconnect();
            return responseCode == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean validatePasswordResetCode(String email, String code) {
        try {
            URI uri = URI.create(BASE_URL + "?action=validatePasswordReset");
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"email\": \"" + email + "\", \"code\": \"" + code + "\"}";

            OutputStream os = con.getOutputStream();
            os.write(jsonInputString.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            con.disconnect();
            return responseCode == 200;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to validate password reset code.");
            return false;
        }
    }
    public static boolean doPasswordReset(String email, String code, String password) {
        try {
            URI uri = URI.create(BASE_URL + "?action=doPasswordReset");
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"email\": \"" + email + "\", \"code\": \"" + code + "\", \"newPassword\": \"" + password + "\"}";

            OutputStream os = con.getOutputStream();
            os.write(jsonInputString.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            con.disconnect();
            return responseCode == 200;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to reset password.");
            return false;
        }
    }

    public static boolean logout(String authToken) {
        try {

            URI uri = URI.create(BASE_URL + "?action=logout");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Auth", authToken);
            connection.setDoOutput(true);
            int responseCode = connection.getResponseCode();

            connection.disconnect();

            if (responseCode == 200) {
                System.out.println("Logout successful.");
                return true;
            }
            else {
                System.out.println("Failed to logout. Response code: " + responseCode);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred while trying to logout.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to logout due to an unexpected exception.");
            return false;
        }
    }


    public static User validateToken(String authToken) {
        try {
            URI uri = URI.create(BASE_URL + "?action=validateToken");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Auth", authToken);
            connection.setDoOutput(true);

            // Send request
            int responseCode = connection.getResponseCode();
            JSONObject data = JSONParser.parse(connection);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                if (data.get("tokenValid").getBool()) {
                    JSONObject userData = data.get("userData");
                    return new User(authToken, userData.get("userID").getInt(), userData.get("verified").getBool(), userData.get("firstName").getString(), userData.get("lastName").getString(), userData.get("email").getString(), userData.get("modePreference").getInt(), userData.get("class").getInt());
                } else {
                    return null;
                }
            } else {
                System.out.println("Token validation failed with response code: " + responseCode);
                System.out.println(data);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to validate token: " + e.getMessage());
            return null;
        }
    }

    public static boolean updatePreferences(Map preferences) {
        System.out.println("Updating preferences: " + preferences.toString());
        try {
            URI uri = URI.create(BASE_URL + "?action=updatePreferences");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Auth", AuthManager.getToken());
            connection.setDoOutput(true);

            //Map.of("mode", isDarkMode, "class", gradeLevel, "widget", widget)
            String jsonInputString = parseToJson(preferences);

            OutputStream os = connection.getOutputStream();
            os.write(jsonInputString.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            if (responseCode == 200) {
                System.out.println("Preferences updated successfully.");
                return true;
            }
            else {
                System.out.println("Failed to update preferences. Response code: " + responseCode);
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Failed to update preferences: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public static Map<String, String> getPreferences() {
        try
        {
            URI uri = URI.create(BASE_URL + "?action=getPreferences");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Auth", AuthManager.getToken());
            connection.setDoOutput(true);

            // Send request
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return parseFromJson(response.toString());
            }
        }
        catch (Exception e) {
            System.out.println("Failed to get preferences: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateUserVocabStats(ArrayList<Integer> wrongVocabIDs, ArrayList<Integer> rightVocabIDs) {
        try {
            URI uri = URI.create(BASE_URL + "?action=updateUserVocabStats");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Auth", AuthManager.getToken()); // Set your authentication token here
            connection.setDoOutput(true);

            // Construct JSON payload
            Map<String, Map<String, Integer>> statUpdates = new HashMap<>();
            Map<String, Integer> stats;

            // Add wrong vocab stats
            for (Integer vocabID : wrongVocabIDs) {
                stats = new HashMap<>();
                stats.put("fails", 1); // Update as per your logic
                stats.put("success", 0); // Update as per your logic
                statUpdates.put(String.valueOf(vocabID), stats);
            }

            // Add right vocab stats
            for (Integer vocabID : rightVocabIDs) {
                stats = new HashMap<>();
                stats.put("fails", 0); // Update as per your logic
                stats.put("success", 1); // Update as per your logic
                statUpdates.put(String.valueOf(vocabID), stats);
            }

            // Wrapping the statUpdates in the main JSON object
            Map<String, Map<String, Map<String, Integer>>> requestBodyMap = new HashMap<>();
            requestBodyMap.put("statUpdates", statUpdates);

            String requestBody = parseToJsonNested(requestBodyMap);
            System.out.println("Request Body: " + requestBody);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Vocabulary statistics updated successfully.");
                return true;
            } else {
                System.out.println("Failed to update vocabulary statistics. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static Map<String, String> parseFromJson(String jsonString) {
        Map<String, String> jsonMap = new HashMap<>();

        // Remove curly braces from the JSON string
        jsonString = jsonString.substring(1, jsonString.length() - 1);

        // Split the key-value pairs by comma
        String[] keyValuePairs = jsonString.split(",");

        for (String pair : keyValuePairs) {
            // Split each pair into key and value
            String[] entry = pair.split(":");
            // Trim leading and trailing whitespace and remove quotes
            String key = entry[0].trim().replaceAll("\"", "");
            String value = entry.length > 1 ? entry[1].trim().replaceAll("\"", "") : "";
            jsonMap.put(key, value);
        }

        return jsonMap;
    }

    @SuppressWarnings("unchecked")
    private static String parseToJson(Map<String, Object> jsonMap) {
        StringBuilder jsonStringBuilder = new StringBuilder();
        jsonStringBuilder.append("{");

        // Iterate over the entries of the Map
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Append key to the JSON string
            jsonStringBuilder.append("\"").append(key).append("\":");

            // Append value based on its type
            if (value instanceof String) {
                jsonStringBuilder.append("\"").append(value).append("\"");
            } else if (value instanceof Integer || value instanceof Double || value instanceof Boolean) {
                jsonStringBuilder.append(value);
            } else if (value instanceof Map) {
                // Recursively call parseToJson for nested maps
                jsonStringBuilder.append(parseToJson((Map<String, Object>) value));
            } else {
                // Unsupported type, you may handle or throw an exception as needed
                throw new IllegalArgumentException("Unsupported value type: " + value.getClass().getSimpleName());
            }

            // Append comma after each entry
            jsonStringBuilder.append(",");
        }

        // Remove the trailing comma if there's at least one entry in the map
        if (!jsonMap.isEmpty()) {
            jsonStringBuilder.deleteCharAt(jsonStringBuilder.length() - 1);
        }

        jsonStringBuilder.append("}");

        return jsonStringBuilder.toString();
    }
    private static String parseToJsonNested(Map<String, Map<String, Map<String, Integer>>> jsonMap) {
        StringBuilder jsonStringBuilder = new StringBuilder();
        jsonStringBuilder.append("{");

        // Iterate over the entries of the Map
        for (Map.Entry<String, Map<String, Map<String, Integer>>> entry : jsonMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Map<String, Integer>> innerMap = entry.getValue();

            // Append key to the JSON string
            jsonStringBuilder.append("\"").append(key).append("\":{");

            // Iterate over the inner map
            for (Map.Entry<String, Map<String, Integer>> innerEntry : innerMap.entrySet()) {
                String innerKey = innerEntry.getKey();
                Map<String, Integer> innerValueMap = innerEntry.getValue();

                // Append inner key to the JSON string
                jsonStringBuilder.append("\"").append(innerKey).append("\":{");

                // Iterate over the inner value map
                for (Map.Entry<String, Integer> valueEntry : innerValueMap.entrySet()) {
                    String innerValueKey = valueEntry.getKey();
                    Integer innerValue = valueEntry.getValue();

                    // Append inner value key-value pair to the JSON string
                    jsonStringBuilder.append("\"").append(innerValueKey).append("\":").append(innerValue).append(",");
                }

                // Remove the trailing comma if there's at least one entry in the inner value map
                if (!innerValueMap.isEmpty()) {
                    jsonStringBuilder.deleteCharAt(jsonStringBuilder.length() - 1);
                }

                jsonStringBuilder.append("},");
            }

            // Remove the trailing comma if there's at least one entry in the inner map
            if (!innerMap.isEmpty()) {
                jsonStringBuilder.deleteCharAt(jsonStringBuilder.length() - 1);
            }

            jsonStringBuilder.append("},");
        }

        // Remove the trailing comma if there's at least one entry in the outer map
        if (!jsonMap.isEmpty()) {
            jsonStringBuilder.deleteCharAt(jsonStringBuilder.length() - 1);
        }

        jsonStringBuilder.append("}");

        return jsonStringBuilder.toString();
    }
}

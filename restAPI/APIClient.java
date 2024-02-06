package restAPI;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class APIclient {

    private static final String BASE_URL = "https://vt.jo-dev.net/";

    public static boolean createUserAccount(String firstName, String lastName, String email, String password, int modePreference, int userClass) {
        try {
            URI uri = URI.create(BASE_URL + "?action=createAccount");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String login(String email, String password) {
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
                TokenManager.saveToken(parseFromJson(response).get("token"));
                return parseFromJson(response).get("token");
            } else {
                System.out.println("Login failed. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String verifyAccount(String email, String code) {
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
                System.out.println("Verification successful. Token: " + response);
                TokenManager.saveToken(parseFromJson(response).get("token"));
                return parseFromJson(response).get("token");
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Verification failed.");
            } else {
                System.out.println("Verification failed. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String loadSession() {
        String loadedToken = TokenManager.loadToken();
        if (loadedToken == null) {
            // Token loader returned null, prompting UI to request login
            return "LOGIN_REQUIRED";
        } else {
            // Token loaded successfully
            return loadedToken;
        }
    }


    public static void deleteAccount(String password, String authToken) {
        try {
            URI uri = URI.create(BASE_URL + "?action=deleteAccount");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Auth", authToken); // Set the Auth header with the token
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
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Failed to delete account. Invalid login credentials.");
            } else {
                System.out.println("Failed to delete account. Response code: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorResponse = errorReader.lines().collect(Collectors.joining());
                System.out.println("Error Response: " + errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean updateUserVocabStats(ArrayList<Integer> wrongVocabIDs, ArrayList<Integer> rightVocabIDs, String authToken) {
        try {
            URI uri = URI.create(BASE_URL + "?action=updateUserVocabStats");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Auth", authToken); // Set your authentication token here
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    

    private static Map<String, String> parseFromJson(String jsonString) {
        Map<String, String> jsonMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(\\w+)\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(jsonString);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
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
    
    

    public static void main(String[] args) {

        // create example account
        String email = "mochasteve@outlook.com";
        String password = "123456";

        boolean created = createUserAccount("Adrian", "Steyer", email, password, 1, 11);

        // verify account
        if (created) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a string: ");
            String inputString = scanner.nextLine();
            scanner.close();
            verifyAccount(email, inputString);
        }

        // login to account
        //login(email, password);
        System.out.println("Token: " + TokenManager.loadToken());

        // delete account
        //deleteAccount(password, TokenManager.loadToken());
    }
}

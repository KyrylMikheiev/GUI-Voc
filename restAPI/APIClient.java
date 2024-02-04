package restAPI;

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

public class APIClient {

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
                System.out.println("Login successful. Response: " + response);
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

            String requestBody = String.format("{\"email\":\"%s\",\"code\":\"%s\"}", email, code);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.lines().collect(Collectors.joining());
                System.out.println("Verification successful. Token: " + response);
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

    public static void deleteAccount(String password, String token) {
        try {
            URI uri = URI.create(BASE_URL + "?action=deleteAccount");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Auth", token); // Set the Auth header with the token
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
    private static String parseToJson(Map<String, String> jsonMap) {
        StringBuilder jsonStringBuilder = new StringBuilder();
        jsonStringBuilder.append("{");
        
        // Iterate over the entries of the Map
        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            // Append key-value pair to the JSON string
            jsonStringBuilder.append("\"").append(key).append("\":\"").append(value).append("\",");
        }
        
        // Remove the trailing comma if there's at least one entry in the map
        if (!jsonMap.isEmpty()) {
            jsonStringBuilder.deleteCharAt(jsonStringBuilder.length() - 1);
        }
        
        jsonStringBuilder.append("}");
        
        return jsonStringBuilder.toString();
    }
    

    public static void main(String[] args) {

        // create example account
        String email = "adr.st@gmx.de";
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
        String token = login(email, password);
        System.out.println("Token: " + token);

        // delete account
        deleteAccount(password, token);
    }
}

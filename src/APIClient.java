package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class APIClient {

    private static final String BASE_URL = "https://vt.jo-dev.net/";

    public static String login(String email, String password) throws IOException {
        URI uri = URI.create(BASE_URL + "?action=login");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        Map<String, String> postData = new HashMap<>();
        postData.put("email", email);
        postData.put("password", password);

        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = mapToJson(postData).getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    public static String createAccount(String firstName, String lastName, String email, String password, String modePreference, String userClass) throws IOException {
        URI uri = URI.create(BASE_URL + "?action=createAccount");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        Map<String, String> postData = new HashMap<>();
        postData.put("firstName", firstName);
        postData.put("lastName", lastName);
        postData.put("email", email);
        postData.put("password", password);
        postData.put("modePreference", modePreference);
        postData.put("class", userClass);

        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = mapToJson(postData).getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static String mapToJson(Map<String, String> map) {
        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        if (map.size() > 0) {
            json.deleteCharAt(json.length() - 1); // Remove trailing comma
        }
        json.append("}");
        return json.toString();
    }

    public static void main(String[] args) {
        
        // Example of using createAccount
        try {
            String response = createAccount("Johnny", "Doe", "johnny@example.com", "securepassword", "light", "11");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String response = login("jonah.emme@web.de", "IchHasseNoah22");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

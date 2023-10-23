package security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Authenticator {
    private static final Map<String, String> USERS = new HashMap<String, String>();
    
    private static final String jdbcDriver  = "com.mysql.cj.jdbc.Driver";
    private static final String dbPath      = "jdbc:mysql://localhost:3306/";
    private static final String dbName      = "new Project";
    private static final String user        = "root";
    private static final String pwd         = "rootroot";
    private static Connection connection = null;
    
    private static final String API_URL = "http://localhost:8080/api/login";
    public static int validate(String user, String password) {
        int id = 0;
        try {
            // Construct the API URL
            URL url = new URL(API_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload for the login credentials
            String jsonPayload = "{\"username\":\"" + user + "\", \"password\":\"" + password + "\"}";
            

            // Write the JSON payload to the request body
            connection.getOutputStream().write(jsonPayload.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == 302) {
                // User login successful
                // Process the response if needed
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                id = Integer.parseInt(response);
                reader.close();

                // Perform the necessary actions after successful login
                // For example, store user information, redirect to home page, etc.
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // Unauthorized access, handle the error response
                System.out.println("User login failed. Invalid credentials.");
            } else {
                // Handle other response codes as needed
                throw new Exception("User login failed. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }
    
}
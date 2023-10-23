/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import connection.ClassConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
//import demo.model.Person;
import model.History;
import model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author abdelrahmankhodr
 */
public class HistoryController extends AnchorPane implements Initializable {
    
    @FXML
    private TableView<History> tableView;
    
    @FXML
    private Button back, clearHistory;

    private Main application;

    private ClassConnection classConnection;

    public void setApp(Main application) {
        this.application = application;
        fetchHistory();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        classConnection = new ClassConnection();
    } 
    
    private void fetchHistory() {
        
        try {
            tableView.getItems().clear();
            User loggedUser = application.getLoggedUser();
            System.out.println("this is name " + loggedUser.getUsername());
            System.out.println("this is id: " + loggedUser.getId());
            // Construct the API URL
            String apiUrl = "http://localhost:8080/api/getHistory?id=" + loggedUser.getId();
            URL url = new URL(apiUrl);

            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response and update the table view
                JSONArray historyArray = new JSONArray(response.toString());
                if(historyArray.length() == 0){
                    clearHistory.setDisable(true);
                }
                ObservableList<History> data = tableView.getItems();
                for (int i = 0; i < historyArray.length(); i++) {
                    JSONObject historyObject = historyArray.getJSONObject(i);
                    int historyId = historyObject.getInt("historyId");
                    String historyTypeHere = historyObject.getString("historyTypeHere");
                    String historyTranslation = historyObject.getString("historyTranslation");
                    History history = new History();
                    history.setHistoryId(historyId);
                    history.setHistoryTypeHere(historyTypeHere);
                    history.setHistoryTranslation(historyTranslation);
                    data.add(history);
                }
            } else {
                // Handle error response codes if needed
                System.out.println("Failed to fetch history. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   
    public void processClearHistory(ActionEvent event){
        try {
            User loggedUser = application.getLoggedUser();
            URL url = new URL("http://localhost:8080/api/clearHistory");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            

            // Create the JSON payload for the delete operation
            JSONObject user = new JSONObject();
            user.put("id", loggedUser.getId());
//            user.put("username", loggedUser.getUsername());
//            System.out.println(user);

//            System.out.println("this savedId: " + saved.getSavedId());
//            JSONObject jsonPayload = new JSONObject();
//            jsonPayload.put("savedId", saved.getSavedId());
//            jsonPayload.put("savedTranslation", saved.getSavedTranslation());
//            jsonPayload.put("savedTypeHere", saved.getSavedTypeHere());

//            System.out.println(jsonPayload);
//            // Write the JSON payload to the request body
            connection.getOutputStream().write(user.toString().getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Save entry deleted successfully
                System.out.println("History cleared.");
                application.goToHistory();
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                // Bad request, handle the error response
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                errorReader.close();

                // Process the error response and display appropriate message
                System.out.println("Failed to clear history. Error response: " + errorResponse.toString());
            } else {
                // Handle other response codes as needed
                throw new Exception("Failed to clear history. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void processGoToTranslationPage(ActionEvent event){
        application.goToTranslationPage();
    }
    
}

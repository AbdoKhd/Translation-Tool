/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import connection.ClassConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import model.User;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * FXML Controller class
 *
 * @author Main
 */
public class FXMLTableUsersController implements Initializable {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TextField idField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    
    @FXML
    private Button back;

    private Main application;

    private ClassConnection classConnection;

    public void setApp(Main application) {
        this.application = application;
        fetchUsers();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        classConnection = new ClassConnection();
    }

    private static final String API_URL = "http://localhost:8080/api/getUsers";
    private ObservableList<User> fetchUsers() {
        try{
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                
                reader.close();
                connection.disconnect();
            
                System.out.println("this:" + response);
                
                //now change response from buffer to json object 
                //put all objects in observable list
              
                JSONArray jsonArray = new JSONArray(response.toString());
                ObservableList<User> userList = tableView.getItems();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int id = jsonObject.getInt("id");
                    String username = jsonObject.getString("username");
                    String email = jsonObject.getString("email");

                
                    User user = User.of(username);
                    user.setId(id);
                    user.setEmail(email);
                
                    userList.add(user);
                }

                return userList;
            
            } else {
                throw new IOException("Failed to fetch users. Response code: " + responseCode);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @FXML
    protected void handleClickTableView(MouseEvent click) {
        User user = tableView.getSelectionModel().getSelectedItem();
        idField.setText(Integer.toString(user.getId()));
        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
    }

//    @FXML
//    protected void savePerson(ActionEvent event) {
//        try {
//            int id = 0;
//            if(!idField.getText().isEmpty())
//                id = Integer.parseInt(idField.getText());
//            
//            User user = User.of(usernameField.getText());
//            user.setId(id);
//            user.setEmail(emailField.getText());
//            String sql = "";
//            if(user.getId() != 0){
//                sql = "update users set username = ?, email = ? where id = ?";
//            }
//////            if (user.getId() == 0) {
//////                sql = "insert into users (username, email, id) values (?, ?, ?)";
//////            } else {
//////                sql = "update users set username = ?, email = ? where id = ?";
//////            }
//            PreparedStatement preparedStatement = classConnection.loadPreparedStatement(sql);
//            preparedStatement.setString(1, user.getUsername());
//            preparedStatement.setString(2, user.getEmail());
//            preparedStatement.setInt(3, user.getId());
//            
//            preparedStatement.execute();
//            
//            
//            
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }finally{
//            fetchUsers();
//        }
//
//    }
    
    public void processGoToTranslationPage(ActionEvent event){
        application.goToTranslationPage();
    }

    public void processLogout(ActionEvent event) {
        if (application == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }

        application.userLogout();
    }

}

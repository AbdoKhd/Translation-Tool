/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author abdelrahmankhodr
 */
public class SignUpController extends AnchorPane implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    TextField email;
    @FXML
    Button signUp;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    private Main application;
    
    
    public void setApp(Main application){
        this.application = application;
    }
    
    public void processSignUp(ActionEvent event) {
        if(username.getText() == null || password.getText() == null){
            System.out.println("Username or Password is empty");
        }
        else{
            application.userSignUp(username.getText(), password.getText(), email.getText());
        }
    }
   
}

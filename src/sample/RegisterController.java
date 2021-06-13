package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import  javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;


import  java.sql.Connection;
import  java.sql.Statement;

public class RegisterController {
    @FXML
    private Button closeButton;
@FXML private  Label registrationMessageLabel;
@FXML
private  PasswordField setPasswordField;
    @FXML
    private  PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private  TextField firstnameTextField;

    @FXML
    private  TextField lastnameTextField;

    @FXML
    private  TextField usernameTextField;

    @FXML
    private  Button ChangePasswordButton;
    @FXML
    private  Button ChangeUsernameButton;

    public  void ChangePasswordButtonOnAction(ActionEvent event) {
        createPasswordForm();
    }
    public  void ChangeUsernameButtonOnAction(ActionEvent event) {
    createUserNameForm();
    }



    public  void registerButtonOnAction(ActionEvent event){
        if(setPasswordField.getText().equals(confirmPasswordField.getText())&& usernameTextField.getText().length()<21&&setPasswordField.getText().length()>3&&setPasswordField.getText().length()<11){
            registerUser();
            //confirmPasswordLabel.setText("you have matched");
            String firstname=firstnameTextField.getText();
            String lastname=lastnameTextField.getText();
            String username=usernameTextField.getText();
            String password=setPasswordField.getText();
            callClient("registerUser"+" "+firstname+" "+lastname+" "+username+" "+password);
            registrationMessageLabel.setText("user is registered successfully");
        }else{
            confirmPasswordLabel.setText("enter again");
        }
        //registerUser();

    }

    public  void closeButtonOnAction(ActionEvent event){
        Stage stage=(Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    public void registerUser(){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();

        String firstname=firstnameTextField.getText();
        String lastname=lastnameTextField.getText();
        String username=usernameTextField.getText();
        String password=setPasswordField.getText();

        String insertFields="INSERT INTO user_account(lastname,firstname,username,password)VALUES('";
        String insertValues=lastname+"','"+firstname+"','"+username+"','"+password+"')";
        String insertToRegister=insertFields+insertValues;
        System.out.println(insertToRegister);
        try{
            Statement statement= connectDB.createStatement();

            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("user is registered successfully");

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }


    }


    public  void  createPasswordForm(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("Password1.fxml"));
            Stage PasswordStage=new Stage();
            PasswordStage.initStyle(StageStyle.UNDECORATED);
            PasswordStage.setScene(new Scene(root, 520, 400));
            PasswordStage.show();



        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }






    public  void  createUserNameForm(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("Username.fxml"));
            Stage UsernameStage=new Stage();
            UsernameStage.initStyle(StageStyle.UNDECORATED);
            UsernameStage.setScene(new Scene(root, 520, 400));
            UsernameStage.show();



        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public  void callClient(String str) {
        try{
            Socket s=new Socket("localhost",6666);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            dout.writeUTF(str);
            dout.flush();
            dout.close();
            s.close();
        }catch(Exception e){System.out.println(e);}
    }


}

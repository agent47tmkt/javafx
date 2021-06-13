package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import  javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PasswordChangeController {
    @FXML
    private TextField UsernameTextField;
    @FXML
    private PasswordField PasswordTextField;
    @FXML
    private PasswordField NewPasswordTextField;
    @FXML
    private  Button changeButton;
    @FXML
    private  Button BackButton;
    @FXML private Label PasswordAccepted;

    public  void BackButtonOnAction(ActionEvent event) {
        BackButton.getScene().getWindow().hide();
    }
    public  void changeButtonOnAction(ActionEvent event) {

        callClient("ChangePasswordFunction"+" "+UsernameTextField.getText()+" "+PasswordTextField.getText()+" "+NewPasswordTextField.getText());
//        ChangePasswordFunction();
    }


    public void ChangePasswordFunction() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


        String Username= UsernameTextField.getText();
        String Password=PasswordTextField.getText();
        String NEWPassword=NewPasswordTextField.getText();
        String SqlPassword = "update user_account set password  = '" +NewPasswordTextField.getText()  +"'   WHERE Username = '"+UsernameTextField.getText()+ "' AND password='"+PasswordTextField.getText()+"'";



        //System.out.println(insertToRegister);
        try {
            Statement statement = connectDB.createStatement();

            statement.executeUpdate(SqlPassword);
            PasswordAccepted.setText("CHANGED successfully");

        } catch (Exception e) {
            PasswordAccepted.setText("not changed");
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
            s.close();       }catch(Exception e){System.out.println(e);}
    }


}

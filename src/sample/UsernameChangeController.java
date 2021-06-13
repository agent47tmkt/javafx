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
public class UsernameChangeController {
    @FXML
    private TextField NewUsernameTextFIELD;

    @FXML
    private TextField UsernameTextFIELD;
    @FXML
    private PasswordField PasswordTextFIELD;
    @FXML
    private  Button ChangeButton;
    @FXML
    private  Button GoBackButton;
    @FXML
    private  Label UsernameAccepted;

    public  void GoBackButtonOnAction(ActionEvent event) {
        GoBackButton.getScene().getWindow().hide();


    }
    public  void ChangeButtonOnAction(ActionEvent event) {
//        ChangeUserFunction();
        callClient("ChangeUserFunction"+" "+UsernameTextFIELD.getText()+" "+PasswordTextFIELD.getText()+" "+NewUsernameTextFIELD.getText());
        UsernameAccepted.setText("CHANGED successfully");
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







    public void ChangeUserFunction() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();


     String OldUsername= UsernameTextFIELD.getText();
     String Password=PasswordTextFIELD.getText();
     String NEWUsername=NewUsernameTextFIELD.getText();
        String SqlUsername = "update user_account set OldUsername  = '" +NewUsernameTextFIELD.getText()  +"'   WHERE OldUsername = '"+UsernameTextFIELD.getText()+ "' AND password='"+PasswordTextFIELD.getText()+"'";



        //System.out.println(insertToRegister);
        try {
            Statement statement = connectDB.createStatement();

            statement.executeUpdate(SqlUsername);
            UsernameAccepted.setText("CHANGED successfully");

        } catch (Exception e) {
            UsernameAccepted.setText("not changed");
            e.printStackTrace();
            e.getCause();


        }

    }

    }

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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.net.*;

public class Controller {
@FXML
    private   Button cancelButton;
@FXML
private Label loginMessageLabel;

@FXML
private TextField usernameTextField;
@FXML
private PasswordField enterPasswordField;

private ServerClient sc = new ServerClient();

    public  void loginButtonOnAction(ActionEvent event){
        if(usernameTextField.getText().isBlank()==false && enterPasswordField.getText().isBlank()==false&&usernameTextField.getText().length()<21&&enterPasswordField.getText().length()>3&&enterPasswordField.getText().length()<11) {
            //            validateLogin();
//            sc.callMyServer("validateLogin");
            //callServer();
            callClient("validateLogin" +" "+usernameTextField.getText()+" "+enterPasswordField.getText());
            //loginMessageLabel.setText("congrats");
           // createAccountForm();
            validateLogin();
        }
        else{
            loginMessageLabel.setText("please enter again");
        }

    }

public void cancelButtonOnAction(ActionEvent event)
{
    Stage stage=(Stage)cancelButton.getScene().getWindow();
    stage.close();


}

public void validateLogin(){
DatabaseConnection connectNow=new DatabaseConnection();
    Connection connectDB=connectNow.getConnection();
    String verifyLogin="SELECT count(1) FROM user_account WHERE username ='"+ usernameTextField.getText() + "' AND password='"+enterPasswordField.getText() +"'";

    try{
        Statement statement =connectDB.createStatement();
        ResultSet queryResult=statement.executeQuery(verifyLogin);

        while(queryResult.next()){
        if(queryResult.getInt(1)==1){
           loginMessageLabel.setText("congrats");
            createAccountForm();


        }else{
            loginMessageLabel.setText("invalid try again");
        }

        }


    }catch (Exception e){
        e.printStackTrace();
        e.getCause();
    }


}
public  void  createAccountForm(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage=new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 400));
            registerStage.show();



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

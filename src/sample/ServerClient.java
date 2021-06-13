package sample;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

public class ServerClient implements Runnable{

    static DataInputStream dis;
    static ServerSocket ss;
    static Socket s;

    public static void main(String[] args) {
        callServer();
}
    public void run() {
        String  str = null;
        try{
            str=(String)dis.readUTF();
            StringTokenizer str1 = new StringTokenizer(str);
            String option = str1.nextToken();
            System.out.println(option);
            System.out.println(str);

            if(option=="validateLogin"){
                String user = str1.nextToken();
                String pass = str1.nextToken();
                validateLogin(user,pass);
                // dout.writeUTF(validateLogin(user,pass));
//                    dout.flush();
            }
            else if(option=="registerUser"){
                String fname = str1.nextToken();
                String lname = str1.nextToken();
                String user = str1.nextToken();
                String pass = str1.nextToken();
                registerUser(fname, lname, user,pass);
                // dout.writeUTF(validateLogin(user,pass));
//                    dout.flush();
            }
            else if(option.equals("ChangePasswordFunction")){
                String user = str1.nextToken();
                String pass = str1.nextToken();
                String newPass = str1.nextToken();

                ChangePasswordFunction( user,  pass,  newPass);
                // dout.writeUTF(validateLogin(user,pass));
//                    dout.flush();
            }
            else if(option.equals("ChangeUserFunction")){
                String user = str1.nextToken();
                String pass = str1.nextToken();
                String newUser = str1.nextToken();

                ChangeUserFunction( user,  pass,  newUser);
                // dout.writeUTF(validateLogin(user,pass));
//                    dout.flush();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }


    }
    public static void callServer(){
        try{
            ss=new ServerSocket(6666);
//            s=ss.accept();//establishes connection
//            dis=new DataInputStream(s.getInputStream());
            //DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            while(true){


                s=ss.accept();//establishes connection
                dis=new DataInputStream(s.getInputStream());
                ServerClient obj = new ServerClient();
                Thread thread = new Thread(obj);
                thread.start();
               // dout=new DataOutputStream(s.getOutputStream());
            }

        }catch(Exception e){System.out.println(e.getMessage());}

    }


    public static String validateLogin(String username, String pass){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();
        String verifyLogin="SELECT count(1) FROM user_account WHERE username ='"+ username + "' AND password='"+pass +"'";

        try{
            Statement statement =connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    return("congrats");


                }else{
                    return "invalid try again";
                }

            }

            return "invalid try again";
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return "invalid try again";
    }

    public static String registerUser(String firstname, String lastname, String username, String password){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB=connectNow.getConnection();

        String insertFields="INSERT INTO user_account(lastname,firstname,username,password)VALUES('";
        String insertValues=lastname+"','"+firstname+"','"+username+"','"+password+"')";
        String insertToRegister=insertFields+insertValues;
        System.out.println(insertToRegister);
        try{
            Statement statement= connectDB.createStatement();

            statement.executeUpdate(insertToRegister);
            return ("user is registered successfully");

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
        return "not registered.";

    }

    public static void ChangePasswordFunction(String username, String pass, String newPass) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String SqlPassword = "update user_account set password  = '" +newPass  +"'   WHERE Username = '"+username+ "' AND password='"+pass+"'";

        System.out.println(SqlPassword);


        //System.out.println(insertToRegister);
        try {
            Statement statement = connectDB.createStatement();

            statement.executeUpdate(SqlPassword);


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();


        }

    }
    public void ChangeUserFunction(String OldUsername,String Password,String NEWUsername) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String SqlUsername = "update user_account set username  = '" +NEWUsername  +"'   WHERE username = '"+OldUsername+ "' AND password='"+Password+"'";

        System.out.println(SqlUsername);

        //System.out.println(insertToRegister);
        try {
            Statement statement = connectDB.createStatement();

            statement.executeUpdate(SqlUsername);


        } catch (Exception e) {
//            UsernameAccepted.setText("not changed");
            e.printStackTrace();
            e.getCause();


        }

    }

}

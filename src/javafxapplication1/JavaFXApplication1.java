/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class JavaFXApplication1 extends Application {
    Stage window;
    GridPane grid, grid2, grid3;
    Scene scene1, scene2, scene3;
    Alert alertExists, alertPurchase, alertWithdraw, alertLogin, alertString, alertNull;
    PasswordField passwordBox;
    Label info, lblUsername, lblPassword, lblEnterUser, lblEnterPass;
    Text welcomeTxt;
    TextField txtUser,txtEnterUser,txtEnterPass,txtDeposit,txtWithdraw,txtPurchase;
    Button LoginBtn,addBtn,deleteBtn, depositBtn, withdrawBtn,purchaseBtn,balanceBtn,logoutBtn, logout2Btn;
    
    
    Manager manager = new Manager();
    Customer customer;
    FileWriter writer;
    
    public static void main(String[] args) {
        launch(args);
    }

    public void logout(){
        window.setScene(scene1);
        txtUser.clear();
        passwordBox.clear();
        txtEnterUser.clear();
        txtEnterPass.clear();
        txtDeposit.clear();
        txtWithdraw.clear();
        txtPurchase.clear();
        info.setText("Waiting for action");
    }
    public void update(String user) throws IOException{
        FileWriter writer = new FileWriter(""+user+".txt");
        writer.write(customer.toString());
        writer.close();        
    }
    private static boolean passwordCheck(Customer customer, String pass) throws IOException {
        return customer.passwordCheck(pass);
    }
    
    private static double readBalance(Customer customer) throws IOException{
        return customer.readBalance();
    }
   
    public void login() throws IOException{
        String user = txtUser.getText();
        String pass = passwordBox.getText();
        boolean exists = (new File(""+user+".txt")).exists();
        if (user.equals("admin") && pass.equals("admin")){
            System.out.println("login successful");
            window.setScene(scene2);
        }
        else if(exists == true){
            if(passwordCheck(new Customer(user, pass), pass)){
                System.out.println("login successful");
                customer = new Customer(user,pass);
                customer.setBalance(readBalance(new Customer(user, pass)));
                window.setScene(scene3);
            }
            else{
                System.out.println("login unsuccessful");
                throw new IllegalArgumentException("wrong username/password");
            }
        }
        else{
            System.out.println("login unsuccessful");
            throw new IllegalArgumentException("wrong username/password"); 
        }
    }
    public void addCustomer(String user, String pass){
        manager.addCustomer(new Customer(user, pass));
        txtEnterUser.clear();
        txtEnterPass.clear();        
    }
    public void deleteCustomer(String user, String pass){
        manager.deleteCustomer(new Customer(user, pass));
        txtEnterUser.clear();
        txtEnterPass.clear();
    }
    public void deposit(double amount) throws IOException{
        customer.deposit(amount);
        update(customer.getUser());
        info.setText("$"+amount+" deposited in "+customer.getUser());
        txtDeposit.clear();         
    }
    public void withdraw(double amount) throws IOException{
        customer.withdraw(amount);
        update(customer.getUser());
        info.setText("$"+amount+" withdrawn from "+customer.getUser());
        txtWithdraw.clear();
    }
    public void purchase(double amount) throws IOException, Exception{
        customer.purchase(amount);
        update(customer.getUser());
        info.setText("purchase of: $"+ amount);
        txtPurchase.clear();
    }
    public void getBalance(){
        info.setText(""+customer.getUser()+" balance: $"+customer.getBalance());
    }
    
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Bank Account");
        
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));
        
        grid2 = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));
        
        grid3 = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));
        
        scene1 = new Scene(grid, 500, 500);
        scene2 = new Scene(grid2, 500, 500);
        scene3 = new Scene(grid3, 500, 500);
                
        welcomeTxt = new Text("welcome");
        welcomeTxt.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
        grid.add(welcomeTxt, 0 , 0);
        
        lblUsername = new Label("Username");
        grid.add(lblUsername, 0 , 1);
                
        txtUser = new TextField();
        txtUser.setPromptText("username");
        grid.add(txtUser, 1 , 1);

        lblPassword = new Label("Password");
        grid.add(lblPassword, 0 , 2);
        
        passwordBox = new PasswordField();
        passwordBox.setPromptText("password");
        grid.add(passwordBox, 1 , 2);
        
        alertLogin = new Alert(AlertType.ERROR);
        alertLogin.setTitle("Error");
        alertLogin.setHeaderText("Username and/or password incorrect!");
        
        LoginBtn = new Button("Login");
        grid.add(LoginBtn, 1, 3);
        LoginBtn.setOnAction(e -> {
            try {
                login();
            } 
            catch (NullPointerException | IllegalArgumentException ex){
                alertLogin.showAndWait();
            }
            catch (IOException ex) {
                Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        lblEnterUser = new Label("Username");
        grid2.add(lblEnterUser, 0 , 1);
        
        txtEnterUser = new TextField();
        txtEnterUser.setPromptText("username");
        grid2.add(txtEnterUser, 1 , 1);

        lblEnterPass = new Label("Password");
        grid2.add(lblEnterPass, 0 , 2);
        
        txtEnterPass = new TextField();
        txtEnterPass.setPromptText("password");
        grid2.add(txtEnterPass, 1 , 2);
        
        alertNull = new Alert(AlertType.ERROR);
        alertNull.setTitle("Error");
        alertNull.setHeaderText("Field must be filled!");
        
        alertExists = new Alert(AlertType.ERROR);
        alertExists.setTitle("Error");
        alertExists.setHeaderText("User already exists!");
                
        addBtn = new Button("Add Customer");
        grid2.add(addBtn, 1, 3);
        addBtn.setOnAction(e -> {
            try{
                addCustomer(txtEnterUser.getText(), txtEnterPass.getText());
            }
            catch (NullPointerException ex){
                alertNull.showAndWait();
            }
            catch (IllegalArgumentException ex){
                alertExists.showAndWait();
            }
        });
        
        deleteBtn = new Button("Delete Customer");
        grid2.add(deleteBtn, 1, 4);
        deleteBtn.setOnAction(e -> {
                deleteCustomer(txtEnterUser.getText(), txtEnterPass.getText());
        });
        
        txtDeposit = new TextField();
        txtDeposit.setPromptText("amount");
        grid3.add(txtDeposit, 1 , 7);
        
        alertString = new Alert(AlertType.ERROR);
        alertString.setTitle("Error");
        alertString.setHeaderText("Value must be a number!");
        
        depositBtn = new Button("Deposit");
        grid3.add(depositBtn, 1, 8);
        depositBtn.setOnAction(e -> {
            try {
                deposit(Double.parseDouble(txtDeposit.getText()));
            } 
            catch (NumberFormatException ex){
                alertString.showAndWait();
            }
            catch (IOException ex) {
                Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        txtWithdraw = new TextField();
        txtWithdraw.setPromptText("amount");
        grid3.add(txtWithdraw, 1 , 9);
        
        alertWithdraw = new Alert(AlertType.ERROR);
        alertWithdraw.setTitle("Error");
        alertWithdraw.setHeaderText("Not enough money to make transaction!");
        
        withdrawBtn = new Button("Withdraw");
        grid3.add(withdrawBtn, 1, 10);
        withdrawBtn.setOnAction(e -> {
            try {
                withdraw(Double.parseDouble(txtWithdraw.getText()));
            }
            catch (NumberFormatException ex){
                alertString.showAndWait();
            }
            catch (IllegalArgumentException ex){
                alertWithdraw.showAndWait();
            } 
            catch (IOException ex) {
                Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        txtPurchase = new TextField();
        txtPurchase.setPromptText("amount");
        grid3.add(txtPurchase, 1 , 11);
        
        alertPurchase = new Alert(AlertType.ERROR);
        alertPurchase.setTitle("Error");
        alertPurchase.setHeaderText("Purchase must be $50 or more");
        
        purchaseBtn = new Button("Purchase");
        grid3.add(purchaseBtn, 1, 12);
        purchaseBtn.setOnAction(e -> {
            try {
                purchase(Double.parseDouble(txtPurchase.getText()));
            }
            catch (NumberFormatException ex){
                alertString.showAndWait();
            }
            catch (IllegalArgumentException ex){
                alertWithdraw.showAndWait();
            }
            catch (Exception ex){
                alertPurchase.showAndWait();
            }
        });
        
        
        balanceBtn = new Button("Get Balance");
        grid3.add(balanceBtn, 1, 13);
        balanceBtn.setOnAction(e -> getBalance());
      
        info = new Label("Waiting for action");
        grid3.add(info, 1, 14);
        
        
        logoutBtn = new Button("Logout");
        grid2.add(logoutBtn, 1, 5);
        logout2Btn = new Button("Logout");
        grid3.add(logout2Btn, 1, 15);
        logoutBtn.setOnAction(e -> logout());
        logout2Btn.setOnAction(e -> logout());
        
        
        window.setScene(scene1);
        window.show();
  
    }
   
}
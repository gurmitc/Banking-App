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
import java.io.IOException;
import java.util.Scanner;

/**
 * Overview: Customer refers to a type of bank account for a customer. 
 *          Customer has a username, password, balance, level, and stringLevel.
 *          Customer is responsible for all the actions (deposit, withdrawal, purchase, check balance), and 
 *          values (username, password, level, balance) stored in the text files.
 *          It is a mutable class as the values of its variables such as balance, level
 *          can be changed after being created.  
 * 
 * AF(c) = c.username + c.password + c.stringLevel + c.balance 
 * 
 * RI(c) = true when c.username != null and c.password != null
 */
public class Customer extends Account{
    private String username, password;
    private State level;
    private String stringLevel;
    private double balance;
    
    public Customer(String user, String pass){
        super(user, pass, "customer");
        username = user;
        password = pass;
        level = new Silver();
        stringLevel = "silver";
        balance = 100;
    }
    /** 
     * Effects: gets stringLevel
     * 
     * @return this.stringLevel
     */
    public String getLevel(){
        return this.stringLevel;
    }
    /**
     * Requires: State level, String stringLevel
     * Modifies: level, stringLevel
     * Effects: sets value of level and stringLevel to level and stringLevel, respectively
     * 
     * @param level
     * @param stringLevel
     *  
     */ 
    public void setLevel(State level, String stringLevel){
        this.level = level;
        this.stringLevel = stringLevel;
    }
    /**
     * Effects: gets username
     * 
     * @return this.username 
     */    
    public String getUser(){
        return this.username;
    }
    /**
     * Effects: gets password
     * 
     * @return this.password
     */    
    public String getPass(){
        return this.password;
    }
    /** 
     * Modifies: level, stringLevel
     * Effects: performs switchState() which sets the level of customer   
     */    
    public void switchState(){
        level.switchState(this);
    }    
    /**
     * Requires: double money greater than 0
     * Modifies: balance, level
     * Effects: adds money to balance
     */
    public void deposit(double money){
        balance += money;
        switchState();
    }
    /**
     * Requires: double money greater than 0
     * Modifies: balance, level
     * Effects: subtracts money from balance if money is less than or equal balance
     */    
    public void withdraw(double money){
        if(money <= balance){
            balance -= money;
            switchState();
        }
        else
            throw new IllegalArgumentException("Withdraw can't be more than balance!");
    }
    /**
     * Requires: double amount greater than 0
     * Modifies: balance
     * Effects: sets the value of balance to amount
     */
    public void setBalance(double amount){
        balance = amount;
    }
    /**
     * Effects: gets balance
     * 
     * @return balance
     */
    public double getBalance(){
        return balance;
    }
    /**
     * Requires: double price greater than 0 
     * Modifies: balance, level
     * Effects: adds appropriate fee to price and subtracts this from balance
     */    
    public void purchase(double price) throws Exception{
        if (price >= 50){    
            if(stringLevel.equals("silver")){
                price += 20;
                this.withdraw(price);
                this.switchState();
            }
            else if(stringLevel.equals("gold")){
                price += 10;
                this.withdraw(price);
                this.switchState();
            }
            else if(stringLevel.equals("platinum")){
                this.withdraw(price);
                this.switchState();
            }
        }
        else 
            throw new Exception("Purchase must be $50 or more!");
    }
    /**
     * Requires: String pass
     * Effects: compares this.password with String pass
     * 
     */
    public boolean passwordCheck(String pass) throws IOException {
        File file = new File("" + this.username + ".txt");        
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        br.close();
        fr.close();
        if(!pass.isEmpty()){
            if(line.contains("password: " + this.password + " ,"))
                return true;
            else
                return false;
        }
        else 
            throw new NullPointerException();
    }
    /**
     * Modifies: balance
     * Effects: reads balance from this customer's text file and sets this.balance to that
     * 
     * @return this.balance
     */
    public double readBalance() throws IOException{
        Scanner scanner = new Scanner(new FileReader(""+this.username+".txt"));
        String line = scanner.nextLine();
        int ind = line.indexOf("balance: ");
        if(ind != -1){
            String value = (line.substring(ind)).replace("balance: ", "");
            return(Double.parseDouble(value));
        }
        else 
            return 0;
    }
    /**
     * Effects: returns string consisting of username, password, stringLevel, and balance
     * 
     * @return 
     */
    @Override
    public String toString(){
        String string = "username: " + username + " , password: " + password + " , level: " + stringLevel + " , balance: " + balance + "";
        return string;
    }
    /** 
     * Effects: returns true if username and password are not null
     * 
     * @return true if repOK, false otherwise 
     */
    public boolean repOK(){
        if(username != null && password != null)
            return true;
        else 
            return false;
    }
}

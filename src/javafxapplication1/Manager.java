/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager extends Account{
    private File file;
    
    public Manager(){
        super("admin", "admin", "manager");
    }
    public void createFile(Customer customer){
        try {
            file = new File("" + customer.getUser() + ".txt");
            file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.println(customer.toString());
            pw.close();            
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void addCustomer(Customer customer){
        if (customer.getUser().isEmpty() || customer.getPass().isEmpty())
            throw new NullPointerException("Enter username/password!");
        else{
            if(!(new File(""+customer.getUser()+".txt").exists()) && customer.repOK())
                createFile(customer);
            else 
                throw new IllegalArgumentException("User already exists!");
        }
    }
    public void deleteCustomer(Customer customer){
        (new File(""+customer.getUser()+".txt")).delete();
    }
}

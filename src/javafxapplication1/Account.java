/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

public class Account {
    private String role;
    private String username;
    private String password;
    
    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }   
}

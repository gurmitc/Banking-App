/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

public class Silver extends State{

    @Override
    void switchState(Customer a) {
        if(a.getBalance() < 10000)
            System.out.println("level already silver");
        
        else if(a.getBalance() >= 10000 && a.getBalance() < 20000){
            a.setLevel(new Gold(), "gold");
            System.out.println("level now gold");            
        }
        
        else if(a.getBalance() >= 20000){
            a.setLevel(new Platinum(), "platinum");
            System.out.println("level now platinum");            
        }
    }
    
}

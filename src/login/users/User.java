/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

/**
 *
 * @author davidecolombo
 */
public class User {
    
    private String username;
    
    public User(String username){
        this.username = username;
    }
    
    public boolean equals(User user){
        return this.username.equals(user.username);
    }
    
}

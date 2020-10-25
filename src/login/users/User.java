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
    private String password;
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public boolean equals(User user){
        return this.username.equals(user.username);
    }
    
    public boolean matchPassword(UserRequest r){
        return this.password.equals(r.getPassword());
    }
    
    public boolean matchUsername(UserRequest r){
        return this.username.equals(r.getUsername());
    }
    
}

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
    
    
    public enum UserState{
        LOGGED_IN, LOGGED_OUT;
    }
    
    private String username;
    private String password;
    private UserState state = UserState.LOGGED_OUT;
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public boolean equals(User user){
        return this.username.equals(user.username);
    }
    
    public boolean matchPassword(String pwd){
        return this.password.equals(pwd);
    }
    
    public boolean matchUsername(String username){
        return this.username.equals(username);
    }
    
    public boolean isLogged(){
        return this.state == UserState.LOGGED_IN;
    }
    
    public boolean isLoggedOut(){
        return this.state == UserState.LOGGED_OUT;
    }
    
    public User login(){
        this.state = UserState.LOGGED_IN;
        return this;
    }
    
    
}

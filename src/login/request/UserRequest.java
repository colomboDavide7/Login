/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.request;

/**
 *
 * @author davidecolombo
 */
public abstract class UserRequest {
    
    public enum RequestType{
        SIGN_UP, LOGIN, LOGOUT;
    }
    
    public static UserRequest loginRequest(String username, String pwd){
        return new LoginRequest(username, pwd);
    }
    
    public static UserRequest signupRequest(String username, String pwd){
        return new SignUpRequest(username, pwd);
    }
    
    public static UserRequest logoutRequest(String username, String pwd){
        return new LogoutRequest(username, pwd);
    }
    
// ================================================================================
    protected String username;
    protected String pwd;
            
    public UserRequest(String username, String pwd){
        this.username = username;
        this.pwd = pwd;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.pwd;
    }
    
}
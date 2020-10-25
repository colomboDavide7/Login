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
public abstract class UserRequest {
    
    public static UserRequest loginRequest(String username, String pwd){
        return new LoginRequest(username, pwd);
    }
    
    public static UserRequest signupRequest(String username, String pwd){
        return new SignUpRequest(username, pwd);
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

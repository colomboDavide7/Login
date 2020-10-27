/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.request;

import java.util.List;
import login.users.IUser;
import login.users.User;

/**
 *
 * @author davidecolombo
 */
public abstract class UserRequest {
    
    public enum RequestType{
        SIGN_UP, LOGIN, LOGOUT;
    }
    
    public static UserRequest signupRequest(IUser u){
        return new SignUpRequest(u);
    }
    
    public static UserRequest loginRequest(IUser u){
        return new LoginRequest(u);
    }
    
    public static UserRequest logoutRequest(IUser u){
        return new LogoutRequest(u);
    }
    
// ================================================================================
    protected IUser u;
    
    public UserRequest(IUser u){
        this.u = u;
    }
    
    public String getUserProperty(User.PropertyName prop) {
        return this.u.getProperty(prop);
    }
    
    public boolean matchUser(IUser u){
        return this.u.equals(u);
    }
    
    public boolean matchUserProperty(User.PropertyName key, String value){
        return this.u.matchProperty(key, value);
    }
    
    public void addUserToList(List<IUser> list){
        list.add(this.u);
    }
    
}

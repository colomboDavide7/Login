/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import java.util.List;
import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class UserRequest {
    
    public enum RequestType{
        SIGN_UP, LOGIN, LOGOUT;
    }
    
    public static UserRequest createRequestByType(IUser u, RequestType t){
        return new UserRequest(u, t);
    }
    
// ================================================================================
    private IUser u;
    private RequestType t;
    
    private UserRequest(IUser u, RequestType t){
        this.u = u;
        this.t = t;
    }
    
    public boolean matchType(RequestType t){
        return this.t == t;
    }
    
    public String getUserProperty(UserProperty prop) {
        return this.u.getProperty(prop);
    }
    
    public boolean matchUser(IUser u){
        return this.u.equals(u);
    }
    
    public boolean matchUserProperty(UserProperty key, String value){
        return this.u.matchProperty(key, value);
    }
    
    public void addUserToList(List<IUser> list){
        list.add(this.u);
    }
    
}

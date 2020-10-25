/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.users.LoginRequest;
import login.users.UserRequest;
import login.users.UserRequest.RequestType;

/**
 *
 * @author davidecolombo
 */
public class RequestFactory {
    
    public static UserRequest createRequest(String username, String pwd, RequestType type){
        switch(type){
            case LOGIN:
                //return new LoginRequest(username, pwd, type);
                
            default:
                return null;
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.request;

import login.users.IUser;


/**
 *
 * @author davidecolombo
 */
public class SignUpRequest extends UserRequest {
    
    public SignUpRequest(IUser u){
        super(u);
    }
    
}

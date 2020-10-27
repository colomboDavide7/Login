/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import login.Application;
import login.request.UserRequest;
import login.tools.CredentialException;

/**
 *
 * @author davidecolombo
 */
public class LoginController {
    
    private Application app;
    
    public void setApplicationReference(Application app){
        this.app = app;
    }
    
    public void sendSignUpRequest(UserRequest r){
        try {
            this.app.sendSignUpRequest(r);
        } catch (CredentialException ex) {
            // Update the view
        }
    }
    
}

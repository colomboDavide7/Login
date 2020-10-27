/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import login.Application;
import login.repository.QueryException;
import login.users.UserRequest;
import login.tools.CredentialException;

/**
 *
 * @author davidecolombo
 */
public class RequestController {
    
    private Application app;
    
    public RequestController(){
        
    }
    
    public void setApplicationReference(Application app){
        this.app = app;
    }
    
    public void signupButtonPressed(UserRequest r){
        try {
            this.app.sendSignUpRequest(r);
        } catch (CredentialException ex) {
            // Update the view
        }
    }
    
    public void loginButtonPressed(UserRequest r){
        try {
            this.app.sendLoginRequest(r);
        } catch (QueryException ex) {
            // Update the view
        }
    }
    
    public void logoutButtonPressed(UserRequest r){
        try {
            this.app.sendLogoutRequest(r);
        } catch (QueryException ex) {
            // Update the view
        }
    }
    
}

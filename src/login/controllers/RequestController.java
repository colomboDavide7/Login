/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.Application;
import login.repository.QueryException;
import login.users.UserRequest;
import login.tools.CredentialException;
import login.tools.UserProperty;
import login.users.CustomerCreationException;
import login.users.IUser;
import login.users.User;

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
    
    public void signupButtonPressed(UserRequest.RequestType t){
        try {
            IUser newCustomer = this.createNewCustomer();
            UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
            this.app.sendSignUpRequest(r);
        } catch (CredentialException ex) {
            // Update the view
        } catch (CustomerCreationException ex) {
            Logger.getLogger(RequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loginButtonPressed(UserRequest.RequestType t){
        try {
            IUser newCustomer = this.createNewCustomer();
            UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
            this.app.sendLoginRequest(r);
        } catch (QueryException ex) {
            // Update the view
        } catch (CustomerCreationException ex) {
            Logger.getLogger(RequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logoutButtonPressed(UserRequest.RequestType t){
        try {
            IUser newCustomer = this.createNewCustomer();
            UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
            this.app.sendLogoutRequest(r);
        } catch (QueryException ex) {
            // Update the view
        } catch (CustomerCreationException ex) {
            Logger.getLogger(RequestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private IUser createNewCustomer() throws CustomerCreationException{
        // All these fields will be dinamically take from the GUI
        String username = "rossiMario99";
        String pwd = "Test1&";
        String firstName = "Mario";
        String lastName  = "Rossi";
        String email     = "rossimario99@ecommerce.com";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        return User.getBasicUser(basicProperties);
    }
    
}

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
        }
    }
    
    public void loginButtonPressed(UserRequest.RequestType t){
        try {
            IUser newCustomer = this.createNewCustomer();
            UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
            this.app.sendLoginRequest(r);
        } catch (QueryException ex) {
            // Update the view
        }
    }
    
    public void logoutButtonPressed(UserRequest.RequestType t){
        try {
            IUser newCustomer = this.createNewCustomer();
            UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
            this.app.sendLogoutRequest(r);
        } catch (QueryException ex) {
            // Update the view
        }
    }
    
    private IUser createNewCustomer(){
        // All these fields will be dinamically take from the GUI
        String username = "rossiMario99";
        String pwd = "Test1&";
        String firstName = "Mario";
        String lastName  = "Rossi";
        String email     = "rossimario99@ecommerce.com";
        
        return new User(username, pwd)
                    .addProperty(User.PropertyName.FIRST_NAME, firstName)
                    .addProperty(User.PropertyName.LAST_NAME, lastName)
                    .addProperty(User.PropertyName.E_MAIL, email);
    }
    
}

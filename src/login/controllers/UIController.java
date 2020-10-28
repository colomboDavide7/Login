/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import login.IAppModel;
import login.UI.IAppUI;
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
public class UIController {
    
    private IAppModel app;
    private IAppUI ui;
    
    public UIController(IAppModel app, IAppUI ui){
        this.app = app;
        this.ui  = ui;
        this.initialize();
    }
    
    private void initialize(){
        this.pressSignup(UserRequest.RequestType.SIGN_UP);
        this.pressLogin(UserRequest.RequestType.LOGIN);
        this.pressLogout(UserRequest.RequestType.LOGOUT);
    }
        
    private void pressSignup(UserRequest.RequestType t){
        JButton signup = ui.getSignupButton();
        signup.addActionListener((ActionEvent e) -> {
            try {
                IUser newCustomer = this.createNewCustomer();
                UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
                this.app.sendSignUpRequest(r);
            } catch (CustomerCreationException ex) {
                System.err.println("error creating the custumer");
            } catch (CredentialException ex) {
                System.err.println("wrong credential");
            }
        });
    }
    
    private void pressLogin(UserRequest.RequestType t){
        JButton login = ui.getLoginButton();
        login.addActionListener((ActionEvent e) -> {
            try {
                IUser newCustomer = this.createNewCustomer();
                UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
                this.app.sendLoginRequest(r);
            } catch (CustomerCreationException ex) {
                System.err.println("error creating the custumer");
            } catch (QueryException ex) {
                System.out.println("query exception has occured");
            }
        });
    }
    
    private void pressLogout(UserRequest.RequestType t){
        JButton logout = ui.getLogoutButton();
        logout.addActionListener((ActionEvent e) -> {
            try {
                IUser newCustomer = this.createNewCustomer();
                UserRequest r     = UserRequest.createRequestByType(newCustomer, t);
                this.app.sendLogoutRequest(r);
            } catch (CustomerCreationException ex) {
                System.err.println("error creating the custumer");
            } catch (QueryException ex) {
                System.out.println("query exception has occured");
            }
        });
    }
    
    private IUser createNewCustomer() throws CustomerCreationException {
        // All these fields will be dinamically take from the GUI
        String username = "rossiMario99";
        String pwd = "Test1&";
        String firstName = "Mario";
        String lastName  = "Rossi";
        String email     = "rossimario99@ecommerce.com";
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
            basicProperties.put(UserProperty.E_MAIL, email);
        
        return User.getBasicUser(basicProperties);
    }
    
}

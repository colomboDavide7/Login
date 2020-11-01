/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import login.IAppModel;
import login.UI.IAppUI;
import login.repository.TransactionException;
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
        this.pressClear();
    }
        
    private void pressClear(){
        JButton clear = this.ui.getSignupPanel().getClearButton();
        clear.addActionListener((ActionEvent evt) ->{
            this.ui.getSignupPanel().clearForm();
        });
    }
    
    private void pressSignup(UserRequest.RequestType t){
        JButton signup = ui.getSignupPanel().getSignupButton();
        signup.addActionListener((ActionEvent e) -> {
            try {
                this.app.sendSignUpRequest(
                        UserRequest.createRequestByType(
                                this.createSignupCustomer(), t
                        )
                );
                this.ui.getSignupPanel().clearForm();
                this.setSignupMessageTextAndColor("Your request was successfully sent", Color.GREEN);
            } catch (CustomerCreationException ex) {
                this.setSignupMessageTextAndColor("Missing mandatory", Color.RED);
                this.ui.getSignupPanel().highlightMissing(ex.getMissingMandatoryList());
            } catch (CredentialException ex) {
                this.setSignupMessageTextAndColor(ex.getErrorMessage(), Color.red);
                this.ui.getSignupPanel().highlightMissing(Arrays.asList(ex.getWrongField()));
            }
        });
    }
    
    private void pressLogin(UserRequest.RequestType t){
        JButton login = ui.getLoginPanel().getLoginButton();
        login.addActionListener((ActionEvent e) -> {
            try {
                this.app.sendLoginRequest(
                        UserRequest.createRequestByType(
                                this.createLoginCustomer(), t
                        )
                );
                this.setLoginMessageTextAndColor("Successfully logged in", Color.GREEN);
            } catch (CustomerCreationException ex) {
                this.setLoginMessageTextAndColor("Missing mandatory", Color.RED);
                this.ui.getLoginPanel().highlightMissing(ex.getMissingMandatoryList());
            } catch (TransactionException ex) {
                this.setLoginMessageTextAndColor(ex.getErrorMessage(), Color.red);
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
            } catch (TransactionException ex) {
                System.out.println("query exception has occured");
            }
        });
    }
    
    private IUser createLoginCustomer() throws CustomerCreationException {
        String username  = this.ui.getLoginPanel().getUsernameField();
        String pwd       = this.ui.getLoginPanel().getPasswordField();
        String firstName = "LOGIN";
        String lastName  = "LOGIN";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
        return User.getBasicUser(basicProperties);
    }
    
    private IUser createSignupCustomer() throws CustomerCreationException{
        return User.getBasicUser(ui.getSignupPanel().getInsertedProperties());
    }
    
    private void setLoginMessageTextAndColor(String text, Color c){
        this.ui.getLoginPanel().getErrorArea().setText(text);
        this.ui.getLoginPanel().getErrorArea().setForeground(c);
    }
    
    private void setSignupMessageTextAndColor(String text, Color c){
        this.ui.getSignupPanel().getErrorCommunicationField().setText(text);
        this.ui.getSignupPanel().getErrorCommunicationField().setForeground(c);
    }

    private IUser createNewCustomer() throws CustomerCreationException {
        // All these fields will be dinamically take from the GUI
        String username  = "rossiMario99";
        String pwd       = "Test1&";
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

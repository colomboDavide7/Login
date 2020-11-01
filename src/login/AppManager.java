/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repository.AppRepository;
import login.repository.IAppRepository;
import login.repository.TransactionException;
import login.tools.CredentialException;
import login.tools.UserProperty;
import login.tools.UserValidator;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class AppManager {
    
    private IAppRepository repo = new AppRepository();
    
    public void parseSignUpRequest(UserRequest r) throws CredentialException {
        try {
            UserValidator.isValidUsername(r.getUserProperty(UserProperty.USERNAME));
            UserValidator.isValidPassword(r.getUserProperty(UserProperty.PASSWORD));
            repo.addNewCustomer(r);
        } catch (TransactionException ex) {
            System.err.println("Transaction exception has occurred\n"
                             + "Bad error, quit application.");
            System.exit(-1);
        }
    }
    
    public void parseLoginRequest(UserRequest r) throws TransactionException {
        this.repo.login(r);
    }
    
    public void parseLogoutRequest(UserRequest r) throws TransactionException {
        this.repo.logout(r);
    }
        
}

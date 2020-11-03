/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import login.repositories.SystemRepository;
import login.repositories.TransactionException;
import login.tools.CredentialException;
import login.system.UserProperty;
import login.tools.PropertyValidator;
import login.system.UserRequest;
import login.repositories.ISystemRepository;

/**
 *
 * @author davidecolombo
 */
public class SystemManager implements ISystemManager {
    
    private ISystemRepository repo = new SystemRepository();
    
    @Override
    public void parseSignUpRequest(UserRequest r) throws CredentialException {
        try {
            PropertyValidator.isValidUsername(r.getUserProperty(UserProperty.USERNAME));
            PropertyValidator.isValidPassword(r.getUserProperty(UserProperty.PASSWORD));
            repo.addNewCustomer(r);
        } catch (TransactionException ex) {
            System.err.println("Transaction exception has occurred\n"
                             + "Bad error, quit application.");
            System.exit(-1);
        }
    }
    
    @Override
    public void parseLoginRequest(UserRequest r) throws TransactionException {
        this.repo.login(r);
    }
    
    public void parseLogoutRequest(UserRequest r) throws TransactionException {
        this.repo.logout(r);
    }
        
}

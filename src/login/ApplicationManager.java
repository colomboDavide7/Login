/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.ArrayList;
import java.util.List;
import login.repository.AppRepository;
import login.repository.IAppRepository;
import login.repository.TransactionException;
import login.tools.CredentialException;
import login.tools.UserProperty;
import login.tools.UserValidator;
import login.users.UserRequest;
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class ApplicationManager {
        
    private List<IUser> users = new ArrayList<>();
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
    
    public IUser parseLogoutRequest(UserRequest r) throws TransactionException {
        for(IUser u : users)
            if(matchUsernameProperty(u, r))
                if(u.isLogged())
                    return u.logout();
                else
                    throw new TransactionException(TransactionException.ErrorCode.NOT_LOGGED_IN);
        throw new TransactionException(TransactionException.ErrorCode.NOT_SIGNED_UP);
    }
        
    private boolean matchUsernameProperty(IUser u, UserRequest r){
        return r.matchUserProperty(UserProperty.USERNAME, u.getProperty(UserProperty.USERNAME));
    }
    
}

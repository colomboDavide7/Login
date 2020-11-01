/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.ArrayList;
import java.util.List;
import login.repository.AppRepository;
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
    private AppRepository repo;
    
    public void parseSignUpRequest(UserRequest r) throws CredentialException {
        UserValidator.isValidUsername(r.getUserProperty(UserProperty.USERNAME));
        UserValidator.isValidPassword(r.getUserProperty(UserProperty.PASSWORD));
        UserValidator.isSignedUp(users.iterator(), r);
        r.addUserToList(users);
    }
    
    public IUser parseLoginRequest(UserRequest r) throws TransactionException {
        for(IUser u : users)
            if(matchUsernameProperty(u, r))
                if(matchPasswordProperty(u, r))
                    if(u.isLoggedOut())
                        return u.login();
                    else
                        throw new TransactionException(TransactionException.ErrorCode.ALREADY_LOGGED_IN);
                else
                    throw new TransactionException(TransactionException.ErrorCode.WRONG_PASSWORD);
        throw new TransactionException(TransactionException.ErrorCode.NOT_SIGNED_UP);
    }
    
    private boolean matchUsernameProperty(IUser u, UserRequest r){
        return r.matchUserProperty(UserProperty.USERNAME, u.getProperty(UserProperty.USERNAME));
    }
    
    private boolean matchPasswordProperty(IUser u, UserRequest r){
        return r.matchUserProperty(UserProperty.PASSWORD, u.getProperty(UserProperty.PASSWORD));
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
    
    public boolean isLoggedIn(IUser u){
        for(IUser user : users)
            if(user.matchProperty(UserProperty.USERNAME, u.getProperty(UserProperty.USERNAME)))
                return user.isLogged();
        return false;
    }
    
    public boolean isLoggedOut(IUser u){
        for(IUser user : users)
            if(user.matchProperty(UserProperty.USERNAME, u.getProperty(UserProperty.USERNAME)))
                return user.isLoggedOut();
        return false;
    }
    
}

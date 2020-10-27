/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.ArrayList;
import java.util.List;
import login.repository.QueryException;
import login.tools.CredentialException;
import login.tools.UserValidator;
import login.users.User;
import login.users.UserRequest;
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class ApplicationManager {
        
    private List<IUser> users = new ArrayList<>();
    
    public void parseSignUpRequest(UserRequest r) throws CredentialException {
        UserValidator.isValidUsername(r.getUserProperty(User.PropertyName.USERNAME));
        UserValidator.isValidPassword(r.getUserProperty(User.PropertyName.PASSWORD));
        UserValidator.isSignedUp(users.iterator(), r);
        r.addUserToList(users);
    }
    
    public IUser parseLoginRequest(UserRequest r) throws QueryException {
        for(IUser u : users)
            if(matchUsernameProperty(u, r))
                if(matchPasswordProperty(u, r))
                    if(u.isLoggedOut())
                        return u.login();
                    else
                        throw new QueryException(QueryException.ErrorCode.ALREADY_LOGGED_IN);
                else
                    throw new QueryException(QueryException.ErrorCode.WRONG_PASSWORD);
        throw new QueryException(QueryException.ErrorCode.NOT_SIGNED_UP);
    }
    
    private boolean matchUsernameProperty(IUser u, UserRequest r){
        return r.matchUserProperty(User.PropertyName.USERNAME, u.getProperty(User.PropertyName.USERNAME));
    }
    
    private boolean matchPasswordProperty(IUser u, UserRequest r){
        return r.matchUserProperty(User.PropertyName.PASSWORD, u.getProperty(User.PropertyName.PASSWORD));
    }
    
    
    public IUser parseLogoutRequest(UserRequest r) throws QueryException {
        for(IUser u : users)
            if(matchUsernameProperty(u, r))
                if(u.isLogged())
                    return u.logout();
                else
                    throw new QueryException(QueryException.ErrorCode.NOT_LOGGED_IN);
        throw new QueryException(QueryException.ErrorCode.NOT_SIGNED_UP);
    }
    
    public boolean isLoggedIn(IUser u){
        for(IUser user : users)
            if(user.matchProperty(User.PropertyName.USERNAME, u.getProperty(User.PropertyName.USERNAME)))
                return user.isLogged();
        return false;
    }
    
    public boolean isLoggedOut(IUser u){
        for(IUser user : users)
            if(user.matchProperty(User.PropertyName.USERNAME, u.getProperty(User.PropertyName.USERNAME)))
                return user.isLoggedOut();
        return false;
    }
    
}

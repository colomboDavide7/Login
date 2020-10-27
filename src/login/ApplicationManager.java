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
import login.request.UserRequest;
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class ApplicationManager {
        
    private List<IUser> users = new ArrayList<>();
    private List<UserRequest> requests = new ArrayList();
    
    public IUser parseSignUpRequest(UserRequest r) throws CredentialException {
        UserValidator.isValidUsername(r.getUsername());
        UserValidator.isValidPassword(r.getPassword());
        UserValidator.isSignedUp(users.iterator(), new User(r.getUsername(), r.getPassword()));
        IUser signed = new User(r.getUsername(), r.getPassword());
        users.add(signed);
        return signed;
    }
    
    public IUser parseLoginRequest(UserRequest r) throws QueryException {
        for(IUser u : users)
            if(u.matchUsername(r.getUsername()))
                if(u.matchPassword(r.getPassword()))
                    if(u.isLoggedOut())
                        return u.login();
                    else
                        throw new QueryException(QueryException.ErrorCode.ALREADY_LOGGED_IN);
                else
                    throw new QueryException(QueryException.ErrorCode.WRONG_PASSWORD);
        throw new QueryException(QueryException.ErrorCode.NOT_SIGNED_UP);
    }
    
    public IUser parseLogoutRequest(UserRequest r) throws QueryException {
        for(IUser u : users)
            if(u.matchUsername(r.getUsername()))
                if(u.isLogged())
                    return u.logout();
                else
                    throw new QueryException(QueryException.ErrorCode.NOT_LOGGED_IN);
        throw new QueryException(QueryException.ErrorCode.NOT_SIGNED_UP);
    }
    
}

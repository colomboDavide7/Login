/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import login.tools.LoginException;
import java.util.ArrayList;
import java.util.List;
import login.tools.LoginException.ErrorCode;
import login.tools.UserValidator;
import login.users.User;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class ApplicationRepository {
    
    private List<User> users = new ArrayList<>();
    
    public void addUser(UserRequest r) throws LoginException {
        try{
            UserValidator.isValidUsername(r.getUsername());
            UserValidator.isValidPassword(r.getPassword());
            UserValidator.isSignedUp(users.iterator(), new User(r.getUsername(), r.getPassword()));
        }catch(LoginException ex){
            if(ex.getErrorCode() != ErrorCode.NOT_SIGNED_UP)
                throw new LoginException(ex.getErrorCode());
            users.add(new User(r.getUsername(), r.getPassword()));
        }
    }
    
    public void userLogin(UserRequest r) throws LoginException {
        try{
            UserValidator.isSignedUp(users.iterator(), new User(r.getUsername(), r.getPassword()));
        }catch(LoginException ex){
            if(ex.getErrorCode() != ErrorCode.USERNAME_ALREADY_USED)
                throw new LoginException(ex.getErrorCode());
            // login
        }
    }
    
    public User findUser(UserRequest r) throws QueryException {
        for(User u : users)
            if(r.matchUser(u))
                return u;
        throw new QueryException();
    }
    
// ================================================================================
    
}

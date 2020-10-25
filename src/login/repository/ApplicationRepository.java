/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import login.tools.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.tools.LoginException.ErrorCode;
import login.tools.UserValidator;
import login.users.User;

/**
 *
 * @author davidecolombo
 */
public class ApplicationRepository {
    
    private List<User> users = new ArrayList<>();
    
    public void addUser(String username, String pwd) throws LoginException {
        UserValidator.isValidUsername(username);
        UserValidator.isValidPassword(pwd);
        alreadyExist(username);
        users.add(new User(username));
    }
    
    public User findUser(String username) throws QueryException{
        for(User u : users)
            if(u.equals(new User(username)))
                return u;
        throw new QueryException();
    }
    
    private boolean alreadyExist(String username) throws LoginException {
        try {
            findUser(username);
            throw new LoginException(ErrorCode.USERNAME_ALREADY_USED);
        } catch (QueryException ex) {
            return false;
        }
    }
        
// ================================================================================
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import java.util.ArrayList;
import java.util.List;
import login.tools.UserValidator;
import login.users.User;

/**
 *
 * @author davidecolombo
 */
public class ApplicationRepository {
    
    List<User> users = new ArrayList<>();
    
    public void addUser(String username, String pwd) throws LoginException {
        if(!UserValidator.isValidUsername(username) || 
           !UserValidator.isValidPassword(pwd)      || alreadyExist(username))
            throw new LoginException();
        users.add(new User(username));
    }
    
    public User findUser(String username) throws QueryException{
        for(User u : users)
            if(username.equals(u.username))
                return u;
        throw new QueryException();
    }
    
    private boolean alreadyExist(String username) {
        for(User u : users)
            if(username.equals(u.username))
                return true;
        return false;
    }
    
    
}

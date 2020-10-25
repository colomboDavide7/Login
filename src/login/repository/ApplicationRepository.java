/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import login.tools.LoginException;
import java.util.ArrayList;
import java.util.List;
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
        UserValidator.alreadyExist(users.iterator(), new User(username));
        users.add(new User(username));
    }
    
    public User findUser(String username) throws QueryException {
        User searchedUser = new User(username);
        for(User u : users)
            if(u.equals(searchedUser))
                return u;
        throw new QueryException();
    }
        
// ================================================================================
    
}

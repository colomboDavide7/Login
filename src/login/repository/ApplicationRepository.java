/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import login.tools.LoginException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import login.tools.LoginException.ErrorCode;
import login.tools.UserValidator;
import login.users.LoginRequest;
import login.users.SignUpRequest;
import login.users.User;

/**
 *
 * @author davidecolombo
 */
public class ApplicationRepository {
    
    private List<User> users = new ArrayList<>();
    
    public void addUser(SignUpRequest r) throws LoginException {
        try{
            UserValidator.isValidUsername(r.getUsername());
            UserValidator.isValidPassword(r.getPassword());
            UserValidator.isSignedUp(users.iterator(), new User(r.getUsername()));
        }catch(LoginException ex){
            if(ex.getErrorCode() != ErrorCode.NOT_SIGNED_UP)
                throw new LoginException(ex.getErrorCode());
            users.add(new User(r.getUsername()));
        }
    }
    
    public void userLogin(LoginRequest r) throws LoginException {
        UserValidator.isSignedUp(users.iterator(), new User(r.getUsername()));
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

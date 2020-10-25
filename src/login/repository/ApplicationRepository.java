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
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class ApplicationRepository {
    
    private List<User> users = new ArrayList<>();
    
    public void parseSignUpRequest(UserRequest r) throws LoginException {
        UserValidator.isValidUsername(r.getUsername());
        UserValidator.isValidPassword(r.getPassword());
        UserValidator.isSignedUp(users.iterator(), new User(r.getUsername(), r.getPassword()));
        users.add(new User(r.getUsername(), r.getPassword()));
    }
    
    public User parseLoginRequest(UserRequest r) throws QueryException {
        for(User u : users)
            if(u.matchUsername(r.getUsername()))
                if(u.matchPassword(r.getPassword()))
                    return u;
                else
                    throw new QueryException(QueryException.ErrorCode.WRONG_PASSWORD);
        
        throw new QueryException(QueryException.ErrorCode.NOT_SIGNED_UP);
    }
    
// ================================================================================
    
}

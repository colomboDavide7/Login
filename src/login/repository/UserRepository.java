/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import java.io.File;
import login.tools.UserProperty;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class UserRepository {
    
    public static UserRepository createUserRepository(UserRequest r){
        return new UserRepository(r);
    }
    
    private File repository;
    private String ownerUser;
    
    private UserRepository(UserRequest r){
        ownerUser = r.getUserProperty(UserProperty.USERNAME);
        repository = new File(ownerUser + ".txt");
    }
    
    public boolean matchOwner(String toMatch){
        return ownerUser.equals(toMatch);
    }
    
}

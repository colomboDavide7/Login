/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.repositories.ISystemRepository;
import login.repositories.SystemRepository;
import login.repositories.TransactionException;
import login.system.UserProperty;
import login.system.UserRequest;
import login.tools.CredentialException;
import login.users.CustomerCreationException;
import login.users.IUser;
import login.users.User;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author davidecolombo
 */
public class TimerTest {
    
    private ISystemRepository repo;
    
    @Before
    public void setup(){
        this.repo = new SystemRepository();
    }
    
    @Test
    public void shouldHaveMaximumNumberOfTentatives() {
        System.out.println("* Timer Test: shouldHaveMaximumNumberOfTentatives()\n");
        
        try {
            this.repo.addNewCustomer(
                    UserRequest.createRequestByType(
                            createValidUser(), UserRequest.RequestType.SIGN_UP
                    )
            );
            
            
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
    
// ====================================================================================
    // User creation logic
    private IUser createValidUser() throws CustomerCreationException{
        String username  = "newCustomer";
        String pwd       = "Test1!";
        String firstName = "new";
        String lastName  = "customer";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
        return User.getBasicUser(basicProperties);
    }
    
}

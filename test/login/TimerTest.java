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
import login.repositories.AuthorizationException;
import login.repositories.ISystemRepository;
import login.repositories.SystemRepository;
import login.repositories.TransactionException;
import login.repositories.UserRepository;
import login.system.RepositoryInfoRequest;
import login.system.TransactionRequest;
import login.system.UserProperty;
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
    public void shouldCreateRepositoryInfoRequest() {
        System.out.println("* Timer Test: shouldCreateRepositoryInfoRequest()\n");
        
        try {
            
            TransactionRequest r = TransactionRequest.createRequestByType(
                    createValidUser(), TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            UserRepository userRepo = UserRepository.createUserRepository(r);
            RepositoryInfoRequest repoReq = RepositoryInfoRequest.createInfoRequest(
                    userRepo, RepositoryInfoRequest.AvailableInfo.LOGIN_ATTEMPTS
            );
            
            boolean matchInfo = this.repo.matchRepositoryInfo(repoReq);
            assertTrue(matchInfo);
            
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldDecreseNumberOfAttemptsWhenWrongPasswordErrorOccur(){
        System.out.println("* Timer Test: shouldDecreseNumberOfAttemptsWhenWrongPasswordErrorOccur()\n");
        TransactionRequest r = null;
        try {
            r = TransactionRequest.createRequestByType(
                    createValidUser(), TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            r = TransactionRequest.createRequestByType(
                    createValidUserWithWrongPassword(), TransactionRequest.TransactionType.LOGIN
            );
            this.repo.login(r);
            
        } catch (CustomerCreationException | CredentialException | AuthorizationException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.WRONG_PASSWORD, ex.getErrorCode());
        }
        
        UserRepository userRepo = UserRepository.createUserRepository(r);
        RepositoryInfoRequest repoReq = RepositoryInfoRequest.createInfoRequest(
                userRepo, RepositoryInfoRequest.AvailableInfo.LOGIN_ATTEMPTS
        );
        
        boolean matchValue = this.repo.matchRepositoryInfo(repoReq);
        assertFalse(matchValue);
        boolean matchByValue = this.repo.matchRepositoryInfoByValue(repoReq, "2");
        assertTrue(matchByValue);
    }
    
    @Test
    public void shouldThrowAuthorizationException(){
        System.out.println("* Timer Test: shouldThrowAuthorizationException()\n");
        
        try {
            TransactionRequest r = TransactionRequest.createRequestByType(
                    createValidUser(), TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            TransactionRequest wrong = TransactionRequest.createRequestByType(
                    createValidUserWithWrongPassword(), TransactionRequest.TransactionType.LOGIN
            );
            
            try {
                this.repo.login(wrong);
            } catch (TransactionException ex) {
                assertEquals(TransactionException.ErrorCode.WRONG_PASSWORD, ex.getErrorCode());
            }
            
            try {
                this.repo.login(wrong);
            } catch (TransactionException ex) {
                assertEquals(TransactionException.ErrorCode.WRONG_PASSWORD, ex.getErrorCode());
            }
            
            try {
                this.repo.login(wrong);
            } catch (TransactionException ex) {
                assertEquals(TransactionException.ErrorCode.WRONG_PASSWORD, ex.getErrorCode());
            }
            
            assertTrue(false);
            
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        } catch (AuthorizationException ex) {
            assertEquals(AuthorizationException.ErrorCode.LOGIN_ATTEMPTS_OVERFLOW, ex.getErrorCode());
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
    
    private IUser createValidUserWithWrongPassword() throws CustomerCreationException{
        String username  = "newCustomer";
        String pwd       = "wrongPassword";
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

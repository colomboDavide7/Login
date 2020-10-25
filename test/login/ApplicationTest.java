/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.tools.LoginException;
import login.tools.LoginException.ErrorCode;
import login.tools.UserValidator;
import login.users.User;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class ApplicationTest {

    private ApplicationRepository repo;
    
    // This snippet of code is always executed before running every single test
    @Before
    public void setup(){
        this.repo = new ApplicationRepository();
    }
    
// ================================================================================
// Username Tests
    @Test
    public void shouldRefuseEmptyUsername() {
        System.out.println("* UserValidator: shouldRefuseEmptyUsername()\n");
        String empty = "";
        
        try {
            UserValidator.isValidUsername(empty);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldRefuseUsernameThatDoesntBeginWithALetter() {
        System.out.println("* UserValidator: shouldRefuseUsernameThatDoesntBeginWithALetter()\n");
        String invalidUsername = "_1test_";
        
        try {
            UserValidator.isValidUsername(invalidUsername);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseUsernameWithSpace() {
        System.out.println("* UserValidator: shouldRefuseUsernameWithSpace()\n");
        String invalidUsername = "test ";
        try {
            UserValidator.isValidUsername(invalidUsername);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shoulAcceptUsernameWithSymbolNumberUpperCase() throws LoginException {
        System.out.println("* UserValidator: shoulAcceptUsernameWithSymbolNumberUpperCase()\n");
        String validUsername = "tEst1_";
        boolean login = UserValidator.isValidUsername(validUsername);
        Assert.assertEquals(true, login);
    }
    
// ================================================================================
    // Password Tests

    @Test 
    public void shouldRefuseEmptyPassword(){
        System.out.println("* UserValidator: shouldRefuseEmptyPassword()\n");
        String invalidPwd = "";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutSymbols(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutSymbols()\n");
        String invalidPwd = "test1";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutNumbers(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutNumbers()\n");
        String invalidPwd = "test!_";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test 
    public void shouldRefusePasswordWithoutLetters(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutLetters()\n");
        String invalidPwd = "!123#_";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutUpperCaseLetter(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutUpperCaseLetter()\n");
        String invalidPwd = "test!_2";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
// ================================================================================
    // User registration
    @Test
    public void shouldAddNewUser() throws LoginException, QueryException {
        System.out.println("* ApplicationRepository: shouldAddNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        
        this.repo.addUser(validUsername, validPassword);
        User searchedUser = repo.findUser(validUsername);
        
        Assert.assertTrue(new User(validUsername).equals(searchedUser));
    }
    
    @Test (expected = LoginException.class)
    public void shouldRejectNewUser() throws LoginException{
        System.out.println("* ApplicationRepository: shouldRejectNewUser()\n");
        String validUsername = " rossiMario97";
        String invalidPassword = "test";
        
        this.repo.addUser(validUsername, invalidPassword);
    }
        
    @Test
    public void shouldRejectUserWithSameName() throws QueryException{
        System.out.println("* ApplicationRepository: shouldRejectUserWithSameName()\n");
        String sameUsername = "rossiMario97";
        
        try {
            this.repo.addUser(sameUsername, "tEst!1");
            User searchedUser = repo.findUser(sameUsername);

            Assert.assertTrue(new User(sameUsername).equals(searchedUser));
            
            repo.addUser(sameUsername, "anotherValid_23");
        } catch (LoginException ex) {
            Assert.assertEquals(ex.getErrorCode(), LoginException.ErrorCode.USERNAME_ALREADY_USED);
        }
    }
    
// ================================================================================
    // User login
    
    
// ================================================================================
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.tools.LoginException;
import login.tools.LoginException.ErrorCode;
import login.tools.UserValidator;
import login.users.LoginRequest;
import login.users.SignUpRequest;
import login.users.User;
import login.users.UserRequest;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class ApplicationTest {

    private ApplicationRepository repo;
    private ApplicationManager manager;
    
    // This snippet of code is always executed before running every single test
    @Before
    public void setup(){
        this.repo = new ApplicationRepository();
        this.manager = new ApplicationManager();
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
    public void shouldAddNewUser() throws QueryException, LoginException {
        System.out.println("* ApplicationRepository: shouldAddNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        
        SignUpRequest loginRequest = new SignUpRequest(validUsername, validPassword);
        this.repo.addUser(loginRequest);
        User searchedUser = repo.findUser(loginRequest.getUsername());
        Assert.assertTrue(new User(validUsername).equals(searchedUser));
    }
    
    @Test
    public void shouldRejectNewUser() {
        System.out.println("* ApplicationRepository: shouldRejectNewUser()\n");
        String validUsername   = "rossiMario97";
        String invalidPassword = "test";
        
        try {
            SignUpRequest loginRequest = new SignUpRequest(validUsername, invalidPassword);
            this.repo.addUser(loginRequest);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
        
    @Test
    public void shouldRejectUserWithSameName() throws QueryException {
        System.out.println("* ApplicationRepository: shouldRejectUserWithSameName()\n");
        String sameUsername = "rossiMario97";
        
        try {
            SignUpRequest loginRequest = new SignUpRequest(sameUsername, "tEst!1");
            this.repo.addUser(loginRequest);
            loginRequest = new SignUpRequest(sameUsername, "anotherValid_23");
            repo.addUser(loginRequest);
        } catch (LoginException ex) {
            Assert.assertEquals(ex.getErrorCode(), LoginException.ErrorCode.USERNAME_ALREADY_USED);
        }
    }
    
// ================================================================================
    // User login
    @Test
    public void shouldRefuseNotSignedUpUsers(){
        System.out.println("* User Login: shouldRefuseNotSignedUpUsers()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            LoginRequest r = new LoginRequest(user, pwd);
            this.repo.userLogin(r);
        }catch(LoginException ex){
            Assert.assertEquals(ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
        
    }
            
    
// ================================================================================
    
}

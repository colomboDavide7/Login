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
    public void shouldSignUpNewUser() throws QueryException, LoginException {
        System.out.println("* ApplicationRepository: shouldSignUpNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        
        UserRequest sign = UserRequest.signupRequest(validUsername, validPassword);
        this.repo.parseSignUpRequest(sign);
        User searchedUser = repo.parseLoginRequest(sign);
        Assert.assertTrue(new User(validUsername, validPassword).equals(searchedUser));
    }
    
    @Test
    public void shouldRejectNewUser() {
        System.out.println("* ApplicationRepository: shouldRejectNewUser()\n");
        String validUsername   = "rossiMario97";
        String invalidPassword = "test";
        
        try {
            UserRequest loginRequest = UserRequest.signupRequest(validUsername, invalidPassword);
            this.repo.parseSignUpRequest(loginRequest);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRejectUserWithSameName() throws QueryException {
        System.out.println("* ApplicationRepository: shouldRejectUserWithSameName()\n");
        String sameUsername = "rossiMario97";
        
        try {
            UserRequest sign = UserRequest.signupRequest(sameUsername, "tEst!1");
            this.repo.parseSignUpRequest(sign);
            sign = UserRequest.signupRequest(sameUsername, "anotherValid_23");
            this.repo.parseSignUpRequest(sign);
        } catch (LoginException ex) {
            Assert.assertEquals(ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
    }
    
// ================================================================================
    // User login
    @Test
    public void shouldRefuseNotSignedUpUsers() {
        System.out.println("* User Login: shouldRefuseNotSignedUpUsers()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            UserRequest r = UserRequest.loginRequest(user, pwd);
            this.repo.parseLoginRequest(r);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseWrongPassword() throws LoginException {
        System.out.println("* User Login: shouldRefuseWrongPassword()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            UserRequest sign = UserRequest.signupRequest(user, pwd);
            this.repo.parseSignUpRequest(sign);

            User searchedUser = repo.parseLoginRequest(sign);
            Assert.assertTrue(new User(user, pwd).equals(searchedUser));

            UserRequest login = UserRequest.loginRequest(user, "wrongPwd");
            this.repo.parseLoginRequest(login);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.WRONG_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseWrongUsername() throws LoginException, QueryException{
        System.out.println("* User Login: shouldRefuseWrongUsername()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
        UserRequest sign = UserRequest.signupRequest(user, pwd);
        this.repo.parseSignUpRequest(sign);
        
        UserRequest login = UserRequest.loginRequest("anotheUsername", pwd);
        this.repo.parseLoginRequest(login);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
        
    }
    
// ================================================================================
    
}

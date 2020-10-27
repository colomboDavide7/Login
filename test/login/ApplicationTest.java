/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.repository.ApplicationRepository;
import login.repository.QueryException;
import login.tools.CredentialException;
import login.tools.CredentialException.ErrorCode;
import login.tools.UserValidator;
import login.users.User;
import login.request.UserRequest;
import login.users.IUser;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class ApplicationTest {

    private ApplicationRepository repo;
    private ApplicationManager manager;
    private Application application;
    
    // This snippet of code is always executed before running every single test
    @Before
    public void setup(){
        this.repo = new ApplicationRepository();
        this.manager = new ApplicationManager();
        this.application = new Application(manager, repo);
    }
    
// ================================================================================
// Username Tests
    @Test
    public void shouldRefuseEmptyUsername() {
        System.out.println("* UserValidator: shouldRefuseEmptyUsername()\n");
        String empty = "";
        
        try {
            UserValidator.isValidUsername(empty);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldRefuseUsernameThatDoesntBeginWithALetter() {
        System.out.println("* UserValidator: shouldRefuseUsernameThatDoesntBeginWithALetter()\n");
        String invalidUsername = "_1test_";
        
        try {
            UserValidator.isValidUsername(invalidUsername);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseUsernameWithSpace() {
        System.out.println("* UserValidator: shouldRefuseUsernameWithSpace()\n");
        String invalidUsername = "test ";
        try {
            UserValidator.isValidUsername(invalidUsername);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_USERNAME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shoulAcceptUsernameWithSymbolNumberUpperCase() throws CredentialException {
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
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutSymbols(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutSymbols()\n");
        String invalidPwd = "test1";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutNumbers(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutNumbers()\n");
        String invalidPwd = "test!_";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test 
    public void shouldRefusePasswordWithoutLetters(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutLetters()\n");
        String invalidPwd = "!123#_";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefusePasswordWithoutUpperCaseLetter(){
        System.out.println("* UserValidator: shouldRefusePasswordWithoutUpperCaseLetter()\n");
        String invalidPwd = "test!_2";
        
        try {
            UserValidator.isValidPassword(invalidPwd);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
// ================================================================================
    // User registration
    @Test
    public void shouldSignUpNewUser() throws QueryException, CredentialException {
        System.out.println("* User SignUp: shouldSignUpNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        
        IUser basicUser = new User(validUsername, validPassword);
        UserRequest sign = UserRequest.signupRequest(basicUser);
        this.manager.parseSignUpRequest(sign);
        IUser searchedUser = manager.parseLoginRequest(sign);
        Assert.assertTrue(basicUser.equals(searchedUser));
    }
    
    @Test
    public void shouldRejectNewUser() {
        System.out.println("* User SignUp: shouldRejectNewUser()\n");
        String validUsername   = "rossiMario97";
        String invalidPassword = "test";
        
        try {
            IUser basicUser = new User(validUsername, invalidPassword);
            UserRequest loginRequest = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(loginRequest);
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.INVALID_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRejectUserWithSameName() throws QueryException {
        System.out.println("* User SignUp: shouldRejectUserWithSameName()\n");
        String sameUsername = "rossiMario97";
        
        try {
            IUser basicUser = new User(sameUsername, "Test_1");
            UserRequest sign = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(sign);
            
            basicUser = new User(sameUsername, "anotherValid_23");
            sign = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(sign);
            
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldHaveTheLoggedOutStateAfterSigningUp() throws CredentialException, QueryException{
        System.out.println("* User SignUp: shouldHaveTheLoggedInStateOnAfterLogin()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.signupRequest(basicUser);
        this.manager.parseSignUpRequest(r);
        boolean isLogged = this.manager.isLoggedIn(basicUser);
        boolean isLoggedOut = this.manager.isLoggedOut(basicUser);
        
        // Put the double assertion in order to be sure that the test will fail.
        Assert.assertFalse(isLogged);
        Assert.assertTrue(isLoggedOut);
    }
    
// ================================================================================
    // User login
    @Test
    public void shouldRefuseNotSignedUpUsers() {
        System.out.println("* User Login: shouldRefuseNotSignedUpUsers()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            IUser basicUser = new User(user, pwd);
            UserRequest r = UserRequest.loginRequest(basicUser);
            this.manager.parseLoginRequest(r);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseWrongPassword() throws CredentialException {
        System.out.println("* User Login: shouldRefuseWrongPassword()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            IUser basicUser = new User(user, pwd);
            UserRequest sign = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(sign);
            
            basicUser = new User(user, "wrongPwd");
            UserRequest login = UserRequest.loginRequest(basicUser);
            this.manager.parseLoginRequest(login);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.WRONG_PASSWORD, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseWrongUsername() throws CredentialException, QueryException{
        System.out.println("* User Login: shouldRefuseWrongUsername()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            IUser basicUser = new User(user, pwd);
            UserRequest sign = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(sign);

            basicUser = new User("anotherUser", pwd);
            UserRequest login = UserRequest.loginRequest(basicUser);
            this.manager.parseLoginRequest(login);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldBeSuccessfullyLoggedIn() throws CredentialException, QueryException{
        System.out.println("* User Login: shouldBeSuccessfullLogin()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest sign = UserRequest.signupRequest(basicUser);
        this.manager.parseSignUpRequest(sign);
        
        UserRequest login = UserRequest.loginRequest(basicUser);
        IUser loggedIn = this.manager.parseLoginRequest(login);
        Assert.assertTrue(loggedIn.isLogged());
    }   
    
    @Test
    public void shouldRefuseLoginRequestWhenAlreadyLoggedIn() throws CredentialException, QueryException{
        System.out.println("* User Login: shouldRefuseLoginRequestWhenAlreadyLoggedIn()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            IUser basicUser = new User(user, pwd);
            UserRequest sign = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(sign);
            UserRequest login = UserRequest.loginRequest(basicUser);
            this.manager.parseLoginRequest(login);
            login = UserRequest.loginRequest(basicUser);
            this.manager.parseLoginRequest(login);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.ALREADY_LOGGED_IN, ex.getErrorCode());
        }
    }
    
// ================================================================================
    // User logout
    @Test
    public void shouldRefuseLogoutWhenNotLoggedIn() throws CredentialException, QueryException{
        System.out.println("* User Logout: shouldRefuseLogoutWhenNotLoggedIn()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            IUser basicUser = new User(user, pwd);
            UserRequest sign = UserRequest.signupRequest(basicUser);
            this.manager.parseSignUpRequest(sign);
            
            UserRequest logout = UserRequest.logoutRequest(basicUser);
            this.manager.parseLogoutRequest(logout);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.NOT_LOGGED_IN, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldRefuseLogoutWhenNotSignedUp(){
        System.out.println("* User Logout: shouldRefuseLogoutWhenNotSignedUp()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        try{
            IUser basicUser = new User(user, pwd);
            UserRequest logout = UserRequest.logoutRequest(basicUser);
            this.manager.parseLogoutRequest(logout);
        }catch(QueryException ex){
            Assert.assertEquals(QueryException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldAcceptLogoutWhenLoggedIn() throws CredentialException, QueryException{
        System.out.println("* User Logout: shouldRefuseLogoutWhenNotLoggedIn()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        IUser basicUser = new User(user, pwd);
        
        // Signing up
        UserRequest sign = UserRequest.signupRequest(basicUser);
        this.manager.parseSignUpRequest(sign);
        // Login
        UserRequest login = UserRequest.loginRequest(basicUser);
        this.manager.parseLoginRequest(login);
        // Logout
        UserRequest logout = UserRequest.logoutRequest(basicUser);
        IUser loggedOut = this.manager.parseLogoutRequest(logout);
        
        Assert.assertTrue(loggedOut.matchPassword(pwd) && loggedOut.matchUsername(user));
    }
    
// ================================================================================
    // Application Manager
    @Test
    public void shouldSendSignUpRequest() throws CredentialException{
        System.out.println("* Application Manager: shouldSendSignUpRequest()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.signupRequest(basicUser);
        boolean ans = this.application.sendSignUpRequest(r);
        Assert.assertTrue(ans);
    }
    
    @Test
    public void shouldSendLoginRequest() throws QueryException, CredentialException{
        System.out.println("* Application Manager: shouldSendLoginRequest()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.signupRequest(basicUser);
        
        boolean ans = this.application.sendSignUpRequest(r);
        Assert.assertTrue(ans);
        
        r = UserRequest.loginRequest(basicUser);
        boolean sended = this.application.sendLoginRequest(r);
        Assert.assertTrue(sended);
    }
    
    @Test
    public void shouldSendLogoutRequest() throws CredentialException, QueryException{
        System.out.println("* Application Manager: shouldSendLoginRequest()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        
        IUser basicUser = new User(user, pwd);
        UserRequest r = UserRequest.signupRequest(basicUser);
        boolean ans = this.application.sendSignUpRequest(r);
        Assert.assertTrue(ans);
        
        r = UserRequest.loginRequest(basicUser);
        boolean sended = this.application.sendLoginRequest(r);
        Assert.assertTrue(sended);
        
        r = UserRequest.logoutRequest(basicUser);
        sended = this.application.sendLogoutRequest(r);
        Assert.assertTrue(sended);
    }
    
    
}

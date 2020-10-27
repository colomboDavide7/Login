/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import junit.framework.Assert;
import login.repository.QueryException;
import login.tools.CredentialException;
import login.tools.CredentialException.ErrorCode;
import login.users.User;
import login.users.UserRequest;
import login.users.IUser;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class CustomerTest {

    private ApplicationManager manager;
    
    // This snippet of code is always executed before running every single test
    @Before
    public void setup(){
        this.manager = new ApplicationManager();
    }
    
// ================================================================================
    // User registration
    @Test
    public void shouldSignUpNewUser() throws QueryException, CredentialException {
        System.out.println("* User SignUp: shouldSignUpNewUser()\n");
        String validUsername = "rossiMario97";
        String validPassword = "tEst!1";
        
        IUser basicUser = new User(validUsername, validPassword);
        UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
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
            UserRequest loginRequest = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
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
            UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
            basicUser = new User(sameUsername, "anotherValid_23");
            sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
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
        UserRequest r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
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
            UserRequest r = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
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
            UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
            basicUser = new User(user, "wrongPwd");
            UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
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
            UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);

            basicUser = new User("anotherUser", pwd);
            UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
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
        UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
        this.manager.parseSignUpRequest(sign);
        
        UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
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
            UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
            this.manager.parseLoginRequest(login);
            login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
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
            UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
            UserRequest logout = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGOUT);
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
            UserRequest logout = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGOUT);
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
        UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
        this.manager.parseSignUpRequest(sign);
        // Login
        UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
        this.manager.parseLoginRequest(login);
        // Logout
        UserRequest logout = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGOUT);
        IUser loggedOut = this.manager.parseLogoutRequest(logout);
        
        Assert.assertTrue(loggedOut.matchPassword(pwd) && loggedOut.matchUsername(user));
    }
    
    
}

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
import junit.framework.Assert;
import static junit.framework.Assert.*;
import login.repositories.AuthorizationException;
import login.repositories.TransactionException;
import login.tools.CredentialException;
import login.tools.CredentialException.ErrorCode;
import login.users.User;
import login.system.TransactionRequest;
import login.users.IUser;
import login.system.UserProperty;
import login.users.CustomerCreationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author davidecolombo
 */
public class CustomerTest {

    private SystemManager manager;
    
    // This snippet of code is always executed before running every single test
    @Before
    public void setup(){
        this.manager = new SystemManager();
    }
    
// ================================================================================
    // User registration
    
    // This test is already covered in FileParserTest
//    @Deprecated
//    @Test
//    public void shouldSignUpNewUser() throws TransactionException, CredentialException, CustomerCreationException {
//        System.out.println("* User SignUp: shouldSignUpNewUser()\n");
//        String username = "valid";
//        String pwd      = "Test_1";
//        String firstName = "Mario";
//        String lastName = "Rossi";
//        Map<UserProperty, String> basicProperties = new HashMap<>();
//        basicProperties.put(UserProperty.USERNAME, username);
//        basicProperties.put(UserProperty.PASSWORD, pwd);
//        basicProperties.put(UserProperty.FIRST_NAME, firstName);
//        basicProperties.put(UserProperty.LAST_NAME, lastName);
//        
//        IUser customer = User.getBasicUser(basicProperties);
//        UserRequest sign = UserRequest.createRequestByType(customer, UserRequest.RequestType.SIGN_UP);
//        this.manager.parseSignUpRequest(sign);
//        IUser searchedUser = manager.parseLoginRequest(sign);
//        Assert.assertTrue(customer.equals(searchedUser));
//    }
    
    @Test
    public void shouldRejectUserWithSameName() throws TransactionException, CustomerCreationException {
        System.out.println("* User SignUp: shouldRejectUserWithSameName()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        IUser customer = User.getBasicUser(basicProperties);
        
        try {
            TransactionRequest sign = TransactionRequest.createRequestByType(customer, TransactionRequest.TransactionType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
            sign = TransactionRequest.createRequestByType(customer, TransactionRequest.TransactionType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
        } catch (CredentialException ex) {
            Assert.assertEquals(ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
    }
    
    // This test is already covered in FileParserTest
//    @Deprecated
//    @Test
//    public void shouldHaveTheLoggedOutStateAfterSigningUp() throws CredentialException, TransactionException, CustomerCreationException{
//        System.out.println("* User SignUp: shouldHaveTheLoggedInStateOnAfterLogin()\n");
//        String username = "valid";
//        String pwd      = "Test_1";
//        String firstName = "Mario";
//        String lastName = "Rossi";
//        Map<UserProperty, String> basicProperties = new HashMap<>();
//        basicProperties.put(UserProperty.USERNAME, username);
//        basicProperties.put(UserProperty.PASSWORD, pwd);
//        basicProperties.put(UserProperty.FIRST_NAME, firstName);
//        basicProperties.put(UserProperty.LAST_NAME, lastName);
//        
//        IUser customer = User.getBasicUser(basicProperties);
//        UserRequest r = UserRequest.createRequestByType(customer, UserRequest.RequestType.SIGN_UP);
//        this.manager.parseSignUpRequest(r);
//        boolean isLogged = this.manager.isLoggedIn(customer);
//        boolean isLoggedOut = this.manager.isLoggedOut(customer);
//        
//        // Put the double assertion in order to be sure that the test will fail.
//        Assert.assertFalse(isLogged);
//        Assert.assertTrue(isLoggedOut);
//    }
    
// ================================================================================
    // User login
    @Test
    public void shouldRefuseNotSignedUpUsers() throws CustomerCreationException {
        System.out.println("* User Login: shouldRefuseNotSignedUpUsers()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        IUser customer = User.getBasicUser(basicProperties);
        
        try{
            TransactionRequest r = TransactionRequest.createRequestByType(customer, TransactionRequest.TransactionType.LOGIN);
            this.manager.parseLoginRequest(r);
        }catch(TransactionException ex){
            Assert.assertEquals(TransactionException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        } catch (AuthorizationException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldRefuseWrongPassword() throws CredentialException {
        System.out.println("* User Login: shouldRefuseWrongPassword()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        try{
            IUser customer = User.getBasicUser(basicProperties);
            TransactionRequest sign = TransactionRequest.createRequestByType(customer, TransactionRequest.TransactionType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
            basicProperties.put(UserProperty.PASSWORD, "wrong");
            customer = User.getBasicUser(basicProperties);
            TransactionRequest login = TransactionRequest.createRequestByType(customer, TransactionRequest.TransactionType.LOGIN);
            this.manager.parseLoginRequest(login);
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        } catch (AuthorizationException ex) {
            assertEquals("Wrong attempts. 2 attempts left", ex.getErrorMessage());
        }
    }
    
    @Test
    public void shouldRefuseWrongUsername() throws CredentialException, TransactionException{
        System.out.println("* User Login: shouldRefuseWrongUsername()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        try{
            IUser basicUser = User.getBasicUser(basicProperties);
            TransactionRequest sign = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);

            basicProperties.put(UserProperty.USERNAME, "anotherUSer");
            basicUser = User.getBasicUser(basicProperties);
            TransactionRequest login = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.LOGIN);
            this.manager.parseLoginRequest(login);
        }catch(TransactionException ex){
            Assert.assertEquals(TransactionException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        } catch (CustomerCreationException | AuthorizationException ex) {
            assertTrue(false);
        }
    }
    
    
    // This test is already covered in FileParserTest
//    @Deprecated
//    @Test
//    public void shouldBeSuccessfullyLoggedIn() throws CredentialException, TransactionException, CustomerCreationException{
//        System.out.println("* User Login: shouldBeSuccessfullLogin()\n");
//        String username = "valid";
//        String pwd      = "Test_1";
//        String firstName = "Mario";
//        String lastName = "Rossi";
//        Map<UserProperty, String> basicProperties = new HashMap<>();
//        basicProperties.put(UserProperty.USERNAME, username);
//        basicProperties.put(UserProperty.PASSWORD, pwd);
//        basicProperties.put(UserProperty.FIRST_NAME, firstName);
//        basicProperties.put(UserProperty.LAST_NAME, lastName);
//        IUser basicUser = User.getBasicUser(basicProperties);
//        UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
//        this.manager.parseSignUpRequest(sign);
//        
//        UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
//        IUser loggedIn = this.manager.parseLoginRequest(login);
//        Assert.assertTrue(loggedIn.isLogged());
//    }   
    
    @Test
    public void shouldRefuseLoginRequestWhenAlreadyLoggedIn() throws CredentialException, TransactionException{
        System.out.println("* User Login: shouldRefuseLoginRequestWhenAlreadyLoggedIn()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        try{
            IUser basicUser = User.getBasicUser(basicProperties);
            TransactionRequest sign = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            TransactionRequest login = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.LOGIN);
            this.manager.parseLoginRequest(login);
            login = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.LOGIN);
            this.manager.parseLoginRequest(login);
        }catch(TransactionException ex){
            Assert.assertEquals(TransactionException.ErrorCode.ALREADY_LOGGED_IN, ex.getErrorCode());
        } catch (CustomerCreationException | AuthorizationException ex) {
            assertTrue(false);
        }
    }
    
// ================================================================================
    // User logout
    @Test
    public void shouldRefuseLogoutWhenNotLoggedIn() throws CredentialException, TransactionException {
        System.out.println("* User Logout: shouldRefuseLogoutWhenNotLoggedIn()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        try{
            IUser basicUser = User.getBasicUser(basicProperties);
            TransactionRequest sign = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.SIGN_UP);
            this.manager.parseSignUpRequest(sign);
            
            TransactionRequest logout = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.LOGOUT);
            this.manager.parseLogoutRequest(logout);
        }catch(TransactionException ex){
            Assert.assertEquals(TransactionException.ErrorCode.NOT_LOGGED_IN, ex.getErrorCode());
        } catch (CustomerCreationException ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void shouldRefuseLogoutWhenNotSignedUp(){
        System.out.println("* User Logout: shouldRefuseLogoutWhenNotSignedUp()\n");
        String username = "valid";
        String pwd      = "Test_1";
        String firstName = "Mario";
        String lastName = "Rossi";
        Map<UserProperty, String> basicProperties = new HashMap<>();
        basicProperties.put(UserProperty.USERNAME, username);
        basicProperties.put(UserProperty.PASSWORD, pwd);
        basicProperties.put(UserProperty.FIRST_NAME, firstName);
        basicProperties.put(UserProperty.LAST_NAME, lastName);
        
        try{
            IUser basicUser = User.getBasicUser(basicProperties);
            TransactionRequest logout = TransactionRequest.createRequestByType(basicUser, TransactionRequest.TransactionType.LOGOUT);
            this.manager.parseLogoutRequest(logout);
        }catch(TransactionException ex){
            Assert.assertEquals(TransactionException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        } catch (CustomerCreationException ex) {
            Logger.getLogger(CustomerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // This test is already covered in FileParserTest
//    @Deprecated
//    @Test
//    public void shouldAcceptLogoutWhenLoggedIn() throws CredentialException, TransactionException, CustomerCreationException{
//        System.out.println("* User Logout: shouldRefuseLogoutWhenNotLoggedIn()\n");
//        String username = "valid";
//        String pwd      = "Test_1";
//        String firstName = "Mario";
//        String lastName = "Rossi";
//        Map<UserProperty, String> basicProperties = new HashMap<>();
//        basicProperties.put(UserProperty.USERNAME, username);
//        basicProperties.put(UserProperty.PASSWORD, pwd);
//        basicProperties.put(UserProperty.FIRST_NAME, firstName);
//        basicProperties.put(UserProperty.LAST_NAME, lastName);
//        
//        IUser basicUser = User.getBasicUser(basicProperties);
//        
//        // Signing up
//        UserRequest sign = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.SIGN_UP);
//        this.manager.parseSignUpRequest(sign);
//        // Login
//        UserRequest login = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGIN);
//        this.manager.parseLoginRequest(login);
//        // Logout
//        UserRequest logout = UserRequest.createRequestByType(basicUser, UserRequest.RequestType.LOGOUT);
//        IUser loggedOut = this.manager.parseLogoutRequest(logout);
//        
//        Assert.assertTrue(loggedOut.matchProperty(UserProperty.PASSWORD, pwd) && 
//                          loggedOut.matchProperty(UserProperty.USERNAME, username));
//    }
    
// ================================================================================
    // User property
    @Test
    public void shouldBeMandatory(){
        System.out.println("* User Property: shouldBeMandatory()\n");
        assertTrue(UserProperty.isMandatory(UserProperty.USERNAME));
        assertTrue(UserProperty.isMandatory(UserProperty.PASSWORD));
        assertFalse(UserProperty.isMandatory(UserProperty.E_MAIL));
        assertFalse(UserProperty.isMandatory(UserProperty.MAIN_PHONE));
        assertTrue(UserProperty.isOptional(UserProperty.E_MAIL));
        assertTrue(UserProperty.isOptional(UserProperty.MAIN_PHONE));
    }
    
// ================================================================================
    // User factory
    @Test
    public void shouldReturnMissingMandatoryError(){
        System.out.println("* User Property: shouldReturnMissingMandatoryError()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        String firstName = "Mario";
        
        try{
            Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, user);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);

            IUser customer = User.getBasicUser(basicProperties);
        }catch(CustomerCreationException ex){
            assertEquals(CustomerCreationException.ErrorCode.MISSING_MANDATORY, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldContainsLastNameProperty(){
        System.out.println("* User Property: shouldContainsLastNameProperty()\n");
        String user = "testUser";
        String pwd  = "Test1!";
        String firstName = "Mario";
        
        try{
            Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, user);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);

            IUser customer = User.getBasicUser(basicProperties);
        }catch(CustomerCreationException ex){
            assertTrue(ex.getMissingMandatoryList().contains(UserProperty.LAST_NAME));
        }
    }
    
    @Test
    public void shouldContainsEmptyProperty(){
        System.out.println("* User Property: shouldContainsEmptyProperty()\n");
        String user = "testUser";
        String pwd  = "";
        String firstName = "Mario";
        String lastName = "Rossi";
        
        try{
            Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, user);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);

            IUser customer = User.getBasicUser(basicProperties);
            assertFalse(true);
        }catch(CustomerCreationException ex){
            assertTrue(ex.getMissingMandatoryList().contains(UserProperty.PASSWORD));
        }
    }
    
}

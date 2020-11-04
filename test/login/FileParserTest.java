/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import login.repositories.AuthorizationException;
import login.repositories.SystemRepository;
import login.repositories.TransactionException;
import login.tools.CredentialException;
import login.tools.FileParser;
import login.tools.ParserSchemeException;
import login.system.UserProperty;
import login.users.CustomerCreationException;
import login.users.IUser;
import login.users.User;
import login.system.TransactionRequest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import login.repositories.ISystemRepository;

/**
 *
 * @author davidecolombo
 */
public class FileParserTest {
    
    private ISystemRepository repo;
    
    @Before
    public void setup(){
        this.repo = new SystemRepository();
    }
    
    @Test
    public void shouldReturnParserSchemeException() throws FileNotFoundException{
        System.out.println("* File Parser: shouldReturnParserSchemeException()\n");
        File f = new File("shouldReturnParserSchemeException.txt");
        try{
            List<String> lines = this.openAndReadedTextFile(f);
            for(String l : lines)
                FileParser.validateRecordScheme(l);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_SCHEME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldReturnWrongKeyValueSeparatorError() throws FileNotFoundException {
        System.out.println("* File Parser: shouldReturnWrongKeyValueSeparatorError()\n");
        File f = new File("shouldReturnWrongKeyValueSeparatorError.txt");
        
        try{
            List<String> lines = this.openAndReadedTextFile(f);
            for(String l : lines)
                FileParser.validateRecordScheme(l);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldValidateUserRecord(){
        System.out.println("* File Parser: shouldValidateUserRecord()\n");
        try {
            IUser newCustomer = this.createValidUser();
            boolean isValid = FileParser.validateRecordScheme(newCustomer.createRecord());
            assertTrue(isValid);
        } catch (CustomerCreationException | ParserSchemeException ex) {
            assertFalse(true);
        }
        
    }
       
    @Test
    public void shouldExistsAfterSignup(){
        System.out.println("* File Parser: shouldExistsAfterSignup()\n");            
        try {
            IUser newCustomer = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer, TransactionRequest.TransactionType.SIGN_UP);
            this.repo.addNewCustomer(r);
            
            boolean exists = repo.isSignedUp(r);
            assertTrue(exists);
        } catch (CustomerCreationException | TransactionException | CredentialException ex) {
            assertFalse(true);
        }
        
    }
    
    @Test
    public void shouldReturnUsernameAlreadyUsedError(){
        System.out.println("* File Parser: shouldReturnUsernameAlreadyUsedError()\n");
        try {
            IUser newCustomer = this.createValidUser();
            this.repo.addNewCustomer(TransactionRequest.createRequestByType(newCustomer, TransactionRequest.TransactionType.SIGN_UP
                    )
            );
            
            this.repo.addNewCustomer(TransactionRequest.createRequestByType(newCustomer, TransactionRequest.TransactionType.SIGN_UP
                    )
            );
            
            assertTrue(false);
        } catch (CustomerCreationException | TransactionException ex) {
            assertTrue(false);
        } catch (CredentialException ex) {
            assertEquals(CredentialException.ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldContainTwoUsersInDatabase(){
        System.out.println("* File Parser: shouldContainTwoUsersInDatabase()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest req1   = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP);
            this.repo.addNewCustomer(req1);
            
            IUser newCustomer2 = this.createSecondValidUser();
            TransactionRequest req2   = TransactionRequest.createRequestByType(newCustomer2, TransactionRequest.TransactionType.SIGN_UP);
            this.repo.addNewCustomer(req2);
            
            boolean exists1 = this.repo.isSignedUp(req1);
            boolean exists2 = this.repo.isSignedUp(req2);
            assertTrue(exists1 && exists2);
            
        } catch (CustomerCreationException | TransactionException ex) {
            assertTrue(false);
        } catch (CredentialException ex) {
            assertEquals(CredentialException.ErrorCode.USERNAME_ALREADY_USED, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldCreateUserRepository(){
        System.out.println("* File Parser: shouldCreateUserRepository()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            boolean exists = this.repo.isSignedUp(r);
            assertTrue(exists);
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
        
    @Test
    public void shouldHaveSignupTransaction(){
        System.out.println("* File Parser: shouldHaveOneTransactionAtCreation()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            boolean signedUp = this.repo.isSignedUp(r);
            assertTrue(signedUp);
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldSendLoginTransaction(){
        System.out.println("* File Parser: shouldSendLoginTransaction()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            TransactionRequest loginRequest = 
                    TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.LOGIN);
            this.repo.login(loginRequest);
            boolean logged = this.repo.isLogged(loginRequest);
            assertTrue(logged);
        } catch (CustomerCreationException | CredentialException | TransactionException | AuthorizationException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldThrowTransactionException(){
        System.out.println("* File Parser: shouldSendLoginTransaction()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(
                    newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            TransactionRequest loginRequest = 
                    TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.LOGIN);
            this.repo.login(loginRequest);
            this.repo.login(loginRequest);
            
            assertTrue(false);
            
        } catch (CustomerCreationException | CredentialException | AuthorizationException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.ALREADY_LOGGED_IN, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldRefuseWrongTransaction(){
        System.out.println("* File Parser: shouldRefuseWrongTransaction()\n");
        try {
            // Signup
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            // Login
            TransactionRequest wrong = 
                    TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.LOGOUT);
            this.repo.login(wrong);
            
        } catch (CustomerCreationException | CredentialException | AuthorizationException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.WRONG_REQUEST, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldReturnNotSignedupError(){
        System.out.println("* File Parser: shouldReturnNotSignedupError()\n");
        try {
            IUser newCustomer1 = this.createValidUser();
            // Login
            TransactionRequest wrong = 
                    TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.LOGIN);
            this.repo.login(wrong);
            assertTrue(false);
        } catch (CustomerCreationException | AuthorizationException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.NOT_SIGNED_UP, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldReturnWrongPasswordError(){
        System.out.println("* File Parser: shouldReturnWrongPasswordError()\n");
        try {
            // Signup
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            // Login
            TransactionRequest wrong = 
                    TransactionRequest.createRequestByType(this.createValidUsernameWithWrongPassword(), TransactionRequest.TransactionType.LOGIN
                    );
            this.repo.login(wrong);
            assertTrue(false);
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            assertTrue(false);
        } catch (AuthorizationException ex) {
            assertEquals("Wrong attempts. 2 attempts left", ex.getErrorMessage());
        }
    }
    
    @Test
    public void shouldReturnNotLoggedInError(){
        System.out.println("* File Parser: shouldReturnNotLoggedInError()\n");
        try {
            // Signup
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            // Logut
            TransactionRequest wrong = 
                    TransactionRequest.createRequestByType(this.createValidUsernameWithWrongPassword(), TransactionRequest.TransactionType.LOGOUT
                    );
            this.repo.logout(wrong);
            assertTrue(false);
        } catch (CustomerCreationException | CredentialException ex) {
            assertTrue(false);
        } catch (TransactionException ex) {
            assertEquals(TransactionException.ErrorCode.NOT_LOGGED_IN, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldLoggedOutSuccessfully(){
        System.out.println("* File Parser: shouldLoggedOutSuccessfully()\n");
        try {
            // Signup
            IUser newCustomer1 = this.createValidUser();
            TransactionRequest r = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.SIGN_UP
            );
            this.repo.addNewCustomer(r);
            
            // Login
            TransactionRequest login = TransactionRequest.createRequestByType(newCustomer1, TransactionRequest.TransactionType.LOGIN
            );
            this.repo.login(login);
            
            // Logut
            TransactionRequest logout = 
                    TransactionRequest.createRequestByType(this.createValidUsernameWithWrongPassword(), TransactionRequest.TransactionType.LOGOUT
                    );
            this.repo.logout(logout);
            boolean isLoggedOut = !this.repo.isLogged(logout);
            assertTrue(isLoggedOut);
            
        } catch (CustomerCreationException | CredentialException | TransactionException ex) {
            System.out.println(ex);
            assertTrue(false);
        } catch (AuthorizationException ex) {
            assertTrue(false);
        }
    }
        
// ====================================================================================
    // Stream opening logic
    private List<String> openAndReadedTextFile(File f){
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        String line;
        
        try{
            reader = new BufferedReader(new FileReader(f));
            while(true){
                line = reader.readLine();
                if(line == null){
                    reader.close();
                    break;
                }
                
                lines.add(line);
            }
            
        }catch(IOException ex){
            System.err.println("Error reading the file\n");
        }finally{
            if(reader != null)
                try {
                    reader.close();
            } catch (IOException ex) {
                    System.err.println("Error closing the file\n");
            }
        }
        
        return lines;
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
    
    private IUser createSecondValidUser() throws CustomerCreationException{
        String username  = "marioRossi99";
        String pwd       = "Test1!";
        String firstName = "Mario";
        String lastName  = "Rossi";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
        return User.getBasicUser(basicProperties);
    }
    
    private IUser createInvalidUser() throws CustomerCreationException{
        String username  = "newCustomer";
        String pwd       = "Test";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
        return User.getBasicUser(basicProperties);
    }
    
    private IUser createValidUsernameWithWrongPassword() throws CustomerCreationException{
        String username  = "newCustomer";
        String pwd       = "wrongpassword";
        String firstName = "new";
        String lastName  = "customer";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
        return User.getBasicUser(basicProperties);
    }
    
// ====================================================================================

}

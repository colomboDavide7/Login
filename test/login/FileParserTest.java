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
import java.util.logging.Level;
import java.util.logging.Logger;
import login.tools.FileParser;
import login.tools.ParserScheme;
import login.tools.ParserSchemeException;
import login.tools.UserProperty;
import login.users.CustomerCreationException;
import login.users.IUser;
import login.users.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davidecolombo
 */
public class FileParserTest {
    
    
    @Test (expected = FileNotFoundException.class)
    public void shouldReturnFileNotFoundException() throws FileNotFoundException, ParserSchemeException{
        System.out.println("* File Parser: shouldReturnFileNotFoundException()\n");
        File f = new File("user2.txt");
        FileParser.parseFile(f, UserProperty.USERNAME, "not used");
    }
    
    @Test
    public void shouldReturnTheValidParserScheme() throws FileNotFoundException{
        System.out.println("* File Parser: shouldReturnTheValidParserScheme()\n");
        File f = new File("user1.txt");
        ParserScheme validScheme = FileParser.getDefaultParserScheme();
        assertEquals(ParserScheme.VALID, validScheme);
    }
    
    @Test
    public void shouldReturnParserSchemeException() throws FileNotFoundException{
        System.out.println("* File Parser: shouldReturnParserSchemeException()\n");
        File f = new File("shouldReturnParserSchemeException.txt");
        try{
            List<String> lines = this.openAndReadedTextFile(f);
            for(String l : lines)
                FileParser.isValidRecord(l);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_SCHEME, ex.getErrorCode());
        }
    }
    
    @Test
    public void shouldReturnWrongKeyValueSeparatorError() throws FileNotFoundException{
        System.out.println("* File Parser: shouldReturnWrongKeyValueSeparatorError()\n");
        File f = new File("shouldReturnWrongKeyValueSeparatorError.txt");
        
        try{
            List<String> lines = this.openAndReadedTextFile(f);
            for(String l : lines)
                FileParser.isValidRecord(l);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR, ex.getErrorCode());
        }
        
    }
    
    @Test
    public void shouldMatchUserNameProperty() throws FileNotFoundException {
        System.out.println("* File Parser: shouldMatchUserNameProperty()\n");
        String toMatch = "marioRossi";
        File f = new File("user1.txt");
        
        try{
            boolean match = FileParser.parseFile(f, UserProperty.USERNAME, toMatch);
            assertTrue(match);
            match = FileParser.parseFile(f, UserProperty.USERNAME, "notPresent");
            assertFalse(match);
        }catch(ParserSchemeException ex){
            assertTrue(false);
        }
    }
    
    @Test
    public void shouldValidateUserRecord(){
        System.out.println("* File Parser: shouldValidateUserRecord()\n");
        File f = new File("user1.txt");
        
        String username  = "newCustomer";
        String pwd       = "Test1!";
        String firstName = "new";
        String lastName  = "customer";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
            
        try {
            IUser newCustomer = User.getBasicUser(basicProperties);
            boolean isValid = FileParser.isValidRecord(newCustomer.createRecord());
            assertTrue(isValid);
        } catch (CustomerCreationException | ParserSchemeException ex) {
            assertFalse(true);
        }
        
    }
       
    @Test
    public void shouldInsertNewCustomerInDatabase(){
        System.out.println("* File Parser: shouldInsertNewCustomerInDatabase()\n");
        String username  = "newCustomer";
        String pwd       = "Test1!";
        String firstName = "new";
        String lastName  = "customer";
        
        Map<UserProperty, String> basicProperties = new HashMap<>();
            basicProperties.put(UserProperty.USERNAME, username);
            basicProperties.put(UserProperty.PASSWORD, pwd);
            basicProperties.put(UserProperty.FIRST_NAME, firstName);
            basicProperties.put(UserProperty.LAST_NAME, lastName);
            
        try {
            IUser newCustomer = User.getBasicUser(basicProperties);
            FileParser.addNewCustomer(newCustomer);
            boolean find = FileParser.searchProperty(UserProperty.USERNAME, username) &&
                           FileParser.searchProperty(UserProperty.PASSWORD, pwd)      &&
                           FileParser.searchProperty(UserProperty.FIRST_NAME, firstName) &&
                           FileParser.searchProperty(UserProperty.LAST_NAME, lastName);
            assertTrue(find);
        } catch (CustomerCreationException | FileNotFoundException | ParserSchemeException ex) {
            assertFalse(true);
        }
        
    }
    
// ====================================================================================
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
    
}

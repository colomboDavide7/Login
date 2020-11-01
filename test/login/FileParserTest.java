/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.File;
import java.io.FileNotFoundException;
import login.tools.FileParser;
import login.tools.ParserScheme;
import login.tools.ParserSchemeException;
import login.tools.UserProperty;
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
            FileParser.parseFile(f, UserProperty.USERNAME, "not used");
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
            FileParser.parseFile(f, UserProperty.USERNAME, "not used");
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
    
}

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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davidecolombo
 */
public class FileParserTest {
    
//    @Test
//    public void shouldParseFile() {
//        System.out.println("* File Parser: shouldParseFile()\n");
//        File f = new File("user1.txt");
//        boolean parsed;
//        try {
//            parsed = FileParser.parseFile(f);
//            assertTrue(parsed);
//        } catch (FileNotFoundException ex) {
//            assertFalse(true);
//            System.out.println(ex.getMessage());
//        }
//    }
    
    @Test (expected = FileNotFoundException.class)
    public void shouldReturnFileNotFoundException() throws FileNotFoundException, ParserSchemeException{
        System.out.println("* File Parser: shouldReturnFileNotFoundException()\n");
        File f = new File("user2.txt");
        FileParser.parseFile(f);
    }
    
    @Test
    public void shouldReturnTheDefaultParserScheme() throws FileNotFoundException{
        System.out.println("* File Parser: shouldReturnTheDefaultParserScheme()\n");
        File f = new File("user1.txt");
        ParserScheme defaultScheme = FileParser.getDefaultParserScheme();
        assertEquals(ParserScheme.KEY_EQUALS_VALUE, defaultScheme);
    }
    
    @Test
    public void shouldReturnParserSchemeException(){
        System.out.println("* File Parser: shouldReturnParserSchemeException()\n");
        File f = new File("shouldReturnParserSchemeException.txt");
        try{
            FileParser.parseFile(f);
            assertTrue(false);
        }catch(ParserSchemeException ex){
            assertEquals(ParserSchemeException.ErrorCode.WRONG_SCHEME, ex.getErrorCode());
        } catch (FileNotFoundException ex) {
            
        }
    }
            
}

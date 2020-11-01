/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class FileParser {
    
    private static ParserScheme currentScheme = ParserScheme.VALID;
    private static File customerFile = new File("user1.txt");
    
    public static boolean isValidRecord(String customerRecord) throws ParserSchemeException{
        if(!customerRecord.contains(currentScheme.getPropertySeparator()))
            throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_SCHEME);
        
        String[] keyValuePairs = customerRecord.split(currentScheme.getPropertySeparator());
        for(String p : keyValuePairs)
            if(!p.contains(currentScheme.getKeyValueSeparator()))
                throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR);
        return true;
    }
    
    public static void addNewCustomer(IUser newCustomer){
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(customerFile));
        } catch (IOException ex) {
            
        }
    }
    
    private StringBuilder createUserRecord(IUser newCustomer){
        StringBuilder sb = new StringBuilder();
        
        return sb;
    }
    
    
    public static boolean parseFile(File toParse, UserProperty p, String value) throws FileNotFoundException, ParserSchemeException {
        if(!toParse.exists())
            throw new FileNotFoundException("toParse = " + toParse);
        
        return openAndReadTextFile(toParse, p, value);
    }
    
    private static boolean openAndReadTextFile(File f, UserProperty p, String value) throws ParserSchemeException {
        BufferedReader reader = null;
        String line;
        try {
            reader = new BufferedReader(new FileReader(f));
            while(true){
                line = reader.readLine();
                if(line == null){
                    reader.close();
                    break;
                }
                
                if(parseLine(line, p, value))
                    return true;
            }
            
        } catch (IOException ex) { 
            
        }finally{
            if(reader != null)
                try {
                    reader.close();
            } catch (IOException ex) {
                    System.err.println("Error closing the stream\n");
            }
        }
        
        return false;
    }
    
    private static boolean parseLine(String line, UserProperty toMatch, String value) throws ParserSchemeException {
        for(String keyValuePair : line.split(currentScheme.getPropertySeparator()))
            if(parseProperty(keyValuePair.split(currentScheme.getKeyValueSeparator()), toMatch, value))
                return true;
        return false;
    }
    
    private static boolean parseProperty(String[] keyValuePair, UserProperty p, String value){
        if(keyValuePair[0].trim().equals(p.name()))
            if(keyValuePair[1].trim().equals(value))
                return true;
        return false;
    }
    
    public static ParserScheme getDefaultParserScheme(){
        return ParserScheme.VALID;
    }    
    
}

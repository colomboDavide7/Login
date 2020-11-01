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
import login.users.IUser;

/**
 *
 * @author davidecolombo
 */
public class FileParser {
    
    private static ParserScheme currentScheme = ParserScheme.VALID;
    private static File customerFile = new File("customer_repo.txt");
    
// ================================================================================
    // Record validation
    public static boolean isValidRecord(String customerRecord) throws ParserSchemeException{
        if(!customerRecord.contains(currentScheme.getPropertySeparator()))
            throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_SCHEME);
        
        String[] keyValuePairs = customerRecord.split(currentScheme.getPropertySeparator());
        for(String p : keyValuePairs)
            if(!p.contains(currentScheme.getKeyValueSeparator()))
                throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR);
        return true;
    }
    
// ================================================================================
    // Writing a new record
    public static void addNewCustomer(File f, String customerRecord){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.append(customerRecord);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            System.err.println("Error writing a record\n");
        }finally{
            if(writer != null)
                try {
                    writer.close();
            } catch (IOException ex) {
                    System.err.println("Error closing the writer\n");
            }
        }
    }
    
    public static boolean searchProperty(File f, UserProperty p, String value) throws FileNotFoundException, ParserSchemeException{
        return parseFile(f, p, value);
    }
    
// ================================================================================
    // Parse property
    private static boolean parseFile(File toParse, UserProperty p, String value) throws FileNotFoundException, ParserSchemeException {
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
            System.err.println("Error reading file: " + f);
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
    
// ================================================================================
    public static ParserScheme getDefaultParserScheme(){
        return ParserScheme.VALID;
    }    
    
// ================================================================================
    
}

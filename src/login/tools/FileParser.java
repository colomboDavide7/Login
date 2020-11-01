/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author davidecolombo
 */
public class FileParser {
    
    private static ParserScheme currentScheme = ParserScheme.VALID;
    
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
            
        }
        
        return false;
    }
    
    private static boolean parseLine(String line, UserProperty toMatch, String value) throws ParserSchemeException {
        if(!line.contains(currentScheme.getPropertySeparator()))
            throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_SCHEME);
        
        String[] customerProperties = line.split(currentScheme.getPropertySeparator());
        for(String p : customerProperties){
            if(!p.contains(currentScheme.getKeyValueSeparator()))
                throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR);
            if(parseProperty(p.split(currentScheme.getKeyValueSeparator()), toMatch, value))
                return true;
        }
        
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

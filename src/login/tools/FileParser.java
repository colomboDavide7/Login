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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davidecolombo
 */
public class FileParser {
    
    private static final char CUSTOMER_SEPARATOR = '#';
    private static ParserScheme currentScheme = ParserScheme.KEY_EQUALS_VALUE;
    
    public static boolean parseFile(File toParse) throws FileNotFoundException, ParserSchemeException {
        if(!toParse.exists())
            throw new FileNotFoundException("toParse = " + toParse);
        
        List<String> lines = openAndReadTextFile(toParse);
        
        return true;
    }
    
    private static List<String> openAndReadTextFile(File f) throws ParserSchemeException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new FileReader(f));
            while(true){
                line = reader.readLine();
                if(line == null){
                    reader.close();
                    break;
                }
                
                if(!line.startsWith(String.valueOf(CUSTOMER_SEPARATOR)))
                    if(parseLine(line))
                        lines.add(line);
            }
            
        } catch (IOException ex) { 
            
        }
        
        return lines;
    }
    
    private static boolean parseLine(String line) throws ParserSchemeException {
        if(!line.contains(currentScheme.getSeparator()))
            throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_SCHEME);
        return true;
    }
    
    public static ParserScheme getDefaultParserScheme(){
        return ParserScheme.KEY_EQUALS_VALUE;
    }    
    
}

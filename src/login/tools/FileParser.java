/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davidecolombo
 */
public class FileParser {
    
    private static ParserScheme currentScheme = ParserScheme.VALID;
    
// ================================================================================
    // Record validation
    public static boolean validateRecordScheme(String customerRecord) throws ParserSchemeException{
        if(!customerRecord.contains(currentScheme.getPropertySeparator()))
            throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_SCHEME);
        for(String keyValuePair : customerRecord.split(currentScheme.getPropertySeparator()))
            if(!keyValuePair.contains(currentScheme.getKeyValueSeparator()))
                throw new ParserSchemeException(ParserSchemeException.ErrorCode.WRONG_KEY_VALUE_SEPARATOR);
        return true;
    }
    
// ================================================================================
    // Writing a new record
    public static void appendRecord(File f, String record, boolean created){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f, created));
            writer.append(record);
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
    
// ================================================================================
    // Open and read a text file
    public static List<String> openAndReadTextFile(File f){
        List<String> lines = new ArrayList<>();
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
                lines.add(line);
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
        return lines;
    }
    
// ================================================================================
    public static ParserScheme getDefaultParserScheme(){
        return ParserScheme.VALID;
    }    
    
// ================================================================================
    
}

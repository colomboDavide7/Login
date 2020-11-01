/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import login.tools.UserProperty;
import login.users.UserRequest;

/**
 *
 * @author davidecolombo
 */
public class UserRepository {
    
    public static UserRepository createUserRepository(UserRequest r){
        return new UserRepository(r);
    }
    
    private File repository;
    private String ownerUser;
    private List<String> lines;
    
    private UserRepository(UserRequest r){
        ownerUser = r.getUserProperty(UserProperty.USERNAME);
        repository = new File(ownerUser + ".txt");
        addTransaction(r.createTransaction());
        lines = new ArrayList<>();
    }
    
// ====================================================================================
    // Add transaction logic
    public final void addTransaction(String transaction){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.repository, true));
            writer.append(transaction);
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
    
    public boolean matchOwner(String toMatch){
        return ownerUser.equals(toMatch);
    }
    
// ====================================================================================
    // Verify
    public boolean verifyTransactionExistence(String transaction){
        return openAndReadTextFile(transaction);
    }
    
    private boolean openAndReadTextFile(String transaction){
        BufferedReader reader = null;
        String line;
        try {
            reader = new BufferedReader(new FileReader(this.repository));
            while(true){
                line = reader.readLine();
                if(line == null){
                    reader.close();
                    break;
                }
                
                if(line.equals(transaction))
                    return true;
                
            }
            
        } catch (IOException ex) { 
            System.err.println("Error reading file: " + this.repository);
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
    
}

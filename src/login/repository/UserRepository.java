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
import login.SystemProperty;
import login.tools.ParserScheme;
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
    private boolean created = false;
    private List<String> transactions = new ArrayList<>();
    
    private UserRepository(UserRequest r){
        ownerUser = r.getUserProperty(UserProperty.USERNAME);
        repository = new File(ownerUser + ".txt");
        addTransaction(r.createTransaction());
        created = true;
    }
    
// ====================================================================================
    // Add transaction logic
    public final void addTransaction(String transaction){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.repository, created));
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
        
        // Update the file content
        openAndReadTextFile();
    }
    
    public boolean matchOwner(String toMatch){
        return ownerUser.equals(toMatch);
    }
    
// ====================================================================================
    // Verify
    public boolean verifyTransactionExistence(String transaction){
        return transactions.stream().anyMatch((t) -> (t.equals(transaction)));
    }
    
    public boolean verifyExistanceOfOneAndOnlyOneSignupTransaction(){
        int counter = 0;
        for(String line : transactions){
            System.out.println("t: " + line);
            if(matchRequestType(line, UserRequest.RequestType.SIGN_UP))
                counter++;
        }
        
        System.out.println("counter = " + counter);
        return (counter == 1);
    }
    
    private void openAndReadTextFile(){
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
                this.transactions.add(line);
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
    }
    
    private boolean matchRequestType(String line, UserRequest.RequestType t){
        return getTransactionValueByKey(line, SystemProperty.TRANSACTION_TYPE.name())
               .equals(t.name());
    }
    
    private String getTransactionValueByKey(String transaction, String key){
        for(String keyValuePair : transaction.split(ParserScheme.VALID.getPropertySeparator())){
            String[] tokens = keyValuePair.split(ParserScheme.VALID.getKeyValueSeparator());
            if(tokens[0].equals(key))
                return tokens[1];
        }
        return "";
    }
    
// ====================================================================================
    
}

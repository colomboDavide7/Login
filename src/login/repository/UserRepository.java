/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import login.SystemProperty;
import login.tools.FileParser;
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
        addTransaction(r);
        created = true;
    }
    
// ====================================================================================
    // Add transaction logic
    public final void addTransaction(UserRequest r){
        FileParser.appendRecord(repository, r.createTransaction(), created);
        // Update the file content
        this.transactions = FileParser.openAndReadTextFile(this.repository);
    }
    
    public boolean matchOwner(String toMatch){
        return ownerUser.equals(toMatch);
    }
    
// ====================================================================================
    // Verify TOTAL transaction
    public boolean verifyTransactionExistence(String transaction){
        return transactions.stream().anyMatch((t) -> (t.equals(transaction)));
    }
    
// ====================================================================================
    // Verify one and only one Signup transaction
    public boolean verifyExistanceOfOneAndOnlyOneSignupTransaction(){
        int counter = 0;
        for(String line : transactions)
            if(matchRequestType(line, UserRequest.RequestType.SIGN_UP))
                counter++;
        return (counter == 1);
    }
    
    private boolean matchRequestType(String line, UserRequest.RequestType t){
        return getTransactionValueByKey(line, SystemProperty.TRANSACTION_TYPE.name())
               .equals(t.name());
    }
    
    public static String getTransactionValueByKey(String transaction, String key){
        for(String keyValuePair : transaction.split(ParserScheme.VALID.getPropertySeparator())){
            String[] tokens = keyValuePair.split(ParserScheme.VALID.getKeyValueSeparator());
            if(tokens[0].equals(key))
                return tokens[1];
        }
        return "";
    }
    
// ====================================================================================
    // Verify last login transaction
    public boolean verifyLastLoginTransactionDateAndTime(){
        String t = findLatestTransaction();
        String transactionType = getTransactionValueByKey(t, SystemProperty.TRANSACTION_TYPE.name());
        return transactionType.equals(UserRequest.RequestType.LOGIN.name());
    }
    
    private String findLatestTransaction(){
        String latestT = transactions.get(0);
        LocalDateTime latest = getTransactionTime(latestT);
        for(String t : transactions)
            if(getTransactionTime(t).isAfter(latest)){
                latest = getTransactionTime(t);
                latestT = t;
            }
        return latestT;
    }
    
    private LocalDateTime getTransactionTime(String transaction){
        return LocalDateTime.parse(
                getTransactionValueByKey(
                        transaction, SystemProperty.TRANSACTION_DATE_TIME.name()
                )
        );
    }
        
// ====================================================================================
    
}

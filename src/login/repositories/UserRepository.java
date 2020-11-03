/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repositories;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import login.system.RepositoryInfoRequest.AvailableInfo;
import login.system.SystemProperty;
import login.tools.FileParser;
import login.tools.ParserScheme;
import login.system.UserProperty;
import login.system.TransactionRequest;

/**
 *
 * @author davidecolombo
 */
public class UserRepository {
    
    public static String getTransactionValueByKey(String transaction, String key){
        for(String keyValuePair : transaction.split(ParserScheme.VALID.getPropertySeparator())){
            String[] tokens = keyValuePair.split(ParserScheme.VALID.getKeyValueSeparator());
            if(tokens[0].equals(key))
                return tokens[1];
        }
        return "";
    }
    
    public static UserRepository createUserRepository(TransactionRequest r){
        return new UserRepository(r);
    }
    
    private File repository;
    private boolean created = false;
    
    private List<String> transactions = new ArrayList<>();
    private RepositoryInfo info = new RepositoryInfo();
    
    private UserRepository(TransactionRequest r){
        setupRepository(r);
        addTransaction(r);
        created    = true;
    }
    
    private void setupRepository(TransactionRequest r){
        this.info.setInfo(AvailableInfo.OWNER, r.getUserProperty(UserProperty.USERNAME));
        repository = new File(r.getUserProperty(UserProperty.USERNAME) + ".txt");
    }
        
// ====================================================================================
    // Add transaction logic
    public final void addTransaction(TransactionRequest r){
        FileParser.appendRecord(repository, r.createTransaction(), created);
        // Update the file content
        this.transactions = FileParser.openAndReadTextFile(this.repository);
    }
    
    public boolean matchOwner(String toMatch){
        return this.info.matchInfoByValue(AvailableInfo.OWNER, toMatch);
    }
    
    public boolean equals(UserRepository repo){
        return this.info.equals(repo.info);
    }
    
    public boolean matchInfo(AvailableInfo i, UserRepository repo){
        return this.info.matchInfo(info, i);
    }
    
    public boolean matchInfoByValue(AvailableInfo i, String toMatch){
        System.out.println("info to match = " + i + ", value to match = " + toMatch);
        return this.info.matchInfoByValue(i, toMatch);
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
            if(matchRequestType(line, TransactionRequest.TransactionType.SIGN_UP))
                counter++;
        return (counter == 1);
    }
    
    private boolean matchRequestType(String line, TransactionRequest.TransactionType t){
        return getTransactionValueByKey(line, SystemProperty.TRANSACTION_TYPE.name())
               .equals(t.name());
    }
    
// ====================================================================================
    // Verify last login transaction
    public boolean verifyLastLoginTransactionDateAndTime(){
        String t = findLatestTransaction();
        String transactionType = getTransactionValueByKey(t, SystemProperty.TRANSACTION_TYPE.name());
        return transactionType.equals(TransactionRequest.TransactionType.LOGIN.name());
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
    
    public boolean wrongAccess() throws AuthorizationException{
        this.info.decrementNumberOfAvailableLoginAttempts();
        return false;
    }
    
}

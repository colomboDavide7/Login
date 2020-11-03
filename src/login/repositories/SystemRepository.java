/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import login.tools.CredentialException;
import login.tools.FileParser;
import login.system.UserProperty;
import login.system.UserRequest;


/**
 *
 * @author davidecolombo
 */
public class SystemRepository implements ISystemRepository {

    private final String SUBSCRIPTION_FILE = "subscriptions.txt";
    
    private File subscriptions;
    private boolean isAppendMode = false;
    private List<UserRepository> usersRepo;
    private List<String> lines = new ArrayList<>();
    
    public SystemRepository(){
        subscriptions = new File(SUBSCRIPTION_FILE);
        usersRepo     = new ArrayList<>();
    }
            
    @Override
    public void addNewCustomer(UserRequest r) throws CredentialException, TransactionException {
        if(isWrongRequestType(r, UserRequest.RequestType.SIGN_UP))
            throw new TransactionException(TransactionException.ErrorCode.WRONG_REQUEST);
        
        if(isSignedUp(r))
            throw new CredentialException(
                    CredentialException.ErrorCode.USERNAME_ALREADY_USED, UserProperty.USERNAME
            );
        
        signup(r);
    }
   
    private void signup(UserRequest r) {
        FileParser.appendRecord(subscriptions, r.createUserRecord(), isAppendMode);
        this.usersRepo.add(UserRepository.createUserRepository(r));
        this.lines = FileParser.openAndReadTextFile(subscriptions);
        
        if(!isAppendMode)
            isAppendMode = true;
    }
    
    @Override
    public void login(UserRequest r) throws TransactionException {
        if(isWrongRequestType(r, UserRequest.RequestType.LOGIN))
            throw new TransactionException(TransactionException.ErrorCode.WRONG_REQUEST);
        
        if(!isSignedUp(r))
            throw new TransactionException(TransactionException.ErrorCode.NOT_SIGNED_UP);
        
        if(isLogged(r))
            throw new TransactionException(TransactionException.ErrorCode.ALREADY_LOGGED_IN);
        
        if(isWrongPassword(r))
            throw new TransactionException(TransactionException.ErrorCode.WRONG_PASSWORD);
        
        this.usersRepo.stream()
                      .filter((repo) -> (repo.matchOwner(r.getUserProperty(UserProperty.USERNAME))))
                      .forEachOrdered(repo -> repo.addTransaction(r));
    }
    
    @Override
    public void logout(UserRequest r) throws TransactionException {
        if(isWrongRequestType(r, UserRequest.RequestType.LOGOUT))
            throw new TransactionException(TransactionException.ErrorCode.WRONG_REQUEST);
        
        if(!isSignedUp(r))
            throw new TransactionException(TransactionException.ErrorCode.NOT_SIGNED_UP);
        
        if(!isLogged(r))
            throw new TransactionException(TransactionException.ErrorCode.NOT_LOGGED_IN);
        
        this.usersRepo.stream()
                      .filter((repo) -> (repo.matchOwner(r.getUserProperty(UserProperty.USERNAME))))
                      .forEachOrdered(repo -> repo.addTransaction(r));
    }
    
    @Override
    public boolean isSignedUp(UserRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                return repo.verifyExistanceOfOneAndOnlyOneSignupTransaction();
        return false;
    }

    @Override
    public boolean isLogged(UserRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                return repo.verifyLastLoginTransactionDateAndTime();
        return false;
    }
    
    private boolean isWrongRequestType(UserRequest r, UserRequest.RequestType toMatch){
        return !r.matchType(toMatch);
    }
    
    private boolean isWrongPassword(UserRequest r){
        return lines.stream()
                    .noneMatch(t -> (this.verifyEntityIdentity(t, r)));
    }
    
    private boolean verifyEntityIdentity(String transaction, UserRequest r){
        String username = UserRepository.getTransactionValueByKey(
                transaction, UserProperty.USERNAME.name());
        String password = UserRepository.getTransactionValueByKey(
                transaction, UserProperty.PASSWORD.name());
        return r.matchUserProperty(UserProperty.USERNAME, username) &&
               r.matchUserProperty(UserProperty.PASSWORD, password);
    }

}

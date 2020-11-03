/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import login.system.RepositoryInfoRequest;
import login.tools.CredentialException;
import login.tools.FileParser;
import login.system.UserProperty;
import login.system.TransactionRequest;


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
    public void addNewCustomer(TransactionRequest r) throws CredentialException, TransactionException {
        if(isWrongRequestType(r, TransactionRequest.TransactionType.SIGN_UP))
            throw new TransactionException(TransactionException.ErrorCode.WRONG_REQUEST);
        
        if(isSignedUp(r))
            throw new CredentialException(
                    CredentialException.ErrorCode.USERNAME_ALREADY_USED, UserProperty.USERNAME
            );
        
        signup(r);
    }
   
    private void signup(TransactionRequest r) {
        FileParser.appendRecord(subscriptions, r.createUserRecord(), isAppendMode);
        this.usersRepo.add(UserRepository.createUserRepository(r));
        this.lines = FileParser.openAndReadTextFile(subscriptions);
        
        if(!isAppendMode)
            isAppendMode = true;
    }
    
    @Override
    public void login(TransactionRequest r) throws TransactionException, AuthorizationException {
        if(isWrongRequestType(r, TransactionRequest.TransactionType.LOGIN))
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
    public void logout(TransactionRequest r) throws TransactionException {
        if(isWrongRequestType(r, TransactionRequest.TransactionType.LOGOUT))
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
    public boolean isSignedUp(TransactionRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                return repo.verifyExistanceOfOneAndOnlyOneSignupTransaction();
        return false;
    }

    @Override
    public boolean isLogged(TransactionRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                return repo.verifyLastLoginTransactionDateAndTime();
        return false;
    }
    
    private boolean isWrongRequestType(TransactionRequest r, TransactionRequest.TransactionType toMatch){
        return !r.matchType(toMatch);
    }
    
    private boolean isWrongPassword(TransactionRequest r) throws AuthorizationException {
        for(String l : lines)
            if(!verifyEntityIdentity(l, r))
                return true;
        return false;
    }
    
    private boolean verifyEntityIdentity(String transaction, TransactionRequest r) throws AuthorizationException {
        String username = UserRepository.getTransactionValueByKey(
                transaction, UserProperty.USERNAME.name());
        String password = UserRepository.getTransactionValueByKey(
                transaction, UserProperty.PASSWORD.name());
        
        if(isRightUsernameButWrongPassword(username, password, r))
            for(UserRepository repo : this.usersRepo)
                if(repo.matchOwner(r.getUserProperty(UserProperty.USERNAME)))
                    return repo.wrongAccess();
        return r.matchUserProperty(UserProperty.USERNAME, username) &&
               r.matchUserProperty(UserProperty.PASSWORD, password);
    }
    
    private boolean isRightUsernameButWrongPassword(String username, String password, TransactionRequest r){
        return r.matchUserProperty(UserProperty.USERNAME, username) &&
               !r.matchUserProperty(UserProperty.PASSWORD, password);
    }

    @Override
    public boolean matchRepositoryInfo(RepositoryInfoRequest r) {
        for(UserRepository repo : this.usersRepo)
            if(r.matchRepository(repo))
                return r.matchRepositoryInfo(repo);
        return false;
    }

    @Override
    public boolean matchRepositoryInfoByValue(RepositoryInfoRequest r, String toMatch) {
        for(UserRepository repo : this.usersRepo)
            if(r.matchRepository(repo))
                return r.matchRepositoryInfoByValue(repo, toMatch);
        return false;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repositories;

import java.util.HashMap;
import java.util.Map;
import login.system.RepositoryInfoRequest.AvailableInfo;

/**
 *
 * @author davidecolombo
 */
public class RepositoryInfo {
    
    private Map<AvailableInfo, String> info = new HashMap<>();
    
    public RepositoryInfo(){
        setupInfo();
    }
    
    private void setupInfo(){
        System.out.println("SETUP INFO");
        for(AvailableInfo i : AvailableInfo.values()){
            this.info.put(i, i.getValue());
            System.out.println(i + " = " + i.getValue());
        }
        System.out.println("************************************************");
    }
    
    public boolean matchInfo(RepositoryInfo r, AvailableInfo i){
        return this.info.get(i).equals(r.info.get(i));
    }
    
    public boolean matchInfoByValue(AvailableInfo key, String value){
        System.out.println("MATCH INFO BY VALUE");
        System.out.println("key = " + key + ", value = " + this.info.get(key));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return this.info.get(key).equals(value);
    }
    
    public void setInfo(AvailableInfo key, String value){
        this.info.put(key, value);
        
        this.info.keySet().forEach((k) -> {
            System.out.println(k + " = " + info.get(k));
        });
        System.out.println("----------------------------------------------");
    }
    
    public boolean equals(RepositoryInfo i){
        return this.info.get(AvailableInfo.OWNER).equals(i.info.get(AvailableInfo.OWNER));
    }
    
    public void decrementNumberOfAvailableLoginAttempts() throws AuthorizationException {
        int currentAttempts = Integer.parseInt(this.info.get(AvailableInfo.LOGIN_ATTEMPTS));
        if(--currentAttempts <= 0)
            attemptOverflow();
        rejectAttempt(currentAttempts);
    }
    
    private void rejectAttempt(int currentAttempts) throws AuthorizationException {
        this.info.replace(AvailableInfo.LOGIN_ATTEMPTS, Integer.toString(currentAttempts));
        throw new AuthorizationException(currentAttempts);
    }
    
    private void attemptOverflow() throws AuthorizationException {
        this.info.replace(AvailableInfo.LOGIN_ATTEMPTS, AvailableInfo.MAX_ATTEMPTS.getValue());
        throw new AuthorizationException(AuthorizationException.ErrorCode.LOGIN_ATTEMPTS_OVERFLOW);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.system;

import login.repositories.UserRepository;

/**
 *
 * @author davidecolombo
 */
public class RepositoryInfoRequest {
    
    public enum AvailableInfo {
        LOGIN_ATTEMPTS("3"), OWNER("");
        
        private String value;
        
        AvailableInfo(String value){
            this.value = value;
        }
        
        public String getValue(){
            return this.value;
        }
    }
    
    public static RepositoryInfoRequest createInfoRequest(UserRepository repo, AvailableInfo i){
        return new RepositoryInfoRequest(repo, i);
    }
    
    private RepositoryInfoRequest(UserRepository repo, AvailableInfo i){
        this.repo = repo;
        this.i    = i;
    }
    
    private UserRepository repo;
    private AvailableInfo i;
    
    public boolean matchRepository(UserRepository repo){
        return this.repo.equals(repo);
    }
    
    public boolean matchRepositoryInfo(UserRepository repo){
        return this.repo.matchInfo(this.i, repo);
    }
    
    public boolean matchRepositoryInfoByValue(UserRepository repo, String toMatch){
        return repo.matchInfoByValue(this.i, toMatch);
    }
    
}

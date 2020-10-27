/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

/**
 *
 * @author davidecolombo
 */
public interface IUser {
    
    IUser addProperty(User.PropertyName key, String value);
    
    String getProperty(User.PropertyName key) throws PropertyException;
    
// ================================================================================
    public boolean equals(IUser user);
    
    public boolean matchPassword(String pwd);
    
    public boolean matchUsername(String username);
    
// ================================================================================
    public boolean isLogged();
    
    public boolean isLoggedOut();
    
    public IUser login();
    
    public IUser logout();
    
// ================================================================================
    
}

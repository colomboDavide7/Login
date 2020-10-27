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
    
}

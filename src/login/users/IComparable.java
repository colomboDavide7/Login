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
public interface IComparable {
    
    public boolean equals(IUser user);
    
    boolean matchProperty(User.PropertyName key, String value);
    
    boolean isEmptyProperty(User.PropertyName key, String value);
    
    boolean isMandatoryProperty(User.PropertyName key);
    
    boolean isOptionalProperty(User.PropertyName key);
    
}

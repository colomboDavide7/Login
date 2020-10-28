/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public interface IComparable {
    
    public boolean equals(IUser user);
    
    boolean matchProperty(UserProperty key, String value);
    
    boolean isEmptyProperty(UserProperty key, String value);
    
}

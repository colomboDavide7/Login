/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author davidecolombo
 */
public class User {
    
    public enum UserState{
        LOGGED_IN, LOGGED_OUT;
    }
    
    public enum PropertyName {
        USERNAME, PASSWORD,
        BIRTH, CITY,
        AGE, GENDER;
    }
    
// ================================================================================

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private Map<PropertyName, String> properties = new HashMap<>();
    private UserState state = UserState.LOGGED_OUT;
    
    public User(String username, String password){
        this.addProperty(PropertyName.USERNAME, username);
        this.addProperty(PropertyName.PASSWORD, password);
    }
    
    public User(String username, String password, LocalDate birth){
        this.addProperty(PropertyName.USERNAME, username);
        this.addProperty(PropertyName.PASSWORD, password);
        this.addProperty(PropertyName.BIRTH, birth.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
    
    public final User addProperty(PropertyName key, String value){
        this.properties.put(key, value);
        return this;
    }
    
    public final String getProperty(PropertyName key){
        if(this.properties.keySet().stream().anyMatch(k -> (k == key)))
            return this.properties.get(key);
        return "";
    }
    
    public boolean equals(User user){
        return this.getProperty(PropertyName.USERNAME)
                   .equals(user.getProperty(PropertyName.USERNAME));
    }
    
    public boolean matchPassword(String pwd){
        return this.getProperty(PropertyName.PASSWORD).equals(pwd);
    }
    
    public boolean matchUsername(String username){
        return this.getProperty(PropertyName.USERNAME).equals(username);
    }
    
    public boolean isLogged(){
        return this.state == UserState.LOGGED_IN;
    }
    
    public boolean isLoggedOut(){
        return this.state == UserState.LOGGED_OUT;
    }
    
    public User login(){
        this.state = UserState.LOGGED_IN;
        return this;
    }
    
    public User logout(){
        this.state = UserState.LOGGED_OUT;
        return this;
    }
    
    public boolean isMyBirthDay(LocalDate date){
        LocalDate myBirth = LocalDate.parse(this.getProperty(PropertyName.BIRTH), formatter);
        
        return date.getMonthValue() == myBirth.getMonthValue() &&
               date.getYear()       == myBirth.getYear()       &&
               date.getDayOfMonth() == myBirth.getDayOfMonth();
    }
    
    public DateTimeFormatter getFormatter(){
        return this.formatter;
    }
    
}

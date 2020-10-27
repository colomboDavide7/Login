/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author davidecolombo
 */
public class User implements IUser {
    
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
        this.addProperty(PropertyName.BIRTH, birth.format(formatter));
    }
    
// ================================================================================
    @Override
    public final IUser addProperty(PropertyName key, String value){
        this.properties.put(key, value);
        return this;
    }
    
    @Override
    public final String getProperty(PropertyName key) throws PropertyException {
        if(this.properties.keySet().stream().anyMatch(k -> (k == key)))
            return this.properties.get(key);
        throw new PropertyException(PropertyException.ErrorCode.NOT_FOUND);
    }
    
// ================================================================================
    @Override
    public boolean equals(IUser user){
        try {
            return this.getProperty(PropertyName.USERNAME)
                    .equals(user.getProperty(PropertyName.USERNAME));
        } catch (PropertyException ex) {
            return false;
        }
    }
    
    @Override
    public boolean matchPassword(String pwd){
        try {
            return this.getProperty(PropertyName.PASSWORD).equals(pwd);
        } catch (PropertyException ex) {
            return false;
        }
    }
    
    @Override
    public boolean matchUsername(String username){
        try {
            return this.getProperty(PropertyName.USERNAME).equals(username);
        } catch (PropertyException ex) {
            return false;
        }
    }
    
// ================================================================================
    @Override
    public boolean isLogged(){
        return this.state == UserState.LOGGED_IN;
    }
    
    @Override
    public boolean isLoggedOut(){
        return this.state == UserState.LOGGED_OUT;
    }
    
    @Override
    public IUser login(){
        this.state = UserState.LOGGED_IN;
        return this;
    }
    
    @Override
    public IUser logout(){
        this.state = UserState.LOGGED_OUT;
        return this;
    }
    
// ================================================================================
    public boolean isMyBirthDay(LocalDate date){
        try {
            LocalDate myBirth = LocalDate.parse(this.getProperty(PropertyName.BIRTH), formatter);
            return date.getMonthValue() == myBirth.getMonthValue() &&
                   date.getYear()       == myBirth.getYear()       &&
                   date.getDayOfMonth() == myBirth.getDayOfMonth();
        } catch (PropertyException ex) {
            return false;
        }
        
    }
    
    public DateTimeFormatter getFormatter(){
        return this.formatter;
    }
    
// ================================================================================
    
}

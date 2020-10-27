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
        FIRST_NAME, LAST_NAME,
        BIRTH, CITY,
        AGE, GENDER, 
        MAIN_PHONE, SECOND_PHONE,
        E_MAIL;
    }
    
    // Initialization of instance variables
    {
        setupPriority();
        setupProperties();
    }
    
// ================================================================================
    private final String MANDATORY = "M";
    private final String OPTIONAL  = "O";
    private final String EMPTY_PROPERTY = "";
    private UserState state = UserState.LOGGED_OUT;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private Map<PropertyName, String> properties;
    private Map<PropertyName, String> priority;
    
    public User(String username, String password){
        this.addProperty(PropertyName.USERNAME, username);
        this.addProperty(PropertyName.PASSWORD, password);
    }
    
    public User(String username, String password, LocalDate birth){
        this.addProperty(PropertyName.USERNAME, username);
        this.addProperty(PropertyName.PASSWORD, password);
        this.addProperty(PropertyName.BIRTH, birth.format(formatter));
    }
    
    private void setupProperties(){
        this.properties = new HashMap<>();
        for(PropertyName p : PropertyName.values())
            properties.put(p, EMPTY_PROPERTY);
    }
    
    private void setupPriority(){
        this.priority = new HashMap<>();
        for(PropertyName p : PropertyName.values())
            if(p == PropertyName.USERNAME  || p == PropertyName.PASSWORD ||
               p == PropertyName.LAST_NAME || p == PropertyName.FIRST_NAME)
            priority.put(p, MANDATORY);
        else
            priority.put(p, OPTIONAL);
    }
    
// ================================================================================
    @Override
    public final IUser addProperty(PropertyName key, String value){
        this.properties.put(key, value);
        return this;
    }
    
    @Override
    public final String getProperty(PropertyName key) {
        return this.properties.get(key);
    }
    
// ================================================================================
    @Override
    public boolean equals(IUser user){
            return this.getProperty(PropertyName.USERNAME)
                    .equals(user.getProperty(PropertyName.USERNAME));
    }
    
    @Override
    public boolean matchProperty(PropertyName key, String value) {
        return this.getProperty(key).equals(value);
    }
    
    @Override
    public boolean isEmptyProperty(PropertyName key, String value) {
        return this.getProperty(key).equals(this.EMPTY_PROPERTY);
    }
    
    @Override
    public boolean isMandatoryProperty(PropertyName key) {
        return this.priority.get(key).equals(this.MANDATORY);
    }
    
    @Override
    public boolean isOptionalProperty(PropertyName key) {
        return this.priority.get(key).equals(this.OPTIONAL);
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
    @Override
    public boolean isMyBirthDay(LocalDate date){
        LocalDate myBirth = LocalDate.parse(this.getProperty(PropertyName.BIRTH), formatter);
        return date.getMonthValue() == myBirth.getMonthValue() &&
               date.getYear()       == myBirth.getYear()       &&
               date.getDayOfMonth() == myBirth.getDayOfMonth();
    }
    
    @Override
    public DateTimeFormatter getFormatter(){
        return this.formatter;
    }
    
// ================================================================================
    
}

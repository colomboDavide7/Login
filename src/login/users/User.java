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
import login.tools.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class User implements IUser {

    public enum UserState{
        LOGGED_IN, LOGGED_OUT;
    }
    
    public static IUser getBasicUser(Map<UserProperty, String> basicProperties) throws CustomerCreationException{
        if(UserProperty.isMissingMandatory(basicProperties))
            throw new CustomerCreationException(CustomerCreationException.ErrorCode.MISSING_MANDATORY);
        return new User(basicProperties);
    }
    
    private void setupProperties(){
        this.properties = new HashMap<>();
        for(UserProperty p : UserProperty.values())
            properties.put(p, EMPTY_PROPERTY);
    }
    
// ================================================================================
    private final String EMPTY_PROPERTY = "";
    private UserState state = UserState.LOGGED_OUT;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private Map<UserProperty, String> properties;
    
    private User(Map<UserProperty, String> basicProperties){
        setupProperties();
        basicProperties.keySet().forEach(p -> properties.put(p, basicProperties.get(p)));
    }
    
// ================================================================================
    @Override
    public final IUser addProperty(UserProperty key, String value){
        this.properties.put(key, value);
        return this;
    }
    
    @Override
    public final String getProperty(UserProperty key) {
        return this.properties.get(key);
    }
    
// ================================================================================
    @Override
    public boolean equals(IUser user){
            return this.getProperty(UserProperty.USERNAME)
                    .equals(user.getProperty(UserProperty.USERNAME));
    }
    
    @Override
    public boolean matchProperty(UserProperty key, String value) {
        return this.getProperty(key).equals(value);
    }
    
    @Override
    public boolean isEmptyProperty(UserProperty key, String value) {
        return this.getProperty(key).equals(this.EMPTY_PROPERTY);
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
        LocalDate myBirth = LocalDate.parse(this.getProperty(UserProperty.BIRTH), formatter);
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

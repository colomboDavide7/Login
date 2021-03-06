/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import login.tools.ParserScheme;
import login.system.UserProperty;

/**
 *
 * @author davidecolombo
 */
public class User implements IUser {
    
    public static IUser getBasicUser(Map<UserProperty, String> basicProperties) throws CustomerCreationException{
        //printMap(basicProperties);
        List<UserProperty> missing = UserProperty.isMissingMandatory(basicProperties);
        if(!missing.isEmpty())
            throw new CustomerCreationException(missing, CustomerCreationException.ErrorCode.MISSING_MANDATORY);
        return new User(basicProperties);
    }
    
    private static void printMap(Map<UserProperty, String> basicProperties){
        StringBuilder sb = new StringBuilder();
        basicProperties.keySet().forEach((p) -> {
            sb.append(p.toString()).append(" = ").append(basicProperties.get(p)).append("\n");
        });
        
        System.out.println(sb.toString());
    }
    
    private void setupProperties(){
        this.properties = new HashMap<>();
        for(UserProperty p : UserProperty.values())
            properties.put(p, EMPTY_PROPERTY);
    }
    
// ================================================================================
    private final String EMPTY_PROPERTY = "";
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
    @Override
    public String createRecord() {
        StringBuilder sb = new StringBuilder();
        this.properties.keySet().forEach((p) -> {
            sb.append(p.name())
                    .append(ParserScheme.VALID.getKeyValueSeparator())
                    .append(properties.get(p))
                    .append(ParserScheme.VALID.getPropertySeparator());
        });
        return sb.toString();
    }
    
// ================================================================================
    
}

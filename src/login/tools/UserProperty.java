/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author davidecolombo
 */
public enum UserProperty {
    
    USERNAME, PASSWORD, FIRST_NAME,    LAST_NAME,  BIRTH, CITY,
         AGE,   GENDER, MAIN_PHONE, SECOND_PHONE, E_MAIL;
    
    private static final String MANDATORY = "M";
    private static final String OPTIONAL  = "O";
    private static Map<UserProperty, String> priority;
    
    static{
        setupPriority();
    }
    
    private static void setupPriority(){
        priority = new HashMap<>();
        for(UserProperty p : UserProperty.values())
            if(p == UserProperty.USERNAME  || p == UserProperty.PASSWORD ||
               p == UserProperty.LAST_NAME || p == UserProperty.FIRST_NAME)
            priority.put(p, MANDATORY);
        else
            priority.put(p, OPTIONAL);
    }
    
    public static boolean isMandatory(UserProperty p){
        for(UserProperty prop : priority.keySet())
            if(prop == p)
                return priority.get(prop).equals(MANDATORY);
        return false;
    }
    
    public static boolean isOptional(UserProperty p){
        for(UserProperty prop : priority.keySet())
            if(prop == p)
                return priority.get(prop).equals(OPTIONAL);
        return false;
    }
    
    public static boolean isMissingMandatory(Map<UserProperty, String> basicProp){
        for(UserProperty p : priority.keySet())
            if(priority.get(p).equals(MANDATORY))
                if(!mandatoryPropertyFound(p, basicProp))
                    return true;
        return false;
    }
    
    private static boolean mandatoryPropertyFound(
            UserProperty mandatoryProp, Map<UserProperty, String> basicProp
    ){
        return basicProp.keySet().stream().anyMatch(p -> (p == mandatoryProp));
    }
    
}

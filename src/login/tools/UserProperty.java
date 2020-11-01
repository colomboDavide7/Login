/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    public static List<UserProperty> isMissingMandatory(Map<UserProperty, String> basicProp){
        List<UserProperty> missing = new ArrayList<>();
        for(UserProperty p : priority.keySet())
            if(priority.get(p).equals(MANDATORY))
                if(!mandatoryPropertyFound(p, basicProp) || 
                   mandatoryFoundButEmpty(p, basicProp) || 
                   mandatoryFoundButNotCompiled(p, basicProp))
                    missing.add(p);
        return missing;
    }
    
    private static boolean mandatoryPropertyFound(
            UserProperty mandatoryProp, Map<UserProperty, String> basicProp
    ){
        return basicProp.keySet().stream().anyMatch(p -> (p == mandatoryProp));
    }
    
    private static boolean mandatoryFoundButEmpty(
            UserProperty mandatoryProp, Map<UserProperty, String> basicProp
    ){
        if(basicProp.containsKey(mandatoryProp))
            if(basicProp.get(mandatoryProp).isEmpty())
                return true;
        return false;
    }
    
    private static boolean mandatoryFoundButNotCompiled(
            UserProperty p, Map<UserProperty, String> basicProp
    ){
        if(basicProp.containsKey(p))
            if(basicProp.get(p).equals(p.name()))
                return true;
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

/**
 *
 * @author davidecolombo
 */
public enum ParserScheme {
    
    VALID("=", ";");
    
    private String keyValueSeparator;
    private String propertySeparator;
    
    ParserScheme(String keyValueSeparator, String propertySeparator){
        this.keyValueSeparator = keyValueSeparator;
        this.propertySeparator = propertySeparator;
    }
    
    public String getKeyValueSeparator(){
        return this.keyValueSeparator;
    }
    
    public String getPropertySeparator(){
        return this.propertySeparator;
    }
    
}

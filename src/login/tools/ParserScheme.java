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
    
    KEY_EQUALS_VALUE("=");
    
    private String separator;
    
    ParserScheme(String separator){
        this.separator = separator;
    }
    
    public String getSeparator(){
        return this.separator;
    }
    
}

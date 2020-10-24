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
public class UserValidator {
    
    private static final String VALID_SYMBOLS = "!@#$%&*()_+=|<>?{}\\\\[\\\\]~-";
    
    public static void isValidUsername(String username) throws LoginException {
        if(username.isEmpty() || !Character.isLetter(username.charAt(0)) || username.contains(" "))
            throw new LoginException();
    }
    
    public static void isValidPassword(String pwd) throws LoginException {
        if(!containsAtLeastOneNumber(pwd) || !containsAtLeastOneSymbol(pwd) ||
           !containsAtLeastOneLetter(pwd) || !containsAtLeastOneUpperCase(pwd))
            throw new LoginException();
    }
    
    public static boolean equalsUsername(String username){
        return username.equals(username);
    }
    
// ================================================================================
    private static boolean containsAtLeastOneLetter(String text){
        for(int ch = 0; ch < text.length(); ch++)
            if(Character.isLetter(text.charAt(ch)))
                return true;
        return false;
    }
    
    private static boolean containsAtLeastOneUpperCase(String text){
        for(int ch = 0; ch < text.length(); ch++)
            if(Character.isUpperCase(text.charAt(ch)))
                return true;
        return false;
    }
    
    private static boolean containsAtLeastOneNumber(String text){
        for(int ch = 0; ch < text.length(); ch++)
            if(Character.isDigit(text.charAt(ch)))
                return true;
        return false;
    }
    
    private static boolean containsAtLeastOneSymbol(String text){
        for(int ch = 0; ch < text.length(); ch++)
            if(findSymbol(text.charAt(ch)))
                return true;
        return false;
    }
    
    private static boolean findSymbol(char c){
        for(int i = 0; i < VALID_SYMBOLS.length(); i++)
            if(c == VALID_SYMBOLS.charAt(i))
                return true;
        return false;
    }
        
// ================================================================================
    
}

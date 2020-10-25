/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.util.Iterator;
import login.tools.LoginException.ErrorCode;
import login.users.LoginRequest;
import login.users.User;

/**
 *
 * @author davidecolombo
 */
public class UserValidator {
    
    private static final String VALID_SYMBOLS = "!@#$%&*()_+=|<>?{}\\\\[\\\\]~-";
        
    public static boolean isValidUsername(String username) throws LoginException {
        if(username.isEmpty() || !Character.isLetter(username.charAt(0)) || 
           username.contains(" "))
            throw new LoginException(ErrorCode.INVALID_USERNAME);
        return true;
    }
    
    public static boolean isValidPassword(String pwd) throws LoginException {
        if(!testString(input -> Character.isDigit(input), pwd)     || 
           !testString(input -> Character.isUpperCase(input), pwd) ||
           !testString(input -> Character.isLetter(input), pwd)    ||
           !testString(input -> findSymbol(input), pwd))
            throw new LoginException(ErrorCode.INVALID_PASSWORD); 
        return true;
    }
    
    public static boolean isSignedUp(Iterator<User> userIter, User specificUser) throws LoginException {
        for (; userIter.hasNext(); )
            if(userIter.next().equals(specificUser))
                throw new LoginException(ErrorCode.USERNAME_ALREADY_USED);
        throw new LoginException(ErrorCode.NOT_SIGNED_UP);
    }
    
// ================================================================================
    private static boolean testString(IFunctionalString op, String input){
        for(int ch = 0; ch < input.length(); ch++)
            if(op.operate(input.charAt(ch)))
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
    // Defining a functional Interface in order to provide a context for Lambda Expressions
    private interface IFunctionalString{
        boolean operate(char ch);
    }
    
// ================================================================================
    
}

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
public class LoginException extends Exception {
    
    public enum ErrorCode{
        USERNAME_ALREADY_USED, INVALID_PASSWORD, INVALID_USERNAME, NOT_SIGNED_UP, 
        WRONG_PASSWORD;
    }
    
    private ErrorCode errorCode;
    
    public LoginException(ErrorCode code){
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
}

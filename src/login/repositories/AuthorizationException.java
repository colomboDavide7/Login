/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repositories;

/**
 *
 * @author davidecolombo
 */
public class AuthorizationException extends Exception {

    public enum ErrorCode{
        LOGIN_ATTEMPTS_OVERFLOW;
    }
    
    private ErrorCode errCode;
    
    public AuthorizationException(ErrorCode code) {
        this.errCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errCode;
    }
    
}

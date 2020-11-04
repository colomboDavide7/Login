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
        WRONG_LOGIN_ATTEMPTS,
        LOGIN_ATTEMPTS_OVERFLOW;
    }
    
    private ErrorCode errCode;
    private int attemptsLeft;
    
    public AuthorizationException(ErrorCode code) {
        this.errCode = code;
    }
    
    public AuthorizationException(int value) {
        this.errCode = ErrorCode.WRONG_LOGIN_ATTEMPTS;
        this.attemptsLeft = value;
    }
    
    public ErrorCode getErrorCode(){
        return this.errCode;
    }
    
    public String getErrorMessage(){
        switch(errCode){
            case WRONG_LOGIN_ATTEMPTS:
                return String.format("Wrong attempts. %d attempts left", this.attemptsLeft);
        }
        
        return "";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.repository;

/**
 *
 * @author davidecolombo
 */
public class TransactionException extends Exception {

    public enum ErrorCode{
        NOT_SIGNED_UP, WRONG_PASSWORD, 
        NOT_LOGGED_IN, ALREADY_LOGGED_IN,
        WRONG_REQUEST;
    }
    
// ================================================================================
    private ErrorCode errorCode;
    
    public TransactionException(ErrorCode code){
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
    public String getErrorMessage(){
        switch(this.errorCode){
            case NOT_SIGNED_UP:
                return String.format("You are not signed up");
            case NOT_LOGGED_IN:
                return String.format("You are not logged in");
            case WRONG_PASSWORD:
                return String.format("Wrong password");
            default:
                return String.format("ErrorCode \"%s\" not found", this.errorCode.name());
        }
    }
    
}

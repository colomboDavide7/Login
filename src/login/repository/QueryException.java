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
public class QueryException extends Exception {

    public enum ErrorCode{
        NOT_SIGNED_UP, WRONG_PASSWORD;
    }
    
    
    private ErrorCode errorCode;
    
    public QueryException(ErrorCode code){
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
}

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
public class ParserSchemeException extends Exception {

    public enum ErrorCode{
        WRONG_SCHEME;
    }
    
    private ErrorCode errorCode;
    
    public ParserSchemeException(ErrorCode code) {
        this.errorCode = code;
    }
    
    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
    
}

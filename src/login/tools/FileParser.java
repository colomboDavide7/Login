/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.tools;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author davidecolombo
 */
public class FileParser {
    
    public static boolean parseFile(File toParse) throws FileNotFoundException {
        if(!toParse.exists())
            throw new FileNotFoundException("toParse = " + toParse);
        return true;
    }
    
}

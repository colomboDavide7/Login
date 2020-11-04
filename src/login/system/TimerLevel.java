/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.system;

/**
 *
 * @author davidecolombo
 */
public enum TimerLevel {
    
    NO_TIMER, 
    TIMER_LEVEL_ONE, 
    TIMER_LEVEL_TWO, 
    TIMER_LEVEL_THREE, 
    BLOCK, 
    TEST;
    
    public static TimerLevel getNextLevel(TimerLevel l){
        switch(l){
            case NO_TIMER:
                return TIMER_LEVEL_ONE;
            case TIMER_LEVEL_ONE:
                return TIMER_LEVEL_TWO;
            case TIMER_LEVEL_TWO:
                return TIMER_LEVEL_THREE;
            case TIMER_LEVEL_THREE:
                return BLOCK;
            default:
                return TEST;
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author WARLords
 */
public class Shooter {

    private Victor vic1, vic2;
    
    private Solenoid solIn, solOut;
    
    public Shooter(Victor vic1, Victor vic2, Solenoid solIn, Solenoid solOut){
        this.solIn = solIn;
        this.solOut = solOut;
        this.vic1 = vic1;
        this.vic2 = vic2;
        
    } 

}

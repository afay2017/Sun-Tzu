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

    public DoubleVictor vic;

    private Solenoid solIn, solOut;

    public boolean isOn;

    public Shooter(Solenoid solIn, Solenoid solOut, DoubleVictor vic){
        this.solIn = solIn;
        this.solOut = solOut;
        this.vic = vic;

    }
//    public boolean isOn(){
//        return isOn;
//    }
    public void On(){
        isOn = true;
    }
    public void Start(){
        vic.set(.2);
        isOn = true;
    }
    public void Shoot(){
        solIn.set(false);
        solOut.set(true);


    }
    public void ResetSol(){
        solIn.set(true);
        solOut.set(false);
    }
    public void set(double value){
        vic.set(value);

    }
    public void Disable(){
        vic.disable();
        isOn = false;
       // solIn.
    }


}

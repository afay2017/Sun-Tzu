/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author WARLords
 */
public class DoubleVictor implements SpeedController {

    SpeedController vic1;
    SpeedController vic2;

    public DoubleVictor(SpeedController vic1, SpeedController vic2) {
        this.vic1 = vic1;
        this.vic2 = vic2;

    }

    public void set(double newVal) {
        vic1.set(newVal);
        vic2.set(newVal);
    }

    public double get() {
        return (vic1.get() + vic2.get())/2;

    }

    public void set(double speed, byte syncGroup) {

    }

    public void disable() {
        vic1.disable();
        vic2.disable();
    }

    public void pidWrite(double output) {

    }
}

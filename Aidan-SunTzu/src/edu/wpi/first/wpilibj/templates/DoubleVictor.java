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
    boolean inverted;
    int inverter1 = 1;
    int inverter2 = 1;

    public DoubleVictor(SpeedController vic1, SpeedController vic2) {
        this.vic1 = vic1;
        this.vic2 = vic2;

    }

    public DoubleVictor(SpeedController vic1, SpeedController vic2, boolean inverter) {
        this.vic1 = vic1;
        this.vic2 = vic2;
        if (inverter) {
            this.inverter1 = 1;
        } else if (!inverter) {
            this.inverter1 = -1;
        }

    }

    public DoubleVictor(SpeedController vic1, SpeedController vic2, boolean inverter1, boolean inverter2) {
        this.vic1 = vic1;
        this.vic2 = vic2;

        if (inverter1) {
            this.inverter1 = 1;
        } else if (!inverter1) {
            this.inverter1 = 1;
        }

        if (inverter2) {
            this.inverter2 = -1;
        } else if (!inverter2) {
            this.inverter2 = -1;
        }

    }

    public void set(double newVal) {
        vic1.set(newVal * inverter1);
        vic2.set(newVal * inverter2);
    }

    public void setvic1(double newVal) {
        vic1.set(newVal * inverter1);
        vic1.set(newVal);
    }

    public void setvic2(double newVal) {
        vic2.set(newVal * inverter2);
        vic2.set(newVal);
    }

    public double get() {
        return (vic1.get() + vic2.get()) / 2;

    }

    public double getvic1() {
        return (vic1.get());

    }

    public double getvic2() {
        return (vic2.get());

    }

    public void set(double speed, byte syncGroup) {

    }

    public void disable() {
        vic1.disable();
        vic2.disable();
    }

    public void invert() {
        inverter1 = -1;
        inverter2 = -1;

    }

    public void uninvert() {
        inverter1 = 1;
        inverter2 = 1;

    }

    public void pidWrite(double output) {

    }
}

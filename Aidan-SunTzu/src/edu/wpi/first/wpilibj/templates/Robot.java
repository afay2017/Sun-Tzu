/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Joystick controller;
    Joystick joy;
    Shooter shooter;
    DoubleVictor shootMotors;
    DoubleVictor LDrive;
    DoubleVictor RDrive;
    DriveTrain drive;
    double move;
    double rot;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        controller = new Joystick(1);
        joy = new Joystick(2);
        Controllers.set(controller, joy);

        shootMotors = new DoubleVictor(new Victor(5), new Victor(6));
        LDrive = new DoubleVictor(new Victor(1), new Victor(2));
        LDrive = new DoubleVictor(new Victor(3), new Victor(4));
        shooter = new Shooter(new Solenoid(3), new Solenoid(4), shootMotors);
        drive = new DriveTrain(new Victor(1), new Victor(2), new Victor(3), new Victor(4), new Solenoid(1), new Solenoid(2), new Gyro(2));
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        if (Controllers.getG13Button(23) && (!shooter.isOn)) {
            shooter.Start();
        }
        if (Controllers.getG13Button(23) && (shooter.isOn)) {
            shooter.Disable();
        }

        if (Controllers.getG13Button(24)) {
            shooter.set(Controllers.JOYSTICK_AXIS_Y);
            System.out.println(Controllers.JOYSTICK_AXIS_Y);

        }
        if (Controllers.getG13Button(4)) {
            move = .1;
        } else if (Controllers.getG13Button(11)) {
            move = -.1;
        } else {
            move = 0;

        }
        if (Controllers.getG13Button(10)) {
            rot = -.1;
        } else if (Controllers.getG13Button(12)) {
            rot = .1;
        } else {
            rot = 0;
        }
        if (Controllers.getG13Button(15)) {
            drive.highGear();
        } else {
            drive.lowGear();
        }

    }

}

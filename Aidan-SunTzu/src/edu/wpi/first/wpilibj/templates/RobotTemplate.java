/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    Joystick controller;
    Joystick joy;
    Shooter shooter;
    DoubleVictor shootMotors;
    RobotDrive drive;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        controller = new Joystick(1);
        joy = new Joystick(2);
        Controllers.set(controller, joy);
        shootMotors = new DoubleVictor(new  Victor(5),new Victor(6));
        shooter = new Shooter(new Solenoid(3),new Solenoid(4),shootMotors);
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
       if(Controllers.getJoystickButton(2)&&(shooter.isOn)){
           shooter.Start();
       }
       if(Controllers.getJoystickButton(2)&&(!shooter.isOn)){
           shooter.Disable();
       }
       if(Controllers.getJoystickButton(1))
           shooter.set(Controllers.JOYSTICK_AXIS_THROTTLE);


    }

}

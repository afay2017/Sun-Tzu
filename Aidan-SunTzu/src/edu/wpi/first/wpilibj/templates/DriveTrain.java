package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive train class extends {@see RobotDrive}
 * and includes gear shifting.
 *
 * @author Marty Kausas
 */
public class DriveTrain extends RobotDrive {

    // Solenoids for gear shifting
    private Solenoid
            gear1,
            gear2;

    public Gyro gyro;

    private final double NORMAL_SPEED_RATING = 0.7,
        FAST_SPEED_RATING = 1.0,
        SLOW_SPEED_RATING = 0.6;

    private double
            driveSpeed = NORMAL_SPEED_RATING,
            lastLinearSpeed = 0.0,
            lastRotationalSpeed = 0.0,
            tapRotationSpeed = 0.5,
            rotationCorrectionConstant = 0.03; // Kp

    public boolean driveStraightMode = false;

    /**
     * Default Constructor
     *
     * @param frontLeft
     * @param backLeft
     * @param rearRight
     * @param rearLeft
     * @param gear1
     * @param gear2
     */
    public DriveTrain(SpeedController frontLeft, SpeedController rearLeft,
            SpeedController frontRight, SpeedController rearRight,
            Solenoid gear1, Solenoid gear2, Gyro gyro) {
        super(frontLeft, rearLeft, frontRight, rearRight);
        super.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        super.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        super.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        super.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        super.setSafetyEnabled(false);

        this.gear1  = gear1;
        this.gear2  = gear2;
        this.gyro   = gyro;
        this.gyro.setSensitivity(0.007);
    }

        public DriveTrain(SpeedController frontLeft, SpeedController rearLeft,
            SpeedController frontRight, SpeedController rearRight,
            Solenoid gear1, Solenoid gear2) {
        super(frontLeft, rearLeft, frontRight, rearRight);
        super.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        super.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        super.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        super.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        super.setSafetyEnabled(false);

        this.gear1  = gear1;
        this.gear2  = gear2;
    }

    /**
     * Custom drive method similar to arcadeDrive
     * with added acceleration code.
     *
     * @param linearSpeed
     * @param rotationalSpeed
     */
    public void warlordDrive(double linearSpeed, double rotationalSpeed) {

        if (Math.abs(linearSpeed) < 0.1) {
            linearSpeed = 0;
        }

        if (Math.abs(rotationalSpeed) < 0.1) {
            rotationalSpeed = 0;
        }

        double normalizeLinearSpeed = normalizeLinearSpeed(linearSpeed);
        double normalizedRotationalSpeed = normalizeRotationalSpeed(rotationalSpeed);

        if (Math.abs(normalizedRotationalSpeed) < 0.1) {
//            System.out.println("Normalized rotational speed is below 0.1, it is " + normalizedRotationalSpeed + "; driving straight is " + driveStraightMode);
            if (!driveStraightMode) {
//                System.out.println("Norm rotational speed is below 0.1 and not in drive straight mode, resetting gyro; drive straight mode enabled");
                gyro.reset();
                driveStraightMode = true;
            }
        } else {
//            System.out.println("Norm rotational speed is above 0.1, it is " + normalizedRotationalSpeed + "; drive straight mode disabled; resetting gyro");
            driveStraightMode = false;
            gyro.reset();
        }

        if (Math.abs(normalizeLinearSpeed) < 0.1 && Math.abs(normalizedRotationalSpeed) < 0.1) {
            gyro.reset();
        }

        if (driveStraightMode) {
//            System.out.println("In warlordDrive, gyro's angle is " + gyro.getAngle());
            super.arcadeDrive(normalizeLinearSpeed(linearSpeed) * driveSpeed, -gyro.getAngle() * rotationCorrectionConstant);
        } else {
            super.arcadeDrive(normalizeLinearSpeed(linearSpeed) * driveSpeed, normalizeRotationalSpeed(rotationalSpeed) * driveSpeed);
        }
    }

    /**
     * Slowly increases the speed of the drive train
     *
     * @param value
     * @return
     */
    private double normalizeLinearSpeed(double value) {
        double slowIncrementValueX = 0.02;

        //value is negative if you are moving forward...so everything below feels backwards!!!

        double delta = value - lastLinearSpeed;  //if delta is negative, then acceleration is positive

        if (delta <= 0 ) {
            //if acceleration is positive (which means delta is negative) can go fast
            lastLinearSpeed = value;
            return value;
        } else {
            if (lastLinearSpeed >= 0 && lastLinearSpeed < 0.25)
                lastLinearSpeed = Math.min(value, 0.25);
            else
                //if acceleration is negative, then decrease velocity slowly
                lastLinearSpeed += Math.min(delta, slowIncrementValueX);
            return lastLinearSpeed;
        }

    }


    /**
     * Ramps up to the inputed controller value by the maxIncrementValueX slowly
     *
     * @param value
     * @return
     */
    private double normalizeRotationalSpeed(double value) {
        double maxIncrementValueY = 0.05;

//        System.out.println("Value is " + value);

        if (lastRotationalSpeed < 0.3 && lastRotationalSpeed > -0.3) {
            lastRotationalSpeed = (value > 0) ? Math.min(value, 0.3) : Math.max(value, -0.3);
//            System.out.println("In normalize rotational; first if lastRotational is " + lastRotationalSpeed);
            return lastRotationalSpeed;
        }

        if ((value - lastRotationalSpeed) > maxIncrementValueY) {
            lastRotationalSpeed += maxIncrementValueY;
//            System.out.println("In normalize rotational; second if lastRotational is " + lastRotationalSpeed);
            return lastRotationalSpeed;
        } else if ((lastRotationalSpeed - value) > maxIncrementValueY) {
            lastRotationalSpeed -= maxIncrementValueY;
//            System.out.println("In normalize rotational; third if lastRotational is " + lastRotationalSpeed);
            return lastRotationalSpeed;
        }
//        System.out.println("We shouldn't be here");
        return lastRotationalSpeed;
    }

    /**
     * Switches the robot into high gear
     */
    public void highGear() {
        gear1.set(false);
        gear2.set(true);
    }

    /**
     * Switches the robot into low gear
     */
    public void lowGear() {
        gear1.set(true);
        gear2.set(false);
    }

    /**
     * Quick rotation to the right if speed is < 20%
     */
    public void tapTurnRight() {
        if (Math.abs(lastLinearSpeed) < 0.2)
            warlordDrive(0.00, tapRotationSpeed + 0.27);
    }

    /**
     * Quick rotation to the left if speed is < 20%
     */
    public void tapTurnLeft() {
        if (Math.abs(lastLinearSpeed) < 0.2)
            warlordDrive(0.00, -tapRotationSpeed);

    }

    /**
     * Switch into high speed mode
     */
    public void setHighSpeed() { driveSpeed = FAST_SPEED_RATING; }

    /**
     * Switch into low speed mode
     */
    public void setLowSpeed() { driveSpeed = SLOW_SPEED_RATING; }

    /**
     * Switch to normal speed mode
     */
    public void setNormalSpeed() { driveSpeed = NORMAL_SPEED_RATING; }

    public double getGyroAngle() {
        return gyro.getAngle();
    }
}

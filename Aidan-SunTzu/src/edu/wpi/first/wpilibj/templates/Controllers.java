package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.client.NetworkTableClient;

/**
 * Assists in obtaining values accurately from controls. NOTE: controller
 * pointers must be configured before use using
 * {@code Controllers.set(primary, secondary)}.
 *
 * @author Bryce Matsumori
 * @see edu.wpi.first.wpilibj.Joystick
 */
public final class Controllers {

    // stores controller objects
    private static Joystick primary = null, secondary = null;
    private static NetworkTable G13Table = NetworkTable.getTable("G13");

    /**
     * Sets the primary and secondary controllers for use in
     * {@code Controllers}.
     *
     * @param primary the primary controller, such as an Xbox 360 controller
     * @param secondary the secondary controller, such as a joystick
     *
     * @throws IllegalArgumentException if the one of the controllers is null
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static void set(Joystick primary, Joystick secondary) {
        if (primary != null && secondary != null) {
            // save the controllers
            Controllers.primary = primary;
            Controllers.secondary = secondary;
        } else {
            throw new IllegalArgumentException(
                    "one of the specified controllers is null (primary:"
                    + (primary == null) + ", secondary:" + (secondary == null) + ")"
            );
        }
    }

    /**
     * The default input threshold value.
     */
    public static final float DEFAULT_INPUT_THRESH = 0.3f;

    // <editor-fold defaultstate="collapsed" desc="Primary Controller Input">
    // <editor-fold defaultstate="collapsed" desc="Xbox Controller Axes">
    /**
     * The primary Xbox 360 controller left X-axis. See
     * {@link http://www.chiefdelphi.com/forums/showpost.php?p=1003245&postcount=8}.
     */
    public static final int XBOX_AXIS_LX = 1;
    /**
     * The primary Xbox 360 controller left Y-axis.
     */
    public static final int XBOX_AXIS_LY = 2;
    /**
     * The primary Xbox 360 controller right X-axis.
     */
    public static final int XBOX_AXIS_RX = 4;
    /**
     * The primary Xbox 360 controller right Y-axis.
     */
    public static final int XBOX_AXIS_RY = 5;
    /**
     * The primary Xbox 360 controller trigger values. Left yields a positive
     * value, right yields a negative value. A value of 0 means either both or
     * none are triggered.
     */
    public static final int XBOX_AXIS_TRIGGER = 3;
    /**
     * The primary Xbox 360 controller directional pad. Buggy, not recommended.
     */
    public static final int XBOX_AXIS_DPAD = 6;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Xbox Controller Buttons">
    /**
     * The primary Xbox 360 controller A (south) button. See
     * {@link http://www.chiefdelphi.com/forums/showpost.php?p=1003245&postcount=8}.
     */
    public static final int XBOX_BTN_A = 1;
    /**
     * The primary Xbox 360 controller B (east) button.
     */
    public static final int XBOX_BTN_B = 2;
    /**
     * The primary Xbox 360 controller X (west) button.
     */
    public static final int XBOX_BTN_X = 3;
    /**
     * The primary Xbox 360 controller Y (north) button.
     */
    public static final int XBOX_BTN_Y = 4;
    /**
     * The primary Xbox 360 controller left bumper.
     */
    public static final int XBOX_BTN_LBUMP = 5;
    /**
     * The primary Xbox 360 controller right bumper.
     */
    public static final int XBOX_BTN_RBUMP = 6;
    /**
     * The primary Xbox 360 controller back button.
     */
    public static final int XBOX_BTN_BACK = 7;
    /**
     * The primary Xbox 360 controller start button.
     */
    public static final int XBOX_BTN_START = 8;
    /**
     * The primary Xbox 360 controller left joystick (pressed).
     */
    public static final int XBOX_BTN_LSTICK = 9;
    /**
     * The primary Xbox 360 controller right joystick (pressed).
     */
    public static final int XBOX_BTN_RSTICK = 10;

    // </editor-fold>
    /**
     * Gets the current value of the specified axis on the primary controller,
     * limited by the default input threshold to prevent idle movement.
     *
     * @param axis the axis
     * @return the axis value (-1...1) or the default input threshold value
     *
     * @throws ControllerNullException if the primary controller has not been
     * set
     * @throws IllegalArgumentException if the axis is not a valid axis value
     *
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static float getAxis(int axis) {
        return getAxis(axis, DEFAULT_INPUT_THRESH);
    }

    /**
     * Gets the current value of the specified axis on the primary controller,
     * limited by an input threshold to prevent idle movement.
     *
     * @param axis the axis
     * @param inputThreshold the minimum input value allowed to indicate
     * movement
     * @return the axis value (-1...1) or the input threshold value
     *
     * @throws ControllerNullException if the primary controller has not been
     * set
     * @throws IllegalArgumentException if the axis is not a valid axis value
     *
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static float getAxis(int axis, float inputThreshold) {
        if (primary == null) // throw new ControllerNullException("Primary controller is null");
        {
            if (axis < 1 || axis > 6) {
                throw new IllegalArgumentException("Xbox axis (" + axis + ") is invalid.");
            }
        }

        float val = (float) primary.getRawAxis(axis);

        // prevent idle movement
        if (inputThreshold > 0 && Math.abs(val) < inputThreshold) {
            return 0;
        }

        return val;
    }

    /**
     * Gets the current value of the specified button on the primary controller.
     *
     * @param button the button
     * @return the button value as a boolean
     *
     * @throws ControllerNullException if the primary controller has not been
     * set
     * @throws IllegalArgumentException if the button is not a valid button
     * value
     *
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static boolean getButton(int button) {
        if (primary == null) //            throw new ControllerNullException("Primary controller is null");
        {
            if (button < 1 || button > 12) {
                throw new IllegalArgumentException("Xbox button number (" + button + ") is invalid.");
            }
        }

        return primary.getRawButton(button);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Secondary Controller Input">
    // <editor-fold defaultstate="collapsed" desc="Joystick Axes">
    /**
     * The secondary joystick X (horizontal) axis.
     */
    public static final int JOYSTICK_AXIS_X = 1;
    /**
     * The secondary joystick Y (vertical) axis.
     */
    public static final int JOYSTICK_AXIS_Y = 2;
    /**
     * The secondary joystick Z (twist) axis.
     */
    public static final int JOYSTICK_AXIS_Z = 3;
    /**
     * The secondary joystick throttle axis.
     */
    public static final int JOYSTICK_AXIS_THROTTLE = 4;

    // </editor-fold>
    /**
     * Gets the current value of the specified axis on the secondary controller,
     * limited by the default input threshold to prevent idle movement.
     *
     * @param axis the axis
     * @return the axis value (-1...1) or the default input threshold value
     *
     * @throws ControllerNullException if the secondary controller has not been
     * set
     * @throws IllegalArgumentException if the axis is not a valid axis value
     *
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static float getJoystickAxis(int axis) {
        return getJoystickAxis(axis, DEFAULT_INPUT_THRESH);
    }

    /**
     * Gets the current value of the specified axis on the secondary controller,
     * limited by an input threshold to prevent idle movement.
     *
     * @param axis the axis
     * @param inputThreshold the minimum input value allowed to indicate
     * movement
     * @return the axis value (-1...1) or the input threshold value
     *
     * @throws ControllerNullException if the secondary controller has not been
     * set
     * @throws IllegalArgumentException if the axis is not a valid axis value
     *
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static float getJoystickAxis(int axis, double inputThreshold) {
        if (secondary == null) // throw new ControllerNullException("Secondary controller is null");
        {
            if (axis < 1 || axis == 3 || axis > 5) {
                throw new IllegalArgumentException("Joystick axis (" + axis + ") is invalid.");
            }
        }

        float val = (float) secondary.getRawAxis(axis);

        // prevent idle movement
        if (inputThreshold > 0 && Math.abs(val) < inputThreshold) {
            return 0;
        }

        return val;
    }

    /**
     * Gets the current value of the specified button on the secondary
     * controller.
     *
     * @param button a button 1-12
     * @return the button value as a boolean
     *
     * @throws ControllerNullException if the secondary controller has not been
     * set
     * @throws IllegalArgumentException if the button is not 1-12
     *
     * @see edu.wpi.first.wpilibj.Joystick
     */
    public static boolean getJoystickButton(int button) {
        if (secondary == null) //            throw new ControllerNullException("Secondary controller is null");
        {
            if (button < 1 || button > 12) {
                throw new IllegalArgumentException("Joystick button number (" + button + ") is invalid.");
            }
        }

        return secondary.getRawButton(button);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Get All Data">
    /**
     * Returns a {@code ControllerDataDump} containing all input data from both
     * primary and secondary controllers.
     *
     * @return the data
     */
    public static ControllerDataDump getAllData() {
        ControllerDataDump dump = new ControllerDataDump();

        dump.xboxAxisLX = (float) primary.getRawAxis(XBOX_AXIS_LX);
        dump.xboxAxisLY = (float) primary.getRawAxis(XBOX_AXIS_LY);
        dump.xboxAxisRX = (float) primary.getRawAxis(XBOX_AXIS_RX);
        dump.xboxAxisRY = (float) primary.getRawAxis(XBOX_AXIS_RY);
        dump.xboxAxisTrigger = (float) primary.getRawAxis(XBOX_AXIS_TRIGGER);
        dump.xboxAxisDPad = (float) primary.getRawAxis(XBOX_AXIS_DPAD);

        dump.xboxBtnA = primary.getRawButton(XBOX_BTN_A);
        dump.xboxBtnB = primary.getRawButton(XBOX_BTN_B);
        dump.xboxBtnX = primary.getRawButton(XBOX_BTN_X);
        dump.xboxBtnY = primary.getRawButton(XBOX_BTN_Y);
        dump.xboxBtnLBump = primary.getRawButton(XBOX_BTN_LBUMP);
        dump.xboxBtnRBump = primary.getRawButton(XBOX_BTN_RBUMP);
        dump.xboxBtnBack = primary.getRawButton(XBOX_BTN_BACK);
        dump.xboxBtnStart = primary.getRawButton(XBOX_BTN_START);
        dump.xboxBtnLStick = primary.getRawButton(XBOX_BTN_LSTICK);
        dump.xboxBtnRStick = primary.getRawButton(XBOX_BTN_RSTICK);

        dump.joystickAxisX = (float) secondary.getRawAxis(JOYSTICK_AXIS_X);
        dump.joystickAxisY = (float) secondary.getRawAxis(JOYSTICK_AXIS_Y);
        dump.joystickAxisZ = (float) secondary.getRawAxis(JOYSTICK_AXIS_Z);
        dump.joystickAxisThrottle = (float) secondary.getRawAxis(JOYSTICK_AXIS_THROTTLE);

        for (int b = 0; b < 12; b++) {
            dump.joystickButtons[b] = secondary.getRawButton(b + 1);
        }

        return dump;
    }

    /**
     * Stores all the input data from both primary and secondary controllers.
     *
     * @see Controllers
     */
    public static final class ControllerDataDump {

        public float xboxAxisLX, xboxAxisLY, xboxAxisRX, xboxAxisRY, xboxAxisTrigger, xboxAxisDPad;
        public boolean xboxBtnA, xboxBtnB, xboxBtnX, xboxBtnY, xboxBtnLBump, xboxBtnRBump, xboxBtnBack, xboxBtnStart, xboxBtnLStick, xboxBtnRStick;

        public float joystickAxisX, joystickAxisY, joystickAxisZ, joystickAxisThrottle;
        public boolean[] joystickButtons = new boolean[12];
    }

    // </editor-fold>
    public static boolean getG13Button(int button) {
        return G13Table.getBoolean(Integer.toString(button),false);

    }
}

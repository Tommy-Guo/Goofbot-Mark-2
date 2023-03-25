package frc.robot.subsystems;

import frc.robot.helpers.appendix;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class ArmSubsystemM1 {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    CANSparkMax motorArm1 = new CANSparkMax(appendix.motorArm1, MotorType.kBrushless);
    CANSparkMax motorArm2 = new CANSparkMax(appendix.motorArm2, MotorType.kBrushless);

    RelativeEncoder motorEncoder1 = motorArm1.getEncoder();

    double motorPosition = 0;

    public ArmSubsystemM1() {
        motorArm1.setIdleMode(IdleMode.kBrake);
        motorArm2.setIdleMode(IdleMode.kBrake);

        motorArm2.follow(motorArm1, true);
        motorEncoder1.setPosition(-1);
    }

    public void teleopPeriodic() {
        if (gamePad.getRawButton(appendix.buttonY)) {
            motorPosition = 275;
        } else if (gamePad.getRawButton(appendix.buttonB)) {
            motorPosition = 300;
        } else if (gamePad.getRawButton(appendix.buttonA)) {
            motorPosition = 355;
        }

        if (Math.abs(gamePad.getRawAxis(appendix.axisRightY)) > 0.05) {
            motorArm1.set(gamePad.getRawAxis(appendix.axisRightY) * -0.85);
            motorPosition = motorEncoder1.getPosition();
        } else {
            moveToPosition(motorPosition);
        }
    }

    public void moveToPosition(double pos) {
        double output = 0;
        double godPosition = motorEncoder1.getPosition();
        if (Math.abs(motorPosition - godPosition) > 3) {
           if (motorPosition > godPosition) {
               output = 0.4;
            } else if (motorPosition < godPosition) {
               output = -0.4;
            }
        }
        motorArm1.set(output);
    }

    public double getArmEncoder() {
        return motorEncoder1.getPosition();
    }
}

package frc.robot.subsystems;

import frc.robot.helpers.appendix;
import frc.robot.helpers.common;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class IntakeSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    CANSparkMax intakeMotor = new CANSparkMax(7, MotorType.kBrushless);

    double targetPosition = 0;
    RelativeEncoder intakeEncoder = intakeMotor.getEncoder();

    public IntakeSubsystem() {
        intakeMotor.setIdleMode(IdleMode.kBrake);
        intakeEncoder.setPosition(-1);
    }

    private double getArmTrigger() {
        double rightValue = gamePad.getRawAxis(appendix.triggerRight);
        double leftValue =  gamePad.getRawAxis(appendix.triggerLeft);
        return (rightValue > leftValue ? rightValue * -1 : leftValue);
    }

    public void teleopPeriodic() {
        if (Math.abs(getArmTrigger()) > 0.05) {
            intakeMotor.set(getArmTrigger() * 0.35);
            targetPosition = intakeEncoder.getPosition();
        } else {
            common.moveToPosition(targetPosition, intakeEncoder.getPosition(), intakeMotor);
        }
    }
}
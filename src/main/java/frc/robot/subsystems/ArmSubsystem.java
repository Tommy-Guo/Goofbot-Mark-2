package frc.robot.subsystems;

import frc.robot.helpers.appendix;
import frc.robot.helpers.common;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class ArmSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    CANSparkMax masterArmMotor = new CANSparkMax(appendix.motorArm1, MotorType.kBrushless);
    CANSparkMax secondaryArmMotor = new CANSparkMax(appendix.motorArm2, MotorType.kBrushless);

    double targetPosition = 0;
    RelativeEncoder motorEncoder1 = masterArmMotor.getEncoder();

    public ArmSubsystem() {
        masterArmMotor.setIdleMode(IdleMode.kBrake);
        secondaryArmMotor.setIdleMode(IdleMode.kBrake);

        secondaryArmMotor.follow(masterArmMotor, true);
        motorEncoder1.setPosition(-1);
    }

    public void teleopPeriodic() {
        if (gamePad.getRawButton(appendix.buttonY)) {
            targetPosition = appendix.level3;
        } else if (gamePad.getRawButton(appendix.buttonB)) {
            targetPosition = appendix.level2;
        } else if (gamePad.getRawButton(appendix.buttonA)) {
            targetPosition = appendix.level1;
        }

        if (Math.abs(gamePad.getRawAxis(appendix.axisRightY)) > 0.05) {
            masterArmMotor.set(gamePad.getRawAxis(appendix.axisRightY) * -0.85);
            targetPosition = motorEncoder1.getPosition();
        } else {
            common.moveToPosition(targetPosition, motorEncoder1.getPosition(), masterArmMotor);
        }
    }
}

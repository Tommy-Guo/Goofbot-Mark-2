// package frc.robot.subsystems;

// import frc.robot.helpers.appendix;
// import edu.wpi.first.wpilibj.Joystick;
// import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

// public class IntakeSubsystem {

//     Joystick gamePad = new Joystick(appendix.driveControllerID);
    
//     WPI_VictorSPX intakeMotor = new WPI_VictorSPX(appendix.motorIntake);
//     WPI_VictorSPX intakeRotationMotor = new WPI_VictorSPX(appendix.motorRotational);

//     public void teleopPeriodic() {
//         intakePeriodic();
//     }

//     private double getArmTrigger() {
//         double rightValue = gamePad.getRawAxis(appendix.triggerRight);
//         double leftValue =  gamePad.getRawAxis(appendix.triggerLeft);
//         return (rightValue > leftValue ? rightValue * -1 : leftValue);
//     }

//     private void intakePeriodic() {
//         if (gamePad.getRawButton(appendix.buttonRight)) {
//             intakeMotor.set(0.6);
//         } else if (gamePad.getRawButton(appendix.buttonLeft)) {
//             intakeMotor.set(-0.6);
//         } else {
//             intakeMotor.set(0);
//         }
//         intakeRotationMotor.set(getArmTrigger());
//     }

// }




package frc.robot.subsystems;

import frc.robot.helpers.appendix;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class IntakeSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    CANSparkMax intakeMotor = new CANSparkMax(7, MotorType.kBrushless);

    RelativeEncoder intakeEncoder = intakeMotor.getEncoder();

    double motorPosition = 0;

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
            motorPosition = intakeEncoder.getPosition();
        } else {
            moveToPosition(motorPosition);
        }
    }

    public void moveToPosition(double pos) {
        double output = 0;
        double godPosition = intakeEncoder.getPosition();
        if (Math.abs(motorPosition - godPosition) > 3) {
           if (motorPosition > godPosition) {
               output = 0.4;
            } else if (motorPosition < godPosition) {
               output = -0.4;
            }
        }
        intakeMotor.set(output);
    }
}

package frc.robot.subsystems;

import frc.robot.helpers.common;
import frc.robot.helpers.appendix;

import javax.naming.ldap.StartTlsRequest;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class ArmSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    // Motors & Data for main arm.
    CANSparkMax masterArmMotor = new CANSparkMax(appendix.motorArm1, MotorType.kBrushless);
    CANSparkMax secondaryArmMotor = new CANSparkMax(appendix.motorArm2, MotorType.kBrushless);
    double armTargetPosition = 0;
    RelativeEncoder armMotorEncoder = masterArmMotor.getEncoder();

    // Motors & Data for arm joint.
    CANSparkMax armJointMotor = new CANSparkMax(appendix.motorJoint, MotorType.kBrushless);
    double armJointTargetPosition = -1;
    RelativeEncoder armJointMotorEncoder = armJointMotor.getEncoder();

    public ArmSubsystem() {
        armJointMotor.setIdleMode(IdleMode.kBrake);

        masterArmMotor.setIdleMode(IdleMode.kBrake);
        secondaryArmMotor.setIdleMode(IdleMode.kBrake);
        secondaryArmMotor.follow(masterArmMotor, true);

        armMotorEncoder.setPosition(-1);
        armJointMotorEncoder.setPosition(-1);
    }

    public void teleopPeriodic() {
        ArmPeriodic();
        JointPeriodic();
        recordData();
    }

    double getArmTrigger() {
        double rightValue = gamePad.getRawAxis(appendix.triggerRight);
        double leftValue =  gamePad.getRawAxis(appendix.triggerLeft);
        return (rightValue > leftValue ? rightValue * -1 : leftValue);
    }

    public void ArmPeriodic() {
        if (gamePad.getRawButton(appendix.buttonY)) {
            armTargetPosition = appendix.armLevel3;
            armJointTargetPosition = appendix.jointLevel3;
        } else if (gamePad.getRawButton(appendix.buttonB)) {
            armTargetPosition = appendix.armLevel2;
            armJointTargetPosition = appendix.jointLevel2;
        } else if (gamePad.getRawButton(appendix.buttonA)) {
            armTargetPosition = appendix.armLevel1;
            armJointTargetPosition = appendix.jointLevel1;
        } else if (gamePad.getRawButton(appendix.buttonX)) {
            armTargetPosition = 5;
            armJointTargetPosition = 32;
        }
        
        if (Math.abs(gamePad.getRawAxis(appendix.axisRightY)) > 0.05) {
            masterArmMotor.set(gamePad.getRawAxis(appendix.axisRightY) * -0.85);
            armTargetPosition = armMotorEncoder.getPosition();
        } else {
            common.moveToPosition(armTargetPosition, armMotorEncoder.getPosition(), masterArmMotor);
        }
    
    }

    public void JointPeriodic() {
        if (Math.abs(getArmTrigger()) > 0.08) {
            armJointMotor.set(getArmTrigger() * -0.25);
            armJointTargetPosition = armJointMotorEncoder.getPosition();
        } else {
        double startTuckEncoderArm = 100;
        double endTuckEncoderArm = 211;

        if (armMotorEncoder.getPosition() > startTuckEncoderArm && armMotorEncoder.getPosition() < endTuckEncoderArm) {
            armJointTargetPosition = -12;
        } 
            common.moveToPosition(armJointTargetPosition, armJointMotorEncoder.getPosition(), armJointMotor);
        }
    }

    public void recordData() {
        if (gamePad.getRawButton(8)) {
            System.out.println("Arm Joint Position: " + armJointMotorEncoder.getPosition() + "; Arm Positions: " + armMotorEncoder.getPosition());
        }
    }

}

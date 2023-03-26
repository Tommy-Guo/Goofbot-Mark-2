package frc.robot.subsystems;

import frc.robot.helpers.common;
import frc.robot.helpers.appendix;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

public class ArmSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    // Motors & Data for main arm.
    CANSparkMax masterArmMotor = new CANSparkMax(appendix.motorArm1, MotorType.kBrushless);
    CANSparkMax secondaryArmMotor = new CANSparkMax(appendix.motorArm2, MotorType.kBrushless);

    double armTargetPosition = 0;
    RelativeEncoder armMotorEncoder = masterArmMotor.getEncoder();

    // Motors & Data for arm joint.
    CANSparkMax armJointMotor = new CANSparkMax(appendix.motorArm1, MotorType.kBrushless);

    double armJointTargetPosition = 0;
    RelativeEncoder armJointMotorEncoder = masterArmMotor.getEncoder();

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
    }

    public void ArmPeriodic() {
        if (gamePad.getRawButton(appendix.buttonY)) {
            armTargetPosition = appendix.level3;
        } else if (gamePad.getRawButton(appendix.buttonB)) {
            armTargetPosition = appendix.level2;
        } else if (gamePad.getRawButton(appendix.buttonA)) {
            armTargetPosition = appendix.level1;
        }

        if (Math.abs(gamePad.getRawAxis(appendix.axisRightY)) > 0.05) {
            masterArmMotor.set(gamePad.getRawAxis(appendix.axisRightY) * -0.85);
            armTargetPosition = armMotorEncoder.getPosition();
        } else {
            common.moveToPosition(armTargetPosition, armMotorEncoder.getPosition(), masterArmMotor);
        }
    }
    private double getArmTrigger() {
        double rightValue = gamePad.getRawAxis(appendix.triggerRight);
        double leftValue =  gamePad.getRawAxis(appendix.triggerLeft);
        return (rightValue > leftValue ? rightValue * -1 : leftValue);
    }

    public void JointPeriodic() {
        if (Math.abs(getArmTrigger()) > 0.05) {
            armJointMotor.set(getArmTrigger() * 0.35);
            armJointTargetPosition = armJointMotorEncoder.getPosition();
        } else {
            common.moveToPosition(armJointTargetPosition, armJointMotorEncoder.getPosition(), armJointMotor);
        }
    }

    public void JointPeriodicAutoLeveling() {
        // imaginary values until tested.
        final double armstartPoint = 0;
        final double armendPoint = 360;

        final double joinStart = 0;
        final double jointEnd = 300;

        final double tuckZoneStart = 120;
        final double tuckZoneEnd = 180;

        final double tuckedPosition = 180;

        if (armMotorEncoder.getPosition() > tuckZoneStart && armMotorEncoder.getPosition() < tuckZoneEnd) {
            common.moveToPosition(tuckedPosition, armJointMotorEncoder.getPosition(), armJointMotor);
        } else {
            double output = common.map(armMotorEncoder.getPosition(), armstartPoint, armendPoint, joinStart, jointEnd);
            common.moveToPosition(output, armJointMotorEncoder.getPosition(), armJointMotor);
        }
    }

}

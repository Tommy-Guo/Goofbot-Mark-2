package frc.robot.subsystems;

import frc.robot.helpers.appendix;

import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class IntakeSubsystemM2 {

    Joystick gamePad = new Joystick(appendix.driveControllerID);

    WPI_VictorSPX intakeMotor = new WPI_VictorSPX(appendix.motorIntake);
    CANSparkMax intakeRotationMotor = new CANSparkMax(appendix.motorRotational, MotorType.kBrushless);

    private double rotationPosition = 0;
    RelativeEncoder intakeRotationEncoder = intakeRotationMotor.getEncoder();

    private ArmSubsystemM1 arm;

    public IntakeSubsystemM2(ArmSubsystemM1 arm) {
        this.arm = arm;
    }

    public void teleopPeriodic() {
        intakePeriodic();
        // intakeRotationPeriodic();
    }

    private void intakePeriodic() {
        if (gamePad.getRawButton(appendix.buttonRight)) {
            intakeMotor.set(0.6);
        } else if (gamePad.getRawButton(appendix.buttonLeft)) {
            intakeMotor.set(-0.6);
        } else {
            intakeMotor.set(0);
        }
    }

    public void moveToPosition(double pos) {
        double output = 0;
        double godPosition = intakeRotationEncoder.getPosition();
        if (Math.abs(rotationPosition - godPosition) > 3) {
            output = rotationPosition > godPosition ? 0.4 : -0.4;
        }
        intakeRotationMotor.set(output);
    }

    // private void rotationalIntake() {
    //     double armPosition = arm.getArmEncoder();
    //     double levelOffset = 90;
    //     if (armPosition > 0 && armPosition < 0) {
    //         moveToPosition(0);
    //     } else {
    //         double calculatedOffset = armPosition > 0 ? -(armPosition % levelOffset) : (armPosition % levelOffset);
    //         moveToPosition(intakeRotationEncoder.getPosition() + calculatedOffset);
    //     }
    // }
}
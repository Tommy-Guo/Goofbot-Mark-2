package frc.robot.subsystems;

import frc.robot.helpers.appendix;

import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class IntakeSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    WPI_VictorSPX intakeMotor = new WPI_VictorSPX(appendix.motorIntake);

    public void teleopPeriodic() {
        if (gamePad.getRawButton(appendix.buttonRight)) {
            intakeMotor.set(-0.85);
        } else if (gamePad.getRawButton(appendix.buttonLeft)) {
            intakeMotor.set(0.85);
        } else {
            intakeMotor.set(0);
        }
    }
}
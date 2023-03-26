package frc.robot.subsystems;

import frc.robot.helpers.appendix;

import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class IntakeSubsystem {
    Joystick gamePad = new Joystick(appendix.driveControllerID);

    WPI_VictorSPX intakeMotor = new WPI_VictorSPX(appendix.motorIntake);

    public void teleopPeriodic() {
        intakeMotor.set(gamePad.getRawButton(appendix.buttonRight) ? 1 : -1);
    }
}
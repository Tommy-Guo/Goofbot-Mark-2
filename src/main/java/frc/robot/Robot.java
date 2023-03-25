// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import frc.robot.autonomous.autonomous;
import frc.robot.subsystems.ArmSubsystemM2;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  // ArmSubsystemM2 armSubsystem;
  IntakeSubsystem intakeSubsystem;
  DriveSubsystem driveSubsystem;

  @Override
  public void robotInit() {

    // this.armSubsystem = new ArmSubsystemM2();
    this.intakeSubsystem = new IntakeSubsystem();
    this.driveSubsystem = new DriveSubsystem();

    UsbCamera usbCamera = CameraServer.startAutomaticCapture("Main Camera", 0);
		usbCamera.setResolution(160, 120);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void disabledExit() {
  }

  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    // }
    // autoSystem = new autonomous();
  }

  @Override
  public void autonomousPeriodic() {
    // autoSystem.runAuto();
  }

  @Override
  public void autonomousExit() {
    
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    // this.armSubsystem.teleopPeriodic();
    this.intakeSubsystem.teleopPeriodic();
    this.driveSubsystem.teleopPeriodic();
  }

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void testExit() {
  }
}

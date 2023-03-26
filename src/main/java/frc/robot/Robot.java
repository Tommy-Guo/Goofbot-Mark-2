// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.autonomous.Autonomous;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {

  Autonomous autoSystem;
  ArmSubsystem armSubsystem;
  DriveSubsystem driveSubsystem;
  IntakeSubsystem intakeSubsystem;

  @Override
  public void robotInit() {

    this.autoSystem = new Autonomous();
    this.armSubsystem = new ArmSubsystem();
    this.driveSubsystem = new DriveSubsystem();
    this.intakeSubsystem = new IntakeSubsystem();

    UsbCamera usbCamera = CameraServer.startAutomaticCapture("Main Camera", 0);
		usbCamera.setResolution(160, 120);
  }

  @Override
  public void robotPeriodic() {
    
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
    autoSystem.runAuto();
  }

  @Override
  public void autonomousExit() {
    
  }

  @Override
  public void teleopInit() {

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
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.*;

public class ManualShooter extends CommandBase {
  /** Creates a new ManualShooter. */
  Shooter2 m_shooter2;
  double speed = 0;


  public ManualShooter(double target_speed) {
    // Use addRequirements() here to declare subsystem dependencies.
      if (target_speed> 0)
      {
        speed = target_speed;
      }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter2 = RobotContainer.getInstance().m_shooter2;
    m_shooter2.setSpeed(speed);
    //SmartDashboard.putNumber("Shooter Speed", Constants.ShooterFarShot);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_shooter2.setSpeed(SmartDashboard.getNumber("Shooter Speed", Constants.ShooterFarShot));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter2.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

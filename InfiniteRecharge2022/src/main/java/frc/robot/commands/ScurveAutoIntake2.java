// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SCurve;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class ScurveAutoIntake2 extends CommandBase {
  /** Creates a new ScurveAutoIntake2. */
  private final SCurve m_sCurve;
  private boolean lowerOnlyWasTriggered = false;
  private boolean middleAndlowerTriggered = false;

  public ScurveAutoIntake2(SCurve subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_sCurve = subsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_sCurve.setSCurveSpeed(0);
    m_sCurve.setIntakeSpeed(Constants.SCurveIntakeAutoForwardSpeed);
    lowerOnlyWasTriggered = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_sCurve.isBottomSensorTriggered() && !m_sCurve.isMiddleSensorTriggered() && !m_sCurve.isTopSensorTriggered())
    {
      m_sCurve.setSCurveSpeed(Constants.SCurveSCurveAutoForwardSpeed);
      lowerOnlyWasTriggered = true;
    }
    if(m_sCurve.isMiddleSensorTriggered() && lowerOnlyWasTriggered)
    {
      m_sCurve.setSCurveSpeed(0);
      lowerOnlyWasTriggered = false;
    } 
    if(m_sCurve.isBottomSensorTriggered() && m_sCurve.isMiddleSensorTriggered() && !m_sCurve.isTopSensorTriggered())
    {
      m_sCurve.setSCurveSpeed(Constants.SCurveSCurveAutoForwardSpeed);
      middleAndlowerTriggered = true;
    }

    if (m_sCurve.isTopSensorTriggered() && middleAndlowerTriggered) {
      m_sCurve.setSCurveSpeed(0);
      middleAndlowerTriggered = false;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_sCurve.setSCurveSpeed(0);
    m_sCurve.setIntakeSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
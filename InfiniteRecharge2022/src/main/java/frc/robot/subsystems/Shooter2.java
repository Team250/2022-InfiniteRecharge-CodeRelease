// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Shooter2 extends SubsystemBase {

  private CANSparkMax sL_motor, sR_motor;
  private SparkMaxPIDController sL_pidController, sR_pidController;
  private RelativeEncoder sL_encoder, sR_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  /** Creates a new Shooter2. */
  public Shooter2() {

    // Initialize Motors
    sL_motor = new CANSparkMax(16, MotorType.kBrushless);
    sR_motor = new CANSparkMax(17, MotorType.kBrushless);

    sL_motor.restoreFactoryDefaults();
    sR_motor.restoreFactoryDefaults();

    sL_motor.setInverted(true);

    /**
     * In order to use PID functionality for a controller, a SparkMaxPIDController
     * object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    sL_pidController = sL_motor.getPIDController();
    sR_pidController = sR_motor.getPIDController();

    // Encoder object created to display position values
    sL_encoder = sL_motor.getEncoder();
    sR_encoder = sR_motor.getEncoder();

    // PID coefficients
    kP = 6e-5;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0.000015;
    kMaxOutput = 1;
    kMinOutput = -1;
    maxRPM = 5700;

    // set PID coefficients
    sL_pidController.setP(kP);
    sL_pidController.setI(kI);
    sL_pidController.setD(kD);
    sL_pidController.setIZone(kIz);
    sL_pidController.setFF(kFF);
    sL_pidController.setOutputRange(kMinOutput, kMaxOutput);

    sR_pidController.setP(kP);
    sR_pidController.setI(kI);
    sR_pidController.setD(kD);
    sR_pidController.setIZone(kIz);
    sR_pidController.setFF(kFF);
    sR_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
  }

  public void setSpeed(double setPoint) {
    setPoint = Constants.ShooterFudgeFactor * setPoint / Constants.ShooterGearRatio;
    sL_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
    sR_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("ProcessVariable Left", sL_encoder.getVelocity());
    SmartDashboard.putNumber("ProcessVariable Right", sR_encoder.getVelocity());
    SmartDashboard.putNumber("Left Shooter RPM", sL_encoder.getVelocity() * Constants.ShooterGearRatio);
    SmartDashboard.putNumber("Right SHooter RPM", sR_encoder.getVelocity() * Constants.ShooterGearRatio);
  }
}

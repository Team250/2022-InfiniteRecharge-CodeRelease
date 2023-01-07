// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.SoftLimitDirection;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMax.IdleMode;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Climber extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private DigitalInput wOHLeft;
    private DigitalInput wOHRight;
    private Solenoid solenoid1;
    private CANSparkMax cLMB;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private SparkMaxPIDController climb_pidController;
    private RelativeEncoder climb_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxAcc, maxVel, minVel, allowedErr;
    public float kSoftLimitTop, kSoftLimitBot;
    /**
    *
    */
    public Climber() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        wOHLeft = new DigitalInput(4);
        addChild("WOH Left", wOHLeft);

        wOHRight = new DigitalInput(5);
        addChild("WOH Right", wOHRight);

        solenoid1 = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);
        addChild("Solenoid 1", solenoid1);

        cLMB = new CANSparkMax(15, MotorType.kBrushless);

        cLMB.restoreFactoryDefaults();
        cLMB.setInverted(false);
        cLMB.setIdleMode(IdleMode.kBrake);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        cLMB.setSmartCurrentLimit(45);
        climb_pidController = cLMB.getPIDController();
        
        // Encoder object created to display position values
        climb_encoder = cLMB.getEncoder();

        // PID coefficients
        kP = 24e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000015;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 500; // Arbartary

        // Smart Motion Coefficients
        maxVel = 2000; // rpm
        maxAcc = 1500;

        // set PID coefficients
        climb_pidController.setP(kP);
        climb_pidController.setI(kI);
        climb_pidController.setD(kD);
        climb_pidController.setIZone(kIz);
        climb_pidController.setFF(kFF);
        climb_pidController.setOutputRange(kMinOutput, kMaxOutput);
        
        kSoftLimitTop = 150;
        kSoftLimitBot = 0;
        /**
        * Smart Motion coefficients are set on a SparkMaxPIDController object
        * 
        * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
        * the pid controller in Smart Motion mode
        * - setSmartMotionMinOutputVelocity() will put a lower bound in
        * RPM of the pid controller in Smart Motion mode
        * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
        * of the pid controller in Smart Motion mode
        * - setSmartMotionAllowedClosedLoopError() will set the max allowed
        * error for the pid controller in Smart Motion mode
        */
        int smartMotionSlot = 0;
        climb_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
        climb_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
        climb_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
        climb_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
        
        cLMB.enableSoftLimit(SoftLimitDirection.kForward, true);
        cLMB.enableSoftLimit(SoftLimitDirection.kReverse, true);
        cLMB.setSoftLimit(SoftLimitDirection.kForward, kSoftLimitTop);
        cLMB.setSoftLimit(SoftLimitDirection.kReverse, kSoftLimitBot);

        SmartDashboard.setDefaultNumber("Set Position", 0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        //System.out.println("Climber target + " + SmartDashboard.getNumber("Set Position", 0));
        //setTargetPosition(SmartDashboard.getNumber("Set Position", 0));
        SmartDashboard.putNumber("Climber Position", getCurrentPosition());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // Returns if Climb Arm is open
    public boolean isOpen() {
        return solenoid1.get();
    }

    // Sets Target Intake Arm position
    public void setOpen(boolean isOpen) {
        solenoid1.set(isOpen);
    }

    public void setTargetPosition(double setPoint) {
        climb_pidController.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
        System.out.println("Target Position:" + setPoint);
    }
    
    public double getCurrentPosition() {
        return climb_encoder.getPosition(); 
    }
}
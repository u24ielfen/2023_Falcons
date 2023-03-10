// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  DutyCycleEncoder intakeEncoder = new DutyCycleEncoder(3);
  CANSparkMax intakeMotor = new CANSparkMax(Constants.Intake.MOTORID, MotorType.kBrushless);
  XboxController controller = new XboxController(3);
  PIDController intakePID = new PIDController(Constants.Intake.PID[0], Constants.Intake.PID[1], Constants.Intake.PID[2]);
  public enum state{
    OPEN,
    CLOSED
  }
  public state currentState;
  public Intake() {
    intakeMotor.restoreFactoryDefaults();
    intakeMotor.setInverted(false);
    intakeMotor.setIdleMode(IdleMode.kCoast);
    intakePID.setTolerance(2);
  }
  @Override
  public void periodic() {
    if(controller.getBButton()){
      moveIntake(-0.08);
    }
    if(controller.getXButton()){
      moveIntake(0.3);
    }

    // This method will be called once per scheduler run
  }
  
  public void zeroIntake(){
      // intakeEncoder.setPosition(0);
      intakeEncoder.reset();
  }
  
  public state getState(){
    return currentState;
  } 

  public void stopIntakeMotor(){
    intakeMotor.set(0);
  }
  
  public void moveIntake(double speed){
    intakeMotor.set(speed);
  }

  public double getIntakeEncoder(){
    return intakeEncoder.getAbsolutePosition();
  }
  
  public void intakeToBam(double ticks){
    if(getIntakeEncoder() > ticks - 5 && getIntakeEncoder() < ticks + 5){
      moveIntake(0);
    }
    else if(getIntakeEncoder() < ticks - 20){
      moveIntake(0.8);
    }
    else if(getIntakeEncoder() > ticks - 20 && getIntakeEncoder() < ticks){
      moveIntake(0.3);
    }
    else if(getIntakeEncoder() > ticks){
      moveIntake(-0.8);
    }
  }
}

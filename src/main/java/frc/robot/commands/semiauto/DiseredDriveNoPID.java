// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.semiauto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrivetrain;

public class DiseredDriveNoPID extends Command {
  /** Creates a new DriveXfeetYfeet. */
  SwerveDrivetrain sDrivetrain;

  double initialX;
  double initialY;

  double xSetpoint;
  double ySetpoint;

  double rotationSetpoint;

  boolean rotateBoolean;

  double outputTranslation = 0;
    double outputStrafe = 0;
    double outputRotation = 0;

  public DiseredDriveNoPID(double xSetpoint, double ySetpoint, double rotationDegreeSetpoint, SwerveDrivetrain sDrivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.sDrivetrain = sDrivetrain;
    addRequirements(sDrivetrain);
    this.xSetpoint = xSetpoint;
    this.ySetpoint = ySetpoint;
    this.rotationSetpoint = rotationDegreeSetpoint;
    rotateBoolean = true;
  }

  public DiseredDriveNoPID(double xSetpoint, double ySetpoint, SwerveDrivetrain sDrivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.sDrivetrain = sDrivetrain;
    addRequirements(sDrivetrain);
    this.xSetpoint = xSetpoint;
    this.ySetpoint = ySetpoint;
    rotateBoolean = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(rotateBoolean){
      rotationSetpoint = rotationSetpoint;
    }else {
      rotationSetpoint = sDrivetrain.getAngle();
    }
    double initialX = Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getX());
    double initialY = Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getY());
   
    xSetpoint = xSetpoint + initialX;
    ySetpoint = ySetpoint + initialY;

    System.out.println("Initialize");

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(Math.abs(Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getX()) - xSetpoint) < .4){
      outputTranslation = 0;
    }else if((Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getX()) - xSetpoint) > .4){
      outputTranslation = -.4;
    }else{
      outputTranslation = .4;
    }

    if(Math.abs(Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getY()) - ySetpoint) < .4){
      outputStrafe = 0;
    }else if((Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getY()) - ySetpoint) > .4){
      outputStrafe = -.4;
    }else{
      outputStrafe = .4;
    }
    
    if(Math.abs(sDrivetrain.getAngle() - rotationSetpoint) < .4){
      outputRotation = 0;
    }else if((sDrivetrain.getAngle() - rotationSetpoint) > .4){
      outputRotation = -.4;
    }else{
      outputRotation = .4;
    }
  // scale up with maxVelo
    sDrivetrain.drive(new Translation2d(outputTranslation, outputStrafe).times(Constants.RobotConstants.driveMaxVelo), outputRotation*Constants.RobotConstants.rotationMaxAngleVelo);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sDrivetrain.drive(new Translation2d(0,0), 0);
    System.out.println("Ended");
    System.out.println("" + interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    boolean val1 = Math.abs(Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getX()) - xSetpoint) < .4; 
    boolean val2 = Math.abs(Units.metersToFeet(sDrivetrain.sOdometry.getPoseMeters().getY()) - ySetpoint) < .4;
    boolean val3 = Math.abs(sDrivetrain.getAngle() - rotationSetpoint) < 1;
    System.out.println("af" + val1 + val2 + val3);
    return val1 && val2 && val3 ;
  }
}
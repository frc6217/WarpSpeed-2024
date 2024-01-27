// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.SwerveDrivetrain;

public class FollowApriltagw extends Command {
  /** Creates a new FollowApriltagw. */
  PIDController rPidController = new PIDController(0, 0, 0);
  PIDController sPidController = new PIDController(0, 0, 0);
  PIDController tPidController = new PIDController(.1, 0, 0);
  SwerveDrivetrain drivetrain;
  Limelight ll;
  double strafeSetpoint = 0;
  double sp = 1/1.7;
  public FollowApriltagw(SwerveDrivetrain drivetrain, Limelight ll) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    this.ll = ll; 
    SmartDashboard.putNumber("p translation", .1);
    SmartDashboard.putNumber("p strafe", .1);
    SmartDashboard.putNumber("p rotation", .1);
    SmartDashboard.putData(tPidController);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tPidController.setSetpoint(sp);
    sPidController.setSetpoint(strafeSetpoint);
    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double pTranslation = SmartDashboard.getNumber("p translation", 0);
      double pStrafe = SmartDashboard.getNumber("p strafe", 0);
      double pRotation = SmartDashboard.getNumber("p rotation", 0);
      tPidController.setP(pTranslation);
      sPidController.setP(pStrafe);
      rPidController.setP(pRotation);
      double getTranslation = -tPidController.calculate(1/ll.getArea());
      double getStrafe = sPidController.calculate(ll.getX());
      double getSkew = rPidController.calculate(ll.getSkew());
      getTranslation = MathUtil.clamp(getTranslation, -1, 1);
      getStrafe = MathUtil.clamp(getStrafe, -1, 1);
      getSkew = MathUtil.clamp(getSkew, -1, 1);
      SmartDashboard.putNumber("tpid out", getTranslation);
      SmartDashboard.putNumber("spid out", getStrafe);
      SmartDashboard.putNumber("rpid out", getSkew);
      if(ll.getArea()!= 0){
      drivetrain.drive(new Translation2d(getTranslation, getStrafe).times(Constants.RobotConstants.driveMaxVelo*0.5), 0);
      }
      else{
        drivetrain.drive(new Translation2d(0,0), 0);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(new Translation2d(0,0), 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

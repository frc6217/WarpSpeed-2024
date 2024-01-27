// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import au.grapplerobotics.LaserCan;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LazerCAN extends SubsystemBase {
  /** Creates a new LazerCAN. */
  LaserCan lazer = new LaserCan(55);
  public LazerCAN() {}

  @Override
  public void periodic() {
    //SmartDashboard.putNumber("Distance (Lazer)", (lazer.getMeasurement().distance_mm));
    SmartDashboard.putNumber("Distance (Lazer)", Units.metersToInches((double)(lazer.getMeasurement().distance_mm)/1000));
    // This method will be called once per scheduler run
  }
}

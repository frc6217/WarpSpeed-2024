// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.helpers.LimelightHelpers;

public class LimeLightLocal extends SubsystemBase {
  /** Creates a new LimeLightLocal. */
  String name;
  LimelightHelpers.PoseEstimate blueEstimate;
  public LimeLightLocal(String name, int portOffset) {
    
    this.name = name;

    for(int port = 5800; port <= 5809; port++){
      PortForwarder.add(port+ portOffset, name + ".local", port);
    }


  }
  LimelightHelpers.PoseEstimate getBlueEstimate() {
    return blueEstimate;
  }
  @Override
  public void periodic() {
    blueEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue(name);
    SmartDashboard.putNumber("kaf;dlkj;kl", blueEstimate.pose.getX());

    // This method will be called once per scheduler run
  }
}

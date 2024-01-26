// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAnalogSensor;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkAnalogSensor.Mode;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.interfaces.IEncoder;

/** Add your docs here. */
public class BBAbsoluteEncoder implements IEncoder{
    SparkAbsoluteEncoder encoder;
    double offset;
    SparkAnalogSensor analogSensor;
    BBAbsoluteEncoder(CANSparkMax motor, double offset){
        //encoder = motor.getAbsoluteEncoder(Type.kDutyCycle);
        analogSensor = motor.getAnalog(Mode.kAbsolute);
        this.offset = offset;
    }


    @Override
    public double getRawValue() {
        // TODO Auto-generated method stub
        return analogSensor.getPosition();
    }

    @Override
    public Rotation2d getAngle() {
        // TODO Auto-generated method stub
        //Scales 3.3 to 360
        return Rotation2d.fromDegrees((getRawValue()*109.1)-offset);
    }

}

/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *

 */
public class SampleMotor {

    DcMotor motor;

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 1.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    /* Constructor */
    public SampleMotor( HardwareMap hardwareMap, boolean useEncoders ) {

        // Define and Initialize Motors
        motor = hardwareMap.dcMotor.get("demo motor");
        motor.setDirection( DcMotor.Direction.FORWARD ); // Set to REVERSE if using AndyMark motors

        // Set all motors to zero power
        motor.setPower(0);

        if( useEncoders ) {
            motor.setMode( DcMotor.RunMode.STOP_AND_RESET_ENCODER );
            motor.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
        } else {
            // Set all motors to run without encoders.
            // May want to use RUN_USING_ENCODERS if encoders are installed.
            motor.setMode( DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        }
    }

    public void setPower( double power ) {
        // Output the safe vales to the motor drives.
        motor.setPower( power );
    }

     public void startMoving( double speed, double inches ) {

        // Determine new target position, and pass to motor controller
        int newTarget = motor.getCurrentPosition() + (int) (inches * COUNTS_PER_INCH);
        motor.setTargetPosition( newTarget );

        // Turn On RUN_TO_POSITION
        motor.setMode( DcMotor.RunMode.RUN_TO_POSITION );

        motor.setPower( Math.abs( speed ) );
    }

    public void stopMoving() {
        // Stop all motion;
        motor.setPower( 0 );

        // Turn off RUN_TO_POSITION
        motor.setMode( DcMotor.RunMode.RUN_USING_ENCODER );
    }

    public boolean hasReachedTarget() {

        return !motor.isBusy();
    }

    public int getEncoderPosition() {
        return motor.getCurrentPosition();
    }

    public int getEncoderTarget() {
        return motor.getTargetPosition();
    }
}
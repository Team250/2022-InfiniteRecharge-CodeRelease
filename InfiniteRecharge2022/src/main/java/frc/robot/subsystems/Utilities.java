// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

public class Utilities {
	private static final int e = 0;

	// Add a center deadband and re-map to account for this
	public static double deadband(double input, double deadbandWidth, double minInput, double maxInput,
		double minOutput, double maxOutput) {
		double centerInput, centerOutput, leftYInt, rightYInt, lineSlope;
		centerInput = (maxInput + minInput) / 2;
		centerOutput = (maxOutput + minOutput) / 2;
		lineSlope = (maxOutput - centerOutput) / (maxInput - centerInput - 2 * deadbandWidth);

		if (input < minInput || input > maxInput) {
			// Invalid input, returning the least dangerous value
			return centerOutput;
		}

		if (input < minInput + deadbandWidth) {
			// Lower deadband
			return minOutput;
		} else if (input < centerInput - deadbandWidth) {
			// Slope of left line
			leftYInt = minOutput - lineSlope * (minInput + deadbandWidth);
			return (lineSlope * input + leftYInt);
		} else if (input < centerInput + deadbandWidth) {
			// Center deadband
			return centerOutput;
		} else if (input < maxInput - deadbandWidth) {
			// Slope of right line
			rightYInt = maxOutput - lineSlope * (maxInput - deadbandWidth);
			return (lineSlope * input + rightYInt);
		} else {
			// Upper deadband
			return maxOutput;
		}
	}

	// Apply deadband to raw joystick output value
	public static double joystickDeadband(double input, double width) {
		return deadband(input, width, -1.0, 1.0, -1.0, 1.0);
	}

	public static double motorRampDelta(double maxChange, double target, double current) {
		// If the target is within the range of maxChange
		if (maxChange + current > target && current - maxChange < target) {
			return target;
		} // If the current is less than the target and out of range of the
			// target
		else if (current < target) {
			return current + maxChange;
		} // If the current is greater than the target and out of range of the
			// target
		else if (current > target) {
			return current - maxChange;
		} // THIS IS A BAD THING. WE SHOULD NOT GET HERE. IF WE DO, YELL,
			// "KYLE!!!!!!!!"
		else {
			return 0;
		}
	}

	public static double joystickMapFunction(double input) {
		if (input < 0) {
			return -Math.abs(Math.pow(input, 2));
		} else {
			return Math.abs(Math.pow(input, 2));
		}
	}

	public static double formatAngle(double angle) {
		return (angle % 360 + 360) % 360;
	}

	public static double formatAngle180(double angle)
	{
		angle = formatAngle(angle);
		if(angle > 180){
			angle = angle - 360;
		}
		return angle;
	}

	public static double angleDifference(double a, double b) {
		a = formatAngle(a);
		b = formatAngle(b);
		double d = a - b;
		if (d > 180) {
			d -= 360;
		}
		if (d < -180) {
			d += 360;
		}
		return d;
	}

	public static double piecewiseReadout(double X1, double X2, double Y1, double Y2, double X) {
		double y = (Y2 - Y1) * ((X - X1) / (X2 - X1)) + Y1;
		return y;
	}

	public static double SigmoidCurve(double K, double C, double X) {
		double y = 1 / (1 + Math.exp(-K * (X - C))) + 1 / (1 + Math.exp(-K * (X + C))) - 1;
		return y;
	}
}

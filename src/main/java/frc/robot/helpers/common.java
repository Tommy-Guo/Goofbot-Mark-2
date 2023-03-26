package frc.robot.helpers;

import com.revrobotics.CANSparkMax;

public class common {

        public static double lerp(double a, double b, double t){
            return a + (b - a) * t;
       }

        public static void moveToPosition(double target, double encoderPosition, CANSparkMax motor) {
            double output = 0;
            if (Math.abs(target - encoderPosition) > 3) {
                output = (target - lerp(target, encoderPosition, 1)) / 100;
            }
            motor.set(output);
        }

        public static double quadraticDrive(double inputAxis) {
            double magnitude = Math.pow(inputAxis, 2);
            return inputAxis < 0 ? -magnitude : magnitude;
        }

        public static double quadraticDrive(double inputAxis, double speedModifier) {
            double magnitude = Math.pow(inputAxis, 2) * speedModifier;
            return inputAxis < 0 ? -magnitude : magnitude;
        }

        public static double map(double sourceNumber, double fromA, double fromB, double toA, double toB) {
            final int decimalPrecision = 6;
            double deltaA = fromB - fromA;
            double deltaB = toB - toA;
            double scale  = deltaB / deltaA;
            double negA   = -1 * fromA;
            double offset = (negA * scale) + toA;
            double finalNumber = (sourceNumber * scale) + offset;
            int calcScale = (int) Math.pow(10, decimalPrecision);
            return (double) Math.round(finalNumber * calcScale) / calcScale;
        }
}
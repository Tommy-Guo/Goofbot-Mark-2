package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightSubsystem {

    private double deltaX;
    
    public void teleopPeriodic() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");

        this.deltaX = tx.getDouble(0.0);
    } 

    public double deltaX() {
        return this.deltaX;
    }
}
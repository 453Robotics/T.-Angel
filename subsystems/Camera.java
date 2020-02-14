  
package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;

public class Camera
{
    CameraServer server;
    CameraServer server2;

    public void init()
    {
        server = CameraServer.getInstance();
        server2 = CameraServer.getInstance();
        server.startAutomaticCapture();
        server2.startAutomaticCapture();
    }
}
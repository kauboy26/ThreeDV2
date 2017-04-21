/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer3D;

import java.awt.Color;
import java.awt.Graphics2D;
import threedv2.GameScreen;

/**
 *
 * @author krishna
 */

public class Transformer {
    
    
    public static final int DIST_FROM_EYE = 2000;
    public static final int X_AXIS = 0, Y_AXIS = 1, Z_AXIS = 2;
    
    public Graphics2D g;
    
    public Transformer(Graphics2D gr)
    {
        g = gr;
    }
    
    /*
    This method converts a given 3D coordinate to a coordinate on the screen, 
    after taking into account the depth. 
    t - the x OR y coordinate to be converted
    zt - the depth
    */
    public static int getJavaCoord(double t, double zt)
    {
        return GameScreen.WIDTH / 2 + (int)(t * getZFactor(zt));
    }
    
    public void setColor(Color c)
    {
        g.setColor(c);
    }
    
    /*
    This method draws a line given 3D coordinates
    */
    public void drawLine(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        
        g.drawLine(getJavaCoord(x1, z1), getJavaCoord(y1, z1), getJavaCoord(x2, z2), getJavaCoord(y2, z2));
        
    }
    
    public void rotateShape(double[][] s, double theta, int axis)
    {
        if(axis == Z_AXIS){
            
            // The x and y coordinates are transformed
            for(int i = 0; i < s.length; i ++)
            {
                double x = s[i][0];
                s[i][0] = s[i][0] * Math.cos(theta) - s[i][1] * Math.sin(theta);
                s[i][1] = s[i][1] * Math.cos(theta) + x * Math.sin(theta);
            }
            
        }else if(axis == Y_AXIS){
            for(int i = 0; i < s.length; i ++)
            {
                double x = s[i][0];
                s[i][0] = s[i][0] * Math.cos(theta) - s[i][2] * Math.sin(theta);
                s[i][2] = s[i][2] * Math.cos(theta) + x * Math.sin(theta);
            }
            
        }else if(axis == X_AXIS){
            for(int i = 0; i < s.length; i ++)
            {
                double z = s[i][2];
                s[i][2] = s[i][2] * Math.cos(theta) - s[i][1] * Math.sin(theta);
                s[i][1] = s[i][1] * Math.cos(theta) + z * Math.sin(theta);
            }
        }
        
    }
    
    
    
    public void customRotate()
    {
        
    }
    
    /*
    This method is used for scaling, according to the given Z coordinate. 
    It returns the scale ratio to be used for drawing onto the screen.
    */
    public static double getZFactor(double zt)
    {
        return (DIST_FROM_EYE) / (DIST_FROM_EYE - zt);
    }
    
    
}

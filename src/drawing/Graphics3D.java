/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import computer3D.Plane2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import threedv2.GameScreen;

/**
 *
 * @author krishna
 */
public class Graphics3D{
    
    public Graphics2D g;
    public BufferedImage image;
    public WritableRaster rast;
    public double zFs[][] = new double[GameScreen.WIDTH][GameScreen.HEIGHT];
    
    
    //inner mechanisms of graphics
    public static int DIST_FROM_EYE = 2000;
    //public double trs[][][] = new double[12][3][2];
    
    
    public Graphics3D()
    {
        image = new BufferedImage(GameScreen.WIDTH, GameScreen.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D)image.getGraphics();
        rast = image.getRaster();
    }
    
    
    //to draw a 3D shape, after inculding the z factoring
    public void draw3D(Plane2D p)
    {
        //this function cannot draw concave shapes
        
        //the triangles
        double ts[][][] = getTriangles(p.pts);
        
        //correspond to the boundary box coordinates and measurements
        double xmin, xmax, ymin, ymax;
        
        for(int i = 0; i < ts.length; i ++)
        {
            //drawing each triangle, based on inequalities and boundary boxes
            xmax = xmin = ts[i][0][0];
            ymax = ymin = ts[i][0][1];
            
            
            //m and c are describe the line
            //when m = infinite, then the equation is classified as infinite type
            //e describes the greater / less / greater infinite type / less infinte type (1 / -1 / 4 / 2)
            double m[] = new double[3];
            double c[] = new double[3];
            int e[] = new int[3];
            
            
            
            // to get the boundary box;
            for(int j = 0; j < 3; j ++)
            {
                if(xmax < ts[i][j][0])
                {
                    xmax = ts[i][j][0];
                }
                if(xmin > ts[i][j][0])
                {
                    xmin = ts[i][j][0];
                }
                if(ymax < ts[i][j][1])
                {
                    ymax = ts[i][j][1];
                }
                if(ymin > ts[i][j][1])
                {
                    ymin = ts[i][j][1];
                }
                
                
                //to get line equations
                
                //the exception, x = c
                if(ts[i][(j + 1) % 3][0] == ts[i][j][0])
                {
                    if(ts[i][(j + 2) % 3][0] > ts[i][j][0])
                    {
                        e[j] = 4;
                    }else
                    {
                        e[j] = 2;
                    }
                    c[j] = ts[i][j][0];
                    continue;
                }
                
                
                
                //calculate slope and intercept
                m[j] = (ts[i][(j + 1) % 3][1] - ts[i][j][1])/(ts[i][(j + 1) % 3][0] - ts[i][j][0]);
                c[j] = -m[j]*ts[i][(j + 1) % 3][0] + ts[i][(j + 1) % 3][1];
                
                if(ts[i][(j + 2) % 3][1] > m[j] * ts[i][(j + 2) % 3][0] + c[j])
                {
                    e[j] = 1;
                }else
                {
                    e[j] = -1;
                }
                
//                System.out.println("x : " + ts[i][j][0] + " y : " + ts[i][j][1]);
//                System.out.println("m : " + m[j] + " c : " + c[j] + " e : " + e[j]);
                
                
            }
            
            
            
            
            
            
            //drawing begins
            
            xLoop: for(int x = (int)xmin; x < (int)xmax + 1; x ++)
            {
                yLoop: for(int y = (int)ymin; y < (int)ymax + 1; y ++)
                    {
                        
                        //to check and take care of the exception
                        for(int j = 0; j < 3; j ++)
                        {
                            if(e[j] > 1)
                            {
                                //to bring e[j] to either -1 or 1
                                e[j] -= 3;
                                
                                if(x * e[j] > c[j])
                                {
                                    if(y * e[(j + 1) % 3] > m[(j + 1) % 3] * x + c[(j + 2) % 3])
                                    {
                                        if(y * e[(j + 2) % 3] > m[(j + 2) % 3] * x + c[(j + 2) % 3])
                                        {
                                            rast.setPixel(x, y, new int[]{0, 255, 0});
                                        }
                                    }
                                }
                                continue yLoop;
                            }
                        }
                        
                        if(y * e[0] >= (m[0] * x + c[0]) * e[0])
                        {
                            if(y * e[1] >= (m[1] * x + c[1]) * e[1])
                            {
                                if(y * e[2] >= (m[2] * x + c[2]) * e[2])
                                {
                                    rast.setPixel(x, y, new int[]{0, 255, 0});
                                }
                            }
                        }
                        
                        
                    }
            }
            
        }
        
    }
    
    
    public void draw2D(Plane2D p)
    {
        //this function cannot draw concave shapes
        
        //the triangles
        double ts[][][] = getTriangles(p.pts);
        
        //correspond to the boundary box coordinates and measurements
        double xmin, xmax, ymin, ymax;
        
        for(int i = 0; i < ts.length; i ++)
        {
            //drawing each triangle, based on inequalities and boundary boxes
            xmax = xmin = ts[i][0][0];
            ymax = ymin = ts[i][0][1];
            
            
            //m and c are describe the line
            //when m = infinite, then the equation is classified as infinite type
            //e describes the greater / less / greater infinite type / less infinte type (1 / -1 / 4 / 2)
            double m[] = new double[3];
            double c[] = new double[3];
            int e[] = new int[3];
            
            
            
            // to get the boundary box;
            for(int j = 0; j < 3; j ++)
            {
                if(xmax < ts[i][j][0])
                {
                    xmax = ts[i][j][0];
                }
                if(xmin > ts[i][j][0])
                {
                    xmin = ts[i][j][0];
                }
                if(ymax < ts[i][j][1])
                {
                    ymax = ts[i][j][1];
                }
                if(ymin > ts[i][j][1])
                {
                    ymin = ts[i][j][1];
                }
                
                
                //to get line equations
                
                //the exception, x = c
                if(ts[i][(j + 1) % 3][0] == ts[i][j][0])
                {
                    if(ts[i][(j + 2) % 3][0] > ts[i][j][0])
                    {
                        e[j] = 4;
                    }else
                    {
                        e[j] = 2;
                    }
                    c[j] = ts[i][j][0];
                    continue;
                }
                
                
                
                //calculate slope and intercept
                m[j] = (ts[i][(j + 1) % 3][1] - ts[i][j][1])/(ts[i][(j + 1) % 3][0] - ts[i][j][0]);
                c[j] = -m[j]*ts[i][(j + 1) % 3][0] + ts[i][(j + 1) % 3][1];
                
                if(ts[i][(j + 2) % 3][1] > m[j] * ts[i][(j + 2) % 3][0] + c[j])
                {
                    e[j] = 1;
                }else
                {
                    e[j] = -1;
                }
                
//                System.out.println("x : " + ts[i][j][0] + " y : " + ts[i][j][1]);
//                System.out.println("m : " + m[j] + " c : " + c[j] + " e : " + e[j]);
                
                
            }
            
            
            
            
            
            
            //drawing begins
            
            xLoop: for(int x = (int)xmin; x < (int)xmax + 1; x ++)
            {
                yLoop: for(int y = (int)ymin; y < (int)ymax + 1; y ++)
                    {
                        
                        //to check and take care of the exception
                        for(int j = 0; j < 3; j ++)
                        {
                            if(e[j] > 1)
                            {
                                //to bring e[j] to either -1 or 1
                                e[j] -= 3;
                                
                                if(x * e[j] > c[j])
                                {
                                    if(y * e[(j + 1) % 3] > m[(j + 1) % 3] * x + c[(j + 2) % 3])
                                    {
                                        if(y * e[(j + 2) % 3] > m[(j + 2) % 3] * x + c[(j + 2) % 3])
                                        {
                                            rast.setPixel(x, y, new int[]{0, 255, 0});
                                        }
                                    }
                                }
                                continue yLoop;
                            }
                        }
                        
                        if(y * e[0] >= (m[0] * x + c[0]) * e[0])
                        {
                            if(y * e[1] >= (m[1] * x + c[1]) * e[1])
                            {
                                if(y * e[2] >= (m[2] * x + c[2]) * e[2])
                                {
                                    rast.setPixel(x, y, new int[]{0, 255, 0});
                                }
                            }
                        }
                        
                        
                    }
            }
            
        }
        
    }
    
    
    
    public double[][][] getTriangles(double pts[][])
    {
        //number of triangles given by (numPts - 2)
        int n = pts.length - 2;
        double ts[][][] = new double[n][3][2];
        
        //to store the triangles
        for(int i = 0; i < n; i ++)
        {
            ts[i][0] = pts[0];
            ts[i][1] = pts[i + 1];
            ts[i][2] = pts[i + 2];
        }
        
        return ts;
    }
    
    public static int getJavaCoord(double t, double zt)
    {
        return GameScreen.WIDTH / 2 + (int)(t * getZFactor(zt));
    }
    
    public static double getZFactor(double zt)
    {
        return (DIST_FROM_EYE) / (DIST_FROM_EYE - zt);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threedv2;

import computer3D.Plane2D;
import drawing.Graphics3D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author krishna
 */
public class TestScreen extends JPanel implements Runnable, KeyListener{

    //related to dimensions
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;
    public static final int SCALE = 2;
    
    //related to thread
    Thread thread;
    public static final int FPS = 50;
    public static final int REFRESH_TIME = 1000 / FPS;
    public boolean isRunning = false;
    private long currTime = 0;
    private long diff;
    boolean paused = false;
    
    //related to graphics
    public Graphics3D g3;
    
    
    
    
    //all tests
    Plane2D p;
    
    
    public TestScreen()
    {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        setVisible(true);
        requestFocus();
        
        init();
        
    }
    
    
    public void init()
    {
        g3 = new Graphics3D();
        
        p = new Plane2D(new double[][]{{20, 20}, {100, 20}, {150, 100}, {200, 100}});
    }
    
    
    public void update()
    {
        
        
    }
    
    public void render()
    {
        
        g3.draw2D(p);
        
    }
    
    public void testGraphDraw()
    {
        for(int i = 0; i < 50; i ++)
        {
            g3.g.setColor(Color.red);
            g3.g.drawLine(0, i * 10, 512, i * 10);
        }
        
        for(int i = 0; i < 50; i ++)
        {
            g3.g.drawLine(i * 10, 0, i * 10, 512);
        }
    }
    
    public void drawToScreen()
    {
        Graphics g2 = this.getGraphics();
        g2.drawImage(g3.image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }
    
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            isRunning = true;
            addKeyListener(this);
            thread.start();
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            while(isRunning)
            {
                currTime = System.currentTimeMillis();
                
                if(!paused)
                {
                    update();
                    render();
                    drawToScreen();
                }
                
                diff = System.currentTimeMillis() - currTime;
                if(diff < REFRESH_TIME)
                {
                    Thread.sleep(REFRESH_TIME - diff);
                }
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_P)
        {
            paused = true;
        }
        
        
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
        
        if(e.getKeyCode() == KeyEvent.VK_P)
        {
            paused = false;
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threedv2;

import computer3D.Transformer;
import drawing.Graphics3D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author krishna
 */
public class GameScreen extends JPanel implements Runnable, KeyListener{

    //related to dimensions
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;
    public static double SCALE = 1.5;
    
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
    Transformer cs;
    int s = 80;
    double cube[][] = 
    {{-s, s, s}, {s, s, s}, {s, s, -s}, {-s, s, -s}, {-s, -s, s}, {s, -s, s}, {s, -s, -s}, {-s, -s, -s}};
    
    double square[][] = 
    {{-s, s + 40, s}, {s, s + 40, s}, {s, s + 40, -s}, {-s, s + 40, -s}};
    public GameScreen()
    {
        setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
        setFocusable(true);
        setVisible(true);
        requestFocus();
        
        init();
    }
    
    
    public void init()
    {
        g3 = new Graphics3D();
        cs = new Transformer(g3.g);
    }
    
    
    public void update()
    {
        cs.rotateShape(cube, 3.14 / 200, Transformer.X_AXIS);
        cs.rotateShape(square, 3.14 / 200, Transformer.X_AXIS);
        cs.rotateShape(cube, 3.14 / 200, Transformer.Z_AXIS);
        cs.rotateShape(square, 3.14 / 200, Transformer.Z_AXIS);
        
    }
    
    public void render()
    {
        g3.g.setColor(Color.black);
        g3.g.fillRect(0, 0, WIDTH, HEIGHT);
        g3.g.setColor(Color.yellow);
        
        for(int i = 0; i < cube.length; i ++)
        {
            for(int j = i + 1; j < cube.length; j ++)
            {
                g3.g.setColor(new Color(0, 255 - (255 / cube.length) * i, (255 / cube.length) * i));
                if(!((i == 0 && j == 6) || (i == 1 && j == 7) || (i == 2 && j == 4) || (i == 3 && j == 5)))
                {
                    cs.drawLine(cube[i][0], cube[i][1], cube[i][2], cube[j][0], cube[j][1], cube[j][2]);
                }
                
            }
        }
        
        for(int i = 0; i < square.length; i ++)
        {
            for(int j = i + 1; j < square.length; j ++)
            {
                g3.g.setColor(Color.red);
                cs.drawLine(square[i][0], square[i][1], square[i][2], square[j][0], square[j][1], square[j][2]);
                
            }
        }
        
    }
    
    public void drawToScreen()
    {
        Graphics g2 = this.getGraphics();
        g2.drawImage(g3.image, 0, 0, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), null);
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
        
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            for(int i = 0; i < cube.length; i ++)
            {
                cube[i][2] += 3;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            for(int i = 0; i < cube.length; i ++)
            {
                cube[i][2] -= 3;
            }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threedv2;

import javax.swing.JFrame;

/**
 *
 * @author krishna
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        JFrame window = new JFrame("3D Version 2");
        window.setContentPane(new GameScreen());
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        
    }
    
}

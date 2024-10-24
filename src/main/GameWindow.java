package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gP) {
        JFrame jF = new JFrame();
        jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jF.add(gP);
        jF.setResizable(false);
        jF.pack();
        jF.setLocationRelativeTo(null);
        jF.setVisible(true);
        jF.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                System.out.println("not focus");
            }

        });
    }
}

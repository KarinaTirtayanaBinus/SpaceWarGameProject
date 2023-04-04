package main;

import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame() {
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Space War");
        this.setIconImage(new ImageIcon("res/logo/FrameLogo.png").getImage());

        this.add(new GamePanel());
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}

package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.event.*;

public class Main {
    public static void main(String[] args) {
        JFrame drframe = new JFrame();
        drframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container drcontent = drframe.getContentPane();
        drcontent.setLayout(new BorderLayout());

        DrawBoard drawBoard = new DrawBoard();
        drcontent.add(drawBoard, BorderLayout.CENTER);
        JPanel panel = new JPanel();

        JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 1);
        widthSlider.setMinorTickSpacing(1);
        widthSlider.setMajorTickSpacing(1);
        widthSlider.setPaintTicks(true);
        widthSlider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                drawBoard.change_width(widthSlider.getValue());
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawBoard.clear();
            }
        });
        JButton pickButton = new JButton("Pick a color");
        pickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawBoard.pickColor();
            }
        });

        panel.add(clearButton);
        panel.add(pickButton);
        panel.add(widthSlider);

        drcontent.add(panel, BorderLayout.SOUTH);

        drframe.setTitle("Paint with JAVA");
        drframe.setSize(1100, 600);
        drframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drframe.setLocationRelativeTo(null);
        drframe.setVisible(true);

    }
}

class DrawBoard extends JComponent {
    Image image;
    Graphics2D graphics;
    int curX, curY, oldX, oldY;

    public DrawBoard() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                curX = e.getX();
                curY = e.getY();
                if (graphics != null)
                    graphics.drawLine(oldX, oldY, curX, curY);
                repaint();
                oldX = curX;
                oldY = curY;
            }
        });
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D) image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        graphics.setPaint(Color.white);
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        graphics.setPaint(Color.black);
        repaint();
    }

    public void pickColor() {
        Color color = JColorChooser.showDialog(null, "Pick a color...I guess", Color.black);
        graphics.setPaint(color);
    }

    public void change_width(int w){
        graphics.setStroke(new BasicStroke(w));
    }
}

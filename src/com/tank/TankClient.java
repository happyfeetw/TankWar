package com.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;
    Tank myTank;
    {
        myTank = new Tank(50, 50);
    }
    Image offScreenImage = null;



    /**
     * 开启窗口，相当于游戏启动
     */
    public void launchFrame(){
        this.setLocation(400, 300);
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        // 使用匿名类实现窗口关闭事件监听
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 0 表示正常的退出
            }
        });
        // 禁止改变窗口大小
        this.setResizable(false);
        // 设置标题栏的文字
        setTitle("TankWar 1.0");
        setVisible(true);
        this.setBackground(Color.GRAY);
        // 启动线程让敌方坦克动起来
        new Thread(()->{
            while (true){
                TankClient.super.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        this.addKeyListener(new KeyMonitor());
    }

    // 画出圆形代替坦克
    @Override
    public void paint(Graphics g) {
        myTank.draw(g);
    }
    /**
     * 调用父类Container类的update方法
     * @param g
     */
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = this.createImage(800,600);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color color = gOffScreen.getColor();
        gOffScreen.setColor(Color.GRAY);
        gOffScreen.fillRect(0,0,800,600);
        gOffScreen.setColor(color);
        paint(gOffScreen);


        g.drawImage(offScreenImage,0,0,null);
    }

    public static void main(String[] args) {
        TankClient tankClient = new TankClient();
        tankClient.launchFrame();
    }

//    // 让坦克动起来
//    private class RepaintThread implements Runnable{
//
//        @Override
//        public void run() {
//            while(true){
//                com.tank.TankClient.super.repaint();
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }
    }
}

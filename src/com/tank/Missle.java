package com.tank;

import java.awt.*;

/**
 * 坦克的炮弹类
 */
public class Missle {
    private static final int HorizontalVelocity =30, VerticalVelocity = 30;

    int x, y;
    public static final int WIDTH = 5, HEIGHT = 5;
    Tank.Direction direction;

    public Missle(int x, int y, Tank.Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void draw(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,Missle.WIDTH,Missle.HEIGHT);
        g.setColor(color);
        move();
    }

    public void move(){
        switch (direction){
            case LEFT:
                x -= VerticalVelocity;
                break;
            case UPPER_LEFT:
                x -= VerticalVelocity;
                y -= HorizontalVelocity;
                break;
            case UP:
                y -= HorizontalVelocity;
                break;
            case UPPER_RIGHT:
                x += VerticalVelocity;
                y -= HorizontalVelocity;
                break;
            case RIGHT:
                x += VerticalVelocity;
                break;
            case DOWN_RIGHT:
                x += VerticalVelocity;
                y += HorizontalVelocity;
                break;
            case DOWN:
                y += HorizontalVelocity;
                break;
            case DOWN_LEFT:
                x -= VerticalVelocity;
                y += HorizontalVelocity;
                break;
        }
    }


}

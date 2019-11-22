package com.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 坦克实体类
 */
public class Tank {

    /**
     * 位置信息
     */
    int x, y, w, h;

    /**
     * 定义坦克移动的速度
     */
    private static final int HorizontalVelocity =20, VerticalVelocity = 20;
//    private static final double DiagonalVelocity = Math.sqrt(HorizontalVelocity);

    /**
     * 表示对应四个方向是否按下的状态成员
     */
    private boolean isLeft = false, isRight = false, isUp = false, isDown = false;

    /**
     * 八个方向+静止状态的枚举
     */
    enum Direction{LEFT, UPPER_LEFT, UP, UPPER_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, STOP}

    /**
     * 坦克的初始状态
     */
    private Direction dir = Direction.STOP;



    public Tank(int x,int y){
        this.x = x;
        this.y = y;
    }

//    public com.tank.Tank(int x, int y, int w, int h) {
//        this.x = x;
//        this.y = y;
//        this.w = w;
//        this.h = h;
//    }

    /**
     * 面向对象 将画坦克的方法定义在tank类里面
     * @param g
     */
    public void draw(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.RED);
        // 画圆 x,y代表圆形的外切正方形的相对于frame对象左上角（坐标远点）的坐标
        g.fillOval(x,y,30,30);
//        // 画扇形 startAngle代表起始边相对于水平位置的角度，arcAngle表示结束边相对于水平位置的角度（钝角）
//        g.fillArc(100,100,100,100,0,120);
        // 颜色还原
        g.setColor(color);

        // 坦克移动后更新坦克的位置
        move();
    }

    /**
     * 键盘控制坦克移动的方法
     * @param event
     */
    public void keyPressed(KeyEvent event){
        int key = event.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT :
                x -= HorizontalVelocity;
                isLeft = true;
                break;
            case KeyEvent.VK_UP :
                y -= VerticalVelocity;
                isUp = true;
                break;
            case KeyEvent.VK_RIGHT :
                x += HorizontalVelocity;
                isRight = true;
                break;
            case KeyEvent.VK_DOWN :
                y += VerticalVelocity;
                isDown = true;
                break;
        }
        // 定位方向
        locate();
    }

    /**
     * 移动的规则
     */
    public void move(){
        switch (dir){
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

    /**
     * 判断坦克的当前朝向（移动方向）
     */
    public void locate(){
        if (isLeft && !isUp && !isRight && !isDown) dir = Direction.LEFT;
        else if (isLeft && isUp && !isRight && !isDown) dir = Direction.UPPER_LEFT;
        else if (!isLeft && isUp && !isRight && !isDown) dir = Direction.UP;
        else if (!isLeft && isUp && isRight && !isDown) dir = Direction.UPPER_RIGHT;
        else if (!isLeft && !isUp && isRight && !isDown) dir = Direction.RIGHT;
        else if (!isLeft && !isUp && isRight && isDown) dir = Direction.DOWN_RIGHT;
        else if (!isLeft && !isUp && !isRight && isDown) dir = Direction.DOWN;
        else if (isLeft && !isUp && !isRight && isDown) dir = Direction.DOWN_LEFT;
        else if (!isLeft && !isUp && !isRight && !isDown) dir = Direction.STOP;
    }
}

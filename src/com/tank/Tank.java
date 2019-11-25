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
     * 坦克的高度和宽度
     */
    public static final int WIDTH = 30, HEIGHT = 30;

    /**
     * 表示对应四个方向是否按下的状态成员
     */
    private boolean isLeft = false, isRight = false, isUp = false, isDown = false;

    /**
     * 八个方向+静止状态的枚举
     */
    enum Direction{LEFT, UPPER_LEFT, UP, UPPER_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, STOP}

    /**
     * 开火方向
     */
    private Direction fireDirection = Direction.DOWN;

    /**
     * 坦克的初始状态
     */
    private Direction dir = Direction.STOP;

    public TankClient tc;

    /**
     * 坦克的构造方法
     * @param x
     * @param y
     */
    public Tank(int x,int y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param x
     * @param y
     * @param tc
     */
    public Tank(int x, int y, TankClient tc) {
        // 调用自身构造方法传入xy的值
        this(x, y);
        // 通过构造方法持有TankClient类的实例
        this.tc = tc;
    }

    /**
     * 面向对象 将画坦克的方法定义在tank类里面
     * @param g
     */
    public void draw(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.RED);
        // 画圆 x,y代表圆形的外切正方形的相对于frame对象左上角（坐标远点）的坐标
        g.fillOval(x,y,WIDTH,HEIGHT);
//        // 画扇形 startAngle代表起始边相对于水平位置的角度，arcAngle表示结束边相对于水平位置的角度（钝角）
//        g.fillArc(100,100,100,100,0,120);
        // 颜色还原
        g.setColor(color);
        // 确定开火方向
        switch (fireDirection){
            case LEFT:
                g.drawLine(x + Tank.WIDTH/2,  y + Tank.HEIGHT/2, x,y + Tank.HEIGHT/2);
                break;
            case UPPER_LEFT:
                g.drawLine(x + Tank.WIDTH/2,  y + Tank.HEIGHT/2, x, y);
                break;
            case UP:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
                break;
            case UPPER_RIGHT:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2 ,x + Tank.WIDTH, y);
                break;
            case RIGHT:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH,y+Tank.HEIGHT/2);
                break;
            case DOWN_RIGHT:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH,y + Tank.HEIGHT);
                break;
            case DOWN:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2,y+Tank.HEIGHT);
                break;
            case DOWN_LEFT:
                g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
                break;
        }
        // 坦克移动后更新坦克的位置
        move();
    }

    /**
     * 键盘按下 坦克的移动
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
            case KeyEvent.VK_F :
                tc.missle = fire();
                break;
        }
        // 定位方向
        locate();
    }

    /**
     * 键盘松开 坦克的移动
     * @param event
     */
    public void keyReleased(KeyEvent event){
        int key = event.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT :
//                x -= HorizontalVelocity;
                isLeft = false;
                break;
            case KeyEvent.VK_UP :
//                y -= VerticalVelocity;
                isUp = false;
                break;
            case KeyEvent.VK_RIGHT :
//                x += HorizontalVelocity;
                isRight = false;
                break;
            case KeyEvent.VK_DOWN :
//                y += VerticalVelocity;
                isDown = false;
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
            case STOP:
                break;
        }
        // 使炮筒方向随着移动方向改变
        if (this.dir != Direction.STOP){
            this.fireDirection = this.dir;
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

    public Missle fire(){
//        // 在坦克圆心发射炮弹的位置
          // todo 考虑实现在坦克朝向的边缘发射炮弹
        int x = this.x + Tank.WIDTH/2 - Missle.WIDTH;
        int y = this.y + Tank.HEIGHT/2 - Missle.HEIGHT;
        // 静止状态下坦克也能打出炮弹
        return new Missle(x, y, fireDirection);
    }
}

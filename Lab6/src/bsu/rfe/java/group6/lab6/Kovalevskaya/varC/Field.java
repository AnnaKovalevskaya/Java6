package bsu.rfe.java.group6.lab6.Kovalevskaya.varC;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Field extends JPanel {

    // ���� ������������������ ��������
    private boolean paused;
    private boolean paused1;
    private boolean resumeLol;


    // ������������ ������ �������� �����
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);


    // ������� �������
    private static  int arrayH=450;
    private static  int arrayW=450;

    // ������ ����������
    private Kirpichik[][] array_kirp= new Kirpichik[arrayH][arrayW];


    // ����� ������ �������� �� ���������� ��������� ������� ActionEvent
    // ��� �������� ��� ���������� ������������ ��������� �����,
    // ����������� ��������� ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            // ������ ����������� ������� ActionEvent - ����������� ����
            repaint();
        }
    });


    public static int getArrayH() {
        return arrayH;
    }

    public static int getArrayW() {
        return arrayW;
    }

    public Kirpichik[][] getArray_kirp() {
        return array_kirp;
    }

    public ArrayList<BouncingBall> getBalls() { return balls; }

    public void setBalls(ArrayList<BouncingBall> balls) { this.balls = balls; }

    // ������� ��������
    public void deletekirp(int x,int y)
    {
        array_kirp[x][y]=null;

    }


    // ����������� ������
    public Field() {
        // ���������� ���� ������� ����
        setBackground(Color.BLACK);
        // ��������� ������
        repaintTimer.start();
    }



    // �������������� �� JPanel ����� ����������� ����������
    public void paintComponent(Graphics g) {
        // ������� ������ ������, �������������� �� ������
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        // ��������������� ��������� ���������� �� ���� ����� �� ������
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }
        // ��������������� ������ ��� ���������
        for(int i=0;i<arrayH;i++)
            for(int j=0;j<arrayW;j++)
                if (array_kirp[i][j] != null)
                    array_kirp[i][j].paint(canvas);

    }

    // ����� ���������� ������ ���� � ������
    public void addBall() {
        //����������� � ���������� � ������ ������ ���������� BouncingBall
        // ��� ������������� ���������, ��������, �������, �����
        // BouncingBall ��������� ��� � ������������
        balls.add(new BouncingBall(this));

    }

    // ����� ���������� ������ ��������� � ������
    public void addKirp() {
        while(true) {
            Kirpichik kirp = new Kirpichik(this);

            if (array_kirp[kirp.getX()][kirp.getY()] == null) {
                array_kirp[kirp.getX()][kirp.getY()] = kirp;
                break;
            }
        }
    }
    public void delKirpichiky()
    {
        for(int i=0;i<arrayH;i++)
            for(int j=0;j<arrayW;j++)
                if(array_kirp[i][j]!=null)
                    array_kirp[i][j]=null;
    }


    public void pause() {
        // �������� ����� �����
        paused1 = true;
        paused = true;
        resumeLol = false;

    }

    // ����� ������������������, �.�. ������ ���� ����� �����
    // ������������ ���� ������
    public synchronized void resume() {
        // ��������� ����� �����
        paused = false;
        paused1 = false;
        // ����� ��� ��������� ����������� ������
        notifyAll();
    }



    // ����� ���������
    public synchronized void speed() {
        for(BouncingBall ball: balls)
        {
            ball.setSpeedX(ball.getSpeedX()+4);
            ball.setSpeedY(ball.getSpeedY()+4);
            ball.setX(ball.getX()+ball.getSpeedX());
            ball.setY(ball.getY()+ball.getSpeedY());
        }
    }

    // ������������������ ����� ��������, ����� �� ��� ���������
    // (�� ������� �� ����� �����?)
    public synchronized void canMove(BouncingBall ball) throws
            InterruptedException {

        if (paused) {
            if (ball.getRadius() < 10)
                wait();
        }
        if (paused1)
            if (resumeLol) {
                if (ball.getRadius() > 10)
                    wait();
            } else
                wait();

        // ���� ����� ����� �������, �� �����, ��������
        // ������ ������� ������, ��������
        //wait();
    }

}


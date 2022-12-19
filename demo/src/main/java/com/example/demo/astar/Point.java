package com.example.demo.astar;

/**
 * @author ptomjie
 * @since 2022-12-01 22:34
 */
public class Point {

    private final int y;

    private final int x;

    private final int score;

    public Point(int y, int x, int score) {
        this.y = y;
        this.x = x;
        this.score = score;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Point{" +
                "y=" + y +
                ", x=" + x +
                ", score=" + score +
                '}';
    }
}

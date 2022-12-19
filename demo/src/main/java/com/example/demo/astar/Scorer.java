package com.example.demo.astar;

/**
 * @author ptomjie
 * @since 2022-12-01 22:48
 */
public interface Scorer {

    double computeCost(Point from, Point to);
}

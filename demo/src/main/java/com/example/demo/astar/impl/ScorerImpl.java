package com.example.demo.astar.impl;

import com.example.demo.astar.Point;
import com.example.demo.astar.Scorer;

/**
 * @author ptomjie
 * @since 2022-12-01 22:48
 */
public class ScorerImpl implements Scorer {
    @Override
    public double computeCost(Point from, Point to) {
        return Math.abs(from.getY() - to.getY()) + Math.abs(from.getX() - to.getX());
    }

}

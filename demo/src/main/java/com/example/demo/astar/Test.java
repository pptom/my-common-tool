package com.example.demo.astar;

import com.example.demo.astar.impl.ScorerImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ptomjie
 * @since 2022-12-01 23:16
 */
public class Test {

    public static void main(String[] args) {
        int[][] maps = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }
        };
        Point[][] map = new Point[maps.length][maps[0].length];
        for (int y = 0; y < maps.length; y++) {
            for (int x = 0; x < maps[y].length; x++) {
                map[y][x] = new Point(y, x, 10);
            }
        }
        Grid grid = new Grid(map);
        for (int y = 0; y < maps.length; y++) {
            for (int x = 0; x < maps[y].length; x++) {
                if (maps[y][x] == 1) {
                    grid.markForbidden(y, x);
                }
            }
        }
        PathFinder pathFinder = new PathFinder(grid, new ScorerImpl(), new ScorerImpl());
        List<Point> path = pathFinder.findPath(grid.getNode(1, 1), grid.getNode(5, 5));
        for (int y = 0; y < maps.length; y++) {
            for (int x = 0; x < maps[y].length; x++) {
                if (path.contains(grid.getNode(y, x))) {
                    System.out.print("2 ");
                } else {
                    System.out.print(maps[y][x] + " ");
                }
            }
            System.out.println();
        }
        path.stream().map(n -> n.getY() + "," + n.getX()).collect(Collectors.toList()).forEach(System.out::println);
    }
}

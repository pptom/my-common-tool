package com.example.demo.astar;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ptomjie
 * @since 2022-12-01 22:53
 */
public class Grid {
    private final Point[][] map;

    private final int height;

    private final int width;

    private final Set<Point> forbiddenArea;

    public Grid(Point[][] map) {
        this.map = map;
        this.height = map.length;
        this.width = map[0].length;
        forbiddenArea = new HashSet<>();
    }

    public void markForbidden(int y, int x) {
        forbiddenArea.add(getNode(y, x));
    }

    public Point getNode(int y, int x) {
        return map[y][x];
    }

    /**
     * 获取该节点上下左右节点
     *
     * @param node 节点
     * @return 返回上下左右节点
     */
    public Set<Point> getAroundNode(Point node) {
        Set<Point> points = new HashSet<>();
        int y = node.getY();
        int x = node.getX();
        // 上
        if (isInArea(y - 1, x)) {
            points.add(map[y - 1][x]);
        }
        // 下
        if (isInArea(y + 1, x)) {
            points.add(map[y + 1][x]);
        }
        // 左
        if (isInArea(y, x - 1)) {
            points.add(map[y][x - 1]);
        }
        // 右
        if (isInArea(y, x + 1)) {
            points.add(map[y][x + 1]);
        }
        return points.stream().filter(this::isReachable).collect(Collectors.toSet());
    }

    public boolean isInArea(int y, int x) {
        return 0 <= y && y < height && 0 <= x && x < width;
    }

    public boolean isReachable(Point node) {
        return !forbiddenArea.contains(node);
    }
}

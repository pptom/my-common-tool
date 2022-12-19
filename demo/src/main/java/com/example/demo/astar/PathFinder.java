package com.example.demo.astar;

import java.util.*;

/**
 * @author ptomjie
 * @since 2022-12-01 23:04
 */
public class PathFinder {
    private final Grid grid;

    private final Scorer nextNodeScorer;

    private final Scorer targetScorer;

    public PathFinder(Grid grid, Scorer nextNodeScorer, Scorer targetScorer) {
        this.grid = grid;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    public List<Point> findPath(Point from, Point to) {
        Map<Point, PathNode> allNodes = new HashMap<>();
        Queue<PathNode> openSet = new PriorityQueue<>();
        PathNode start = new PathNode(from, null, 0D, targetScorer.computeCost(from, to));
        allNodes.put(from, start);
        openSet.add(start);
        while (!openSet.isEmpty()) {
            PathNode next = openSet.poll();
            if (next.getCurrent().equals(to)) {
                List<Point> path = new ArrayList<>();
                PathNode current = next;
                do {
                    path.add(0, current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);
                return path;
            }
            Set<Point> aroundNodeList = grid.getAroundNode(next.getCurrent());
            aroundNodeList.forEach(aroundNode -> {
                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), aroundNode);
                PathNode nextNode = allNodes.getOrDefault(aroundNode, new PathNode(aroundNode));
                allNodes.put(aroundNode, nextNode);
                if (nextNode.getRouteScore() > newScore) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(aroundNode, to));
                    openSet.add(nextNode);
                }
            });
        }
        return new ArrayList<>();
    }
}

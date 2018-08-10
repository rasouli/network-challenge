package com.github.rasouli.upworks.network;

import java.util.*;


public class Network {

    /**
     *     graph adjacent matrix represented by list
     */
    private List<LinkedList<Integer>> adjacentList;


    /**
     *
     * @param totalVertexCount total number of the vertex inside the graph
     * @throws Exception
     */
    public Network(int totalVertexCount) throws Exception {

        if (totalVertexCount <= 0) {
            throw new Exception("Expected Non-Negative vertex size for the graph");
        }

        adjacentList = new ArrayList<>();

        // initialize the adjacent list
        for (int curIndex = 1; curIndex <= totalVertexCount + 1; curIndex++) {
            adjacentList.add(new LinkedList<Integer>());
        }
    }

    /**
     * put an edge between two vertexes.
     * change of the order does not create new edge. also cycles are supported (edge where begin and end is the same vertex)
     * @param vertexFrom from vertex
     * @param vertexTo to vertex
     * @throws Exception
     */
    public void connect(int vertexFrom, int vertexTo) throws Exception {
        checkVertexValidity(vertexFrom, vertexTo);

        if (!edgeExists(vertexFrom, vertexTo)) {
            if (vertexFrom != vertexTo) {
                adjacentList.get(vertexFrom).add(vertexTo);
                adjacentList.get(vertexTo).add(vertexFrom);
            } else {
                // handle cycle
                adjacentList.get(vertexFrom).add(vertexTo);
            }
        }


    }

    /**
     * check if edge exists between two vertex
     * @param fromVertex
     * @param toVertex
     * @return
     */
    private boolean edgeExists(int fromVertex, int toVertex) {
        return adjacentList.get(fromVertex).contains(toVertex);
    }

    /**
     * query that a path exists between two given vertex.
     * this implementation uses BFS algorithm with a Queue.
     * @param vertexFrom
     * @param vertexTo
     * @return
     * @throws Exception
     */
    public boolean query(int vertexFrom, int vertexTo) throws Exception {

        checkVertexValidity(vertexFrom, vertexTo);

        Queue<Integer> bfsQueue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        bfsQueue.addAll(adjacentList.get(vertexFrom));
        visited.add(vertexFrom);

        while (!bfsQueue.isEmpty()) {

            int currentVertex = bfsQueue.remove();
            if (currentVertex == vertexTo) {
                bfsQueue.clear();
                visited.clear();
                return true;
            } else {
                visited.add(currentVertex);
                for (Integer adjacentVertex : adjacentList.get(currentVertex)) {
                    if (!visited.contains(adjacentVertex)) {
                        bfsQueue.add(adjacentVertex);
                    }
                }
            }
        }

        return false;
    }

    /**
     * for a given list of vertexes, check vertex label value, to be between the defined range and non-negative and non zero.
     * @param vertexes
     * @throws Exception
     */
    private void checkVertexValidity(int... vertexes) throws Exception {
        for (int vertex : vertexes) {
            if (!vertexValueIsWithinRange(vertex)) {
                throw new Exception(String.format("Vertex value %d is not acceptable!", vertex));
            }
        }
    }

    private boolean vertexValueIsWithinRange(int vertex) {
        return vertex > 0 && vertex < adjacentList.size();
    }


}

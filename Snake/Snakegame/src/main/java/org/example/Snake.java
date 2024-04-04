package org.example;

import java.util.LinkedList;
import java.util.List;

public class Snake {
    private List<int[]> body;
    private int[] head;
    private char direction;

    public Snake(int startX, int startY) {
        body = new LinkedList<>();
        head = new int[] { startX, startY };
        body.add(head);
        direction = 'R'; // Start moving right
    }

    public void move() {
        int newX = head[0];
        int newY = head[1];

        switch (direction) {
            case 'U':
                newY--;
                break;
            case 'D':
                newY++;
                break;
            case 'L':
                newX--;
                break;
            case 'R':
                newX++;
                break;
        }

        head = new int[] { newX, newY };
        body.add(0, head);
        body.remove(body.size() - 1);
    }

    public void grow() {
        body.add(body.get(body.size() - 1));
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int[] getHead() {
        return head;
    }

    public List<int[]> getBody() {
        return body;
    }
}

package org.example;

public class GameBoard {
    private final int width;
    private final int height;
    private char[][] board;

    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[height][width];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    board[i][j] = '#'; // Wall
                } else {
                    board[i][j] = ' '; // Empty space
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public char getBoardValue(int x, int y) {
        return board[y][x];
    }

    public void setBoardValue(int x, int y, char value) {
        board[y][x] = value;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

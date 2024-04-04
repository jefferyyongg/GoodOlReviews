package org.example;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.Random;

public class Game {
    private GameBoard board;
    private Snake snake;
    private int foodX;
    private int foodY;

    public Game(int width, int height) {
        board = new GameBoard(width, height);
        snake = new Snake(width / 2, height / 2);
        placeFood();
    }

    private void placeFood() {
        Random random = new Random();
        foodX = random.nextInt(board.getWidth() - 2) + 1;
        foodY = random.nextInt(board.getHeight() - 2) + 1;
        board.setBoardValue(foodX, foodY, '*');
    }

    private void updateSnakePosition() {
        snake.move();
        for (int[] part : snake.getBody()) {
            board.setBoardValue(part[0], part[1], 'O');
        }
    }

    private void checkFoodCollision() {
        if (snake.getHead()[0] == foodX && snake.getHead()[1] == foodY) {
            snake.grow();
            placeFood();
        }
    }

    private void checkWallCollision() {
        int x = snake.getHead()[0];
        int y = snake.getHead()[1];
        if (board.getBoardValue(x, y) == '#') {
            System.out.println("Game Over!");
            System.exit(0);
        }
    }

    private void checkSelfCollision() {
        int[] head = snake.getHead();
        for (int i = 1; i < snake.getBody().size(); i++) {
            int[] part = snake.getBody().get(i);
            if (head[0] == part[0] && head[1] == part[1]) {
                System.out.println("Game Over!");
                System.exit(0);
            }
        }
    }

    private void updateGame() {
        updateSnakePosition(); // Update the snake's position on the board
        checkFoodCollision();  // Check if the snake has eaten food
        checkWallCollision();  // Check if the snake has hit a wall
        checkSelfCollision();  // Check if the snake has collided with itself
    }

    public void start() {
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.terminal();
            terminal.enterRawMode(); // Enter raw mode to read character by character
            System.out.println("Use WASD keys to control the snake. Press 'q' to quit.");

            boolean running = true;

            while (running) {
                board.initializeBoard();
                updateGame();
                board.printBoard();

                if (terminal.reader().ready()) { // Check if a key is pressed
                    int inputChar = terminal.reader().read();

                    switch (inputChar) {
                        case 'w':
                        case 'W':
                            snake.setDirection('U');
                            break;
                        case 'a':
                        case 'A':
                            snake.setDirection('L');
                            break;
                        case 's':
                        case 'S':
                            snake.setDirection('D');
                            break;
                        case 'd':
                        case 'D':
                            snake.setDirection('R');
                            break;
                        case 'q':
                        case 'Q':
                            running = false; // Quit game
                            break;
                        default:
                            // Ignore other keys
                            break;
                    }
                }
                // Simple game loop delay, can be improved
                try {
                    Thread.sleep(200); // Sleep for 200 milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (terminal != null) {
                try {
                    terminal.close(); // Restore terminal to its original state
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game(20, 20);
        game.start();
    }
}

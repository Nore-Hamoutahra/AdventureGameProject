package com.hamoutahra.adventuregame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AdventureGame {
    private char[][] map;
    private int x, y;

    public AdventureGame(String mapFilePath) throws IOException {
        readMap(mapFilePath);
    }

    private void readMap(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        map = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }
    }

    public void processMovements(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        validateMovementsFile(lines);
        setStartPosition(lines.get(0));
        executeMovements(lines.get(1));
    }

    private void validateMovementsFile(List<String> lines) {
        if (lines.isEmpty() || !lines.get(0).contains(",")) {
            throw new IllegalArgumentException("Invalid movements file format");
        }
    }

    private void setStartPosition(String startPosition) {
        String[] positions = startPosition.split(",");
        x = Integer.parseInt(positions[0]);
        y = Integer.parseInt(positions[1]);

        if (isOutOfMapBounds(x, y) || isStartPositionInvalid(x, y)) {
            throw new IllegalArgumentException("Starting position is out of map bounds or invalid");
        }
    }

    private boolean isOutOfMapBounds(int x, int y) {
        return x < 0 || y < 0 || y >= map.length || x >= map[y].length;
    }

    private boolean isStartPositionInvalid(int x, int y) {
        return map[y][x] == '#';
    }

    private void executeMovements(String movements) {
        for (char move : movements.toCharArray()) {
            processSingleMovement(move);
        }
    }

    private void processSingleMovement(char move) {
        switch (move) {
            case 'N': if (y > 0 && map[y - 1][x] == ' ') y--; break;
            case 'S': if (y < map.length - 1 && map[y + 1][x] == ' ') y++; break;
            case 'E': if (x < map[0].length - 1 && map[y][x + 1] == ' ') x++; break;
            case 'O': if (x > 0 && map[y][x - 1] == ' ') x--; break;
            default: throw new IllegalArgumentException("Invalid movement command: " + move);
        }
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public static void main(String[] args) {
        try {
            AdventureGame game = new AdventureGame("src/main/resources/map.txt");
            game.processMovements("src/main/resources/movements.txt");
            System.out.println("Final position: (" + game.x + "," + game.y + ")");
        } catch (IOException e) {
            System.err.println("Error while reading the files" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Processing error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

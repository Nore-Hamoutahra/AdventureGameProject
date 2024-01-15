package com.hamoutahra.adventuregame;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import static org.junit.jupiter.api.Assertions.*;

class AdventureGameTest {

    private AdventureGame game;

    @BeforeEach
    void setUp() throws IOException {
        game = new AdventureGame("src/test/resources/map.txt");
    }

    @Test
    void testMovementToExpectedPosition() throws IOException {
        game.processMovements("src/test/resources/movements.txt");
        assertArrayEquals(new int[]{9, 2}, game.getPosition());
    }

    @Test
    void testExceptionWhenMapFileNotFound() {
        assertThrows(NoSuchFileException.class, () -> new AdventureGame("src/test/resources/invalidMap.txt"));
    }

    @Test
    void testExceptionWhenMovementsFileNotFound() {
        assertThrows(NoSuchFileException.class, () -> game.processMovements("src/test/resources/invalidMovementsName.txt"));
    }

    @Test
    void testExceptionOnInvalidMovementFormat() {
        assertThrows(IllegalArgumentException.class, () -> game.processMovements("src/test/resources/invalidMovements.txt"));
    }

    @Test
    void testInvalidStartPosition() {
        assertThrows(IllegalArgumentException.class, () -> game.processMovements("src/test/resources/invalidStartPosition.txt"));
    }

    @Test
    void testInvalidMovementsFileFormat() {
        assertThrows(IllegalArgumentException.class, () -> game.processMovements("src/test/resources/invalidMovementsFormat.txt"));
    }
}

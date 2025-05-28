package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.awt.*;
import java.util.*;

public class AppTest {
    private Game game;
    private Player player;

    @BeforeEach
    void setup() {
        game = new Game();
        player = new Player();
        player.symbol = 'X';
    }

    @Test
    void gameStartsWithEmptyBoard() {
        assertAll(
                () -> assertEquals(State.PLAYING, game.state),
                () -> assertEquals(9, game.board.length)
        );
    }

    @Test
    void newPlayerHasDefaultValues() {
        Player p = new Player();
        assertAll(
                () -> assertEquals('\u0000', p.symbol),
                () -> assertEquals(0, p.move),
                () -> assertFalse(p.selected),
                () -> assertFalse(p.win)
        );
    }

    @Test
    void detectPlayingState() {
        char[] board = {'X',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void detectDrawState() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void detectRowWin() {
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void detectColumnWin() {
        char[] board = {'O',' ',' ','O',' ',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void detectDiagonalWin() {
        char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void getAllMovesOnEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void getAvailableMovesOnPartialBoard() {
        char[] board = {'X','O',' ',' ','X','O','X',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(Arrays.asList(2, 3, 7, 8), moves);
    }

    @Test
    void evaluateDrawPosition() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        player.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, player));
    }


    @Test
    void aiReturnsValidMove() {
        player.symbol = 'X';
        int move = game.MiniMax(game.board, player);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void cellStoresPositionCorrectly() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertAll(
                () -> assertEquals(5, cell.getNum()),
                () -> assertEquals(2, cell.getCol()),
                () -> assertEquals(1, cell.getRow())
        );
    }

    @Test
    void markingCellDisablesIt() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertAll(
                () -> assertEquals('X', cell.getMarker()),
                () -> assertFalse(cell.isEnabled())
        );
    }

    @Test
    void panelHasCorrectStructure() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    void clickOnCellMarksIt() {
        System.setProperty("java.awt.headless", "true");
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        cell.doClick();
        assertNotEquals(' ', cell.getMarker());
    }
}
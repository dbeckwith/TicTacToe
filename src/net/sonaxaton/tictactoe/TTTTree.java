package net.sonaxaton.tictactoe;

/**
 *
 * @author Daniel Beckwith <sonaxaton.net>
 */
public class TTTTree {

    public static class Node {

        Node[] subNodes;

        public Node() {
            subNodes = new Node[TicTacToe.SIZE];
        }
    }

    public static Node play(TicTacToe game) {
        if (game.hasWinner()) {
            return null;
        }
        Node n = new Node();
        for (int i = 0; i < TicTacToe.BOARD_AREA; i++) {
            TicTacToe g = new TicTacToe(game);
            g.move(i % TicTacToe.SIZE, i / TicTacToe.SIZE);
            n.subNodes[i] = play(g);
        }
        return n;
    }

}

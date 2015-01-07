package net.sonaxaton.tictactoe;

import processing.core.PConstants;
import processing.core.PGraphics;

/**
 *
 * @author Daniel Beckwith <sonaxaton.net>
 */
public class TicTacToe {

    public final static int SIZE = 3;
    public final static int BOARD_AREA = SIZE * SIZE;

    public enum Player {

        NONE,
        X,
        O;
    }

    private final Player[] board;
    private final int win_num;
    private Player curr_player;
    private int last_move_x, last_move_y;
    private int num_moves;

    public TicTacToe() {
        this.win_num = SIZE;
        board = new Player[BOARD_AREA];
        for (int i = 0; i < board.length; i++) {
            board[i] = Player.NONE;
        }
        curr_player = Player.X;
        last_move_x = -1;
        last_move_y = -1;
        num_moves = 0;
    }

    public TicTacToe(TicTacToe copy) {
        this.win_num = copy.win_num;
        this.board = copy.board.clone();
        this.curr_player = copy.curr_player;
        this.last_move_x = copy.last_move_x;
        this.last_move_y = copy.last_move_y;
        this.num_moves = copy.num_moves;
    }

    public boolean validMove(int x, int y) {
        return get(x, y) == Player.NONE;
//        switch (curr_player) {
//            case X:
//                int xx = x,
//                 yy = y;
//
//                boolean found = false;
//
//                if (!found) {
//                    search:
//                    for (int x1 = 0; x1 < SIZE; x1++) {
//                        for (int y1 = 0; y1 < SIZE; y1++) { // find an o
//                            if (get(x1, y1) == Player.O) {
//                                for (int dx = 1; dx >= -1; dx--) {
//                                    for (int dy = 1; dy >= -1; dy--) {
//                                        if (!(dx == 0 && dy == 0)) {
//                                            if (get(x1 + dx, y1 + dy) == Player.O && get(x1 + dx * 2, y1 + dy * 2) == Player.NONE) {
//                                                xx = x1 + dx * 2;
//                                                yy = y1 + dy * 2;
//                                                found = true;
//                                                break search;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (!found) {
//                    search:
//                    for (int x1 = 0; x1 < SIZE; x1++) {
//                        for (int y1 = 0; y1 < SIZE; y1++) { // find an x
//                            if (get(x1, y1) == Player.X) {
//                                for (int dx = 1; dx >= -1; dx--) {
//                                    for (int dy = 1; dy >= -1; dy--) {
//                                        if (!(dx == 0 && dy == 0)) {
//                                            if (get(x1 + dx, y1 + dy) == Player.X && get(x1 + dx * 2, y1 + dy * 2) == Player.NONE) {
//                                                xx = x1 + dx * 2;
//                                                yy = y1 + dy * 2;
//                                                found = true;
//                                                break search;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (!found) {
//                    search:
//                    for (int x1 = 0; x1 < SIZE; x1++) {
//                        for (int y1 = 0; y1 < SIZE; y1++) { // find an x
//                            if (get(x1, y1) == Player.X) {
//                                for (int i = 1; i < SIZE; i++) {
//                                    for (int dx = 1; dx >= -1; dx--) {
//                                        for (int dy = 1; dy >= -1; dy--) {
//                                            if (!(dx == 0 && dy == 0)) {
//                                                if (get(x1 + dx * i, y1 + dy * i) == Player.NONE) {
//                                                    xx = x1 + dx * i;
//                                                    yy = y1 + dy * i;
//                                                    break search;
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                return get(x, y) == Player.NONE && x == xx && y == yy;
//            case O:
//                return get(x, y) == Player.NONE;
//            default:
//                return false;
//        }
    }

    public void move(int x, int y) {
        board[x + y * SIZE] = curr_player;
        last_move_x = x;
        last_move_y = y;
        num_moves++;
        switch (curr_player) {
            case X:
                curr_player = Player.O;
                break;
            case O:
                curr_player = Player.X;
                break;
            default:
                break;
        }
    }

    public int getNumMoves() {
        return num_moves;
    }

    public Player get(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return null;
        }
        return board[x + y * SIZE];
    }

    public Player getWinner() {
        int x = last_move_x;
        int y = last_move_y;
        Player player = get(x, y);
        if (player != Player.NONE) {
            //System.out.println(player);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (!(dx == 0 && dy == 0)) {
                        boolean all_in_a_row = true;
                        for (int i = 1; i < win_num; i++) {
                            //System.out.format("i: %d dx: % d dy: % d x: %d y: %d %s %s%n", i, dx, dy, x, y, get(x + i * dx, y + i * dy), player);
                            if (get(x + i * dx, y + i * dy) != player) {
                                all_in_a_row = false;
                                break;
                            }
                        }
                        if (all_in_a_row) {
                            return player;
                        }
                    }
                }
            }

        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (get(i, j) == Player.NONE) {
                    return null;
                }
            }
        }
        return Player.NONE;
    }

    public boolean hasWinner() {
        return getWinner() != null;
    }

//    private PGraphics getImg(Player p, int size) {
//        PGraphics g = imgs.get(p).get(size);
//        if (g != null) {
//            return g;
//        }
//        else {
//            g = app.createGraphics(size, size);
//            g.beginDraw();
//            g.colorMode(PConstants.HSB, 360, 100, 100, 100);
//
//            g.scale(size / SIZE, size / SIZE);
//
//            g.endDraw();
//            imgs.get(p).put(size, g);
//            return g;
//        }
//    }
    public void draw(PGraphics g, float x, float y, float w, float h, float fade) {
        g.pushStyle();
        g.pushMatrix();
        g.translate(x, y);
        g.noStroke();
        g.scale(w, h);
        Player winner = getWinner();
        if (winner == Player.X) {
            g.fill(0, 20, 100);
        }
        else if (winner == Player.O) {
            g.fill(240, 20, 100);
        }
        else if (winner == Player.NONE) {
            g.fill(120, 20, 100);
        }
        else {
            g.fill(0, 0, 100);
        }
        g.rect(0, 0, 1, 1);

        g.scale(1f / SIZE, 1f / SIZE);
        float s = 0.05f;
        float b = 0.01f;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                g.pushMatrix();
                g.pushStyle();
                g.translate(i, j);

                switch (get(i, j)) {
                    case X:
                        g.noStroke();
                        g.fill(0, 100, 100);
                        g.beginShape();
                        g.vertex(0, 0);
                        g.vertex(0.05f, 0);
                        g.vertex(0.5f, 0.5f - s);
                        g.vertex(1 - s, 0);
                        g.vertex(1, 0);
                        g.vertex(1, s);
                        g.vertex(0.5f + s, 0.5f);
                        g.vertex(1, 1 - s);
                        g.vertex(1, 1);
                        g.vertex(1 - s, 1);
                        g.vertex(0.5f, 0.5f + s);
                        g.vertex(s, 1);
                        g.vertex(0, 1);
                        g.vertex(0, 1 - s);
                        g.vertex(0.5f - s, 0.5f);
                        g.vertex(0, s);
                        g.endShape(PConstants.CLOSE);
                        break;
                    case O:
                        g.stroke(240, 100, 100);
                        g.strokeWeight(s);
                        g.noFill();
                        g.ellipseMode(PConstants.CENTER);
                        g.ellipse(0.5f, 0.5f, 1 - s, 1 - s);
                        break;
                    default:
                        break;
                }

                //g.fill(0, 0, 100, 100 * (1f - fade));
                //g.rect(0, 0, 1, 1);

                g.noStroke();
                g.fill(0);
                g.rect(0, 0, b, 1);
                g.rect(0, 0, 1, b);
                g.rect(0, 1, 1, -b);
                g.rect(1, 0, -b, 1);

                g.popStyle();
                g.popMatrix();
            }
        }

        g.popMatrix();
        g.popStyle();
    }

    public void print() {
        for (int y = 0; y < SIZE; y++) {
            if (y != 0) {
                for (int x = 0; x < SIZE; x++) {
                    if (x != 0) {
                        System.out.print("+");
                    }
                    System.out.print("---");
                }
                System.out.println();
            }
            for (int x = 0; x < SIZE; x++) {
                System.out.print(" ");
                if (get(x, y) != Player.NONE) {
                    System.out.print(get(x, y));
                }
                else {
                    System.out.print(" ");
                }
                System.out.print(" ");
                if (x != SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }

}

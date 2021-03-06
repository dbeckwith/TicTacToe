package net.sonaxaton.tictactoe;

import processing.core.PApplet;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.map;
import static processing.core.PConstants.HSB;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.event.MouseEvent;

/**
 *
 * @author Daniel Beckwith <sonaxaton.net>
 */
public class Main extends PApplet {

    // TODO: 3D version? each cell stacks up as blocks
    // TODO: version that does best move each time
    // TODO: maintain list of used board objects, or just generate all at beginning
    float zoom, zoom_fact;
    PVector pan;
    float size;
    boolean show_ends_only;

    @Override
    public void setup() {
        size(displayWidth, displayHeight);
        smooth(8);
        colorMode(HSB, 360, 100, 100, 100);

        size = min(width, height);

        zoom = 0;
        zoom_fact = 1;
        pan = new PVector(0, 0);

        show_ends_only = false;
    }

    @Override
    public void draw() {
        pushMatrix();
        pushStyle();
        background(0, 0, 15);
        // translate by pan and scale by zoom_fact
        drawTTT(g, width / 2 + zoom_fact * (pan.x - size / 2), height / 2 + zoom_fact * (pan.y - size / 2), size * zoom_fact, size * zoom_fact, 20,
                new TicTacToe(), show_ends_only);
//        noStroke();
//        fill(0, 100, 100);
//        ellipse(pan.x, pan.y, 10, 10);
//        scale(100f / size);
//        stroke(0);
//        fill(0, 0, 100);
//        rect(0, 0, size, size);
//        fill(120, 100, 100, 20);
//        rect(size / 2 + (-pan.x - width / 2) / zoom_fact, size / 2 + (-pan.y - height / 2) / zoom_fact, width / zoom_fact, height / zoom_fact);
        popMatrix();
        popStyle();

        // framerate in corner
        noStroke();
        fill(0, 0, 0, 80);
        rect(0, height, textWidth("" + round(frameRate)) + 2, -14);
        fill(0, 0, 100);
        text(round(frameRate), 0, height - 2);
    }

    /**
     * Draws the Tic Tac Tow board recursively.
     *
     * @param g the image to draw on
     * @param x the starting x position of the board
     * @param y the starting y position of the board
     * @param w the starting width of the board
     * @param h the starting height of the board
     * @param min_size the minimum size to render tiles at
     * @param game the current game state
     * @param only_ends whether to only show winning boards or not
     *
     * @return whether a border should be drawn around the board that was just drawn
     */
    public boolean drawTTT(PGraphics g, float x, float y, float w, float h, float min_size, TicTacToe game, boolean only_ends) {
        if (only_ends) { // fixed min_size if showing only winning boards
            min_size = 3;
        }
        // if the size is too small or the board is outside the visible window, don't draw anything
        if (w < min_size || h < min_size || (x < -w || x >= width + w || y < -h || y >= height + h)) {
            return false;
        }
        boolean winner = game.hasWinner();
        if (!only_ends || (only_ends && winner)) {
            // draw the game board
            game.draw(g, x, y, w, h, constrain(map(w, min_size * 1.5f, min_size, 1, 0), 0, 1));
        }
        if (winner) {
            // don't need a border
            return false;
        }
        // make all possible next moves
        for (int i = 0; i < TicTacToe.SIZE; i++) {
            for (int j = 0; j < TicTacToe.SIZE; j++) {
                if (game.validMove(i, j)) { // if they're valid of course
                    TicTacToe t = new TicTacToe(game); // copy the current game
                    t.move(i, j); // make the next move
                    // draw the next smaller board in the right position
                    if (drawTTT(g, x + i * w / TicTacToe.SIZE, y + j * h / TicTacToe.SIZE, w / TicTacToe.SIZE, h / TicTacToe.SIZE, min_size, t, only_ends)) {
                        // if it returned true, draw a thick border around it to separate it from the others
                        g.pushStyle();
                        g.stroke(0);
                        g.strokeWeight(2 * pow(3, -game.getNumMoves()) * zoom_fact);
                        g.noFill();
                        g.rect(x + i * w / TicTacToe.SIZE, y + j * h / TicTacToe.SIZE, w / TicTacToe.SIZE, h / TicTacToe.SIZE);
                        g.popStyle();
                    }
                }
                // if the move wasn't valid, a large X or O will be left behind in that spot, marking where the previous move was
            }
        }
        return true;
    }

    @Override
    public void mouseDragged() {
        // distance the mouse moved since last frame
        PVector d = new PVector(mouseX - pmouseX, mouseY - pmouseY);
        // divide by zoom factor so always panning the same speed
        d.div(zoom_fact);
        pan.add(d);
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        // zooming
        zoom += -event.getCount() * 0.05f;
        zoom_fact = pow(3, zoom);
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            show_ends_only = !show_ends_only;
        }
    }

    @Override
    public boolean sketchFullScreen() {
        return true;
    }

    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

}

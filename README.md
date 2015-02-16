# TicTacToe
Graphical representation of all possible Tic Tac Toe games.

Made using [Processing](https://processing.org/).

Description
-----------
To trace a particular set of moves, move to a section of the graph corresponding to the square you make your move in.

For example, the center ninth of the board contains all possible boards where the first move was in the center square.
The center of that square shows just an X since no other moves can be made in the center. The top left ninth of this
center square contains two filled in squares, the top left and the center, and seven other squares that continue on
with the game.

Once a player wins or the game is a draw, the graph stops.

Overall the picture represents a graphical tree representation of all possible Tic Tac Toe games.

The full graph would technically be `3 ^ 9 = 19683` boards wide, so to improve rendering speeds not all boards are drawn
at once. Zoom in to see more of them.

Controls
--------
Zoom in with the mouse wheel, click and drag to pan. Press <kbd>spacebar</kbd> to toggle between showing all boards and only winning boards.

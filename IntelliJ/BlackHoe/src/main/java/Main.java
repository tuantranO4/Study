
import enums.Direction;
import gamestate.Board;

public class Main {
    public static void main(String[] args) {

        GameFrame gui = new GameFrame();
        Board boardtemp = new Board(5);
        //debug
        boardtemp.toString();
        boardtemp.traverse(1, 0, Direction.RIGHT);
        boardtemp.traverse(2, 4, Direction.LEFT);
        boardtemp.traverse(2, 0, Direction.UP);
    }
}

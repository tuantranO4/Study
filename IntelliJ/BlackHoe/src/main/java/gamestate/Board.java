package gamestate;

import enums.BoardStatus; //static member btw
import enums.Direction;
import enums.PlayerTurn;

public class Board {
    private final int boardSize;
    private BoardStatus bs;
    private Direction dir;
    private Field[][] board;
    private Cell[][] cell;
    private Player player1;
    private Player player2;
    private PlayerTurn turn;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        board = new Field[this.boardSize][this.boardSize];
        cell = new Cell[this.boardSize][this.boardSize];
        this.player1 = new Player();
        this.player2 = new Player();
        this.turn= PlayerTurn.TURN_1;

        for (int i = 0; i < this.boardSize; ++i) {
            for (int j = 0; j < this.boardSize; ++j) {
                board[i][j] = new Field(); //making clicking color, UI thing
                cell[i][j] = new Cell(i, j, BoardStatus.EMPTY); //populate
            }
        }
        initCellData(boardSize);
        
    }//make board

    public void initCellData(int boardSize){ //populate array
        int bh=(boardSize-1)/2;
        int bsz=boardSize;

        for (int i = 0; i < bsz; i++) {
            for (int j = 0; j < bsz; j++) {
                if( i>0 && i<bsz &&j==0 ){
                    cell[i][j].setStatus(BoardStatus.SHIP_1);
                }else if(j==bsz-1 && i>=0 && i<(bsz-1)){
                    cell[i][j].setStatus(BoardStatus.SHIP_2);
                }else if (cell[i][j].getX() == bh && cell[i][j].getY() == bh) {
                    cell[i][j].setStatus(BoardStatus.BLACKHOLE);
                } else {
                    cell[i][j].setStatus(bs = BoardStatus.EMPTY);
                }
            }
        }
    }

    public void swapState(int x1, int y1, int x2, int y2) {
        //within bound if()
        if (x1 >= 0 && x1 < boardSize && y1 >= 0 && y1 < boardSize && x2 >= 0 && x2 < boardSize && y2 >= 0 && y2 < boardSize) {
            BoardStatus temp = cell[x1][y1].getStatus();
            cell[x1][y1].setStatus(cell[x2][y2].getStatus());
            cell[x2][y2].setStatus(temp);
        } else {
            throw new IllegalArgumentException("Invalid coords.");
        }
    }

    public void traverse(int x, int y, Direction dir){
        if (cell[x][y].getStatus() == BoardStatus.EMPTY || cell[x][y].getStatus() == BoardStatus.BLACKHOLE) {
            System.out.println("Invalid, pls select a ship cell.");
            return;
        }

        if (turn == PlayerTurn.TURN_1 && cell[x][y].getStatus() != BoardStatus.SHIP_1) {
            System.out.println("Player 1 can only move SHIP_1");
            return;
        } else if (turn == PlayerTurn.TURN_2 && cell[x][y].getStatus() != BoardStatus.SHIP_2) {
            System.out.println("Player 2 can only move SHIP_2");
            return; 
        }

        Direction direction = dir;
        boolean validMoveMade = false;  // BoolFlag
        switch (direction) {
            case UP -> {
                for(int i = x-1; i >= 0; i--){
                    if(cell[i][y].getStatus() == BoardStatus.BLACKHOLE){
                        if (turn == PlayerTurn.TURN_1 && cell[x][y].getStatus()==BoardStatus.SHIP_1)
                            player1.increaseScore();
                        else if (turn == PlayerTurn.TURN_2 && cell[x][y].getStatus()==BoardStatus.SHIP_2)
                            player2.increaseScore();
                        cell[x][y].setStatus(BoardStatus.EMPTY);
                        validMoveMade = true;
                        break;
                    } else if(cell[i][y].getStatus() == BoardStatus.SHIP_1 || cell[i][y].getStatus() == BoardStatus.SHIP_2){
                        swapState(x, y, i+1, y);
                        validMoveMade = true;
                        break;
                    } else if(i == 0 && cell[i][y].getStatus() == BoardStatus.EMPTY){
                        swapState(x, y, i, y);
                        validMoveMade = true;
                        break;
                    }
                }
            }
            case DOWN -> {
                for(int i = x + 1; i < boardSize; i++){
                    if(cell[i][y].getStatus() == BoardStatus.BLACKHOLE){
                        if (turn == PlayerTurn.TURN_1 && cell[x][y].getStatus()==BoardStatus.SHIP_1)
                            player1.increaseScore();
                        else if (turn == PlayerTurn.TURN_2 && cell[x][y].getStatus()==BoardStatus.SHIP_2)
                            player2.increaseScore();
                        cell[x][y].setStatus(BoardStatus.EMPTY);
                        validMoveMade = true;
                        break;
                    } else if(cell[i][y].getStatus() == BoardStatus.SHIP_1 || cell[i][y].getStatus() == BoardStatus.SHIP_2){
                        swapState(x, y, i-1, y);
                        validMoveMade = true;
                        break;
                    } else if(i == boardSize - 1 && cell[i][y].getStatus() == BoardStatus.EMPTY){
                        swapState(x, y, i, y);
                        validMoveMade = true;
                        break;
                    }
                }
            }
            case LEFT -> {
                for (int j = y - 1; j >= 0; j--){
                    if(cell[x][j].getStatus() == BoardStatus.BLACKHOLE){
                        if (turn == PlayerTurn.TURN_1 && cell[x][y].getStatus()==BoardStatus.SHIP_1)
                            player1.increaseScore();
                        else if (turn == PlayerTurn.TURN_2 && cell[x][y].getStatus()==BoardStatus.SHIP_2)
                            player2.increaseScore();
                        validMoveMade = true;
                        cell[x][y].setStatus(BoardStatus.EMPTY);
                        break;
                    } else if(cell[x][j].getStatus() == BoardStatus.SHIP_1 || cell[x][j].getStatus() == BoardStatus.SHIP_2){
                        swapState(x, y, x, j+1);
                        validMoveMade = true;
                        break;
                    } else if(j == 0 && cell[x][j].getStatus() == BoardStatus.EMPTY){
                        swapState(x, y, x, j);
                        validMoveMade = true;
                        break;
                    }
                }
            }
            case RIGHT -> {
                for (int j = y + 1; j < boardSize; j++){
                    if(cell[x][j].getStatus() == BoardStatus.BLACKHOLE){
                        if (turn == PlayerTurn.TURN_1 && cell[x][y].getStatus()==BoardStatus.SHIP_1)
                            player1.increaseScore();
                        else if (turn == PlayerTurn.TURN_2 && cell[x][y].getStatus()==BoardStatus.SHIP_2)
                            player2.increaseScore();
                        validMoveMade = true;
                        cell[x][y].setStatus(BoardStatus.EMPTY);
                        break;
                    } else if(cell[x][j].getStatus() == BoardStatus.SHIP_1 || cell[x][j].getStatus() == BoardStatus.SHIP_2){
                        swapState(x, y, x, j-1);
                        validMoveMade = true;
                        break;
                    } else if(j == boardSize - 1 && cell[x][j].getStatus() == BoardStatus.EMPTY){
                        swapState(x, y, x, j);
                        validMoveMade = true;
                        break;
                    }
                }
            }
            default -> throw new AssertionError("Invalid direction: " + direction);
        }
        
        // Interchange turns
        if (validMoveMade) {
            interchangeTurn();
        }
    }
    
    public PlayerTurn getTurn(){
        return this.turn;
    }

    public Cell getCell(int x, int y) {
        return this.cell[x][y];
    }
    
    public int getBoardSize() {
        return boardSize;
    }

    public void interchangeTurn() {
        if (turn == PlayerTurn.TURN_1) {
            turn = PlayerTurn.TURN_2;
        } else {
            turn = PlayerTurn.TURN_1;
        }
    }

    public boolean isOver() {
        if (player1.getScore()==((boardSize-1)/2) || player2.getScore()==((boardSize-1)/2)){
            return true;
        }
        return false;
    }

    public String winnerAnnouncer(){
        if (player1.getScore()==((boardSize-1)/2)){
            return("P1 gewonnen!");
        }else if(player2.getScore()==((boardSize-1)/2)){
            return("P2 gewonnen!");
        }
        return null;
    }



    //debug
    @Override
public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("Current Turn: ");
    if (turn == PlayerTurn.TURN_1) {
        builder.append("Player 1 (SHIP_1)");
    } else {
        builder.append("Player 2 (SHIP_2)");
    }
    builder.append("\n");

    builder.append("Scores: Player 1 = ").append(player1.getScore())
           .append(", Player 2 = ").append(player2.getScore()).append("\n\n");

    builder.append("+");
    for (int j = 0; j < boardSize; j++) {
        builder.append("--+");
    }
    builder.append("\n");

    for (int i = 0; i < boardSize; i++) {
        builder.append("|");
        for (int j = 0; j < boardSize; j++) {
            builder.append(" ");
            BoardStatus status = cell[i][j].getStatus();
            if (status == BoardStatus.SHIP_1) {
                builder.append("1");
            } else if (status == BoardStatus.SHIP_2) {
                builder.append("2");
            } else {
                builder.append(status.toString().substring(0, 1));
            }
            builder.append("|");
        }
        builder.append("\n");

        builder.append("+");
        for (int j = 0; j < boardSize; j++) {
            builder.append("--+");
        }
        builder.append("\n");
    }

    return builder.toString();
}
    
}

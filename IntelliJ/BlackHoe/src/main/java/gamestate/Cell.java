package gamestate;
import enums.BoardStatus;

public class Cell {
    private BoardStatus bs;
    private int x;
    private int y;

    public Cell(int x, int y, BoardStatus status) {
        this.x = x;
        this.y = y;
        this.bs = status;
    }
    
    public void setX(int val){
        this.x=val;
    };

    public int getX(){
        return this.x;
    };

    public void setY(int val){
        this.y=val;
    };

    public int getY(){
        return this.y;
    };

    public BoardStatus getStatus(){
        return this.bs;
    };

    public void setStatus(BoardStatus st){
        this.bs=st;
    };

}

package theater;
import theater.seating.Seat;
import theater.seating.SeatType;
public class TheaterSeating {
    private Seat[][] seats;
    private int giftsTotal;
    
    public Seat[][] getSeats(){
        return this.seats; //*shrugs* idk why
    }
    public TheaterSeating(int rowCount, int colCount) {
    if (rowCount <= 0 || colCount <= 0) {
        throw new IllegalArgumentException("TheaterSeating struct: (row,col <=0).");
    }
    this.seats = new Seat[rowCount][colCount];
    }
    
    private void initSeating(int rowCount, int colCount){

    }

    public Seat bookSeat(){
        return seats[1][2];
    }

    public Seat bookSeat(int row, int col){
        return this.seats[row][col];
    }

    public Seat bookTailoredEmptySeat(SeatType st, boolean b){
        return seats[1][2]; 
    }
    
    public int totalTakenGifts(){
        return 0;
    }
    
    public void decreaseGifts(){

    }

    @Override
    public String toString(){
        return "asdasd";
    }
}

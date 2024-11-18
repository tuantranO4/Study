package theater;
import theater.seating.Seat;
import theater.seating.SeatType;
public class TheaterSeating {
    private Seat[][] seats;
    private int giftsTotal;
    
    public Seat[][] getSeats(){
        return this.seats; //*shrugs* idk why
    }
    
    public TheaterSeating(int row, int col) throws IllegalArgumentException {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("TheaterSeating struct: (row,col <=0).");
        }
        this.seats = new Seat[row][col];
        initSeating(row,col);
    }
    
    private void initSeating(int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String id ="Row "+i+"Col "+j;
                boolean hasgift=(i+j)%2==1;
                SeatType st;
                if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                    st = SeatType.OT; 
                } else if (j == seats[i].length / 2) {
                    st = SeatType.MT; 
                } else {
                    st = SeatType.IT; 
                }
                seats[i][j] = new Seat(id, hasgift, st);
                if(hasgift==true){
                    giftsTotal++;
                }
            }
        }
    }
    

    public int getAmountOfGifts(){
        return giftsTotal;
    }

    public Seat bookSeat(){
        //gonna use Seat here instead of Seat[][] since we work with INDIVIDUAL element not entirety of the array
        Seat closestAvailable=null; //lol almost forgot to initialise null 
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) { //we dont use global var so we need to iterate dynamically with .length
                if (seats[i][j].getIsOccupied()==false){
                    closestAvailable=seats[i][j];
                    closestAvailable.setIsOccupied(true);
                    return closestAvailable;
                }
            } 
        }
        return null;
    }

    public Seat bookSeat(int row, int col) throws IllegalArgumentException{
        if(row<=0 || col <=0 || row>=seats.length || col>=seats.length){
            throw new IllegalArgumentException("row col book seat wrong.");
        }
        Seat bookingProcess=seats[row][col];

        if (bookingProcess.getIsOccupied()==true){
            throw new IllegalArgumentException("booked.");
        }
        bookingProcess.setIsOccupied(false);
        return bookingProcess;
    }

    public Seat bookTailoredEmptySeat(SeatType st, boolean bitchassLikedLeft) {
        //Middle
        if (st == SeatType.MT) { 
            if (bitchassLikedLeft) {
                for (int i = 0; i < seats.length; i++) {
                    int middleIndex = seats[i].length / 2; //return row length
                    if (seats[i][middleIndex] != null && !seats[i][middleIndex].getIsOccupied()) {
                        seats[i][middleIndex].setIsOccupied(true);
                        return seats[i][middleIndex];
                    }
                }
            } else {
                for (int i = seats.length - 1; i >= 0; i--) { //zero base indexing btw
                    int middleIndex = seats[i].length / 2;
                    if (seats[i][middleIndex] != null && !seats[i][middleIndex].getIsOccupied()) {
                        seats[i][middleIndex].setIsOccupied(true);
                        return seats[i][middleIndex];
                    }
                }
            }
        }
        //Outer
        if (st==SeatType.OT){
            if(bitchassLikedLeft){
                for (int i = 0; i < seats.length; i++) {
                    for (int j = 0; j < seats.length; j++){
                        if(i==0 || i==seats.length-1||j==seats[i].length-1||j==0){
                            if (seats[i][j]!= null && !seats[i][j].getIsOccupied()) {
                                seats[i][j].setIsOccupied(true);
                                return seats[i][j];
                            }
                        } 
                    }
                }
            }else {
                for (int i = seats.length - 1; i >= 0; i--) {
                    for (int j = seats[i].length-1; j >=0; j--){
                        if(i==0 || i==seats.length-1||j==seats[i].length-1||j==0){
                            if (seats[i][j]!= null && !seats[i][j].getIsOccupied()) {
                                seats[i][j].setIsOccupied(true);
                                return seats[i][j];
                            }
                        } 
                    }
                }
            }
        }
        //Inner
        if (st==SeatType.IT){
            if(bitchassLikedLeft){
                for (int i = 1; i < seats.length-2; i++) {
                    int middleIndex = seats[i].length / 2; //lmao 5:2=2.5->3
                    for (int j = 1; j < seats[i].length-2; j++){
                        if(j!=middleIndex){
                            if (seats[i][j]!= null && !seats[i][j].getIsOccupied()) {
                                seats[i][j].setIsOccupied(true);
                                return seats[i][j];
                            }
                        } 
                    }
                }
            }else {
                for (int i = seats.length - 2; i >= 1; i--) {
                    int middleIndex = seats[i].length / 2;
                    for (int j = seats[i].length-2; j >=1; j--){
                        if(j!=middleIndex){
                            if (seats[i][j]!= null && !seats[i][j].getIsOccupied()) {
                                seats[i][j].setIsOccupied(true);
                                return seats[i][j];
                            }
                        } 
                    }
                }
            }
        }
        return null; 
    }    
    
    public int totalTakenGifts(){
        int remainGifts = 0;
        return remainGifts;
    }
    
    public void decreaseGifts(){
        if (giftsTotal>0){
            giftsTotal--;
        }
    }

    @Override
    public String toString(){
        StringBuilder SeatingPrinter = new StringBuilder();//HOLY MOTHERFUCKING SHIT STRINGBUILDER ASKJBDSBFKJQEDUFACIASDAWOIDKJASDASSDJKAKOD
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) { 
                if (seats[i][j].getIsOccupied()) {
                    SeatingPrinter.append("[B] ");
                } else {
                    SeatingPrinter.append("[A] "); 
                }
            }
            SeatingPrinter.append("\n"); 
        }
    
        return SeatingPrinter.toString(); 
    }
}

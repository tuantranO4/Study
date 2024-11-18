package theater.person;
import java.util.Random;
import theater.TheaterSeating;
import theater.seating.Seat;
import theater.seating.SeatType;

public class Spectator {
    private Seat seat;
    private final String name;

    public Seat getSeat(){
        return this.seat;
    }
    public String getName(){
        return this.name;
    }

    public Spectator(String nameval){
        if(nameval==null){
            throw new IllegalArgumentException("Empty name Spectator.java");
        }
        this.name=nameval;
    }
    
    public boolean takeGift() {
        if (seat != null && seat.getHasGift()) {
        return true; 
        }
        return false;
    }

    public void bookAnySeat(TheaterSeating ts) {
    Seat bookedSeat = ts.bookSeat();
    if (bookedSeat != null) {
        this.seat = bookedSeat;
    } else {
        throw new IllegalStateException("Null bookAnySeat spectator.java");
    }
    }

    public void bookSpecificSeat(TheaterSeating ts, int row, int col){
        try {
            Seat bookedSeat = ts.bookSeat(row, col);
            this.seat = bookedSeat;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("bookSpecificSeat Spectator.java Null");
        }
    }

    public void bookTailoredSeat(TheaterSeating ts){
        
        Random random = new Random();
        boolean randomBoolean = random.nextBoolean(); //just for funsies
        if(name.length()%2==0&&name.length()>=3){
            Seat evenNameSeat = ts.bookTailoredEmptySeat(SeatType.IT,true);
        }else if(name.length()<3){
            Seat ThreeletterNameSeat = ts.bookTailoredEmptySeat(SeatType.OT,randomBoolean);
        }else{
            Seat NormieFucksLMAOKillYourself=ts.bookTailoredEmptySeat(SeatType.MT,randomBoolean);
        }
    }
}

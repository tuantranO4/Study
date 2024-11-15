package theater.person;
import theater.TheaterSeating;
import theater.seating.Seat;

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
        this.name=nameval;
    }
    
    public boolean takeGift(){
        return true;
    }

    public void bookAnySeat(TheaterSeating ts){
        
    }

    public void bookSpecificSeat(TheaterSeating ts, int row, int col){
        
    }

    public void bookTailoredSeat(TheaterSeating ts){
        
    }
}

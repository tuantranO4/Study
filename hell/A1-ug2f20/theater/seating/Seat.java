package theater.seating;

public class Seat {
    private final String id;
    private boolean hasGift;
    private final SeatType seatType;
    private boolean isOccupied;

    public String getId(){
        return this.id;
    }
    public void setHasGift(boolean val){
        this.hasGift=val;
    }

    public boolean getHasGift(){
        return this.hasGift;
    }

    public SeatType getSeatType(){
        return this.seatType;
    }

    public boolean getIsOccupied(){
        return this.isOccupied;
    }
    public void setIsOccupied(boolean val2){
        this.isOccupied=val2;
    }
    
    public Seat(String id, boolean hasGift, SeatType seatType){
        this.id = id;
        this.hasGift = hasGift;
        this.seatType=seatType;
    }
}

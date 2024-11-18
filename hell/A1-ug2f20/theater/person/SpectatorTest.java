package theater.person;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import theater.TheaterSeating;
import theater.seating.Seat;
import theater.seating.SeatType;

import static org.junit.jupiter.api.Assertions.*;
import theater.seating.Seat;
import theater.seating.SeatType;

public class SpectatorTest {
    private static final int ROW_COUNT=4;
    private static final int COL_COUNT=5;


    @ParameterizedTest
    @CsvSource({

    })
    public void testTakeGift(boolean hasGift, String name){

    }

    @Test
    public void testConstructorWithNullName(){
        
    }

    @Test
    public void testBookAnySeat(){
        TheaterSeating ts = new TheaterSeating(ROW_COUNT, COL_COUNT);
        Spectator ss = new Spectator("Blanc");
        ss.bookAnySeat(ts);
        Seat bookAny = ss.getSeat();
        assertNotNull(bookAny);
        assertEquals(true, bookAny.getIsOccupied(),"kys");//expect, actual, msg
    }

    @ParameterizedTest
    @CsvSource({
        "0,0",
        "0,4",
        "3,0",
        "3,4"
    })
    public void testBookSpecificSeat(int bookedRow, int bookedCol){
        TheaterSeating ts = new TheaterSeating(ROW_COUNT, COL_COUNT);
        Spectator ss = new Spectator("Blanc");
        ss.bookSpecificSeat(ts, bookedRow, bookedCol);
        Seat bookSpecific = ss.getSeat();
        assertNotNull(bookSpecific);
        assertEquals(true, bookSpecific.getIsOccupied(),"occupy");
    }

    
    @ParameterizedTest
    @CsvSource({
        
    })
    public void testBookTailoredSeat(String name, SeatType expected){
        
    }
    
    @Test
    public void testGetName(){
        
    }
    
    @Test
    public void testGetSeatInitialState(){
        
    }
}

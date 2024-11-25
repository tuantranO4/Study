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


    @Test
    public void testConstructorWithNullName(){
        String nullName = null;
        assertThrows(IllegalArgumentException.class, () -> new Spectator(nullName));
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

    @Test
    public void testBookSpecificSeat(){
        TheaterSeating ts = new TheaterSeating(ROW_COUNT, COL_COUNT);
        Spectator ss = new Spectator("Blanc");
        ss.bookSpecificSeat(ts, 0, 0);
        Seat bookSpecific = ss.getSeat();
        
        assertNotNull(bookSpecific);
        assertEquals(true, bookSpecific.getIsOccupied(),"all corners"); //forgot to set to true in the bookSpecificSeat lmao
    }

    
    @ParameterizedTest
    @CsvSource({
        "Roxy, IT",
        "Al, OT"
    })
    public void testBookTailoredSeat(String name, SeatType expected){
        TheaterSeating ts1 = new TheaterSeating(ROW_COUNT, COL_COUNT);
        Spectator ss1 = new Spectator(name);
        ss1.bookTailoredSeat(ts1);
        Seat bookTailored = ss1.getSeat();
        assertNotNull(bookTailored);
        assertEquals(expected, bookTailored.getSeatType(),"seat type wrong: "+ name);
    }
    
    @Test
    public void testGetName(){
        String expectedName = "RTX 4060";
        Spectator ss = new Spectator(expectedName);

        String actualName = ss.getName();

        assertEquals(expectedName, actualName, "Spectator's name should match the provided name");
    }
    
    @Test
    public void testGetSeatInitialState(){
        Spectator ss = new Spectator("Eblan");
        assertEquals(null, ss.getSeat(),"null");
    }
}

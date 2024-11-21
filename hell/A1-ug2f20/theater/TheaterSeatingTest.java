package theater;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import theater.seating.Seat;
import theater.seating.SeatType;

public class TheaterSeatingTest {
    @Test
    public void testInitialization() {
        int row = 5;
        int col = 5;
        TheaterSeating theaterSeating = new TheaterSeating(row, col);
        Seat[][] seatsGet = theaterSeating.getSeats();
        assertEquals(row, seatsGet.length, "Row count issue");
        assertEquals(col, seatsGet[0].length, "Column count issue");

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == 0 || i == row - 1 || j == 0 || j == col - 1) {
                    assertEquals(SeatType.OT, seatsGet[i][j].getSeatType(), "Outer seat test");
                } else if (j == col-1 / 2) {
                    assertEquals(SeatType.MT, seatsGet[i][j].getSeatType(), "Middle seat test");
                } else {
                    assertEquals(SeatType.IT, seatsGet[i][j].getSeatType(), "Inner seat test");
                }
            }
        }
    }
    
    @ParameterizedTest
    @CsvSource({
        "12, 5, 5",   
        "18, 6, 6",
        "40, 9, 9"     
    })
    public void testGiftsInitialization(int expectedGifts, int rows, int cols) {
    TheaterSeating ts = new TheaterSeating(rows, cols);
    assertEquals(expectedGifts, ts.getAmountOfGifts(), "Gifts init test.");
    }
    
    @Test
    public void testBookSeat() {
        int row = 5;
        int col = 5;
        TheaterSeating theaterSeating = new TheaterSeating(row, col);
        Seat[][] seatsGet = theaterSeating.getSeats(); 
        Seat bookedSeat = theaterSeating.bookSeat();

        assertNotNull(bookedSeat, "null");
        assertEquals(seatsGet[0][0], bookedSeat, "First seat booking");
        //assertEquals(null, seatsGet[0][0]);
        assertEquals(true,seatsGet[0][0].getIsOccupied(), "First seat occupied");
    }

    @Test
    public void testBookTailoredEmptySeat(){
        int row = 5;
        int col = 5;
        TheaterSeating theaterSeating = new TheaterSeating(row, col);
        Seat[][] seatsGet = theaterSeating.getSeats(); 
        
        Seat OTSeat = theaterSeating.bookTailoredEmptySeat(SeatType.OT, true);
        Seat OTSeat2 = theaterSeating.bookTailoredEmptySeat(SeatType.OT, false);
        Seat MTSeat = theaterSeating.bookTailoredEmptySeat(SeatType.MT, true);
        Seat MTSeat2 = theaterSeating.bookTailoredEmptySeat(SeatType.MT, false);
        Seat bookedTailorSeat = theaterSeating.bookTailoredEmptySeat(SeatType.IT, true);
        Seat bookedTailorSeat2 = theaterSeating.bookTailoredEmptySeat(SeatType.IT, false); 
        assertNotNull(bookedTailorSeat, "null");
        assertEquals(seatsGet[0][0], OTSeat); 
        assertEquals(seatsGet[4][4], OTSeat2); 
        assertEquals(seatsGet[0][2], MTSeat); 
        assertEquals(seatsGet[4][2], MTSeat2); 
        assertEquals(seatsGet[1][1], bookedTailorSeat); //(1,1) for first IT
        assertEquals(seatsGet[1][1].getIsOccupied(), bookedTailorSeat.getIsOccupied()); //should be occupied
        assertEquals(seatsGet[3][3], bookedTailorSeat2); //(3,3) for 2nd IT. IT'S 3AM WTF AM I DOING HOLY SHITTTTTTTTTTTTTTTTTT
    }


@Test
public void testText() {
    int rows = 5;
    int cols = 5;


    TheaterSeating theaterSeating = new TheaterSeating(rows, cols);
    theaterSeating.getSeats()[0][0].setIsOccupied(true); 
    theaterSeating.getSeats()[1][1].setIsOccupied(true); 
    theaterSeating.getSeats()[1][2].setIsOccupied(true); 
    theaterSeating.getSeats()[4][1].setIsOccupied(true);

    String actual = theaterSeating.toString();

    String expected=
        "[B] [A] [A] [A] [A] \n" +
        "[A] [B] [B] [A] [A] \n" +
        "[A] [A] [A] [A] [A] \n" +
        "[A] [A] [A] [A] [A] \n" +
        "[A] [B] [A] [A] [A] \n";

    assertEquals(expected, actual, "Kill yourself lmao");
}
}


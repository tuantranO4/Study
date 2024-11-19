package theater.person;
import theater.TheaterSeating;
import theater.seating.Seat;
//be sure to compile OUTSIDE the folder (in the A1-UG2F20) since idk cannot find symbol error
public class Debug {
    public static void main(String[] args) {
        TheaterSeating ts = new TheaterSeating(4, 5);
        Spectator ss = new Spectator("Blanc");
        ss.bookSpecificSeat(ts, 3, 4);
        Seat bookSpecific = ss.getSeat();
        System.out.println(bookSpecific);

        TheaterSeating ts2 = new TheaterSeating(4, 5);
        Spectator ss2 = new Spectator("RTXRaytracing");
        ss2.bookTailoredSeat(ts2);
        System.out.println(ss2.getName());
        System.out.println(ss2.getName().length());
        Seat bookTailored = ss2.getSeat();

    }
}

import static check.CheckThat.*;
import static check.CheckThat.Condition.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.MethodOrderer.*;
import check.*;

import theater.TheaterSeating;
import theater.seating.Seat;
import theater.seating.SeatType;

@TestMethodOrder(OrderAnnotation.class)
public class SpectatorTestStructureTest {
    @BeforeAll
    public static void init() {
        CheckThat.theClass("theater.person.SpectatorTest")
                 .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
                 ;
    }

    @Test @DisabledIf(notApplicable) @Order(1_00)
    public void fieldROW_COUNT() {
        it.hasField("ROW_COUNT: int")
        	.withInitialValue(4)
            .thatIs(USABLE_WITHOUT_INSTANCE, NOT_MODIFIABLE, VISIBLE_TO_NONE)
            .thatHasNo(GETTER, SETTER);
    }

    @Test @DisabledIf(notApplicable) @Order(1_01)
    public void fieldCOL_COUNT() {
    	it.hasField("COL_COUNT: int")
    	  .withInitialValue(5)
    	  .thatIs(USABLE_WITHOUT_INSTANCE, NOT_MODIFIABLE, VISIBLE_TO_NONE)
    	  .thatHasNo(GETTER, SETTER);
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodTestTakeGift() {
        it.hasMethod("testTakeGift", withParams("name: String", "hasGift: boolean"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatHasAnnotation("ParameterizedTest", withCases("true", "false"))
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_00)
    public void methodTestConstructor() {
    	it.hasMethod("testConstructorWithNullName", withNoParams())
    	.thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
    	.thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_01)
    public void methodTestBookAnySeat() {
        it.hasMethod("testBookAnySeat", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_02)
    public void methodTestBookSpecificSeat() {
        it.hasMethod("testBookSpecificSeat", withParams("bookedRow: int", "bookedCol: int"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatHasAnnotation("ParameterizedTest", withCases("all corners", "one arbitrary seat"))
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_03)
    public void methodTestTailoredSeat() {
        it.hasMethod("testBookTailoredSeat", withParams("name: String", "expected: theater.seating.SeatType"))
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatHasAnnotation("ParameterizedTest", withCases("Roxy", "Bob", "Al"))
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_04)
    public void methodTestGetName() {
        it.hasMethod("testGetName", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

    @Test @DisabledIf(notApplicable) @Order(3_05)
    public void methodTestGetSeatInitialState() {
        it.hasMethod("testGetSeatInitialState", withNoParams())
          .thatIs(FULLY_IMPLEMENTED, INSTANCE_LEVEL, VISIBLE_TO_ALL)
          .thatReturnsNothing();
    }

}


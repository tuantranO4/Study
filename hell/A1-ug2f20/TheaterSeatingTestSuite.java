import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import theater.TheaterSeatingTest;
import theater.person.SpectatorTest;

@SelectClasses({
    TheaterSeatingTestSuite.StructuralTests.class,
    TheaterSeatingTestSuite.FunctionalTests.class,
})
@Suite public class TheaterSeatingTestSuite {
    @SelectClasses({
        SeatTypeStructureTest.class,
        SeatStructureTest.class,

        TheaterSeatingStructureTest.class,
        TheaterSeatingTestStructureTest.class,

        SpectatorStructureTest.class,
        SpectatorTestStructureTest.class,
    })
    @Suite public static class StructuralTests {}

    @SelectClasses({
        SpectatorTest.class,
        TheaterSeatingTest.class,
    })
    @Suite public static class FunctionalTests {}
}


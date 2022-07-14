package de.msg.game.takegame.player;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.internal.Numbers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.msg.io.Writer;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class ComputerPlayerTest {


    private ComputerPlayer objectUnderTest;


    private Writer writer;

    @BeforeEach
    void beforeEach() {
        writer = mock(Writer.class);
        objectUnderTest = new ComputerPlayer(writer);
    }

    @DisplayName("Bin gespannt")
    @ParameterizedTest(name = "doTurn Lauf Nr. {index} Given: {0} Stones When: doTurn called Then: return {1}")
    @CsvSource({"20,3", "21,1", "22,1", "23,2"})
    void doTurn_divisionsRest0_returns3(int input, int expected) {
        assertEquals(expected, objectUnderTest.doTurn(input));
        verify(writer,times(1)).write(String.format("Computer nimmt %s Steine.",expected));

    }


    // Achtung keine @BeforeEach vor den einzelnen Tests nur vor gesamten Methodenaufruf
    @TestFactory
    Stream<DynamicTest> dynamicTestForStream() {
        return Stream.of(
                new Param(20,3),
                new Param(21,1),
                new Param(22,1),
                new Param(23,2))

        .map(param->DynamicTest.dynamicTest("doTurn: " + param, createTestImpl(param)));
    }



    private Executable createTestImpl(Param param)  {
        return ()->{
            beforeEach();
            assertEquals(param.getExcpected(), objectUnderTest.doTurn(param.getInput()));
            verify(writer,times(1)).write(String.format("Computer nimmt %s Steine.",param.getExcpected()));
        };
    }

    @Test
    void doTurn_divisionsRest0_returns3() {
        assertEquals(3, objectUnderTest.doTurn(20));
        verify(writer,times(1)).write("Computer nimmt 3 Steine.");
    }

    @RepeatedTest(3)
    void dummyTest() {
        System.out.println("Dummy");
    }

    @Test
    void doTurn_divisionsRest1_returns1() {
        assertEquals(1, objectUnderTest.doTurn(21));
        verify(writer,times(1)).write("Computer nimmt 1 Steine.");
    }

    @Test
    void doTurn_divisionsRest2_returns1() {
        assertEquals(1, objectUnderTest.doTurn(22));
        verify(writer,times(1)).write("Computer nimmt 1 Steine.");
    }

    @Test
    void doTurn_divisionsRest3_returns2() {
        assertEquals(2, objectUnderTest.doTurn(23));
        verify(writer,times(1)).write("Computer nimmt 2 Steine.");
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE}) // six numbers
    void isOdd_ShouldReturnTrueForOddNumbers(int number) {
        assertTrue(Math.abs(number % 2) == 1);
    }

    static class Param {
        private final int input;
        private final int excpected;

        public Param(int input, int excpected) {
            this.input = input;
            this.excpected = excpected;
        }

        public int getInput() {
            return input;
        }

        public int getExcpected() {
            return excpected;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Param{");
            sb.append("input=").append(input);
            sb.append(", excpected=").append(excpected);
            sb.append('}');
            return sb.toString();
        }
    }
}

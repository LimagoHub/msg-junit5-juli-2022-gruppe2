package de.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StapelTest {

    @InjectMocks private Stapel objectUnderTest;

//    @BeforeEach
//    void setUp() {
//        objectUnderTest = new Stapel();
//    }

    @Nested
    @DisplayName("Tests zur Methode isEmpty")
    class IsEmptyTests {
        @Test
        @DisplayName("bei leerem Stapel wird der Rückgabewert true erwartet")
        void isEmptyEmptyStackReturnTrue() {
            assertThat(objectUnderTest.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("bei nicht mehr leerem Stapel wird der Rückgabewert false erwartet")
        void isEmptyNotEmptyStackReturnFalse() throws StapelException{
            objectUnderTest.push(0);
            assertThat(objectUnderTest.isEmpty()).isFalse();
        }
    }

    @Nested
    @DisplayName("Tests zur Methode isFull")
    class IsFullTests {
        @Test
        @DisplayName("bei vollem Stapel wird der Rückgabewert true erwartet")
        void isFull_vollerStapel_returnsTrue() throws StapelException{
            for (int i = 0; i < 10; i++) {
                objectUnderTest.push(i);
            }
            assertThat(objectUnderTest.isFull()).isTrue();
        }

        @Test
        @DisplayName("bei nicht vollem Stapel wird der Rückgabewert false erwartet")
        void isFullNotNotfullReturnFalse() throws StapelException{

            assertThat(objectUnderTest.isFull()).isFalse();
        }
    }

    @Nested
    @DisplayName("Tests zur Methode push")
    class pushTests {
        @Test
        @DisplayName("fill up to Limit No Exception is Thrown")
        void push_fillUpToLimit_noExceptionIsThrown() throws StapelException{
            // Arrange
            for (int i = 0; i < 9; i++) {
                objectUnderTest.push(i);
            }

            // Act + Assert
            assertDoesNotThrow(()->objectUnderTest.push(0));
        }

        @Test
        @DisplayName("overflow should throw StapelException with errormessage 'Overflow'")
        void pushOverflowThrowsStapelException() throws StapelException{
            // Arrange
            for (int i = 0; i < 10; i++) {
                objectUnderTest.push(i);
            }
            StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(0));
            assertThat(ex.getMessage()).isEqualTo("Overflow");
        }
    }
}
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TestTest {

    @Mock
    private List<String> listMock;



    @DisplayName("Dies ist ein einfacher Test")
    @Test
    public void simpleTest() {
        int value = 10;
        int expected = 10;

        assertThat(value).isEqualTo(expected );
    }

    @DisplayName("Dies ist ein einfacher Test für eine Liste")
    @Test
    public void simpleListTest() {
        List<String> liste = Arrays.asList("eins", "zwei", "drei", "vier");

        assertThat(liste).isNotNull().isNotEmpty().containsOnly("eins", "zwei", "drei");

    }


    @DisplayName("Dies ist ein einfacher MockTest")
    @Test
    public void simpleMockTest() {

        when(listMock.contains(anyString())).thenReturn(true);

        assertThat(listMock.contains("Fritz")).isTrue();

        verify(listMock).contains("Fritz");
    }
}

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void nullHorseException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullHorseMassage() {
        try {
            new Hippodrome(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    public void emptyHorseException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void emptyHorseMassage() {
        try {
            new Hippodrome(new ArrayList<>());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i + 1, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move() {

        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        // JDK выше 20 не работает
        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            verify(horse, atLeastOnce()).move();
        }
    }

    @Test
    public void getHorse() {
        Horse horse1 = new Horse("Horse1", 1, 4.999);
        Horse horse2 = new Horse("Horse2", 1, 5);
        Horse horse3 = new Horse("Horse3", 1, 4);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse2, hippodrome.getWinner());
    }
}

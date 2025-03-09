import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 20));
    }

    @Test
    public void nullNameMassage() {
        try {
            new Horse(null, 1, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\t\t", "\n", "\n\n"})
    public void blankNameException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 20));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\t\t", "\n", "\n\n"})
    public void blankNameMassage(String name) {

        try {
            new Horse(name, 1, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Blaze", -1, 20));
    }

    @Test
    public void negativeSpeedMassage() {
        try {
            new Horse("Blaze", -1, 20);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Blaze", 1, -1));
    }

    @Test
    public void negativeDistanceMassage() {
        try {
            new Horse("Blaze", 1, -1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Blaze", 1, 20);
        assertEquals("Blaze", horse.getName());

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("Blaze", nameValue);
    }

    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Blaze", 1, 20);
        assertEquals(1, horse.getSpeed());

        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        Double speedValue = (double) speed.get(horse);
        assertEquals(1, speedValue);
    }
    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Blaze", 1, 20);
        assertEquals(20, horse.getDistance());

        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        Double distanceValue = (double) distance.get(horse);
        assertEquals(20, distanceValue);
    }

    @Test
    public void getDistanceDefault() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Blaze", 1);
        assertEquals(0, horse.getDistance());

        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        Double distanceValue = (double) distance.get(horse);
        assertEquals(0, distanceValue);
    }

    @Test
    public void moveGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Blaze", 1, 20).move();
            mockedStatic.verify(()-> Horse.getRandomDouble(0.2, 0.9)) ;
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.3, 0.7, 1.9, 0.0, 10.999, 999.999,})
    void move(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Blaze", 50, 100);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn((Double)random);
            horse.move();

            assertEquals(100+50*random, horse.getDistance());

        }

    }

}

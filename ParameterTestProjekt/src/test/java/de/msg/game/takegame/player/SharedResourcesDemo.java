package de.msg.game.takegame.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ_WRITE;

@Execution(CONCURRENT)

class SharedResourcesDemo {
    @Test
    //@ResourceLock(value = "system.properties", mode = READ)
    void customPropertyIsNotSetByDefault() throws Exception{
        Thread.sleep(3000);
        assertNull(System.getProperty("my.prop"));
    }

    @Test
    //@ResourceLock(value = "system.properties", mode = READ_WRITE)
    void canSetCustomPropertyToFoo() throws Exception{
        Thread.sleep(3000);
        System.setProperty("my.prop", "foo");

        assertEquals("foo", System.getProperty("my.prop"));
    }

    @Test
    //@ResourceLock(value = "system.properties", mode = READ_WRITE)
    void canSetCustomPropertyToBar() throws Exception{
        Thread.sleep(3000);
        System.setProperty("my.prop", "bar");

        assertEquals("bar", System.getProperty("my.prop"));
    }
}
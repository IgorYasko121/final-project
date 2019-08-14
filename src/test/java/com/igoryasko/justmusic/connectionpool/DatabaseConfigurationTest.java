package com.igoryasko.justmusic.connectionpool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseConfigurationTest {

    @Test
    public void testGetURL(){
        String expected = "jdbc:postgresql://localhost:5432/justmusicdb";
        String actual = DatabaseConfiguration.INSTANCE.getURL();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetUSER(){
        String expected = "postgres";
        String actual = DatabaseConfiguration.INSTANCE.getUSER();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetPASSWORD(){
        String expected = "root";
        String actual = DatabaseConfiguration.INSTANCE.getPASSWORD();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetDRIVER(){
        String expected = "org.postgresql.Driver";
        String actual = DatabaseConfiguration.INSTANCE.getDRIVER();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testPoolSize(){
        int expected = 32;
        int actual = DatabaseConfiguration.INSTANCE.getPoolSize();
        Assertions.assertEquals(actual, expected);
    }

}

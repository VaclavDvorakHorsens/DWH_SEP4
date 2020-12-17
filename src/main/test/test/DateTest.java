package test;

import dwh.models.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {
    Date t;
    Date t1;
    @BeforeEach
    void setUp() {
        t=new Date(1,1,2020);
        t1=new Date();
    }
    @Test
    void constructor(){
        assertEquals(LocalDate.now().getDayOfMonth(),t1.getDay());
        assertEquals(LocalDate.now().getMonthValue(),t1.getMonth());
        assertEquals(LocalDate.now().getYear(),t1.getYear());
    }


    @Test
    void getMonth() {
        for (int i = -100; i < 0; i++) {
            t.setMonth(i);
            assertEquals(1,t.getMonth());
        }
        for (int i = 13; i < 112; i++) {
            t.setMonth(i);
            assertEquals(1,t.getMonth());
        }
    }

    @Test
    void setMonth() {
        for (int i = 1; i < 100; i++) {
            t.setMonth(i);
            if (i>12) assertEquals(12,t.getMonth());
           if(i<=12) assertEquals(i,t.getMonth());

        }

    }

    @Test
    void getDay() {
        for (int i = -100; i < 0; i++) {
            t.setDay(i);
            assertEquals(1,t.getDay());
        }
        for (int i = 32; i < 131; i++) {
            t.setDay(i);
            assertEquals(1,t.getDay());
        }
    }

    @Test
    void setDay() {
        for (int i = 1; i < 100; i++) {
            t.setDay(i);
            if (i>31) assertEquals(31,t.getDay());
            if(i<=31) assertEquals(i,t.getDay());

        }
    }

    @Test
    void before() {
        t1.setYear(2020);
        t1.setMonth(12);
        t1.setDay(15);
        for (int i = 0; i < 2020; i++) {
            t.setYear(i);
            assertTrue(t.before(t1));
        }
        t.setYear(2020);
        for (int i = 1; i < 13; i++){
            t.setMonth(i);
            assertTrue(t.before(t1));
        }
        t.setMonth(12);
        for (int i = 1;i<32;i++){
            t.setDay(i);
            if(t.getDay()<15) assertTrue(t.before(t1));
            else if (t.getDay()==15) assertFalse(t.before(t1));
            else assertFalse(t.before(t1));
        }
    }
}
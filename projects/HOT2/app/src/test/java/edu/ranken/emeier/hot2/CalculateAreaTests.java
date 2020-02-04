package edu.ranken.emeier.hot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class CalculateAreaTests {

    @Test
    public void twoValidNumbersArea() {
        assertThat(Calculations.calculateArea(20d, 20d), is(equalTo(400d)));
    }

    @Test
    public void twoValidDoublesArea() {
        assertThat(Calculations.calculateArea(5.5d, 2.225d), is(equalTo(12.2375d)));
    }

    @Test
    public void zeroEntryArea() {
        assertThat(Calculations.calculateArea(5.5d, 0d), is(equalTo(0d)));
    }

    @Test
    public void bothZeroEntriesArea() {
        assertThat(Calculations.calculateArea(0d, 0d), is(equalTo(0d)));
    }
}

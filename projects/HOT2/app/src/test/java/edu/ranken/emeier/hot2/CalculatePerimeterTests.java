package edu.ranken.emeier.hot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class CalculatePerimeterTests {

    @Test
    public void twoValidNumbersPerimeter() {
        assertThat(Calculations.calculatePerimeter(20d, 20d), is(equalTo(80d)));
    }

    @Test
    public void twoValidDoublesPerimeter() {
        assertThat(Calculations.calculatePerimeter(5.5d, 2.225d), is(equalTo(15.45d)));
    }

    @Test
    public void zeroEntryPerimeter() {
        assertThat(Calculations.calculatePerimeter(5.5d, 0d), is(equalTo(11d)));
    }

    @Test
    public void bothZeroEntriesPerimeter() {
        assertThat(Calculations.calculatePerimeter(0d, 0d), is(equalTo(0d)));
    }
}

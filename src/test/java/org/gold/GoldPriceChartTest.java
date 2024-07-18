package org.gold;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jfree.data.xy.XYDataset;
import org.junit.Test;

public class GoldPriceChartTest {

    @Test
    public void testCreateDataset() {
        GoldPriceChart chart = new GoldPriceChart("Gold Price in India", "Historical Gold Prices in India (1974 - 2024)");
        XYDataset dataset = chart.createDataset();

        // Check that the dataset is not null
        assertNotNull(dataset);

        // Check that the dataset contains the correct number of series and data points
        assertEquals("Number of series", 1, dataset.getSeriesCount());
        assertEquals("Number of data points", 50, dataset.getItemCount(0));

        // Check that specific data points are correctly set
        assertEquals("Gold Price in 1974", 369.0, dataset.getYValue(0, 0), 0.001);
        assertEquals("Gold Price in 2024", 73780.0, dataset.getYValue(0, 49), 0.001);
    }
}

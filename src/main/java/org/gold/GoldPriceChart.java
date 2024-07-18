package org.gold;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

public class GoldPriceChart extends ApplicationFrame {

    public GoldPriceChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = createChart(createDataset(), chartTitle);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public XYDataset createDataset() {
        XYSeries series = new XYSeries("Gold Price");
        int[] years = {
                1974, 1975, 1976, 1977, 1978, 1979, 1980, 1981, 1982, 1983,
                1984, 1985, 1986, 1987, 1988, 1989, 1990, 1991, 1992, 1993,
                1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003,
                2004, 2005, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014,
                2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024
        };
        double[] prices = {
                369, 520, 545, 486, 685, 890, 1300, 1800, 1600, 1800,
                1900, 2000, 2100, 2500, 3000, 3100, 3200, 3400, 4300, 4100,
                4500, 4650, 5100, 4700, 4000, 4200, 4400, 4300, 5000, 5700,
                5800, 7000, 9000, 10800, 12500, 14500, 18000, 25000, 32000, 33000,
                30000, 26343.5, 28623.5, 29667.5, 31438, 35220, 48651, 48720, 52670, 73780  // Corrected value for 2024
        };

        for (int i = 0; i < years.length; i++) {
            series.add(years[i], prices[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Year",
                "Gold Price (INR per 10 grams)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(renderer);

        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.darkGray);
        return chart;
    }

    public static void main(String[] args) {
        GoldPriceChart chart = new GoldPriceChart(
                "Gold Price in India",
                "Historical Gold Prices in India (1974 - 2024)");

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cleano.utils;
import java.awt.Color;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author abdurraihan
 */
public class ChartBuilder {
     public static JPanel createBarChart(String title, String categoryAxisLabel, String valueAxisLabel, Map<String, Double> data) {
        // Buat dataset dari Map
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Pendapatan", entry.getKey());
        }

        // Buat chart batang
        JFreeChart chart = ChartFactory.createBarChart(
                title,                 // Judul
                categoryAxisLabel,     // Label sumbu X
                valueAxisLabel,        // Label sumbu Y
                dataset                // Dataset
        );

        // Kustomisasi plot
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.gray);

        // Ubah warna batang
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, new Color(0, 153, 204));
        plot.setRenderer(renderer);

        // Bungkus dalam JPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(false); // untuk bar chart, zoom jarang dipakai
        chartPanel.setRangeZoomable(false);
        return chartPanel;
    }
}

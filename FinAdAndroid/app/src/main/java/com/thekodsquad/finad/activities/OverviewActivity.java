package com.thekodsquad.finad.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.thekodsquad.finad.R;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        configureLineGraph();
    }

    private void configureLineGraph() {
        LineChart chart = (LineChart) findViewById(R.id.chart);

        double[] data = generateData(100);
        List<Entry> entries = new ArrayList<Entry>();
        int i = 0;
        for (double v : data) {
            // turn your data into Entry objects
            entries.add(new Entry(i, (float) v));
            i++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(getColor(R.color.purple_200));
        dataSet.setDrawCircles(false);
        //dataSet.setValueTextColor(getColor(R.color.design_default_color_primary)); // styling, ...

        LineData lineData = new LineData(dataSet);
        //chart.setBackgroundColor(getColor(R.color.white));
        chart.setData(lineData);
        chart.setDrawGridBackground(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.setDrawBorders(false);
        chart.invalidate(); // refresh
    }

    private double[] generateData(int length) {
        double[] data = new double[length];

        for (int i = 0; i < length; i++) {
            data[i] = Math.sin(i*0.1);
        }

        return data;
    }
}

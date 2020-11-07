package com.thekodsquad.finad;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;

public class FinAdLineChart extends LineChart {
    public FinAdLineChart(Context context) {
        super(context);
        setupChart(context);


    }

    public FinAdLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupChart(context);
    }

    public FinAdLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupChart(context);
    }

    private void setupChart(Context context) {
        setDrawGridBackground(false);

        AxisBase lAxis = getAxisLeft();

        //chart.getAxisRight().setDrawLabels(false);
        getAxisRight().setEnabled(false);
        //chart.getAxisLeft().setDrawLabels(false);
        lAxis.setEnabled(true);
        lAxis.setGridColor(context.getColor(R.color.white));
        lAxis.setTextColor(context.getColor(R.color.white));
        lAxis.setAxisLineColor(context.getColor(R.color.white));
        lAxis.setGranularity(1f);
        lAxis.setAxisLineWidth(2f);
        lAxis.setGridLineWidth(2f);
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        getXAxis().setEnabled(false);
        getLegend().setEnabled(false);
        getDescription().setEnabled(false);

        setDrawBorders(false);
    }
}

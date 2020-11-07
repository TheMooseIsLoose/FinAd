package com.thekodsquad.finad;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;

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
        lAxis.setGridColor(Color.argb(0.5f, 1.0f, 1.0f, 1.0f));
        lAxis.setTextColor(context.getColor(R.color.white));
        lAxis.setAxisLineColor(context.getColor(R.color.white));
        lAxis.setGranularity(1f);
        lAxis.setAxisLineWidth(2f);
        lAxis.setGridLineWidth(0.5f);

        getXAxis().setDrawGridLines(false);
        getXAxis().setDrawAxisLine(true);
        getXAxis().setAxisLineWidth(2f);
        getXAxis().setAxisLineColor(context.getColor(R.color.white));
        getXAxis().setTextColor(context.getColor(R.color.white));
        getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        getXAxis().setDrawLabels(false);

        getLegend().setEnabled(false);
        getDescription().setEnabled(false);

        setDrawBorders(false);
    }
}

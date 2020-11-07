package com.thekodsquad.finad.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.thekodsquad.finad.R;
import com.thekodsquad.finad.sta.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OverviewFragment newInstance(String param1, String param2) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_overview);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        configureLineGraph(view);
        return view;
    }

    private void configureLineGraph(View view) {
        LineChart chart = (LineChart) view.findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        int day = 0;
        BigDecimal spent = BigDecimal.ZERO;
        for (Transaction trans : MainActivity.account.getTransactions()) {
            if (trans.getAmount().compareTo(BigDecimal.ZERO) > 0) continue;
            spent = spent.subtract(trans.getAmount());
            entries.add(new Entry(day, spent.floatValue()));
            day++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(getActivity().getColor(R.color.purple_200));
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });
        dataSet.setFillColor(getActivity().getColor(R.color.purple_500));
        //dataSet.setValueTextColor(getColor(R.color.design_default_color_primary)); // styling, ...

        LineData lineData = new LineData(dataSet);
        //chart.setBackgroundColor(getColor(R.color.white));
        chart.setData(lineData);

        LimitLine limit = new LimitLine(1000);
        limit.setLineColor(getActivity().getColor(R.color.design_default_color_primary_dark));
        chart.getAxisLeft().addLimitLine(limit);
        chart.getAxisLeft().setAxisMaximum(1100);
        chart.getAxisLeft().setAxisMinimum(0);


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
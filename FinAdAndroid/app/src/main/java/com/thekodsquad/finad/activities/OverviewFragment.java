package com.thekodsquad.finad.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Pair;
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
import com.thekodsquad.finad.sta.Account;
import com.thekodsquad.finad.sta.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
        int day = 1;
        BigDecimal spent = BigDecimal.ZERO;
        BigDecimal spentCurrentDate = BigDecimal.ZERO;
        for (Transaction trans : MainActivity.account.getTransactions()) {
            if (trans.getAmount().compareTo(BigDecimal.ZERO) > 0) continue;
            int date = trans.getTimestamp().get(Calendar.DATE);
            if (day == date) {
                spentCurrentDate = spentCurrentDate.subtract(trans.getAmount());
            } else {
                spent = spent.add(spentCurrentDate);
                entries.add(new Entry(day, spent.floatValue()));
                for (int i = day + 1; i < date; i++) {
                    entries.add(new Entry(i, spent.floatValue()));
                }
                day = date;
                spentCurrentDate = trans.getAmount().abs();
            }
        }
        entries.add(new Entry(day, spent.add(spentCurrentDate).floatValue()));

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(getActivity().getColor(R.color.white));
        dataSet.setLineWidth(getContext().getResources().getInteger(R.integer.line_width));
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });
        dataSet.setFillColor(getActivity().getColor(R.color.primary_text));
        //dataSet.setValueTextColor(getColor(R.color.design_default_color_primary)); // styling, ...

        LineData lineData = new LineData(dataSet);

        List<Entry> regressionEntries = new ArrayList<>();
        Pair<BigDecimal, BigDecimal> regression = generateRegression();
        regressionEntries.add(entries.get(entries.size() - 1));
        regressionEntries.add(new Entry(regression.first.floatValue(), regression.second.floatValue()));

        LineDataSet regressionDataSet = (LineDataSet) dataSet.copy();
        regressionDataSet.setDrawFilled(false);
        regressionDataSet.setDrawCircles(false);
        regressionDataSet.setValues(regressionEntries);
        regressionDataSet.enableDashedLine(10f, 4f, 0f);
        lineData.addDataSet(regressionDataSet);
        //chart.setBackgroundColor(getColor(R.color.white));
        chart.setData(lineData);


        LimitLine limit = new LimitLine(1000);
        limit.setLineColor(getActivity().getColor(R.color.orange_main));
        limit.enableDashedLine(10, 4f, 0);
        limit.setLineWidth(getContext().getResources().getInteger(R.integer.limit_line_width));

        limit.setTextColor(getActivity().getColor(R.color.orange_main));
        limit.setTextSize(16);

        chart.getAxisLeft().addLimitLine(limit);
        chart.getAxisLeft().setAxisMaximum(1100);
        chart.getAxisLeft().setAxisMinimum(0);

        chart.invalidate(); // refresh
    }

    private Pair<BigDecimal, BigDecimal> generateRegression() {
        // Get an account
        Account account = MainActivity.account;
        // Budget goal
        BigDecimal budget = new BigDecimal("3000");
        // Current day, or day of month to start at
        List<Transaction> transactionList = account.getTransactions();
        transactionList.sort(Comparator.comparing(Transaction::getTimestamp));

        Calendar currentDay = transactionList.get(0).getTimestamp();

        BigDecimal moneySpent = new BigDecimal(0);
        for (Transaction transaction : transactionList) {
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) continue;
            moneySpent = moneySpent.add(transaction.getAmount());
        }

        BigDecimal days = BigDecimal.valueOf(ChronoUnit.DAYS.between(transactionList.get(0).getTimestamp().toInstant(), transactionList.get(transactionList.size() - 1).getTimestamp().toInstant()));
        BigDecimal moneySpentPerDay = moneySpent.divide(days, 2, RoundingMode.HALF_UP);
        Calendar lastDay = Calendar.getInstance();
        lastDay.set(currentDay.get(Calendar.YEAR), currentDay.get(Calendar.MONTH), currentDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        BigDecimal daysLeft = BigDecimal.valueOf(ChronoUnit.DAYS.between(currentDay.toInstant(), lastDay.toInstant()));
        BigDecimal estMoneySpent = daysLeft.multiply(moneySpentPerDay);

        return new Pair<>(daysLeft, estMoneySpent.abs());

    }

    private double[] generateData(int length) {
        double[] data = new double[length];

        for (int i = 0; i < length; i++) {
            data[i] = Math.sin(i*0.1);
        }

        return data;
    }
}
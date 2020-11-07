package com.thekodsquad.finad.activities;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.thekodsquad.finad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        PieChart budgetChart = view.findViewById(R.id.budgetChart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(50f, "Housing"));
        entries.add(new PieEntry(20.f, "Food"));
        entries.add(new PieEntry(10.0f, "Leisure"));
        entries.add(new PieEntry(5f, "Health"));
        entries.add(new PieEntry(15f, "Saving"));
        PieDataSet set = new PieDataSet(entries, "Budget");
        set.setColors(getContext().getColor(R.color.orange_main),
                getContext().getColor(R.color.purple),
                getContext().getColor(R.color.yellow),
                getContext().getColor(R.color.blue),
                getContext().getColor(R.color.green),
                getContext().getColor(R.color.green_secondary),
                getContext().getColor(R.color.orange_secondary)
                );
        set.setDrawValues(false);
        set.setValueLineColor(Color.WHITE);
        set.setValueLinePart1OffsetPercentage(10.f);
        set.setValueLinePart1Length(0.43f);
        set.setValueLinePart2Length(.1f);
        set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        budgetChart.setHoleRadius(50);
        budgetChart.getLegend().setEnabled(false);
        budgetChart.setEntryLabelColor(Color.WHITE);

        PieData data = new PieData(set);
        budgetChart.setDrawCenterText(true);
        budgetChart.setCenterText("1000 EUR");
        budgetChart.setData(data);
        budgetChart.setCenterTextSize(20);
        budgetChart.getLegend().setEnabled(false);
        budgetChart.getDescription().setEnabled(false);
        budgetChart.setExtraLeftOffset(32);
        budgetChart.setExtraRightOffset(32);

        budgetChart.animateXY(1000, 1000, Easing.EaseInQuad);

        budgetChart.invalidate(); // refresh

        LineChart savingChart = view.findViewById(R.id.savingChart);

        List<Entry> savingEntries = new ArrayList<>();
        savingEntries.add(new Entry(0, 0));
        savingEntries.add(new Entry(1, 100));
        savingEntries.add(new Entry(2, 200));
        savingEntries.add(new Entry(3, 150));
        savingEntries.add(new Entry(4, 200));
        savingEntries.add(new Entry(5, 300));
        savingEntries.add(new Entry(6, 350));
        savingEntries.add(new Entry(7, 400));

        LineDataSet savingDataSet = new LineDataSet(savingEntries, "Savings");
        savingDataSet.setDrawCircles(false);
        savingDataSet.setDrawValues(false);

        savingDataSet.setColor(getActivity().getColor(R.color.white));
        savingDataSet.setLineWidth(getContext().getResources().getInteger(R.integer.line_width));

        savingDataSet.setDrawFilled(true);
        savingDataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return savingChart.getAxisLeft().getAxisMinimum();
            }
        });
        savingDataSet.setFillColor(getActivity().getColor(R.color.primary_text));

        LineData savingData = new LineData(savingDataSet);
        LimitLine limit = new LimitLine(750);
        limit.setLineColor(getActivity().getColor(R.color.orange_main));
        limit.enableDashedLine(10, 4f, 0);
        limit.setLineWidth(getContext().getResources().getInteger(R.integer.limit_line_width));
        limit.setLabel("Goal: 750 EUR");
        limit.setTextColor(getActivity().getColor(R.color.orange_main));
        limit.setTextSize(16);

        savingChart.getAxisLeft().addLimitLine(limit);
        savingChart.getAxisLeft().setAxisMaximum(900);
        savingChart.getAxisLeft().setAxisMinimum(0);
        savingChart.setPinchZoom(false);

        savingChart.setData(savingData);

        savingChart.animateY(500, Easing.Linear);
        return view;
    }
}
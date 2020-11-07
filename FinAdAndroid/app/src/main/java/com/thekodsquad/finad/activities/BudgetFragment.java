package com.thekodsquad.finad.activities;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
        set.setColors(Color.GREEN, Color.YELLOW, Color.RED, Color.BLUE, Color.MAGENTA);
        set.setDrawValues(false);
        set.setValueLinePart1OffsetPercentage(10.f);
        set.setValueLinePart1Length(0.43f);
        set.setValueLinePart2Length(.1f);
        set.setValueTextColor(Color.BLACK);
        set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        budgetChart.setEntryLabelColor(Color.BLUE);
        PieData data = new PieData(set);
        budgetChart.setDrawCenterText(true);

        budgetChart.setCenterText("1000 EUR");
        budgetChart.setData(data);
        budgetChart.invalidate(); // refresh

        return view;
    }
}
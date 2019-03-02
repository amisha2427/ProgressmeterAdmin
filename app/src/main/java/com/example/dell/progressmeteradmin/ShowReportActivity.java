package com.example.dell.progressmeteradmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowReportActivity extends AppCompatActivity {

    private PieChart reportChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report2);

        init();
        setUpPieChart();

    }

    private void init()
    {
        reportChart =(PieChart)findViewById(R.id.showReport_reportChart);
    }

    private void setUpPieChart()
    {
        List<PieEntry> pieEntryList = new ArrayList<>();
        pieEntryList.add(new PieEntry(20,"False Reports"));
        pieEntryList.add(new PieEntry(80,"True Reports"));

        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"Project Details");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);

        reportChart.setData(pieData);
        //reportChart.setTouchEnabled(true);
        reportChart.animateY(1000);
        reportChart.invalidate();
    }
}

package com.esilv.fink.Chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.esilv.fink.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.hotmail.or_dvir.easysettings.pojos.EasySettings;

import java.util.ArrayList;

public class BarChartItem extends ChartItem {

    private final Typeface mTf;

    public BarChartItem(ChartData<?> cd, Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.getDescription().setEnabled(false);
        holder.chart.setDrawGridBackground(false);
        holder.chart.setDrawBarShadow(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(12);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChartData.setValueTypeface(mTf);

        // set data
        holder.chart.setData((BarData) mChartData);
        ArrayList<String> BarEntryLabels = new ArrayList<String>();
        BarEntryLabels.add("J");
        BarEntryLabels.add("J");
        BarEntryLabels.add("F");
        BarEntryLabels.add("M");
        BarEntryLabels.add("A");
        BarEntryLabels.add("M");
        BarEntryLabels.add("J");
        BarEntryLabels.add("J");
        BarEntryLabels.add("A");
        BarEntryLabels.add("S");
        BarEntryLabels.add("O");
        BarEntryLabels.add("N");
        BarEntryLabels.add("D");
        holder.chart.getXAxis().setTextColor(Color.WHITE);
        holder.chart.getLegend().setEnabled(false);   // Hide the legend

        boolean value = EasySettings.retrieveSettingsSharedPrefs(c).getBoolean("darkmode", false);
        if (value) {
            holder.chart.getXAxis().setTextColor(Color.WHITE);
            rightAxis.setTextColor(Color.WHITE);
            leftAxis.setTextColor(Color.WHITE);

        }
        else{
            holder.chart.getXAxis().setTextColor(Color.BLACK);
            rightAxis.setTextColor(Color.BLACK);
            leftAxis.setTextColor(Color.BLACK);

        }

        holder.chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(BarEntryLabels));
        holder.chart.setFitBars(true);

        // do not forget to refresh the chart
//        holder.chart.invalidate();
        holder.chart.animateY(700);

        return convertView;
    }

    private static class ViewHolder {
        BarChart chart;
    }
}

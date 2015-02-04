
package com.iwillcode.limetraytwitterassignment.LimetrayUI;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import com.iwillcode.limetraytwitterassignment.R;

import com.iwillcode.limetraytwitterassignment.Utilities.Graph;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class LimetrayGraph extends Activity  {








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limetray_graph);


        GraphView limetrayGraph = (GraphView) findViewById(R.id.graph);



        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{


                new DataPoint(1, 6),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)

        });


        limetrayGraph.addSeries(series);








    }






}
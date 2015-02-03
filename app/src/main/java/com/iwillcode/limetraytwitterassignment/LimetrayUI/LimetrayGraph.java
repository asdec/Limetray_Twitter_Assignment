
package com.iwillcode.limetraytwitterassignment.LimetrayUI;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import com.iwillcode.limetraytwitterassignment.R;
import com.jjoe64.graphview.GraphView;

import java.util.Date;

public class LimetrayGraph extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limetray_graph);
    }

}
/*
    GraphView graphView = new LineGraphView(this, "TweetGraph") {
        @Override
        protected String formatLabel(double value, boolean isValueX) {
            if (isValueX) {
                // convert unix time to human time
                return dateTimeFormatter.format(new Date((long) value*1000));
            } else return super.formatLabel(value, isValueX); // let the y-value be normal-formatted
        }
    };

}
*/

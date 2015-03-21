package org.coursera.android.dsinkovskiy.modernart.modernartui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "ModernArtUI";
    private static final String URL = "http://www.moma.org";

    private LinearLayout[] rectangles;

    private static final int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find all rectangles' views
        rectangles = new LinearLayout[5];
        rectangles[0] = (LinearLayout) findViewById(R.id.top_right_rectangle);
        rectangles[1] = (LinearLayout) findViewById(R.id.bottom_right_rectangle);
        rectangles[2] = (LinearLayout) findViewById(R.id.top_left_rectangle);
        rectangles[3] = (LinearLayout) findViewById(R.id.middle_left_rectangle);
        rectangles[4] = (LinearLayout) findViewById(R.id.bottom_left_rectangle);

        // Set initial colors to rectangles programmatically
        int i = -1;
        for (LinearLayout item : rectangles) {
            i++;
            item.setBackgroundColor(colors[i]);
        }

        // Set change listener for seek bar to change the colors
        SeekBar mSeekBar = (SeekBar) findViewById(R.id.change_color_seek_bar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Change colors of the rectangles when seek bar progress is changing
                int i = -1;
                for (LinearLayout item : rectangles) {
                    i++;
                    item.setBackgroundColor(getChangedRectangleColor(
                            colors[i], progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.more_info) {
            // Create and show the dialog box
            Log.i(TAG, "More info option has been chosen");
            AlertDialog.Builder builder = new AlertDialog.Builder(this,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            builder.setMessage(R.string.more_info_dialog_message)
                    .setPositiveButton(R.string.more_info_dialog_button_visit_web_site,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent openWebSite = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(URL));
                                    startActivity(openWebSite);
                                }
                            })
                    .setNegativeButton(R.string.more_info_dialog_button_not_now,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Do nothing
                                }
                            });
            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    // Colors changing logic
    private int getChangedRectangleColor (int initialColor, int progress) {
        int red = Color.red(initialColor);
        int green = Color.green(initialColor);
        int blue = Color.blue(initialColor);

        red = changeColor(red, progress);
        green = changeColor(green, progress);
        blue = changeColor(blue, progress);

        return Color.rgb(red, green, blue);
    }

    private int changeColor (int initialColor, int progress) {
        return initialColor == 0 ? progress : Math.abs(progress - initialColor);
    }
}
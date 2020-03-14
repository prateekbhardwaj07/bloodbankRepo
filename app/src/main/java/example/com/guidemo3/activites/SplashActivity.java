package example.com.guidemo3.activites;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.guidemo3.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.splashProgress);
        // Get the Drawable custom_progressbar
        Drawable draw=getResources().getDrawable(R.drawable.custom_progress_bar);
        // set the drawable as progress drawable
        progressBar.setProgressDrawable(draw);
        progressBar.setProgress(0);

        final long period = 50;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(i<50)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    progressBar.setProgress(i*2);
                    i++;
                }
                else
                {
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this,PhoneAuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },0,period);
    }
}

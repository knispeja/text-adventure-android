package knispeja.textadventure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import knispeja.textadventure.knispeja.textadventure.ui.LimitedTextLog;

public class MainActivity extends AppCompatActivity
{
    LimitedTextLog log;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.log = new LimitedTextLog(this);
    }

    private final Runnable dummyButtonClicked = new Runnable()
    {
        @Override
        public void run()
        {
            log.AddToLog("This is some long text intended to wrap around at least one line " + x++);
        }
    };

    public void onClickBtn(View v)
    {
        new Thread(dummyButtonClicked).start();
    }
}

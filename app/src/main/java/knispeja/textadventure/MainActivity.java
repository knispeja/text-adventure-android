package knispeja.textadventure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import knispeja.textadventure.ui.TextLog;

public class MainActivity extends AppCompatActivity
{
    private TextLog log;
    private int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.log = new TextLog(this);
    }

    private final Runnable dummyButtonClicked = new Runnable()
    {
        @Override
        public void run()
        {
            log.appendWithNewLine("This is some long text intended to wrap around at least one line... " + x++);
        }
    };

    public void onClickBtn(View v)
    {
        new Thread(dummyButtonClicked).start();
    }
}

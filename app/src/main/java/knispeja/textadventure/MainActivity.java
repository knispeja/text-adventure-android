package knispeja.textadventure;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import knispeja.textadventure.knispeja.textadventure.ui.TextLog;

public class MainActivity extends AppCompatActivity
{
    TextLog log;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.log = new TextLog(this);
        this.log.getTextStyle().addStyle(new BackgroundColorSpan(Color.RED));
    }

    private final Runnable dummyButtonClicked = new Runnable()
    {
        @Override
        public void run()
        {
            log.addToLog("This is some long text intended to wrap around at least one line... " + x++);
        }
    };

    public void onClickBtn(View v)
    {
    	if (x == 2)
	    {
	    	this.log.getTextStyle().clear();
	    }

	    if (x % 2 == 0)
	    {
		    this.log.getTextStyle().clear();
	    	this.log.getTextStyle().addStyle(new StrikethroughSpan());
	    }
	    else
	    {
	    	this.log.getTextStyle().clear();
	    	this.log.getTextStyle().addStyle(new SubscriptSpan());
	    }

        new Thread(dummyButtonClicked).start();
    }
}

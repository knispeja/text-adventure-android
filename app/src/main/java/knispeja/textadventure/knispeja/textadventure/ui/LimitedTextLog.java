package knispeja.textadventure.knispeja.textadventure.ui;

import android.app.Activity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import knispeja.textadventure.R;

public class LimitedTextLog
{
	private static final int MAXIMUM_LOG_LENGTH = 7000;

	private static final String DEFAULT_LINE_PREFIX = "> ";
	private static final int DEFAULT_DELAY_PER_CHARACTER_MS = 12;
	private static final int COMMA_DELAY_MULTIPLIER = 5;
	private static final int PERIOD_DELAY_MULTIPLIER = 10;

	private final Activity activity;
	private final TextView logTextView;
	private final ScrollView scrollView;
	private String currentLogText;

	public LimitedTextLog(Activity activity)
	{
		this.activity = activity;
		this.logTextView = activity.findViewById(R.id.textLog);
		this.scrollView = activity.findViewById(R.id.textLogScrollView);
		this.currentLogText = "";
	}

	public void ClearLog()
	{
		SetLogText("");
	}

	public void AddToLog(String text)
	{
		AddToLog(text, DEFAULT_LINE_PREFIX);
	}

	public void AddToLog(String text, String linePrefix)
	{
		AddToLog(text, linePrefix, DEFAULT_DELAY_PER_CHARACTER_MS);
	}

	public void AddToLog(String text, int delayPerCharacterMs)
	{
		AddToLog(text, DEFAULT_LINE_PREFIX, delayPerCharacterMs);
	}

	public synchronized void AddToLog(String text, String linePrefix, int delayPerCharacterMs)
	{
		String newLogText = this.currentLogText
						  + "\n"
						  + linePrefix;

		for (char c : text.toCharArray())
		{
			newLogText += c;
			SetLogText(newLogText);

			int delayMs = delayPerCharacterMs;
			if (c == ',')
			{
				delayMs *= COMMA_DELAY_MULTIPLIER;
			}
			else if (c == '.')
			{
				delayMs *= PERIOD_DELAY_MULTIPLIER;
			}

			try
			{
				Thread.sleep(delayMs);
			}
			catch (InterruptedException e)
			{
				return;
			}
		}

		if (newLogText.length() > MAXIMUM_LOG_LENGTH)
		{
			int newlineIndex = newLogText.indexOf("\n");
			if (newlineIndex >= 0)
			{
				newLogText = newLogText.substring(newlineIndex);
			}
			SetLogText(newLogText);
		}
	}

	private final Runnable updateLogText = new Runnable() {
		@Override
		public synchronized void run()
		{
			logTextView.setText(currentLogText);
			ScrollViewToBottom();
		}
	};

	private synchronized void SetLogText(String text)
	{
		this.currentLogText = text;
		activity.runOnUiThread(updateLogText);
	}

	private void ScrollViewToBottom()
	{
		View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
		int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
		int sy = scrollView.getScrollY();
		int sh = scrollView.getHeight();
		int delta = bottom - (sy + sh);
		scrollView.scrollBy(0, delta);
	}
}

package knispeja.textadventure.knispeja.textadventure.ui;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import knispeja.textadventure.R;

public class TextLog
{
	public int delayPerCharacterMs;

	private static final int MAXIMUM_LOG_CHARACTERS = 300;
	private static final int DEFAULT_DELAY_PER_CHARACTER_MS = 6;
	private static final int COMMA_DELAY_MULTIPLIER = 5;
	private static final int PERIOD_DELAY_MULTIPLIER = 10;

	private final Activity activity;
	private final TextView logTextView;
	private final ScrollView scrollView;

	private TextStyle textStyle;

	public TextLog(Activity activity)
	{
		this.activity = activity;
		this.logTextView = activity.findViewById(R.id.textLog);
		this.scrollView = activity.findViewById(R.id.textLogScrollView);

		this.textStyle = new TextStyle();

		this.delayPerCharacterMs = DEFAULT_DELAY_PER_CHARACTER_MS;
	}

	public TextStyle getTextStyle()
	{
		return this.textStyle;
	}

	public void setTextStyle(TextStyle textStyle)
	{
		this.textStyle = textStyle;
	}

	public synchronized void nextLine()
	{
		appendLogText("\n");
	}

	public synchronized void addToLog(CharSequence text)
	{
		addToLog(text, true);
	}

	public synchronized void addToLog(CharSequence textToAdd, boolean automaticNewline)
	{
		CharSequence logText = this.logTextView.getText();
		if (automaticNewline)
		{
			if (logText.length() > 0)
			{
				nextLine();
			}
		}

		if (textToAdd == null || textToAdd.length() == 0)
		{
			return;
		}

		logText = this.logTextView.getText();
		SpannableStringBuilder builder = this.textStyle.startFormattedText(textToAdd.charAt(0));
		for (int i=1; i<textToAdd.length(); i++)
		{
			char currentChar = textToAdd.charAt(i);

			int delayMs = this.delayPerCharacterMs;
			if (currentChar == ',')
			{
				delayMs *= COMMA_DELAY_MULTIPLIER;
			}
			else if (currentChar == '.')
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

			builder.append(currentChar);
			setLog(logText, builder);
		}

		logText = this.logTextView.getText();
		if (logText.length() > MAXIMUM_LOG_CHARACTERS)
		{
			int firstNewline = TextUtils.indexOf(logText, '\n');
			if (firstNewline >= 0)
			{
				int secondNewline = TextUtils.indexOf(logText, '\n', firstNewline);
				if (secondNewline >= 0 && secondNewline < logText.length() - 2)
				{
					setLog(logText.subSequence(secondNewline + 1, logText.length()));
				}
			}
		}
	}

	public synchronized void clearLog()
	{
		setLog("");
	}

	public synchronized void setLog(final CharSequence... textArgs)
	{
		if (textArgs.length < 1)
		{
			return;
		}

		final UiThreadTask setTextTask = new UiThreadTask()
		{
			@Override
			protected void doWork()
			{
				CharSequence finalText = textArgs[0];
				for (int i=1; i<textArgs.length; i++)
				{
					finalText = TextUtils.concat(finalText, textArgs[i]);
				}
				logTextView.setText(finalText);
				scrollToBottom();
			}
		};

		setTextTask.runOnUiThread(this.activity);
	}


	private synchronized void appendLogText(final CharSequence text)
	{
		final UiThreadTask setTextTask = new UiThreadTask()
		{
			@Override
			protected void doWork()
			{
				logTextView.append(text);
				scrollToBottom();
			}
		};

		setTextTask.runOnUiThread(this.activity);
	}

	private void scrollToBottom()
	{
		View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
		int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
		int sy = scrollView.getScrollY();
		int sh = scrollView.getHeight();
		int delta = bottom - (sy + sh);
		scrollView.scrollBy(0, delta);
	}
}

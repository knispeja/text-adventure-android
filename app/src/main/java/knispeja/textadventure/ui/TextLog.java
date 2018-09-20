package knispeja.textadventure.ui;

import android.app.Activity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import knispeja.textadventure.R;
import knispeja.textadventure.ui.style.IScrollingTextStyle;
import knispeja.textadventure.ui.style.ITextStyle;
import knispeja.textadventure.ui.style.ScrollingTextStyle;
import knispeja.textadventure.ui.style.TextStyle;

public class TextLog implements ITextLog
{
	private int delayPerCharacterMs;

	private static final int MAXIMUM_LOG_CHARACTERS = 300;

	private final Activity activity;
	private final TextView logTextView;
	private final ScrollView scrollView;

	private ScrollingTextStyle textStyle;

	public TextLog(Activity activity)
	{
		this.activity = activity;
		this.logTextView = activity.findViewById(R.id.textLog);
		this.scrollView = activity.findViewById(R.id.textLogScrollView);

		this.textStyle = new ScrollingTextStyle();
	}

	public IScrollingTextStyle getTextStyle()
	{
		return this.textStyle;
	}

	public synchronized void appendWithNewLine(final CharSequence textToAdd)
	{
		append("\n" + textToAdd);
	}

	public synchronized void append(final CharSequence textToAdd)
	{
		if (textToAdd == null || textToAdd.length() == 0)
		{
			return;
		}

		CharSequence logText = this.logTextView.getText();
		String firstCharacter = Character.toString(textToAdd.charAt(0));
		SpannableStringBuilder builder = this.textStyle.startFormattedText(firstCharacter);
		for (int i=1; i<textToAdd.length(); i++)
		{
			char currentChar = textToAdd.charAt(i);
			int delayMs = this.textStyle.getDelayForCharacterMs(currentChar);

			try
			{
				Thread.sleep(delayMs);
			}
			catch (InterruptedException e)
			{
				return;
			}

			// Ensures that the new character is styled
			builder.append(currentChar);
			set(logText, builder);
		}

		// Cut off the top of the log if we exceeded the character limit
		logText = this.logTextView.getText();
		if (logText.length() > MAXIMUM_LOG_CHARACTERS)
		{
			int firstNewline = TextUtils.indexOf(logText, '\n');
			if (firstNewline >= 0)
			{
				int secondNewline = TextUtils.indexOf(logText, '\n', firstNewline);
				if (secondNewline >= 0 && secondNewline < logText.length() - 2)
				{
					set(logText.subSequence(secondNewline + 1, logText.length()));
				}
			}
		}
	}

	public synchronized void clear()
	{
		set("");
	}

	public synchronized void nextLine()
	{
		append("\n");
	}

	public void scrollToBottom()
	{
		View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
		int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
		int sy = scrollView.getScrollY();
		int sh = scrollView.getHeight();
		int delta = bottom - (sy + sh);
		scrollView.scrollBy(0, delta);
	}

	public synchronized void set(final CharSequence... textArgs)
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

	private synchronized void appendSimple(final CharSequence text)
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
}

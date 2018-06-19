package knispeja.textadventure.knispeja.textadventure.ui;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import java.util.ArrayList;

public class TextStyle
{
	private ArrayList<CharacterStyle> textStyles;

	public TextStyle()
	{
		this.textStyles = new ArrayList<>();
	}

	public void clear()
	{
		this.textStyles.clear();
	}

	public void addStyle(CharacterStyle style)
	{
		this.textStyles.add(style);
	}

	public SpannableStringBuilder startFormattedText(char c)
	{
		SpannableStringBuilder builder = new SpannableStringBuilder(Character.toString(c));
		for (CharacterStyle style : this.textStyles)
		{
			builder.setSpan(CharacterStyle.wrap(style), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		return builder;
	}

	public SpannableString formatText(CharSequence text)
	{
		SpannableString formattedText = new SpannableString(text);
		for (CharacterStyle style : this.textStyles)
		{
			formattedText.setSpan(CharacterStyle.wrap(style), 0, text.length(), 0);
		}
		return formattedText;
	}
}

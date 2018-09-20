package knispeja.textadventure.ui.style;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import java.util.ArrayList;

public class TextStyle implements ITextStyle
{
	private ArrayList<CharacterStyle> textStyles;

	public TextStyle()
	{
		this.textStyles = new ArrayList<>();
	}

	public void add(CharacterStyle style)
	{
		this.textStyles.add(style);
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

	public void reset()
	{
		this.textStyles.clear();
	}

	public SpannableStringBuilder startFormattedText(String text)
	{
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		for (CharacterStyle style : this.textStyles)
		{
			builder.setSpan(CharacterStyle.wrap(style), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		return builder;
	}
}

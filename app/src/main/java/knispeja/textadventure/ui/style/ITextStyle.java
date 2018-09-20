package knispeja.textadventure.ui.style;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;

public interface ITextStyle
{
	/**
	 * Applies the given style to the current style.
	 * @param style The style to combine with the current one.
	 */
	void add(CharacterStyle style);

	/**
	 * Applies this style to the given text and returns the result.
	 * @param text The text to be styled.
	 * @return The formatted text.
	 */
	SpannableString formatText(CharSequence text);

	/**
	 * Returns to the default style.
	 */
	void reset();

	/**
	 * Format the given text and return a builder which can be used to format and append additional text.
	 * @param text The text to format and start off the builder with.
	 * @return
	 */
	SpannableStringBuilder startFormattedText(String text);
}

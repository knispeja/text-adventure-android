package knispeja.textadventure.ui.style;

public interface ITextContainer
{
	/**
	 * Removes all text from the text log.
	 */
	void clear();

	/**
	 * Gets the style that will be used for any new text added to the log.
	 * @return The style that can be manipulated to format new text in the log.
	 */
	IScrollingTextStyle getTextStyle();

	/**
	 * Sets the log to a combination of all input text.
	 * @param textArgs Text to combine into the log, in order.
	 */
	void set(final CharSequence... textArgs);
}

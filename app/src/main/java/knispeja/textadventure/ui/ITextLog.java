package knispeja.textadventure.ui;

import knispeja.textadventure.ui.style.IScrollingTextContainer;

/**
 * Thread-safe object for interacting with a scrolling, loggable text view.
 */
interface ITextLog extends IScrollingTextContainer
{
	/**
	 * Adds the given text to a new line at the head of the text log.
	 * @param textToAdd Text to add to the text log.
	 */
	void appendWithNewLine(final CharSequence textToAdd);

	/**
	 * Adds the given text to the head of the text log.
	 * @param textToAdd Text to add to the text log.
	 */
	void append(final CharSequence textToAdd);

	/**
	 * Creates an empty line at the head of the text log.
	 */
	void nextLine();

	/**
	 * Force the scroll view containing the text log to the bottom.
	 */
	void scrollToBottom();
}

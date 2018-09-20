package knispeja.textadventure.ui.style;

public interface IScrollingTextContainer extends ITextContainer
{
	/**
	 * Gets the style that will be used for any new text added to the log.
	 * @return The style that can be manipulated to format new text in the log.
	 */
	IScrollingTextStyle getTextStyle();
}

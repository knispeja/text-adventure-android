package knispeja.textadventure.ui.style;

public interface IScrollingTextStyle extends ITextStyle
{
	/**
	 * Gets the time to delay when printing most characters.
	 * @return Time to delay for a character in milliseconds.
	 */
	int getDelayForTypicalCharacterMs();

	/**
	 * Gets the time to delay when printing the given character.
	 * @param characterToGetDelayFor The particular character to retrieve the delay for.
	 * @return Time to delay for the character in milliseconds.
	 */
	int getDelayForCharacterMs(final char characterToGetDelayFor);

	/**
	 * Sets the time to delay when printing the given character.
	 * @param characterToSetDelayFor The particular character to set the delay for.
	 * @param characterDelayMs Time to delay for the given character in milliseconds.
	 * @return The previous value for the delay for the given character in milliseconds.
	 */
	int setDelayForCharacterMs(final char characterToSetDelayFor, final int characterDelayMs);

	/**
	 * Sets the time to delay when printing most characters.
	 * @param typicalCharacterDelayMs Time to delay for a character in milliseconds.
	 */
	void setTypicalCharacterDelayMs(final int typicalCharacterDelayMs);
}

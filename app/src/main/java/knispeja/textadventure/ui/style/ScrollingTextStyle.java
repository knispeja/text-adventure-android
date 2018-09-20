package knispeja.textadventure.ui.style;

import java.util.HashMap;

public class ScrollingTextStyle extends TextStyle implements IScrollingTextStyle
{
	private static final int DEFAULT_CHARACTER_DELAY_MS = 6;

	private int typicalCharacterDelayMs = DEFAULT_CHARACTER_DELAY_MS;
	private HashMap<Character, Integer> characterToSpecialDelayMs = new HashMap<Character, Integer>(3)
	{{
		put(',', 30);
		put('.', 60);
	}};

	public int getDelayForTypicalCharacterMs()
	{
		return this.typicalCharacterDelayMs;
	}

	public int getDelayForCharacterMs(final char characterToGetDelayFor)
	{
		Integer delay = characterToSpecialDelayMs.get(characterToGetDelayFor);
		if (delay == null)
		{
			delay = this.typicalCharacterDelayMs;
		}
		return delay;
	}

	public int setDelayForCharacterMs(final char characterToSetDelayFor, final int characterDelayMs)
	{
		Integer oldValue = this.characterToSpecialDelayMs.put(characterToSetDelayFor, characterDelayMs);
		if (oldValue == null)
		{
			oldValue = DEFAULT_CHARACTER_DELAY_MS;
		}
		return oldValue;
	}

	public void setTypicalCharacterDelayMs(final int typicalCharacterDelayMs)
	{
		this.typicalCharacterDelayMs = typicalCharacterDelayMs;
	}
}

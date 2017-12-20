package com.scrabble.model;

public enum IADifficulties {
    EASY("Facile"),NORMAL("Normal"),HARD("Difficile");

    private final String displayName;

    IADifficulties(final String display)
    {
        this.displayName = display;
    }

    @Override public String toString()
    {
        return this.displayName;
    }
}


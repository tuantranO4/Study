package org.gamelogic;


public enum LevelItem {
    DRAKE('$'), DESTINATION('.'), WALL('#'), EMPTY(' ');
    public final char representation;
    LevelItem(char rep){ representation = rep; }
}


package org.example.factions.factionsmain.factions;

import java.util.List;

public class FactionPlayer {
    public FactionPlayer(List<String> flags) {
        this.flags = flags;
    }

    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public void addFlag(String flag) {
        flags.add(flag);
    }

    /* Contains flags describing the players role and permissions within the faction */
    private List<String> flags;
}

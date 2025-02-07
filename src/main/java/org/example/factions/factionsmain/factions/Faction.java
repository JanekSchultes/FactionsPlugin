package org.example.factions.factionsmain.factions;

import java.util.HashMap;
import java.util.UUID;

public class Faction {
    public Faction() {
        this.members = new HashMap<>();
    }

    public void addUser(UUID uuid, FactionPlayer player) {
        members.put(uuid, player);
    }

    private HashMap<UUID, FactionPlayer> members;
}

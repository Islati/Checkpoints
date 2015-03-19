package com.caved_in.checkpoints.user;

import com.caved_in.commons.player.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CheckpointUser extends User {
    private Location previousCheckpoint = null;

    public CheckpointUser(Player p) {
        super(p);
    }

    public Location getCheckpoint() {
        return previousCheckpoint;
    }

    public boolean hasCheckpoint() {
        return previousCheckpoint != null;
    }

    public void setCheckpoint(Location loc) {
        this.previousCheckpoint = loc;
    }
}

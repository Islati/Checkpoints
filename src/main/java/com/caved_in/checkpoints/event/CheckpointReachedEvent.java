package com.caved_in.checkpoints.event;

import com.caved_in.checkpoints.Checkpoints;
import com.caved_in.checkpoints.user.CheckpointUser;
import com.caved_in.checkpoints.user.CheckpointUserManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CheckpointReachedEvent extends PlayerEvent implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    private static CheckpointUserManager userManager = null;

    private Location checkpoint;

    private boolean cancelled = false;

    public CheckpointReachedEvent(Player who, Location loc) {
        super(who);

        if (userManager == null) {
            userManager = Checkpoints.getInstance().getUserManager();
        }

        this.checkpoint = loc;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Location getCheckpoint() {
        return checkpoint;
    }

    public void handle() {
        CheckpointUser user = userManager.getUser(player);
        user.setCheckpoint(checkpoint);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}

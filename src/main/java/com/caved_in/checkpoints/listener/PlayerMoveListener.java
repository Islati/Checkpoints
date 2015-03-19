package com.caved_in.checkpoints.listener;

import com.caved_in.checkpoints.Checkpoints;
import com.caved_in.checkpoints.event.CheckpointReachedEvent;
import com.caved_in.checkpoints.location.CheckpointLocations;
import com.caved_in.checkpoints.user.CheckpointUser;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private static Checkpoints plugin = Checkpoints.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Location to = e.getTo();
        Location from = e.getFrom();

        if (e.isCancelled()){
            return;
        }

        CheckpointLocations checkpointLocations = plugin.getCheckpointManager();
        if (!checkpointLocations.isCheckpoint(to)) {
            return;
        }

        CheckpointReachedEvent checkpointReachedEvent = new CheckpointReachedEvent(e.getPlayer(),to);
        Plugins.callEvent(checkpointReachedEvent);
        checkpointReachedEvent.handle();
    }
}

package com.caved_in.checkpoints.command;

import com.caved_in.checkpoints.Checkpoints;
import com.caved_in.checkpoints.location.CheckpointLocations;
import com.caved_in.checkpoints.user.CheckpointUser;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.player.Players;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CheckpointCommand {

    private Checkpoints plugin = Checkpoints.getInstance();

    @Command(identifier = "checkpoint")
    public void onCheckpointCommand(Player p) {
        CheckpointUser user = plugin.getUserManager().getUser(p);

        if (!user.hasCheckpoint()) {
            Chat.actionMessage(p,"&eYou've not crossed any checkpoints!");
            return;
        }

        Players.teleport(p,user.getCheckpoint());
        Chat.actionMessage(p,"&aYou were brought to your last checkpoint!");
    }

    @Command(identifier = "checkpoint add",permissions = {"checkpoints.add"})
    @Flags(identifier = {"-cursor"})
    public void onCheckpointAddCommand(Player p, @FlagArg("-cursor")boolean cursor) {
        Location checkpoint = null;

        if (cursor) {
            checkpoint = Players.getTargetLocation(p);
        } else {
            checkpoint = p.getLocation();
        }

        CheckpointLocations checkpoints = plugin.getCheckpointManager();
        if (checkpoints.isCheckpoint(checkpoint)) {
            Chat.actionMessage(p,"&eThat checkpoint already exists!");
            return;
        }

        checkpoints.addCheckpoint(checkpoint);

        Chat.actionMessage(p,String.format("&aCheckpoint added @ &e%s", Messages.locationCoords(checkpoint)));
    }
}

package com.caved_in.checkpoints.listener;

import com.caved_in.checkpoints.Checkpoints;
import com.caved_in.checkpoints.PluginProperties;
import com.caved_in.checkpoints.event.CheckpointReachedEvent;
import com.caved_in.checkpoints.gadget.CheckpointGadget;
import com.caved_in.checkpoints.user.CheckpointUser;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CheckpointListener implements Listener {

    private static Title checkpointReachedTitle;

    private static Checkpoints checkpoints;

    public CheckpointListener() {
        checkpoints = Checkpoints.getInstance();
        checkpointReachedTitle  = new TitleBuilder().subtitle("You've reached a checkpoint!").subtitleColor(ChatColor.GREEN).fadeIn(0).stay(2).fadeOut(1).seconds().build();
    }

    @EventHandler
    public void onCheckpointReach(CheckpointReachedEvent e) {
        Location checkpoint = e.getCheckpoint();

        Player p = e.getPlayer();

        PlayerInventory inv = p.getInventory();

        ItemStack checkpointGadget = CheckpointGadget.getInstance().getItem();

        if (!Inventories.contains(inv, checkpointGadget)) {
            Players.setHotbarItem(p, checkpointGadget, PluginProperties.GADGET_SLOT);
        }

        CheckpointUser user = checkpoints.getUserManager().getUser(p);

        Location userCheckpoint = user.getCheckpoint();

        if (user.hasCheckpoint() && Locations.isInRadius(checkpoint, userCheckpoint, 1)) {
            Chat.debug("Player is still in the same checkpoint as previous");
            e.setCancelled(true);
            return;
        }

        //Check if the player is passing through their previous checkpoint, and if so stop.
        checkpointReachedTitle.send(e.getPlayer());

        Chat.actionMessage(e.getPlayer(), "&aReturn here by using your '&eReturn to Checkpoint'&a gadget");

        ParticleEffects.sendToLocation(ParticleEffects.INSTANT_SPELL, checkpoint, NumberUtil.getRandomInRange(5, 10));
    }
}

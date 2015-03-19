package com.caved_in.checkpoints.gadget;

import com.caved_in.checkpoints.Checkpoints;
import com.caved_in.checkpoints.user.CheckpointUser;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CheckpointGadget extends ItemGadget {
    private static Checkpoints plugin = Checkpoints.getInstance();

    private static CheckpointGadget instance = null;

    public static CheckpointGadget getInstance() {
        if (instance == null) {
            instance = new CheckpointGadget();
        }

        return instance;
    }

    protected CheckpointGadget() {
        super(ItemBuilder.of(Material.PAPER).name("&eReturn to Checkpoint").lore("Right click to return to","your most recent checkpoint!").item());
    }

    @Override
    public int id() {
        return 13337;
    }

    @Override
    public void perform(Player p) {
        CheckpointUser user = plugin.getUserManager().getUser(p);

        if (!user.hasCheckpoint()) {
            Chat.actionMessage(p, "&eYou've not crossed any checkpoints!");
            return;
        }

        Players.teleport(p, user.getCheckpoint());
        Chat.actionMessage(p,"&aYou were brought to your last checkpoint!");
    }
}

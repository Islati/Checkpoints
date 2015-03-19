package com.caved_in.checkpoints.user;

import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;

public class CheckpointUserManager extends UserManager<CheckpointUser> {

    public CheckpointUserManager() {
        super(CheckpointUser.class);
    }
}

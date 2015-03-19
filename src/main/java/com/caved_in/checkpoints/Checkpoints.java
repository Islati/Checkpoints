package com.caved_in.checkpoints;

import com.caved_in.checkpoints.command.CheckpointCommand;
import com.caved_in.checkpoints.gadget.CheckpointGadget;
import com.caved_in.checkpoints.listener.CheckpointListener;
import com.caved_in.checkpoints.listener.PlayerMoveListener;
import com.caved_in.checkpoints.location.CheckpointLocations;
import com.caved_in.checkpoints.user.CheckpointUserManager;
import com.caved_in.commons.game.listener.GameConnectionListener;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.utilities.Str;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
public class Checkpoints extends BukkitPlugin {
    private static Checkpoints instance = null;

    private CheckpointUserManager userManager;

    private CheckpointLocations checkpointManager;


    private final String checkpointsFile = "plugins/Checkpoints/Checkpoints.xml";

    public static Checkpoints getInstance() {
        return instance;
    }

    @Override
    public void startup() {
        instance = this;

        userManager = new CheckpointUserManager();

        registerListeners(
                new GameConnectionListener(userManager),
                new PlayerMoveListener(),
                new CheckpointListener()
        );

        registerCommands(
                new CheckpointCommand()
        );

        registerGadgets(
                CheckpointGadget.getInstance()
        );

    }

    @Override
    public void shutdown() {
        /*
        Save all the checkpoints in the manager! Just to be safe that we don't miss any!
         */
//        checkpointManager.getSerializableLocations().stream().forEach(this::saveCheckpoints);
        saveCheckpoints();
    }

    @Override
    public String getVersion() {
        return "Brandon Curtis";
    }

    @Override
    public String getAuthor() {
        return "1.1-RELEASE";
    }

    public void saveCheckpoints() {
        Serializer serializer = new Persister();
        File checkpointFile = new File(this.checkpointsFile);

        try {
            serializer.write(checkpointManager, checkpointFile);
//            getLogger().info("Saved " + fileName + " to file!");
        } catch (Exception e) {
            getLogger().severe(Str.getStackStr(e));
        }
    }

    @Override
    public void initConfig() {
        File checkpointsManagerFile = new File(checkpointsFile);

        Serializer serializer = new Persister();

        if (!checkpointsManagerFile.exists()) {
            try {
                serializer.write(new CheckpointLocations(),checkpointsManagerFile);
                debug("Created the default checkpoints manager");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            CheckpointLocations checkLocs = serializer.read(CheckpointLocations.class, checkpointsManagerFile);
            debug("Loaded the checkpoint location from disk!");
            this.checkpointManager = checkLocs;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CheckpointUserManager getUserManager() {
        return userManager;
    }

    public CheckpointLocations getCheckpointManager() {
        return checkpointManager;
    }
}

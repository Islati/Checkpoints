package com.caved_in.checkpoints.location;

import com.caved_in.checkpoints.Checkpoints;
import com.caved_in.checkpoints.PluginProperties;
import com.caved_in.commons.config.XmlLocation;
import com.caved_in.commons.location.Locations;
import org.bukkit.Location;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "checkpoint-locations")
public class CheckpointLocations {
    @ElementList(name = "checkpoints",entry = "checkpoint",type = XmlLocation.class)
    private List<XmlLocation> checkpoints = new ArrayList<>();


    public CheckpointLocations(@ElementList(name = "checkpoints",entry = "checkpoint",type = XmlLocation.class)List<XmlLocation> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public CheckpointLocations() {

    }

    public void addCheckpoint(Location loc) {
        checkpoints.add(XmlLocation.fromLocation(loc));
        Checkpoints.getInstance().saveCheckpoints();
    }

    public boolean isCheckpoint(Location loc) {
        if (checkpoints.isEmpty()) {
            return false;
        }

        for(Location checkpoint : checkpoints) {
            /*
            Check if the location given is within the means of a check point
             */
            if (!Locations.isInRadius(checkpoint,loc, PluginProperties.CHECKPOINT_REACH_RADIUS)) {
                continue;
            }
            return true;
        }
        return false;
    }

}

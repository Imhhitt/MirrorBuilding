package dev.smartshub.mirrorBuilding.listeners;

import dev.smartshub.mirrorBuilding.MirrorBuilding;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final MirrorBuilding plugin;

    public BlockBreakListener(MirrorBuilding plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBlockBreak (BlockBreakEvent event){

        if(!plugin.getReferenceMap().containsKey(event.getPlayer().getUniqueId())){
            return;
        }

        if(!plugin.getReferenceMap().get(event.getPlayer().getUniqueId()).getWorld().getName().equalsIgnoreCase(
                event.getBlock().getLocation().getWorld().getName())){
            return;
        }

        breakMirror(event.getBlock().getLocation(), event.getPlayer().getUniqueId(), event.getBlock());
    }




    private void breakMirror(Location brokenLocation, UUID playerUUID, Block brokenBlock) {
        // Obtain reference from HashMap
        Location referenceLocation = plugin.getReferenceMap().get(playerUUID);
        if (referenceLocation == null) {
            return;
        }

        // Obtain world
        var world = brokenLocation.getWorld();

        // Calculate mirrored locations
        Location mirroredX = new Location(world,
                referenceLocation.getX() - (brokenLocation.getX() - referenceLocation.getX()),
                brokenLocation.getY(),
                brokenLocation.getZ());

        Location mirroredZ = new Location(world,
                brokenLocation.getX(),
                brokenLocation.getY(),
                referenceLocation.getZ() - (brokenLocation.getZ() - referenceLocation.getZ()));

        Location mirroredXZ = new Location(world,
                mirroredX.getX(),
                brokenLocation.getY(),
                mirroredZ.getZ());

        Location mirroredY = new Location(world,
                brokenLocation.getX(),
                referenceLocation.getY() - (brokenLocation.getY() - referenceLocation.getY()),
                brokenLocation.getZ());

        // Reflect across X and Y axes
        Location mirroredXY = new Location(world,
                mirroredX.getX(),
                mirroredY.getY(),
                brokenLocation.getZ());

        // Reflect across Z and Y axes
        Location mirroredZY = new Location(world,
                brokenLocation.getX(),
                mirroredY.getY(),
                mirroredZ.getZ());

        // Reflect across all three axes
        Location mirroredXYZ = new Location(world,
                mirroredX.getX(),
                mirroredY.getY(),
                mirroredZ.getZ());

        // Mirror breaking
        breakBlockAtLocation(mirroredX);
        breakBlockAtLocation(mirroredZ);
        breakBlockAtLocation(mirroredXZ);
        breakBlockAtLocation(mirroredY);
        breakBlockAtLocation(mirroredXY);
        breakBlockAtLocation(mirroredZY);
        breakBlockAtLocation(mirroredXYZ);
    }

    // Method to break a block at a given location
    private void breakBlockAtLocation(Location location) {
        Block blockToBreak = location.getBlock();
        blockToBreak.setType(Material.AIR); // Removes the block by setting its type to AIR
    }


}

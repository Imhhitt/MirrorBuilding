package dev.smartshub.mirrorBuilding.listeners;

import dev.smartshub.mirrorBuilding.MirrorBuilding;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class BlockPlaceListener implements Listener {

    private final MirrorBuilding plugin;

    public BlockPlaceListener(MirrorBuilding plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBlockPlace (BlockPlaceEvent event){

        if(!plugin.getReferenceMap().containsKey(event.getPlayer().getUniqueId())){
            return;
        }

        if(!plugin.getReferenceMap().get(event.getPlayer().getUniqueId()).getWorld().getName().equalsIgnoreCase(
                event.getBlock().getLocation().getWorld().getName())){
            return;
        }

        buildMirror(event.getBlock().getLocation(), event.getPlayer().getUniqueId(), event.getBlock());
    }


    private void buildMirror(Location placedLocation, UUID playerUUID, Block placedBlock) {
        // Obtain reference from HashMap
        Location referenceLocation = plugin.getReferenceMap().get(playerUUID);

        // Get the world
        var world = placedLocation.getWorld();

        // Calculate mirrored coordinates
        // Reflect across the X-axis
        Location mirroredX = new Location(world,
                referenceLocation.getX() - (placedLocation.getX() - referenceLocation.getX()),
                placedLocation.getY(),
                placedLocation.getZ());

        // Reflect across the Z-axis
        Location mirroredZ = new Location(world,
                placedLocation.getX(),
                placedLocation.getY(),
                referenceLocation.getZ() - (placedLocation.getZ() - referenceLocation.getZ()));

        // Reflect across both X and Z axes
        Location mirroredXZ = new Location(world,
                mirroredX.getX(),
                placedLocation.getY(),
                mirroredZ.getZ());

        // Reflect across the Y-axis
        Location mirroredY = new Location(world,
                placedLocation.getX(),
                referenceLocation.getY() - (placedLocation.getY() - referenceLocation.getY()),
                placedLocation.getZ());

        // Reflect across X and Y axes
        Location mirroredXY = new Location(world,
                mirroredX.getX(),
                mirroredY.getY(),
                placedLocation.getZ());

        // Reflect across Z and Y axes
        Location mirroredZY = new Location(world,
                placedLocation.getX(),
                mirroredY.getY(),
                mirroredZ.getZ());

        // Reflect across all three axes
        Location mirroredXYZ = new Location(world,
                mirroredX.getX(),
                mirroredY.getY(),
                mirroredZ.getZ());

        // Mirror the block in all calculated locations
        placeBlockAtLocation(mirroredX, placedBlock);
        placeBlockAtLocation(mirroredZ, placedBlock);
        placeBlockAtLocation(mirroredXZ, placedBlock);
        placeBlockAtLocation(mirroredY, placedBlock);
        placeBlockAtLocation(mirroredXY, placedBlock);
        placeBlockAtLocation(mirroredZY, placedBlock);
        placeBlockAtLocation(mirroredXYZ, placedBlock);
    }


    private void placeBlockAtLocation(Location location, Block originalBlock) {
        Block blockToPlace = location.getBlock();
        blockToPlace.setType(originalBlock.getType());
        blockToPlace.setBlockData(originalBlock.getBlockData());
    }


}

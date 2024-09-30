package dev.smartshub.mirrorBuilding;

import com.sun.tools.javac.Main;
import dev.smartshub.mirrorBuilding.commands.MainCommand;
import dev.smartshub.mirrorBuilding.commands.TabCompletion;
import dev.smartshub.mirrorBuilding.config.MainConfig;
import dev.smartshub.mirrorBuilding.config.MainConfigManager;
import dev.smartshub.mirrorBuilding.listeners.BlockBreakListener;
import dev.smartshub.mirrorBuilding.listeners.BlockPlaceListener;
import dev.smartshub.mirrorBuilding.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import java.util.HashMap;
import java.util.UUID;

public final class MirrorBuilding extends JavaPlugin {

    // Declara el HashMap como variable de instancia
    private HashMap<UUID, Location> referenceMap;
    private int maxSize;
    private BukkitAudiences adventure;

    public BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        referenceMap = new HashMap<>();
        this.adventure = BukkitAudiences.create(this);
        MainConfigManager mainConfigManager = new MainConfigManager(this);
        mainConfigManager.loadConfig();
        MessageUtils.setAdventure(this.adventure);

        maxSize = getConfig().getInt("Mirror-Max-Size");

        getCommand("mirrorbuilding").setExecutor(new MainCommand(this));
        getCommand("mirrorbuilding").setTabCompleter(new TabCompletion());
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
    }

    @Override
    public void onDisable() {
    }

    // Get the HashMap
    public HashMap<UUID, Location> getReferenceMap() {
        return referenceMap;
    }

    // Get the max radius size
    public int getMaxSize(){
        return maxSize;
    }

    public void setMaxSixe(int radius){
        maxSize = radius;
    }
}


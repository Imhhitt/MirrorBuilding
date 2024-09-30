package dev.smartshub.mirrorBuilding.config;

import dev.smartshub.mirrorBuilding.MirrorBuilding;
import org.bukkit.configuration.file.FileConfiguration;

public class MainConfigManager {

    // Manejar la config.yml
    private final MainConfig configFile;


    // Cargar la configuración
    public MainConfigManager(MirrorBuilding LuckyPillars) {
        configFile = new MainConfig("config.yml", null, LuckyPillars);
        configFile.registerConfig();
        loadConfig();
    }

    // Cargar los mensajes de la config en variables
    public void loadConfig(){
        FileConfiguration configuration = configFile.getConfig();
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }


}
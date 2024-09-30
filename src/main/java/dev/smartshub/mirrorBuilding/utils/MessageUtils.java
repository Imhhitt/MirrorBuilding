package dev.smartshub.mirrorBuilding.utils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


public class MessageUtils {

    private static BukkitAudiences adventure;

    public static void setAdventure(BukkitAudiences adventureInstance) {
        adventure = adventureInstance;
    }

    public static @NotNull String miniMessageParse(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        return LegacyComponentSerializer.legacySection().serialize(text);
    }

    public static @NotNull String miniMessageParseWithLocation(String message, Location location) {
        // Replace %reference% with coords
        String formattedLocation = formatLocation(location);
        message = message.replace("%reference%", formattedLocation);

        MiniMessage miniMessage = MiniMessage.miniMessage();
        TextComponent text = (TextComponent) miniMessage.deserialize(message);
        return LegacyComponentSerializer.legacySection().serialize(text);
    }

    private static @NotNull String formatLocation(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return x + ", " + y + ", " + z;
    }

}

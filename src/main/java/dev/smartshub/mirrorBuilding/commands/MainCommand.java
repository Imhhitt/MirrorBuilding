package dev.smartshub.mirrorBuilding.commands;

import dev.smartshub.mirrorBuilding.MirrorBuilding;
import dev.smartshub.mirrorBuilding.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MainCommand implements CommandExecutor {


    private final MirrorBuilding plugin;

    public MainCommand(MirrorBuilding plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        // /Mirror enable
        if(args.length == 1 && args[0].equalsIgnoreCase("enable")){

            // Sender is not a player, the return
            if(!(sender instanceof Player player)){
                sender.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.Only-Players-Command")));
                return true;
            }

            // Permission check for sender
            if(!(sender.hasPermission("mirrorbuilding.command"))){
                player.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.No-Permission")));
            }

            if(player.getTargetBlockExact(100) == null){
                player.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.Not-A-Valid-Block")));
                return true;
            }

            // Get values and add player to the HashMap
            Location location = player.getTargetBlockExact(plugin.getMaxSize()).getLocation();
            UUID uuid = player.getUniqueId();
            plugin.getReferenceMap().put(uuid, location);

            player.sendMessage(MessageUtils.miniMessageParseWithLocation(plugin.getConfig().getString("Messages.Mirror-Enabled"), location));
        }


        // /Mirror disable
        if(args.length == 1 && args[0].equalsIgnoreCase("disable")){

            // Sender is not a player, the return
            if(!(sender instanceof Player player)){
                sender.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.Only-Players-Command")));
                return true;
            }

            // Permission check for sender
            if(!(sender.hasPermission("mirrorbuilding.command"))){
                player.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.No-Permission")));
            }

            // Get the value and remove player from the HashMap
            UUID uuid = player.getUniqueId();
            plugin.getReferenceMap().remove(uuid);

            player.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.Mirror-Disabled")));


        }


        // /Mirror reload
        if(args.length == 1 && args[0].equalsIgnoreCase("reload")){

            // Sender is not a player, the return
            if(!(sender instanceof Player player)){
                sender.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.Only-Players-Command")));
                return true;
            }

            // Permission check for sender
            if(!(sender.hasPermission("mirrorbuilding.reload"))){
                player.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.No-Permission")));
            }

            plugin.reloadConfig();
            plugin.setMaxSixe(plugin.getConfig().getInt("Mirror-Max-Size"));
            player.sendMessage(MessageUtils.miniMessageParse(plugin.getConfig().getString("Messages.Config-Reloaded")));

        }



        return true;
    }
}

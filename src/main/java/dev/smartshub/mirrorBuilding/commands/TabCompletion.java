package dev.smartshub.mirrorBuilding.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if(!sender.hasPermission("mirrorbuilding.command")){
            return completions;
        }
        if (args.length == 1) {
            completions.add("enable");
            completions.add("disable");
        }
        if(sender.hasPermission("mirrorbuilding.reload")){
            completions.add("reload");
        }
        return completions;
    }


}


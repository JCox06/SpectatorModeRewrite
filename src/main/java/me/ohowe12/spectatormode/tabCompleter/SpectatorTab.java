/*
 * SpectatorModeRewrite
 *
 * Copyright (c) 2020. Oliver Howe
 *
 * MIT License
 */

package me.ohowe12.spectatormode.tabcompleter;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpectatorTab implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command,
            @NotNull final String alias, @NotNull final String[] args) {
        final List<String> arguments = new ArrayList<>();
        if (sender.hasPermission("smpspectator.force")) {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                arguments.add(p.getName());
            }
        }
        if (sender.hasPermission("smpspectator.enable")) {
            arguments.add("enable");
            arguments.add("disable");
            arguments.add("reload");
        }

        return TabCompleteUtil.getStrings(args, arguments);
    }
}


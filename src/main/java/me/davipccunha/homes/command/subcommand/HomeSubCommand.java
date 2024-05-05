package me.davipccunha.homes.command.subcommand;

import org.bukkit.entity.Player;

public interface HomeSubCommand {
    boolean execute(Player player, String[] args);

    String getUsage();
}

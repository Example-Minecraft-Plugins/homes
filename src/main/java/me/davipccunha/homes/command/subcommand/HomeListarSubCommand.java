package me.davipccunha.homes.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.homes.HomesPlugin;
import me.davipccunha.homes.impl.HomeUserImpl;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class HomeListarSubCommand implements HomeSubCommand {
    private final HomesPlugin plugin;

    @Override
    public boolean execute(Player player, String[] args) {
        final HomeUserImpl homeUser = plugin.getHomeUserCache().get(player.getName().toLowerCase());

        final int count = homeUser == null ? 0 : homeUser.getHomeCount();

        player.sendMessage(String.format("§aVocê possui §f%d §ahomes salvas:", count));

        final String homeList = homeUser == null ? "" : homeUser.getHomeNames().toString();

        if (count > 0) player.sendMessage(homeList.substring(1, homeList.length() - 1));

        return true;
    }

    @Override
    public String getUsage() {
        return "/home listar";
    }
}

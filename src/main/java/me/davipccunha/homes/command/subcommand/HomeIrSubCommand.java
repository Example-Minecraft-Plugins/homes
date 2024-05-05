package me.davipccunha.homes.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.homes.HomesPlugin;
import me.davipccunha.homes.impl.HomeUserImpl;
import me.davipccunha.homes.model.HomeLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class HomeIrSubCommand implements HomeSubCommand {
    private final HomesPlugin plugin;

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length <= 1) return false;

        final String homeName = args[1].toLowerCase();

        final HomeUserImpl homeUser = plugin.getHomeUserCache().get(player.getName());

        if (homeUser == null || !homeUser.hasHome(homeName)) {
            player.sendMessage("§cVocê não tem uma home com esse nome.");
            return true;
        }

        final HomeLocation homeLocation = homeUser.getHome(homeName);

        final Location teleportLocation = new Location(plugin.getServer().getWorld(homeLocation.getWorldName()),
                homeLocation.getX(), homeLocation.getY(), homeLocation.getZ(),
                homeLocation.getYaw(), homeLocation.getPitch());

        player.teleport(teleportLocation);
        player.sendMessage(String.format("§aVocê foi teleportado para sua home §f%s §acom sucesso.", homeName));

        return true;
    }

    @Override
    public String getUsage() {
        return "§e/home ir <nome>";
    }
}

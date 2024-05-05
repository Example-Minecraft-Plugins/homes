package me.davipccunha.homes.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.homes.HomesPlugin;
import me.davipccunha.homes.impl.HomeUserImpl;
import me.davipccunha.homes.model.HomeLocation;
import me.davipccunha.utils.cache.RedisCache;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class HomeDefinirSubCommand implements HomeSubCommand {
    private final HomesPlugin plugin;

    @Override
    public boolean execute(Player player, String[] args) {
        final RedisCache<HomeUserImpl> cache = plugin.getHomeUserCache();

        if (args.length <= 1) return false;

        final String homeName = args[1].toLowerCase();

        HomeUserImpl homeUser = cache.get(player.getName());

        if (homeUser == null) {
            homeUser = new HomeUserImpl(player.getName());
            cache.add(player.getName(), homeUser);
        }

        final Location playerLocation = player.getLocation();

        final HomeLocation homeLocation = new HomeLocation(playerLocation.getWorld().getName(),
                playerLocation.getX(), playerLocation.getY(), playerLocation.getZ(),
                playerLocation.getPitch(), playerLocation.getYaw());

        homeUser.createHome(homeName, homeLocation);
        cache.add(player.getName(), homeUser);

        player.sendMessage(String.format("§aHome §f%s §acriada com sucesso.", homeName));

        return true;
    }

    @Override
    public String getUsage() {
        return "§e/home definir <nome>";
    }
}

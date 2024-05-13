package me.davipccunha.homes.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.homes.HomesPlugin;
import me.davipccunha.homes.impl.HomeUserImpl;
import me.davipccunha.homes.model.HomeLocation;
import me.davipccunha.utils.cache.RedisCache;
import me.davipccunha.utils.player.PermissionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class HomeDefinirSubCommand implements HomeSubCommand {
    private final HomesPlugin plugin;

    @Override
    public boolean execute(Player player, String[] args) {
        final RedisCache<HomeUserImpl> cache = plugin.getHomeUserCache();

        if (args.length <= 1) return false;

        HomeUserImpl homeUser = cache.get(player.getName().toLowerCase());

        if (homeUser == null) {
            homeUser = new HomeUserImpl(player.getName());
            cache.add(player.getName().toLowerCase(), homeUser);
        }

        Optional<PermissionAttachmentInfo> maxLimitPermission = player.getEffectivePermissions().stream()
                .filter(permission -> permission.getPermission().startsWith("homes.limit."))
                .max(Comparator.comparingInt(permission -> NumberUtils.toInt(permission.getPermission().split("\\.")[2])));

        final int defaultMaxLimit = plugin.getConfig().getInt("default-max-limit");

        final int maxLimit = maxLimitPermission.map(permissionAttachmentInfo ->
                PermissionUtils.extractNumberSuffix(permissionAttachmentInfo.getPermission())).orElse(defaultMaxLimit);

        if (homeUser.getHomeCount() >= maxLimit && !player.hasPermission("homes.admin.no-limit")) {
            player.sendMessage("§cVocê já atingiu seu limite de homes.");
            return true;
        }

        final String homeName = args[1].toLowerCase();

        final Location playerLocation = player.getLocation();

        final HomeLocation homeLocation = new HomeLocation(playerLocation.getWorld().getName(),
                playerLocation.getX(), playerLocation.getY(), playerLocation.getZ(),
                playerLocation.getPitch(), playerLocation.getYaw());

        homeUser.createHome(homeName, homeLocation);
        cache.add(player.getName().toLowerCase(), homeUser);

        player.sendMessage(String.format("§aHome §f%s §acriada com sucesso.", homeName));

        return true;
    }

    @Override
    public String getUsage() {
        return "/home definir <nome>";
    }
}

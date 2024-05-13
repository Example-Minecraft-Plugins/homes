package me.davipccunha.homes.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.homes.HomesPlugin;
import me.davipccunha.homes.impl.HomeUserImpl;
import me.davipccunha.utils.cache.RedisCache;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class HomeDeletarSubCommand implements HomeSubCommand {
    private final HomesPlugin plugin;

    @Override
    public boolean execute(Player player, String[] args) {
        final RedisCache<HomeUserImpl> cache = plugin.getHomeUserCache();

        if (args.length <= 1) return false;

        final String homeName = args[1].toLowerCase();

        final HomeUserImpl homeUser = cache.get(player.getName().toLowerCase());

        if (homeUser == null || !homeUser.hasHome(homeName)) {
            player.sendMessage("§cVocê não tem uma home com esse nome.");
            return true;
        }

        homeUser.deleteHome(homeName);

        if (homeUser.getHomeCount() <= 0) {
            cache.remove(player.getName().toLowerCase());
        } else {
            cache.add(player.getName().toLowerCase(), homeUser);
        }

        player.sendMessage(String.format("§aHome §f%s §adeletada com sucesso.", homeName));

        return true;
    }

    @Override
    public String getUsage() {
        return "/home deletar <nome>";
    }
}

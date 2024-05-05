package me.davipccunha.homes;

import lombok.Getter;
import me.davipccunha.homes.command.HomeCommand;
import me.davipccunha.homes.impl.HomeUserImpl;
import me.davipccunha.utils.cache.RedisCache;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HomesPlugin extends JavaPlugin {

    private RedisCache<HomeUserImpl> homeUserCache;

    @Override
    public void onEnable() {
        this.init();
        getLogger().info("Homes plugin loaded!");
    }

    public void onDisable() {
        getLogger().info("Homes plugin unloaded!");
    }

    private void init() {
        this.registerListeners();
        this.registerCommands();

        saveDefaultConfig();

        this.loadCaches();
    }

    private void registerListeners() {
        // getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    private void registerCommands() {
        this.getCommand("home").setExecutor(new HomeCommand(this));
    }

    private void loadCaches() {
        this.homeUserCache = new RedisCache<>("homes", HomeUserImpl.class);
    }
}

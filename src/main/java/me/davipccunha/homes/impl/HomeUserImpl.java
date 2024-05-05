package me.davipccunha.homes.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.davipccunha.homes.model.HomeLocation;
import me.davipccunha.homes.model.HomeUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class HomeUserImpl implements HomeUser {
    private final String playerName;
    private final Map<String, HomeLocation> homes = new HashMap<>();

    @Override
    public void createHome(String name, HomeLocation location) {
        this.homes.put(name, location);
    }

    @Override
    public void deleteHome(String name) {
        this.homes.remove(name);
    }

    @Override
    public HomeLocation getHome(String name) {
        return this.homes.get(name);
    }

    @Override
    public boolean hasHome(String name) {
        return this.homes.containsKey(name);
    }

    @Override
    public int getHomeCount() {
        return this.homes.size();
    }

    @Override
    public Set<String> getHomeNames() {
        return this.homes.keySet();
    }
}

package me.davipccunha.homes.model;

import java.util.Set;

public interface HomeUser {
    void createHome(String name, HomeLocation location);

    void deleteHome(String name);

    HomeLocation getHome(String name);

    boolean hasHome(String name);

    int getHomeCount();

    Set<String> getHomeNames();
}

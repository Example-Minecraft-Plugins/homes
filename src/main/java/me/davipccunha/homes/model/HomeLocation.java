package me.davipccunha.homes.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HomeLocation {
    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
}

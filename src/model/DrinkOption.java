package model;

import util.DrinkIcon;

public class DrinkOption {
    private String name;
    private int size;
    private int waterP;
    private DrinkIcon icon;

    public DrinkOption(String name, DrinkIcon icon, int size, int waterP) {
        this.name = name;
        this.size = size;
        this.waterP = waterP;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWaterP() {
        return waterP;
    }

    public void setWaterP(int waterP) {
        this.waterP = waterP;
    }

    public DrinkIcon getIcon() {
        return icon;
    }

    public void setIcon(DrinkIcon icon) {
        this.icon = icon;
    }
}
package com.ide.codekit.casttotv.Model;

public class CustomBottomNavModel {

    private int itemId;
    private int itemIcon;
    private String itemName;

    public CustomBottomNavModel(int itemId, int itemIcon) {
        this.itemId = itemId;
        this.itemIcon = itemIcon;
    }

    public CustomBottomNavModel(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public CustomBottomNavModel(int itemId, int itemIcon, String itemName) {
        this.itemId = itemId;
        this.itemIcon = itemIcon;
        this.itemName = itemName;
    }
}

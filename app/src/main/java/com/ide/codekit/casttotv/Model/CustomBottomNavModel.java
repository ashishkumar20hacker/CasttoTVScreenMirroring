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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

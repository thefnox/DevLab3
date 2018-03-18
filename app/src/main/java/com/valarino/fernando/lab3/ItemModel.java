package com.valarino.fernando.lab3;


public class ItemModel <T extends ItemDetailFragment> {
    public final String fragmentClass;
    public final String id;
    public final String content;

    public ItemModel(String id, String content, String fragmentClass ) {
        this.fragmentClass = fragmentClass;
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}

package net.guardiandev.terrabuild.backend.api;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ModContent {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }
}

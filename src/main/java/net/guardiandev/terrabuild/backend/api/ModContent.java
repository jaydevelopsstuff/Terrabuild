package net.guardiandev.terrabuild.backend.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ModContent {
    private final Mod parent;
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        parent.dirty();
    }

    public void removeItem(Item item) {
        items.remove(item);
        parent.dirty();
    }
}

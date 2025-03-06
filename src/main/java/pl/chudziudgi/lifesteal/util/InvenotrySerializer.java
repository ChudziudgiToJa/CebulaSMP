package pl.chudziudgi.lifesteal.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Base64;

public class InvenotrySerializer {

    public static String serializeInventory(Inventory inventory) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeInt(inventory.getSize());
            for (ItemStack item : inventory.getContents()) {
                out.writeObject(item);
            }
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Inventory deserializeInventory(String data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(data)); ObjectInputStream in = new ObjectInputStream(bis)) {
            int size = in.readInt();
            Inventory inventory = Bukkit.createInventory(null, size);
            ItemStack[] items = new ItemStack[size];
            for (int i = 0; i < size; i++) {
                items[i] = (ItemStack) in.readObject();
            }
            inventory.setContents(items);
            return inventory;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

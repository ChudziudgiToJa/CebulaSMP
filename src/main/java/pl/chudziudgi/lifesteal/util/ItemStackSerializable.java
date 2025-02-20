package pl.chudziudgi.lifesteal.util;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ItemStackSerializable {

    public static String write(ArrayList<ItemStack> items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeInt(items.size());

            for (ItemStack item : items)
                dataOutput.writeObject(item);

            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception ignored) {
            return "";
        }
    }


    public static String write(ItemStack item) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(item);
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception ignored) {
            return "";
        }
    }

    public static ItemStack readItemStack(String data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) dataInput.readObject();

        } catch (Exception ignored) {
            return null;
        }
    }


    public static ItemStack[] readItemStackList(String source) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(source));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++)
                items[i] = (ItemStack) dataInput.readObject();

            return items;
        } catch (Exception ignored) {
            return new ItemStack[0];
        }
    }
}

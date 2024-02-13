package club.someoneice.togocup.tags;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collection;
import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public class Tag<E> {
    protected final List<E> items;
    public final String name;

    Tag(String name) {
        this.name = name;
        this.items  = Lists.newCopyOnWriteArrayList();
    }

    /**
     * Copy a list from tag. <br />
     * 从Tag复制内容到列表。
     * */
    public List<E> getList() {
        return Lists.newArrayList(items);
    }

    public void put(E item) {
        if (!this.items.contains(item)) this.items.add(item);
    }

    public void addAll(Collection<E> items) {
        this.items.addAll(items);
    }

    /**
     * Return true when object in this tag.<br />
     * 当物件在此标签时返回True。
     * */
    public boolean has(Object item) {
        return item != null && this.items.contains(item);
    }

    /**
     * Return true when object is assign by someone object in tag. <br />
     * 当物件是被Tag中某个物件所继承时返回True。
     */
    public boolean hasAssignableFrom(Object item) {
        for (E obj : items)
            if (obj.getClass().isAssignableFrom(item.getClass())) return true;

        return false;
    }

    /**
     * Return true when someone object in tag is assign by object. <br />
     * 当物件是Tag中某个物件继承时返回True。
     */
    public boolean hasAssignableBy(Object item) {
        for (E obj : items)
            if (item.getClass().isAssignableFrom(obj.getClass())) return true;

        return false;
    }


    public boolean remove(E item) {
        return this.items.remove(item);
    }

    /**
     * From Tag to OreDictionary. There will be no real-time synchronization, and must be restated if they update. <br />
     * 从标签到矿物辞典。不会实时同步，若发生更新必须重新申明。
     * */
    public boolean asOreDict() {
        if (this.items.isEmpty()) return false;
        if (this.items.get(0) instanceof Item) {
            for (Item item : (List<Item>) items)
                OreDictionary.registerOre(name, item);
            return true;
        } else if (this.items.get(0) instanceof ItemStack) {
            for (ItemStack item : (List<ItemStack>) items)
                OreDictionary.registerOre(name, item);
            return true;
        } else if (this.items.get(0) instanceof Block) {
            for (Block block : (List<Block>) items)
                OreDictionary.registerOre(name, block);
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tag)) return false;
        return ((Tag<?>) obj).name.equals(this.name);
    }
}

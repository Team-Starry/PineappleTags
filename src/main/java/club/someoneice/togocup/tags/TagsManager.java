package club.someoneice.togocup.tags;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class TagsManager {
    private static final Map<String, Tag<?>> tags = Maps.newConcurrentMap();
    public static final TagsManager INSTANCE = new TagsManager();

    TagsManager() {}

    public static TagsManager manager() {
        return INSTANCE;
    }

    /**
     * Create a new Tag or take a Tag from tags pool.  <br />
     * 创建新标签，或从标签池中取得标签。
     * */
    public <E> Tag<E> registerTag(String name, @Nullable E ... items) {
        if (tags.containsKey(name)) {
            try {
                Tag<E> tag = (Tag<E>) tags.get(name);
                if (items != null) tag.addAll(Arrays.asList(items));
                return tag;
            } catch (Exception e) {
                PineappleTags.LOGGER.error("One of mod's Tags " + name + " is broken when create because the type is not same.");
            }
        }

        Tag<E> tag = new Tag<>(name);
        if (items != null) tag.addAll(Arrays.asList(items));
        tags.put(name, tag);
        return tag;
    }

    /**
     * Create a tag from OreDictionary. There will be no real-time synchronization, and must be restated if they update <br />
     * 从矿物辞典到Tag。从标签到矿物辞典。不会实时同步，若发生更新必须重新申明。
     * @throws TagNotSameFatalException When tag in tag pool with same name but it not an Item's Tag. <br />
     * 当标签池内的标签与矿物辞典同名但不是一个物品类型的标签时抛出。
     * */
    public Tag<Item> registerTagFromOreDictionary(String name) throws TagNotSameFatalException {
        List<Item> items = Lists.newArrayList();
        List<ItemStack> list = OreDictionary.getOres(name);
        for (ItemStack item : list) items.add(item.getItem());
        try {
            Tag<Item> tag;
            if (tags.containsKey(name))
                tag = (Tag<Item>) tags.get(name);
            else tag = new Tag<>(name);
            tag.addAll(items);
            tags.put(name, tag);
            return tag;
        } catch (Exception e) {
            throw new TagNotSameFatalException("A fatal exception occurred while trying to convert an OreDictionary to Tag, because the OreDictionary's name in tag pool is not an Item's tag.");
        }
    }

    /**
     * Get the tags from an Object if it has. <br />
     * 取得物件的标签，如果他有的话。
     * */
    public List<String> geTagsFromObjects(Object item) {
        List<String> l = Lists.newArrayList();
        for (String s : tags.keySet())
            if (tags.get(s).has(item))
                l.add(s);

        return l;
    }
}

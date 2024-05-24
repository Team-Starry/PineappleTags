package club.someoneice.togocup.tags;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collection;

/**
 * Ingredient is an object for recipe check. Create an Ingredient to check the ItemStack or Tag.
 * Ingredient 是一个为食谱检查准备的对象。 创建 Ingredient 来检查 ItemStack 或 Tag.
 * @see ItemStack
 * @see Tag
 */
@SuppressWarnings("unused")
public class Ingredient {
    private final ImmutableList<ItemStack> obj;

    public Ingredient(Tag<? extends Item> obj) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        obj.getList().forEach(it -> builder.add(new ItemStack(it)));
        this.obj = builder.build();
    }

    public Ingredient(ItemStackTag obj) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        obj.getList().forEach(it -> builder.add(it.copy()));
        this.obj = builder.build();
    }

    public Ingredient(ItemStack[] obj) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        Arrays.stream(obj).forEach(it -> builder.add(it.copy()));
        this.obj = builder.build();
    }

    public Ingredient(Collection<ItemStack> obj) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        obj.forEach(it -> builder.add(it.copy()));
        this.obj = builder.build();
    }

    public Ingredient(ItemStack obj) {
        this.obj = ImmutableList.of(obj.copy());
    }

    public Ingredient(Item obj) {
        this.obj = ImmutableList.of(new ItemStack(obj));
    }

    public Ingredient(Block obj) {
        this.obj = ImmutableList.of(new ItemStack(obj));
    }

    public ImmutableList<ItemStack> getObj() {
        return obj;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ingredient)) return false;
        Ingredient ingredient = (Ingredient) obj;
        ImmutableList<ItemStack> list1 = this.getObj();
        ImmutableList<ItemStack> list2 = ingredient.getObj();
        return list1.size() == list2.size() && list1.equals(list2);
    }
}

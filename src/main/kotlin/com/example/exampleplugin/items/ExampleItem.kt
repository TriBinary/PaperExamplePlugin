package com.example.exampleplugin.items

import com.example.exampleplugin.registration.PluginItem
import com.example.exampleplugin.utils.itemStack
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * Example custom item — a glowing Nether Star Shard.
 *
 * Declared as a Kotlin `object` so it can be referenced directly in recipe
 * files and other code without going through [com.example.exampleplugin.registration.ItemRegistrar]:
 *
 * ```kotlin
 * val stack = ExampleItem.create()       // one shard
 * val stack3 = ExampleItem.create(3)     // three shards
 *
 * // Use as a recipe ingredient (see ExampleRecipe)
 * recipe.setIngredient('S', ExampleItem.asChoice())
 * ```
 *
 * Replace or remove this file and add your own items inside this package.
 */
object ExampleItem : PluginItem("example_item") {

    override fun buildItem(amount: Int): ItemStack = itemStack(Material.NETHER_STAR) {
        amount(amount)
        name("<gradient:gold:yellow><bold>Nether Star Shard</bold></gradient>")
        lore(
            "<gray>A fragment of the void,",
            "<gray>pulsing with dark energy."
        )
        enchant(Enchantment.UNBREAKING, 3)
    }
}

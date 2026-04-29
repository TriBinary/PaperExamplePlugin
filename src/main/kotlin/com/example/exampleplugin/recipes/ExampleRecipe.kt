package com.example.exampleplugin.recipes

import com.example.exampleplugin.items.ExampleItem
import com.example.exampleplugin.registration.PluginRecipe
import org.bukkit.Material
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin

/**
 * Example shaped crafting-table recipe that produces one [ExampleItem].
 *
 * Ingredient layout (G = Gold Ingot, N = Nether Star):
 * ```
 * G G G
 * G N G
 * G G G
 * ```
 *
 * Demonstrates mixing vanilla ingredients ([vanillaChoice]) with a custom
 * plugin item result. Replace or remove this file and add your own recipes
 * inside this package.
 */
class ExampleRecipe : PluginRecipe("example_recipe") {

    override fun build(plugin: JavaPlugin): Recipe {
        val recipe = ShapedRecipe(namespacedKey(plugin), ExampleItem.create())
        recipe.shape(
            "GGG",
            "GNG",
            "GGG"
        )
        recipe.setIngredient('G', vanillaChoice(Material.GOLD_INGOT))
        recipe.setIngredient('N', vanillaChoice(Material.NETHER_STAR))
        return recipe
    }
}

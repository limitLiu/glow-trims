package net.wu_chinese.recipe

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.wu_chinese.GlowTrims

object ModRecipes {
  @JvmField
  val TRIM_EFFECT_CRAFTING: RecipeSerializer<TrimEffectCraftingRecipe> =
    Registry.register(
      BuiltInRegistries.RECIPE_SERIALIZER,
      GlowTrims.id("trim_effect_crafting"),
      RecipeSerializer(
        TrimEffectCraftingRecipe.MAP_CODEC,
        TrimEffectCraftingRecipe.STREAM_CODEC,
      ),
    )

  fun initialize() = Unit
}

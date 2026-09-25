package net.wu_chinese.recipe

import com.mojang.serialization.MapCodec
import net.minecraft.core.component.DataComponents
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import net.wu_chinese.component.GlowingTrim
import net.wu_chinese.component.ShimmeringTrim
import net.wu_chinese.registry.ModComponents

class TrimEffectCraftingRecipe private constructor() : CustomRecipe() {
  override fun getSerializer(): RecipeSerializer<out CustomRecipe> = ModRecipes.TRIM_EFFECT_CRAFTING

  override fun matches(
    input: CraftingInput,
    level: Level,
  ): Boolean {
    val inputs = findInputs(input) ?: return false
    return inputs.operation.canApply(inputs.armor)
  }

  override fun assemble(input: CraftingInput): ItemStack {
    val inputs = findInputs(input) ?: return ItemStack.EMPTY
    if (!inputs.operation.canApply(inputs.armor)) {
      return ItemStack.EMPTY
    }
    return inputs.armor.copyWithCount(1).also(inputs.operation::apply)
  }

  private data class Inputs(val armor: ItemStack, val operation: Operation)

  private enum class Operation {
    ADD_GLOW {
      override fun apply(armor: ItemStack) {
        armor.set(ModComponents.GLOWING_TRIM, GlowingTrim)
      }

      override fun canApply(armor: ItemStack): Boolean = !armor.has(ModComponents.GLOWING_TRIM)
    },
    REMOVE_GLOW {
      override fun apply(armor: ItemStack) {
        armor.remove(ModComponents.GLOWING_TRIM)
      }

      override fun canApply(armor: ItemStack): Boolean = armor.has(ModComponents.GLOWING_TRIM)
    },
    ADD_SHIMMER {
      override fun apply(armor: ItemStack) {
        armor.set(ModComponents.SHIMMERING_TRIM, ShimmeringTrim)
      }

      override fun canApply(armor: ItemStack): Boolean = !armor.has(ModComponents.SHIMMERING_TRIM)
    },
    REMOVE_SHIMMER {
      override fun apply(armor: ItemStack) {
        armor.remove(ModComponents.SHIMMERING_TRIM)
      }

      override fun canApply(armor: ItemStack): Boolean = armor.has(ModComponents.SHIMMERING_TRIM)
    };

    abstract fun canApply(armor: ItemStack): Boolean

    abstract fun apply(armor: ItemStack)

    companion object {
      fun from(material: ItemStack): Operation? =
        when {
          material.item === Items.GLOW_INK_SAC -> ADD_GLOW
          material.item === Items.INK_SAC -> REMOVE_GLOW
          material.item === Items.ECHO_SHARD -> ADD_SHIMMER
          material.item === Items.HONEYCOMB -> REMOVE_SHIMMER
          else -> null
        }
    }
  }

  companion object {
    @JvmField val INSTANCE = TrimEffectCraftingRecipe()

    @JvmField val MAP_CODEC: MapCodec<TrimEffectCraftingRecipe> = MapCodec.unit(INSTANCE)

    @JvmField
    val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, TrimEffectCraftingRecipe> =
      StreamCodec.unit(INSTANCE)

    private fun findInputs(input: CraftingInput): Inputs? {
      if (input.ingredientCount() != 2) {
        return null
      }
      var armor: ItemStack? = null
      var operation: Operation? = null
      for (index in 0 until input.size()) {
        val stack = input.getItem(index)
        if (stack.isEmpty) {
          continue
        }
        if (stack.has(DataComponents.TRIM)) {
          if (armor != null) {
            return null
          }
          armor = stack
        } else {
          val candidate = Operation.from(material = stack)
          if (candidate == null || operation != null) {
            return null
          }
          operation = candidate
        }
      }
      return if (armor != null && operation != null) Inputs(armor, operation) else null
    }
  }
}

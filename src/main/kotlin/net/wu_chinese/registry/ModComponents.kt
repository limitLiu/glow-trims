package net.wu_chinese.registry

import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.wu_chinese.GlowTrims
import net.wu_chinese.component.GlowingTrim
import net.wu_chinese.component.ShimmeringTrim

object ModComponents {
  @JvmField
  val GLOWING_TRIM: DataComponentType<GlowingTrim> =
    Registry.register(
      BuiltInRegistries.DATA_COMPONENT_TYPE,
      GlowTrims.id("glowing_trim"),
      DataComponentType.builder<GlowingTrim>()
        .persistent(GlowingTrim.CODEC)
        .networkSynchronized(GlowingTrim.STREAM_CODEC)
        .build(),
    )

  @JvmField
  val SHIMMERING_TRIM: DataComponentType<ShimmeringTrim> =
    Registry.register(
      BuiltInRegistries.DATA_COMPONENT_TYPE,
      GlowTrims.id("shimmering_trim"),
      DataComponentType.builder<ShimmeringTrim>()
        .persistent(ShimmeringTrim.CODEC)
        .networkSynchronized(ShimmeringTrim.STREAM_CODEC)
        .build(),
    )

  fun initialize() {
    ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, GLOWING_TRIM)
    ItemComponentTooltipProviderRegistry.addAfter(GLOWING_TRIM, SHIMMERING_TRIM)
  }
}

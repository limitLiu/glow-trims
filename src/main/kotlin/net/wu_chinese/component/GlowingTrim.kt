package net.wu_chinese.component

import com.mojang.serialization.Codec
import java.util.function.Consumer
import net.minecraft.core.component.DataComponentGetter
import net.minecraft.core.component.DataComponents
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.Item
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipProvider

object GlowingTrim : TooltipProvider {
  @JvmField val CODEC: Codec<GlowingTrim> = Codec.BOOL.xmap({ GlowingTrim }, { true })

  @JvmField
  val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, GlowingTrim> =
    StreamCodec.unit(GlowingTrim)

  override fun addToTooltip(
    context: Item.TooltipContext,
    consumer: Consumer<Component>,
    flag: TooltipFlag,
    components: DataComponentGetter,
  ) {
    val trim = components.get(DataComponents.TRIM)
    val label: Component =
      if (trim != null) {
        Component.translatable("item.glow-trims.glowing_trim")
          .copy()
          .withStyle(trim.material().value().description().style)
      } else {
        Component.translatable("item.glow-trims.glowing_trim")
      }
    consumer.accept(CommonComponents.space().append(label))
  }
}

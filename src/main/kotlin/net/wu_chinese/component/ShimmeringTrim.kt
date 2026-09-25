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

object ShimmeringTrim : TooltipProvider {
  @JvmField val CODEC: Codec<ShimmeringTrim> = Codec.BOOL.xmap({ ShimmeringTrim }, { true })

  @JvmField
  val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, ShimmeringTrim> =
    StreamCodec.unit(ShimmeringTrim)

  override fun addToTooltip(
    context: Item.TooltipContext,
    consumer: Consumer<Component>,
    flag: TooltipFlag,
    components: DataComponentGetter,
  ) {
    val trim = components.get(DataComponents.TRIM)
    val label: Component =
      if (trim != null) {
        Component.translatable("item.glow-trims.shimmering_trim")
          .copy()
          .withStyle(trim.material().value().description().style)
      } else {
        Component.translatable("item.glow-trims.shimmering_trim")
      }
    consumer.accept(CommonComponents.space().append(label))
  }
}

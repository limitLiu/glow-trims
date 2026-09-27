package net.wu_chinese.client.mixin

import com.mojang.blaze3d.vertex.PoseStack
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin
import net.minecraft.client.model.Model
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer
import net.minecraft.client.resources.model.EquipmentClientInfo
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.equipment.EquipmentAsset
import net.wu_chinese.registry.ModComponents
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.ModifyArgs
import org.spongepowered.asm.mixin.injection.invoke.arg.Args

private const val FULL_BRIGHT = 0x00F000F0

private const val RENDER_LAYERS =
    $$"renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V"

private const val SUBMIT_MODEL =
    "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V"

@Mixin(EquipmentLayerRenderer::class)
abstract class EquipmentLayerRendererMixin {
  @ModifyArgs(
      method = [RENDER_LAYERS],
      at = [At(value = "INVOKE", target = SUBMIT_MODEL, ordinal = 1)],
  )
  private fun <S : Any> modifyTrimArguments(
      args: Args,
      layerType: EquipmentClientInfo.LayerType,
      asset: ResourceKey<EquipmentAsset>,
      model: Model<in S>,
      state: S,
      itemStack: ItemStack,
      poseStack: PoseStack,
      submitNodeCollector: SubmitNodeCollector,
      lightCoords: Int,
      playerTextureOverride: Identifier?,
      outlineColor: Int,
      order: Int,
  ) {
    if (itemStack.has(ModComponents.GLOWING_TRIM)) {
      args.set(4, FULL_BRIGHT)
    }
    if (itemStack.has(ModComponents.SHIMMERING_TRIM)) {
      val originalColor = args.get<Int>(6)
      args.set(6, pulse(original = originalColor))
    }
  }

  private fun pulse(original: Int): Int {
    val seconds = System.nanoTime() / 1_000_000_000.0
    val phase = seconds * (PI * 2.0 / 3.0)
    val intensity = 0.675 + 0.325 * sin(phase)
    val red = (((original ushr 16) and 0xFF) * intensity).roundToInt()
    val green = (((original ushr 8) and 0xFF) * intensity).roundToInt()
    val blue = ((original and 0xFF) * intensity).roundToInt()
    return (original and 0xFF000000.toInt()) or (red shl 16) or (green shl 8) or blue
  }
}

package net.wu_chinese

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import net.wu_chinese.recipe.ModRecipes
import net.wu_chinese.registry.ModComponents
import org.slf4j.LoggerFactory

object GlowTrims : ModInitializer {
  const val MOD_ID: String = "glow-trims"

  private val LOGGER = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModComponents.initialize()
    ModRecipes.initialize()
    LOGGER.info("Glow Trims initialized!")
  }

  fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}

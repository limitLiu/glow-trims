# Glow Trims

English | [简体中文](docs/README_zh.md)

Glow Trims is a Fabric mod for Minecraft Java Edition 26.3 that adds craftable glowing and smoothly shimmering effects to existing armor trims.

## Features

- Supports any equipment carrying a vanilla armor trim.
- Works with any armor material, trim pattern, and trim material.
- Only the trim layer glows; the armor itself remains affected by ambient lighting.
- Displays "Glowing" and "Shimmering" below the trim pattern and material in the vanilla "Upgrades" tooltip, using the trim material's color.
- Add or remove the glowing effect with a glow ink sac or ink sac.
- Add or remove the shimmering effect with an echo shard or honeycomb.
- Glowing and shimmering are independent effects. They can be applied separately or used together.
- Preserves the equipment's custom name, enchantments, durability, trim, lore, and other item components.
- Equipment without a trim cannot receive either effect.
- A recipe only works when it would change the selected effect; an existing effect cannot be added again, and an absent effect cannot be removed.

The effects are stored in the custom Data Components `glow-trims:glowing_trim` and `glow-trims:shimmering_trim`. The vanilla `minecraft:trim` component is not modified.

## Requirements

- Minecraft Java Edition 26.3
- Fabric Loader 0.19.5 or later
- Fabric API
- Fabric Language Kotlin
- Java 25

## Make a Trim Glow

Place exactly one of each of the following ingredients in any arrangement in the player's 2x2 crafting grid or a crafting table:

```text
Any equipment with a trim + Glow Ink Sac
```

After taking the output, the equipment's existing trim will remain bright in dark environments. Equipment without a vanilla armor trim will not produce an output.

## Remove the Glowing Effect

Place exactly one of each of the following ingredients in any arrangement in a crafting grid or crafting table:

```text
Trimmed equipment with the Glowing effect + Ink Sac
```

The output equipment will no longer glow, restoring the vanilla trim's normal response to ambient lighting. Its trim pattern and material remain unchanged.

## Make a Trim Shimmer

Place exactly one of each of the following ingredients in any arrangement in a crafting grid or crafting table:

```text
Any equipment with a trim + Echo Shard
```

The output equipment's trim will shimmer smoothly between 35% and 100% brightness over a cycle of approximately 3 seconds. The equipment does not need to have the Glowing effect.

## Remove the Shimmering Effect

Place exactly one of each of the following ingredients in any arrangement in a crafting grid or crafting table:

```text
Trimmed equipment with the Shimmering effect + Honeycomb
```

The output equipment will no longer shimmer. This does not change whether the equipment glows, nor does it alter the trim pattern or material.

## View the Effects

Press `F5` to switch to third-person view. On a Mac keyboard, you may need to press `Fn + F5`.

Enter a dark environment, or use the following command to switch the time to midnight:

```mcfunction
/time set minecraft:midnight
```

The trim on equipment with the Glowing effect remains fully lit, while the armor itself still darkens with the environment. The Shimmering effect continuously pulses the trim's brightness and can be observed in any lighting condition.

## License

This project is available under the CC0 license.

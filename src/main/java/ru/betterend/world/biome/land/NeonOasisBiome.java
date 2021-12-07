package ru.betterend.world.biome.land;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.betterend.registry.EndFeatures;
import ru.betterend.registry.EndSounds;
import ru.betterend.world.biome.EndBiome;

public class NeonOasisBiome extends EndBiome.Config {
	public NeonOasisBiome() {
		super("neon_oasis");
	}

	@Override
	protected void addCustomBuildData(BCLBiomeBuilder builder) {
		builder.genChance(0.5F)
			   .fogColor(226, 239, 168)
			   .fogDensity(2)
			   .waterAndFogColor(106, 238, 215)
			   //TODO: 1.18 surface Rules
			   //.surface(EndBlocks.ENDSTONE_DUST, EndBlocks.END_MOSS)
			   .particles(ParticleTypes.WHITE_ASH, 0.01F)
			   .loop(EndSounds.AMBIENT_DUST_WASTELANDS)
			   .music(EndSounds.MUSIC_OPENSPACE)
			   .feature(EndFeatures.DESERT_LAKE)
			   .feature(EndFeatures.NEON_CACTUS)
			   .feature(EndFeatures.UMBRELLA_MOSS)
			   .feature(EndFeatures.CREEPING_MOSS)
			   .feature(EndFeatures.CHARNIA_GREEN)
			   .feature(EndFeatures.CHARNIA_CYAN)
			   .feature(EndFeatures.CHARNIA_RED)
			   .structure(VANILLA_FEATURES.getEND_CITY())
			   .spawn(EntityType.ENDERMAN, 50, 1, 2);
	}
}

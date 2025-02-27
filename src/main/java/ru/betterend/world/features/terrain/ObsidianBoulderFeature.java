package ru.betterend.world.features.terrain;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import ru.bclib.api.tag.CommonBlockTags;
import ru.bclib.sdf.SDF;
import ru.bclib.sdf.operator.SDFDisplacement;
import ru.bclib.sdf.operator.SDFScale3D;
import ru.bclib.sdf.primitive.SDFSphere;
import ru.bclib.util.MHelper;
import ru.bclib.world.features.DefaultFeature;
import ru.betterend.noise.OpenSimplexNoise;
import ru.betterend.registry.EndBlocks;

import java.util.Random;

public class ObsidianBoulderFeature extends DefaultFeature {
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureConfig) {
		final Random random = featureConfig.random();
		BlockPos pos = featureConfig.origin();
		final WorldGenLevel world = featureConfig.level();
		pos = getPosOnSurface(
			world,
			new BlockPos(pos.getX() + random.nextInt(16), pos.getY(), pos.getZ() + random.nextInt(16))
		);
		if (!world.getBlockState(pos.below()).is(CommonBlockTags.END_STONES)) {
			return false;
		}
		
		int count = MHelper.randRange(1, 5, random);
		for (int i = 0; i < count; i++) {
			BlockPos p = getPosOnSurface(
				world,
				new BlockPos(pos.getX() + random.nextInt(16) - 8, pos.getY(), pos.getZ() + random.nextInt(16) - 8)
			);
			makeBoulder(world, p, random);
		}
		
		return true;
	}
	
	private void makeBoulder(WorldGenLevel world, BlockPos pos, Random random) {
		if (!world.getBlockState(pos.below()).is(CommonBlockTags.END_STONES)) {
			return;
		}
		
		float radius = MHelper.randRange(1F, 5F, random);
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(Blocks.OBSIDIAN);
		float sx = MHelper.randRange(0.7F, 1.3F, random);
		float sy = MHelper.randRange(0.7F, 1.3F, random);
		float sz = MHelper.randRange(0.7F, 1.3F, random);
		sphere = new SDFScale3D().setScale(sx, sy, sz).setSource(sphere);
		OpenSimplexNoise noise = new OpenSimplexNoise(random.nextLong());
		sphere = new SDFDisplacement().setFunction((vec) -> {
			return (float) (noise.eval(vec.x() * 0.2, vec.y() * 0.2, vec.z() * 0.2) * 1.5F);
		}).setSource(sphere);
		
		BlockState mossy = EndBlocks.MOSSY_OBSIDIAN.defaultBlockState();
		sphere.addPostProcess((info) -> {
			if (info.getStateUp().isAir() && random.nextFloat() > 0.1F) {
				return mossy;
			}
			return info.getState();
		}).setReplaceFunction((state) -> {
			return state.getMaterial().isReplaceable() || state.is(CommonBlockTags.GEN_END_STONES) || state.getMaterial()
																							   .equals(Material.PLANT);
		}).fillRecursive(world, pos);
	}
}

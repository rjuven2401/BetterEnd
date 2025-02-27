package ru.betterend.blocks.basis;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import ru.bclib.api.tag.CommonBlockTags;
import ru.bclib.blocks.BasePlantBlock;
import ru.betterend.interfaces.PottablePlant;

public class EndPlantBlock extends BasePlantBlock implements PottablePlant {
	public EndPlantBlock() {
		this(false);
	}
	
	public EndPlantBlock(int light) {
		this(false, light);
	}
	
	public EndPlantBlock(boolean replaceable) {
		super(replaceable);
	}
	
	public EndPlantBlock(boolean replaceable, int light) {
		super(replaceable, light);
	}
	
	public EndPlantBlock(Properties settings) {
		super(settings);
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(CommonBlockTags.END_STONES);
	}
	
	@Override
	public boolean canPlantOn(Block block) {
		return isTerrain(block.defaultBlockState());
	}
	
	@Override
	public boolean canBePotted() {
		return getStateDefinition().getProperties().isEmpty();
	}
}

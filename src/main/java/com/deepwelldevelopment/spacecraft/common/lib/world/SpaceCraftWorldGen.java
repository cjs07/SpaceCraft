package com.deepwelldevelopment.spacecraft.common.lib.world;

import com.deepwelldevelopment.spacecraft.common.block.SpaceCraftBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class SpaceCraftWorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) { // the overworld
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOre(SpaceCraftBlocks.oreCopper.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 4 + random.nextInt(5), 8);
        generateOre(SpaceCraftBlocks.oreTin.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 4 + random.nextInt(5), 7);
        generateOre(SpaceCraftBlocks.oreAluminum.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 4 + random.nextInt(5), 6);
        generateOre(SpaceCraftBlocks.oreLead.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 32, 4 + random.nextInt(5), 5);
        generateOre(SpaceCraftBlocks.oreSilver.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 32, 4 + random.nextInt(5), 5);
        generateOre(SpaceCraftBlocks.oreNickel.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 32, 2 + random.nextInt(5), 4);
        generateOre(SpaceCraftBlocks.orePlatinum.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 32, 4 + random.nextInt(5), 3);
        generateOre(SpaceCraftBlocks.oreUranium.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 64, 1 + random.nextInt(2), 3);
        generateOre(SpaceCraftBlocks.oreIridium.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 15, 1 + random.nextInt(2), 1);
    }

    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
        int deltaY = maxY - minY;

        for (int i = 0; i < chances; i++) {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));

            WorldGenMinable generator = new WorldGenMinable(ore, size);
            generator.generate(world, random, pos);
        }
    }
}

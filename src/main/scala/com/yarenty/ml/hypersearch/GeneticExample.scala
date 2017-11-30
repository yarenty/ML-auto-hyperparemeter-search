package com.yarenty.ml.hypersearch


import io.jenetics.BitChromosome
import io.jenetics.BitGene
import io.jenetics.Genotype
import io.jenetics.engine.Engine
import io.jenetics.engine.EvolutionResult
import io.jenetics.util.Factory


object GeneticExample { 
  
  // 2.) Definition of the fitness function.
  private def eval(gt: Genotype[BitGene]): Comparable[Int] = gt.getChromosome.as(classOf[BitChromosome]).bitCount

  def main(args: Array[String]): Unit = {
    
    // 1.) Define the genotype (factory) suitable for the problem.
    val gtf: Factory[Genotype[BitGene]] = Genotype.of(BitChromosome.of(10, 0.5))
    
//    // 3.) Create the execution environment.
//    val engine: Engine[BitGene, Int] = Engine.builder(
//      GeneticExample.eval _,
//      gtf).build()
//    
//    
//    // 4.) Start the execution (evolution) and collect the result.
//    val result: Genotype[BitGene] = engine.stream.limit(100).collect(EvolutionResult.toBestGenotype())
//    
//    System.out.println("Hello World:\n" + result)
  }
}
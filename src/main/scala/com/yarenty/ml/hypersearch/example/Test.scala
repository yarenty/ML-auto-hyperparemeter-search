package com.yarenty.ml.hypersearch.example

import org.apache.spark.SparkContext
import org.apache.spark.h2o.{H2OContext, H2OFrame}
import org.apache.spark.sql.{SQLContext, SparkSession}
import water.fvec.H2OFrame
import water.support.{H2OFrameSupport, SparkContextSupport, SparklingWaterApp}

/**
  * Created by yarenty on 28/04/2017.
  */
class Test  ( implicit val sc: SparkContext,
              @transient override val sqlContext: SQLContext,
              @transient override val h2oContext: H2OContext)
  extends SparklingWaterApp with H2OFrameSupport {

  import h2oContext._



  def split(in: H2OFrame):(H2OFrame,H2OFrame)  = {
    import h2oContext.implicits._

    val keys = Array[String]("trainHyper.hex", "testHyper.hex")
    val ratios = Array[Double](0.8, 0.2)
    val frs = splitFrame(in, keys, ratios)
    val (train, test) = (frs(0), frs(1))
    (train,test)
  }

}


object Test extends SparkContextSupport {

  def main(args: Array[String]): Unit = {



      // Prepare environment
      implicit val sc = new SparkContext(configure("H2O Hyper-parameter AI search"))
      // SQL support
      implicit val sqlContext = SparkSession.builder().getOrCreate().sqlContext
      // Start H2O services
      implicit val h2oContext = H2OContext.getOrCreate(sc)

      val app = new Test()

    println(h2oContext.toString)
    println(s"\n\n H2O CONTEXT is HERE !!!!!!\n")

    val params = GLMHyperParams.getParams()

    val out = GLMModelUnderTest.calculate(params)

    // HyperAIFinder.calculate(params)

    println("AUCC:" + out)
  }
}
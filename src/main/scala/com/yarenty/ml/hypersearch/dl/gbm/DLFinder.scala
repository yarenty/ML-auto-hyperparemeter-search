package com.yarenty.ml.hypersearch.dl.gbm

import com.yarenty.ml.hypersearch.getParser
import org.apache.spark.SparkContext
import org.apache.spark.h2o.H2OContext
import org.apache.spark.sql.{SQLContext, SparkSession}
import water.fvec.{H2OFrame, Vec}
import water.support.{H2OFrameSupport, SparkContextSupport, SparklingWaterApp}

/**
  * Created by yarenty on 28/04/2017.
  */
class DLFinder(implicit val sc: SparkContext,
               @transient override val sqlContext: SQLContext,
               @transient override val h2oContext: H2OContext)
  extends SparklingWaterApp with H2OFrameSupport {


  def split(in: H2OFrame): (H2OFrame, H2OFrame) = {
    val keys = Array[String]("trainHyper.hex", "testHyper.hex")
   split(in,keys)
  }


  def split(in: H2OFrame, keys:Array[String]): (H2OFrame, H2OFrame) = {
    import h2oContext.implicits._


    val ratios = Array[Double](0.8, 0.2)
    val frs = splitFrame(in, keys, ratios)
    val (train, test) = (frs(0), frs(1))
    (train, test)
  }


  def initialRound(train:H2OFrame, valid:H2OFrame): Array[Vector[Any]] = {
    var hp1 = Vector[Double]()
    var hp2 = Vector[Double]()
    var hp3 = Vector[Array[Int]]()
    var hp_out = Vector[Double]()
    val params = DLHyperParams.getParams()
    for (a <- DLHyperParams.l2) {
      for (l <- DLHyperParams.dropout) {
        for (b <- DLHyperParams.hiddens) {
          hp1 = hp1 :+ a
          hp2 = hp2 :+ l
          hp3 = hp3 :+ b
          params._l2 = a
          params._input_dropout_ratio = l
          params._hidden = b

          val out = DLModelUnderTest.calculate(params,train,valid)
          hp_out = hp_out :+ out
          println(s"l2:$a, dropout:$l, hidden layers:$b, out:$out")

        }
      }
    }

    println("===============================")
    println("OUTPUT:")
    for (i <- hp1.indices) {
      println(s"${hp1(i)};${hp2(i)};${hp3(i)};${hp_out(i)};")
    }

    print(s"val alpha  = Array(" + hp1.mkString(",") + ")")
    print(s"val lambde = Array(" + hp2.mkString(",") + ")")
    print(s"val betha  = Array(" + hp3.mkString(",") + ")")
    print(s"val acc    = Array(" + hp_out.mkString(",") + ")")


    Array(hp1, hp2, hp3, hp_out)
  }

/*
  def toPredict(): Array[Vector[Double]] = {
    var hp1 = Vector[Double]()
    var hp2 = Vector[Double]()
    var hp3 = Vector[Double]()
    var hp_out = Vector[Double]()
    val params = DLHyperParams.getParams()
    for (a <- 0.0 to 1.0 by 0.1) {
      for (l <- 0.0 to 20.0 by 0.1) {
        for (b <- 1e-5 to 1e-3 by 1e-5) {
          hp1 = hp1 :+ a
          hp2 = hp2 :+ l
          hp3 = hp3 :+ b
          hp_out = hp_out :+ 0.0

        }
      }
    }
    Array(hp1, hp2, hp3, hp_out)
  }


  def getIds(vec: Vec): Array[Int] = {
    val min = vec.maxs().min
    vecToArray(vec).zipWithIndex.filter(_._1 >= min).map(_._2)
  }


  def vecToArray(v: Vec): Array[Double] = {
    val arr = Array.ofDim[Double](v.length.toInt)
    for (i <- 0 until v.length.toInt) {
      arr(i) = v.at(i)
    }
    arr
  }


  def prevPredict(): Array[Vector[Double]] = {
    var hp1 = Vector[Double]()
    var hp2 = Vector[Double]()
    var hp3 = Vector[Double]()
    
 
    val params = GBMHyperParams.getParams()
    for (a <- GBMHyperParams.alphas) {
      for (l <- GBMHyperParams.lambdas) {
        for (b <- GBMHyperParams.betas) {
          hp1 = hp1 :+ a
          hp2 = hp2 :+ l
          hp3 = hp3 :+ b
        }
      }
    }
    val acc  = Vector(...)

    Array(hp1, hp2, hp3, acc)
  }
  
   
*/
  
  
}


object DLFinder extends SparkContextSupport {

  def main(args: Array[String]): Unit = {


    // Prepare environment
    implicit val sc = new SparkContext(configure("H2O Hyper-parameter AI search"))
    // SQL support
    implicit val sqlContext = SparkSession.builder().getOrCreate().sqlContext
    // Start H2O services
    implicit val h2oContext = H2OContext.getOrCreate(sc)

    val app = new DLFinder()
    val params = DLHyperParams.getParams()



    val datadir = "/opt/data/mercedes"



    val input = new H2OFrame(getParser, new java.net.URI(s"${datadir}/train.csv"))
    val names = input._names.drop(2) //ID,y
    println(names.mkString(";"))
    input.colToEnum(names)
    val (train, valid) = app.split(input, Array("train_input.hex","valid_input.hex"))
    val test = new H2OFrame(getParser, new java.net.URI(s"${datadir}/test.csv"))
    test.colToEnum(names)
    
    val out = DLModelUnderTest.calculate(params, train,valid)
    
    
//   val hp = app.initialRound( train,valid)
/*    val hp = app.prevPredict()

    val vecs:Array[Vec] = for (h <- hp) yield {
      val v = Vec.makeZero(h.length)
      for (i <- h.indices) {
        v.set(i,h(i))
      }
      v
    }
    

    
    val outFrame = new H2OFrame(new Frame(Array("alpha", "lambda", "beta", "acc"), vecs))
    val (train, test) = app.split(outFrame)




    val pVecs:Array[Vec] = for (h <-  app.toPredict()) yield {
      val v = Vec.makeZero(h.length)
      for (i <- h.indices) {
        v.set(i,h(i))
      }
      v
    }
    val predMe = new H2OFrame(new Frame(Array("alpha", "lambda", "beta", "acc"), pVecs))
    
    
    val out = HyperAIFinder.calculate(train, test, predMe)

    println(out.maxs().mkString(";"))
    
    val ids = app.getIds(out)

    val params = GBMHyperParams.getParams()
    
    println(" predicted best  output:")
    
    var pred = Vector[Double]()
    var real = Vector[Double]()

    for (i <-ids) {
      
      
      val a = pVecs(0).at(i)
      val l = pVecs(1).at(i)
      val b = pVecs(2).at(i)
      val p = out.at(i)
      
      println(s"$i => a=$a; l=$l; b=$b; predOut=$p;")


      params._alpha = Array(a)
      params._lambda = Array(l)
      params._beta_epsilon = b

      val fout = GLMModelUnderTest.calculate(params)
      println(s"$i => a=$a; l=$l; b=$b; predOut=$p; REAL!! = $fout ")
      pred = pred :+ p
      real = real :+ fout
    }

    val initial = outFrame.vec("acc").maxs()
    
    println("SUMMARY:")
    val _p = new GLMParameters()
    _p._family = GLMParameters.Family.binomial
    _p._alpha = Array(1.0) 
    _p._lambda = Array(0.0)
    
    val blind = GLMModelUnderTest.calculate(_p)
    println("SUMMARY:")
    
    println("            blind guess=" + blind)
    println("     initial max values=" + initial.mkString(";"))
    println(" predicted by inception=" + pred.mkString(";"))
    println("      real after retest=" + real.mkString(";"))

*/

  }
}
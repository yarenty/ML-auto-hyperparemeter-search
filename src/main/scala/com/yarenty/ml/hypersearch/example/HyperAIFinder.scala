package com.yarenty.ml.hypersearch.example

import hex.glm.{GLM, GLMModel}
import hex.glm.GLMModel.GLMParameters
import hex.splitframe.ShuffleSplitFrame
import hex.tree.drf.DRF
import hex.tree.drf.DRFModel.DRFParameters
import org.apache.spark.h2o
import org.apache.spark.h2o.H2OContext
import org.apache.spark.sql.SQLContext
import water.fvec.{H2OFrame, Vec}

/**
  * Created by yarenty on 11/05/2017.
  */

class HyperAIFinder(train: H2OFrame, test: H2OFrame)(implicit h2OContext: H2OContext) {

  def calculate(params: GLMParameters)(implicit h2oContext: H2OContext): GLMModel = {
    import h2oContext.implicits._
    params._train = train
    params._valid = test
    params._response_column = "acc"

    val lr = new GLM(params)
    val model = lr.trainModel.get
    model
  }


}

object HyperAIFinder {

  def calculate(train: H2OFrame, test: H2OFrame, doit:H2OFrame)(implicit h2oContext: H2OContext): Vec = {
    import h2oContext.implicits._
    val params: DRFParameters = new DRFParameters()
    params._train = train
    params._valid = test
    params._response_column = "acc"
    
    

    val lr = new DRF(params)
    val model = lr.trainModel.get
    println( model._output._validation_metrics)
    
    val pred = model.score(doit)
    
    pred.lastVec
  }


}

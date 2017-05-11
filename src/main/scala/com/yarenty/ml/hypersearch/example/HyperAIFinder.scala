package com.yarenty.ml.hypersearch.example

import hex.glm.{GLM, GLMModel}
import hex.glm.GLMModel.GLMParameters
import hex.splitframe.ShuffleSplitFrame
import org.apache.spark.h2o
import org.apache.spark.h2o.H2OContext
import org.apache.spark.sql.SQLContext
import water.fvec.H2OFrame

/**
  * Created by yarenty on 11/05/2017.
  */

class HyperAIFinder(train: H2OFrame, test: H2OFrame)(implicit h2OContext: H2OContext) {

  def calculate(params: GLMParameters)(implicit h2oContext: H2OContext): GLMModel = {
    import h2oContext.implicits._
    params._train = train
    params._valid = test
    params._response_column = "dep_delayed_15min"

    val lr = new GLM(params)
    val model = lr.trainModel.get
    model
  }


}

object HyperAIFinder {

  def calculate(train: H2OFrame, test: H2OFrame, params: GLMParameters)(implicit h2oContext: H2OContext): Double = {
    import h2oContext.implicits._
    params._train = train
    params._valid = test
    params._response_column = "dep_delayed_15min"

    val lr = new GLM(params)
    val model = lr.trainModel.get
    model._output._cross_validation_metrics.auc_obj()._auc
  }


}

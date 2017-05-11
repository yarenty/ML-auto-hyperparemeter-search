package com.yarenty.ml.hypersearch.example

import hex.glm.GLM
import hex.glm.GLMModel.GLMParameters
import water.fvec.H2OFrame

/**
  * Created by yarenty on 11/05/2017.
  */
object GLMModelUnderTest {

  val datadir = "/opt/data/airlines"
  val tname = "0.01m"


  val d_train = new H2OFrame(getParser, new java.net.URI(s"${datadir}/train-${tname}.csv"))
  val d_test = new H2OFrame(getParser, new java.net.URI(s"${datadir}/test.csv"))


  def calculate(params: GLMParameters): Double = {
    params._train = d_train.key
    params._valid = d_test.key
    params._response_column = "dep_delayed_15min"

    val lr = new GLM(params)
    val model = lr.trainModel.get
    println(model._output)
    println(model._output._cross_validation_metrics)
    println(model._output._cross_validation_metrics.auc_obj())
    println(model._output._cross_validation_metrics.auc_obj()._auc)
    
    0.5
  }


}

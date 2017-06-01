package com.yarenty.ml.hypersearch.gbm

import hex.tree.gbm.GBM
import hex.tree.gbm.GBMModel.GBMParameters
import water.fvec.H2OFrame

/**
  * Created by yarenty on 01/06/2017.
  */
object GBMModelUnderTest {
  

  def calculate(params: GBMParameters, train:H2OFrame, valid:H2OFrame): Double = {
    params._train = train.key
    params._valid = valid.key
    params._response_column = "y"
    params._ntrees = 500
    params._ignored_columns = Array("ID")

    val lr = new GBM(params)
    val model = lr.trainModel.get

    model._output._validation_metrics._MSE
  }


}

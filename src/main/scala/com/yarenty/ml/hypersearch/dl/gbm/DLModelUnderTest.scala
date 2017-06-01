package com.yarenty.ml.hypersearch.dl.gbm

import hex.deeplearning.DeepLearning
import hex.deeplearning.DeepLearningModel.DeepLearningParameters
import water.fvec.H2OFrame

/**
  * Created by yarenty on 01/06/2017.
  */
object DLModelUnderTest {
  

  def calculate(params: DeepLearningParameters, train:H2OFrame, valid:H2OFrame): Double = {
    params._train = train.key
    params._valid = valid.key
    params._response_column = "y"
    params._ignored_columns = Array("ID")

    val lr = new DeepLearning(params)
    val model = lr.trainModel.get

    model._output._validation_metrics._MSE
  }


}

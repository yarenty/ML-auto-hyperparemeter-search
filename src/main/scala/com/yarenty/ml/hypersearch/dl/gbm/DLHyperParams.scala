package com.yarenty.ml.hypersearch.dl.gbm

import hex.deeplearning.DeepLearningModel.DeepLearningParameters
import hex.deeplearning.DeepLearningModel.DeepLearningParameters.Activation
import hex.tree.gbm.GBMModel.GBMParameters

/**
  * Created by yarenty on 11/05/2017.
  */
object DLHyperParams {

  private val params = new DeepLearningParameters()


  val l2 = 0.0 to 0.2 by 0.05
  val dropout = 0.0 to 0.5 by 0.1
  val hiddens  = Array(Array(600,200,10), Array(600,300,100), Array(64,64,64),Array(32,32,32,32,32))

  def getParams(): DeepLearningParameters = {

    params._categorical_encoding // = CategoricalEncodingScheme.AUTO
    params._distribution //DistributionFamily.AUTO
    params._l1
    params._l2 = 0.1
    params._epochs = 10
    params._hidden = Array(600,300,100)
    params._hidden_dropout_ratios= Array(0.4,0.4,0.4)
    params._input_dropout_ratio = 0.4
    params._activation = Activation..RectifierWithDropout
    
    

    params
  }


}

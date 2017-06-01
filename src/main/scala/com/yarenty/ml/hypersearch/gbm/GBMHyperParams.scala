package com.yarenty.ml.hypersearch.gbm

import hex.tree.gbm.GBMModel.GBMParameters

/**
  * Created by yarenty on 11/05/2017.
  */
object GBMHyperParams {

  private val params = new GBMParameters()


  val max_depth = 4 to 20
  val nbins_cats = Array(1024, 512, 320, 256, 240,224, 216, 192, 180, 128,64)
  val learn_rate_annealing = Array(1.0, 0.999, 0.99)

  def getParams(): GBMParameters = {

    params._categorical_encoding // = CategoricalEncodingScheme.AUTO
    params._max_depth // 5
    params._nbins_cats //1024
    params._learn_rate_annealing //0.99 or 0.999
    params._distribution //DistributionFamily.AUTO

    params
  }


}

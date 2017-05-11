package com.yarenty.ml.hypersearch.example

import hex.glm.GLMModel.GLMParameters

/**
  * Created by yarenty on 11/05/2017.
  */
object GLMHyperParams {

  private val params = new GLMParameters()

  def getParams(): GLMParameters = {

    params._family = GLMParameters.Family.binomial
    params._alpha = Array(1.0)
    params._lambda = Array(0.0)
    params
  }


}

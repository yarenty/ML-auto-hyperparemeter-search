package com.yarenty.ml.hypersearch.example

import hex.glm.GLMModel.GLMParameters

/**
  * Created by yarenty on 11/05/2017.
  */
object GLMHyperParams {

  private val params = new GLMParameters()

  
  val lambdas = Array(0.0, 0.1, 0.2, 0.4, 0.6, 0.9, 1.1, 1.5, 2.0, 3.0, 5.0, 10.0, 20.0, 40.0 ,50.0)
  val alphas = 0.0 to 1.0 by 0.1
  val betas = Array(1e-5,1e-4,2e-4, 4e-4, 1e-3, 2e-3, 5e-3, 1e-2,5e-2,1e-1)
  
  def getParams(): GLMParameters = {

    params._family = GLMParameters.Family.binomial
    params._alpha = Array(1.0) //0.0 to 1.0 inclussive
    
    params._lambda = Array(0.0) //overfitting
//    params._lambda_min_ratio = -1
    
    params._beta_epsilon = 1e-4  //Precision of the vector of coefficients
  
    
   // params._lambda_search = true 50 down to 
//    
//    params._gradient_epsilon = -1
//    params._max_active_predictors = -1
//    params._max_iterations = -1
//    params._nlambdas = -1
//    params._obj_reg =  -1
//    params._objective_epsilon = -1
//    params._prior = -1
//    params._nfolds = 0
    params
  }


}

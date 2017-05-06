package com.yarenty.ml.hypersearch.example

// Create an environment
import hex.genmodel.utils.DistributionFamily
import hex.deeplearning.DeepLearningModel
import hex.tree.gbm.GBMModel
import hex.{Model, ModelMetricsBinomial}
import hex.glm.{GLM, GLMModel}
import hex.glm.GLMModel.GLMParameters
import water.H2OStarter
import water.fvec.{H2OFrame, Vec}
import water.parser._


/**
  * Created by yarenty on 28/04/2017.
  */
object Test extends H2OStarter {


  def main(args: Array[String]): Unit = {

    val args = Array("-name", "YOLO",
      "-ip", "127.0.0.1",
      "--ga_opt_out")
    H2OStarter.start(args, System.getProperty("user.dir"))


    val datadir = "/opt/data/airlines"
    val tname = "0.01m"

    def getParser: ParseSetup = {
      val p = new ParseSetup()
      p.setParseType(DefaultParserProviders.CSV_INFO)
      p.setSeparator(44)
      p.setSingleQuotes(false)
      p.setCheckHeader(1)
      p
    }


    val t0 = System.nanoTime

    val d_train = new H2OFrame(getParser, new java.net.URI(s"${datadir}/train-${tname}.csv"))
    val d_test = new H2OFrame(getParser, new java.net.URI(s"${datadir}/test.csv"))


    val t1 = System.nanoTime
    println("Data loaded: " + ((t1 - t0) / 1e9))

    val params = new GLMParameters()
    params._train = d_train.key
    params._valid = d_test.key
    params._response_column = "dep_delayed_15min"
    params._family = GLMParameters.Family.binomial
    params._alpha = Array(1.0)
    params._lambda = Array(0.0)
    val lr = new GLM(params)
    val model = lr.trainModel.get
    val t2 = System.nanoTime

    println("Execution completed: " + ((t2 - t1) / 1e9))


    val in = model._parms

    println("HYPERPARAMS:" + in.toJsonString)

    val auc = model._output._validation_metrics

    println("AUCC:" + auc)
  }

}

package com.yarenty.chart

import java.util.Random

import org.jzy3d.analysis.{AbstractAnalysis, AnalysisLauncher}
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.colors.Color
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.Scatter
import org.jzy3d.plot3d.rendering.canvas.Quality
//
//
//object DemoScatter {
//  @throws[Exception]
//  def main(args: Array[String]): Unit = {
//    AnalysisLauncher.open(new DemoScatter)
//  }
//}

object DemoScatter extends AbstractAnalysis {

  def main(args: Array[String]): Unit = {
    AnalysisLauncher.open(this)
  }
  
  override def init(): Unit = {
    val size = 500
    val points = new Array[Coord3d](size)
    val colors = new Array[Color](size)
    val r = new Random
    r.setSeed(0)

    for (i <- 0 until size) {
      val x = r.nextFloat - 0.5f
      val y = r.nextFloat - 0.5f
      val z = r.nextFloat - 0.5f
      points(i) = new Coord3d(x, y, z)
      val a = 0.25f
      colors(i) = new Color(x, y, z, a)

    }
    println("filled")

    val scatter = new Scatter(points, colors)
    chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt")
    chart.getScene.add(scatter)
//    chart.getScene.getGraph.add(scatter)
  }
}
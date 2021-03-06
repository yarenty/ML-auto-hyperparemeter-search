package com.yarenty.chart

import org.jzy3d.analysis.{AbstractAnalysis, AnalysisLauncher}
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.colors.{Color, ColorMapper}
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.maths.Range
import org.jzy3d.plot3d.builder.{Builder, Mapper}
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid
import org.jzy3d.plot3d.rendering.canvas.Quality


object DemoSurface extends AbstractAnalysis {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    AnalysisLauncher.open(this)
  }

  override def init(): Unit = {

    // Define a function to plot
    val mapper = new Mapper() {
      override def f(x: Double, y: Double): Double = x * Math.sin(x * y)
    }

    // Define range and precision for the function to plot
    val range = new Range(-3, 3)
    val steps = 80

    // Create the object to represent the function over the given range.
    val surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper)
    surface.setColorMapper(new ColorMapper(new ColorMapRainbow, surface.getBounds.getZmin, surface.getBounds.getZmax, new Color(1, 1, 1, .5f)))
    surface.setFaceDisplayed(true)
    surface.setWireframeDisplayed(false)

    // Create a chart
    chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType)
    chart.getScene.getGraph.add(surface)
  }
}
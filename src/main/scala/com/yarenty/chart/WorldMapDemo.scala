package com.yarenty.chart

import java.util.concurrent.Executors
import org.jzy3d.analysis.AbstractAnalysis
import org.jzy3d.analysis.AnalysisLauncher
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.maths.BoundingBox3d
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout
import org.jzy3d.plot3d.primitives.axes.layout.providers.SmartTickProvider
import org.jzy3d.plot3d.primitives.axes.layout.renderers.IntegerTickRenderer
import org.jzy3d.plot3d.rendering.canvas.ICanvas
import org.jzy3d.plot3d.rendering.canvas.Quality
import org.jzy3d.plot3d.rendering.scene.Scene
import org.jzy3d.plot3d.rendering.view.CroppingView
import org.jzy3d.plot3d.rendering.view.View


object WorldMapDemo extends AbstractAnalysis {


  @throws[Exception]
  def main(args: Array[String]): Unit = {
    AnalysisLauncher.open(this)
  }

  @throws[Exception]
  override def init(): Unit = {

    // Create the world map chart
    val f = new AWTChartComponentFactory() {
      override def newView(scene: Scene, canvas: ICanvas, quality: Quality) = new CroppingView(getFactory, scene, canvas, quality)
    }

    chart = f.newChart(Quality.Advanced, "awt")


    // Instantiate world map and parse the file
    val worldMap = new WorldMapLoader()
    worldMap.parseFile("world_map.csv")


    // Add world map line stripe to chart
    chart.getScene.getGraph.add(worldMap.lineStrips)


    // Set axis labels for chart
    val axeLayout = chart.getAxeLayout
    axeLayout.setXAxeLabel("Longitude (deg)")
    axeLayout.setYAxeLabel("Latitude (deg)")
    axeLayout.setZAxeLabel("Altitude (km)")

    // Set precision of tick values
    axeLayout.setXTickRenderer(new IntegerTickRenderer)
    axeLayout.setYTickRenderer(new IntegerTickRenderer)
    axeLayout.setZTickRenderer(new IntegerTickRenderer)

    // Define ticks for axis
    axeLayout.setXTickProvider(new SmartTickProvider(10))
    axeLayout.setYTickProvider(new SmartTickProvider(10))
    axeLayout.setZTickProvider(new SmartTickProvider(10))

    // Set map viewpoint
    chart.getView.setViewPoint(new Coord3d(-2 * Math.PI / 3, Math.PI / 4, 0))

    // Animate bounds change for demo
    Executors.newCachedThreadPool.execute(shiftBoundsTask)
  }

  private def shiftBoundsTask = new Runnable() {
    val step = 1

    override

    def run(): Unit = {
      while (true) {

        val b = chart.getView.getBounds
        if (b.getXRange.getMin > 0) {
          chart.getView.setScaleX(b.getXRange.add(-90), false)
          chart.getView.setScaleY(b.getYRange.add(-90), false)
        }
        chart.getView.setScaleX(b.getXRange.add(step), false)
        chart.getView.setScaleY(b.getYRange.add(step), false)
        chart.getView.shoot()
        try
          Thread.sleep(25)
        catch {
          case e: InterruptedException =>

        }
      }
    }
  }
}
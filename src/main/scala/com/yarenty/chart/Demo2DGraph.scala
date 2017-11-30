package com.yarenty.chart

import java.awt.{BorderLayout, Component}
import java.awt.event.{WindowAdapter, WindowEvent}
import java.io.IOException
import javax.swing.{BorderFactory, JFrame, JPanel}

import net.miginfocom.swing.MigLayout
import org.jzy3d.chart.Chart
import org.jzy3d.chart2d.Chart2d
import org.jzy3d.colors.Color
import org.jzy3d.plot2d.primitives.Serie2d
import org.jzy3d.plot3d.primitives.ConcurrentLineStrip
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout
import org.jzy3d.plot3d.primitives.axes.layout.providers.PitchTickProvider
import org.jzy3d.plot3d.primitives.axes.layout.renderers.PitchTickRenderer
import org.jzy3d.ui.LookAndFeel


/**
  * Showing a pair of 2d charts to represent pitch and amplitude variation of an
  * audio signal.
  *
  * Noticed problems on chart resize. Suspect "wrong stuffs" around miglayout or jogl.
  *
  * FIXME : use ChartGroup to build interface. Miglayout/JOGL interaction causes problem when downsizing windows
  *
  * @author Martin Pernollet
  */
object Demo2DGraph {
  var duration = 60f
  /** milisecond distance between two generated samples */
  var interval = 50
  var maxfreq = 880
  var nOctave = 5

  @throws[Exception]
  def main(args: Array[String]): Unit = {
    val log = new Demo2DGraph.PitchAmpliControlCharts(duration, maxfreq, nOctave)
    new Demo2DGraph.TimeChartWindow(log.getCharts)
    generateSamplesInTime(log)
    // generateSamples(log, 500000);
  }

  @throws[InterruptedException]
  def generateSamples(log: Demo2DGraph.PitchAmpliControlCharts, n: Int): Unit = {
    System.out.println("will generate " + n + " samples")
    var i = 0
    while ( {
      i < n
    }) { // Random audio info
      val pitch = Math.random * maxfreq
      val ampli = Math.random
      // Add to time series
      log.seriePitch.add(time(n, i), pitch)
      log.serieAmpli.add(time(n, i), ampli)

      {
        i += 1; i - 1
      }
    }
  }

  def time(n: Int, i: Int): Double = (i.toDouble / n) * duration

  @throws[InterruptedException]
  def generateSamplesInTime(log: Demo2DGraph.PitchAmpliControlCharts): Unit = {
    System.out.println("will generate approx. " + duration * 1000 / interval + " samples")
    start
    while ( {
      elapsed < duration
    }) {
      val pitch = Math.random * maxfreq
      val ampli = Math.random
      log.seriePitch.add(elapsed, pitch)
      log.serieAmpli.add(elapsed, ampli)
      // Wait a bit
      Thread.sleep(interval)
    }
  }

  /** Hold 2 charts, 2 time series, and 2 drawable lines */
  class PitchAmpliControlCharts(val timeMax: Float, val freqMax: Int, val nOctave: Int) {
    var pitchChart = new Chart2d
    pitchChart.asTimeChart(timeMax, 0, freqMax, "Time", "Frequency")
    val axe: IAxeLayout = pitchChart.getAxeLayout
    axe.setYTickProvider(new PitchTickProvider(nOctave))
    axe.setYTickRenderer(new PitchTickRenderer)
    var seriePitch = pitchChart.getSerie("frequency", Serie2d.Type.LINE)
    seriePitch.setColor(Color.BLUE)
    var pitchLineStrip = seriePitch.getDrawable.asInstanceOf[ConcurrentLineStrip]
    var ampliChart = new Chart2d
    ampliChart.asTimeChart(timeMax, 0, 1.1f, "Time", "Amplitude")
    var serieAmpli = ampliChart.getSerie("amplitude", Serie2d.Type.LINE)
    serieAmpli.setColor(Color.RED)
    var amplitudeLineStrip = serieAmpli.getDrawable.asInstanceOf[ConcurrentLineStrip]

    def getCharts: List[Chart] = {
       List(pitchChart,ampliChart)
    }
  }

  /** A frame to show a list of charts */
  @SerialVersionUID(7519209038396190502L)
  class TimeChartWindow @throws[IOException]
  (val charts: List[Chart]) extends JFrame {
    LookAndFeel.apply()
    val lines = "[300px]"
    val columns = "[500px,grow]"
    setLayout(new MigLayout("", columns, lines))
    var k = 0

    for (c <- charts) {
      addChart(c, {
        k += 1; k - 1
      })
    }
    windowExitListener()
    this.pack()
    show()
    setVisible(true)

    def addChart(chart: Chart, id: Int): Unit = {
      val canvas = chart.getCanvas.asInstanceOf[Component]
      val chartPanel = new JPanel(new BorderLayout)
      /*chartPanel.setMaximumSize(null);
                  chartPanel.setMinimumSize(null);
                  canvas.setMinimumSize(null);
                  canvas.setMaximumSize(null);*/ val b = BorderFactory.createLineBorder(java.awt.Color.black)
      chartPanel.setBorder(b)
      chartPanel.add(canvas, BorderLayout.CENTER)
      add(chartPanel, "cell 0 " + id + ", grow")
    }

    def windowExitListener(): Unit = {
      addWindowListener(new WindowAdapter() {
        override def windowClosing(e: WindowEvent): Unit = {
          TimeChartWindow.this.dispose()
          System.exit(0)
        }
      })
    }
  }

  /** Simple timer */
  protected var start = System.nanoTime
  

  def elapsed: Double = (System.nanoTime - start) / 1000000000.0
}
package com.yarenty.chart

import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.util
import javax.swing.JOptionPane

import org.jzy3d.colors.Color
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.CroppableLineStrip
import org.jzy3d.plot3d.primitives.Point

import scala.io.Source


class WorldMapLoader {
  var lineStrips = new util.ArrayList[CroppableLineStrip]

  // scalastyle:off
  def getFileName(fileName: String): String = {
    var is = getClass.getResource(fileName)
    if (null == is) is = getClass.getClassLoader.getResource(fileName)
    if (null == is) classOf[WorldMapLoader].getResource("/../resources/" + fileName)
    if (null == is) classOf[WorldMapLoader].getResource("/../../resources/test/" + fileName)
    is.getPath
  }


  def parseFile(filename: String): util.List[CroppableLineStrip] = {
    // Create local line strip and set color
    var lineStrip = new CroppableLineStrip
    lineStrip.setWireframeColor(Color.BLACK)
    
    val bufferedSource = Source.fromFile(getFileName(filename))
    for (line <- bufferedSource.getLines) {
      line.length match {
        case 0 =>
          // If row is blank, add line strip to list of line
          // strips and clear line strip
          lineStrips.add(lineStrip)
          lineStrip = new CroppableLineStrip
          lineStrip.setWireframeColor(Color.BLACK)
          
        case _ =>
          val cols = line.split(",").map(_.trim)
          lineStrip.add(new Point(new Coord3d(cols(0).toFloat, cols(1).toFloat, 0.0)))
      }
    }
    

      // Add the final lineStrip after while loop is complete.
      lineStrips.add(lineStrip)
     lineStrips
  }
}
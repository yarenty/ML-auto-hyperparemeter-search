package com.yarenty.ml.hypersearch

import water.parser.{DefaultParserProviders, ParseSetup}

/**
  * Created by yarenty on 11/05/2017.
  */
package object example {


  def getParser: ParseSetup = {
    val p = new ParseSetup()
    p.setParseType(DefaultParserProviders.CSV_INFO)
    p.setSeparator(44)
    p.setSingleQuotes(false)
    p.setCheckHeader(1)
    p
  }

}

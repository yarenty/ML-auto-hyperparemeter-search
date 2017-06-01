package com.yarenty.ml

import water.parser.{DefaultParserProviders, ParseSetup}

/**
  * Created by yarenty on 11/05/2017.
  */
package object hypersearch {


  def getParser: ParseSetup = {
    val p = new ParseSetup()
    p.setParseType(DefaultParserProviders.CSV_INFO)
    p.setSeparator(44)
    p.setSingleQuotes(false)
    p.setCheckHeader(1)
    p
  }

}

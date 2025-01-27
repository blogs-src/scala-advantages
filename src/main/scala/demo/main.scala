package demo

import extractor_lib.Extractor

@main def hello(): Unit = {
  val src = """
    |   enum Priority(val id: String) extends Model:
    |      case Low         extends Priority("low")
    |      case MediumLow   extends Priority("medium_low")
    |      case Medium      extends Priority("medium")
    |      case MediumHigh  extends Priority("medium_high")
    |      case High        extends Priority("high")
    |""".stripMargin
  val r = Extractor().processEnumSrc(src)
  println(r)
}

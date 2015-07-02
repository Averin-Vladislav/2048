package application
import scala.collection.JavaConverters._


object DecodingFile {
  def Decoding(array : java.util.ArrayList[String], output : String): String = {
    def Make(array : List[String], output : String): String = {
      array match {
        case head :: rest => {
          Make(rest, output
                    .concat("Direction: "   + head.charAt(0))
                    .concat("\nDigit: "     + head.charAt(1))
                    .concat("\nPositionX: " + head.charAt(2))
                    .concat("\nPositionY: " + head.charAt(3))
                    .concat("\n\n"))
        }
        case List() => output
      }
    }
    Make(array.asScala.toList, output)
  }
}






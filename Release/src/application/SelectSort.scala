package application
import scala.collection.JavaConverters._

object SelectSort {
  def MakeSort(mas: java.util.ArrayList[Integer]): java.util.List[Integer] = {
    @annotation.tailrec
    def Sort(notSortedMas: List[Integer], sortedMas: List[Integer]): List[Integer] = {
      notSortedMas match {
        case Nil => sortedMas
        case x::rest =>
          val (bigger, smaller) = sortedMas.partition(_ > x)
          Sort(rest, bigger ::: x :: smaller)
      }
    }
    Sort(mas.asScala.toList, List()).asJava
  }
}



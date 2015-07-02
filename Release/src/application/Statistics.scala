package application
import scala.collection.JavaConverters._



object Statistics {
  def FindStatisticsElement[T](list: java.util.ArrayList[T]): T = {
    def Find(list: List[T]): T = {
      list.foldLeft(Map.empty[T, Int].withDefaultValue(0)) ({
        case (map, key) => map.updated(key, map(key) + 1)
      }).maxBy(_._2)._1
    }
    Find(list.asScala.toList)
  }
}
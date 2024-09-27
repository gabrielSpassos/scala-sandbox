package semigroups

import cats.Semigroup
import cats.implicits.*

object CollectionSemigroup {

  def combineStrings(collection: Seq[String]): String = {
    collection.foldLeft("")(Semigroup[String].combine)
  }
}

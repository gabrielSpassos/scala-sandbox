package monoid

import cats.Monoid

object CollectionMonoid {
  def combineAll[A](collection: Seq[A], monoid: Monoid[A]): A = {
    collection.foldLeft(monoid.empty)(monoid.combine)
  }
}

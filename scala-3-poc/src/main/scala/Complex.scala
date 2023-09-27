class Complex (real: Double, imaginary: Double):
    def re = real
    def im() = imaginary
    override def toString() =
    "" + re + (if im() >= 0 then "+" else "") + im() + "i"


@main def Clazz: Unit =
    val c1 = new Complex(1.6, 2.8)
    val c2 = new Complex(1.6, -1)
    println(c1.re)
    println(c1.im())
    println(c1)
    println(c2.re)
    println(c2.im())
    println(c2)

import java.util.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Random, Success, Try}

class ExecutionContextSamples {
  private val forkJoinPool: ExecutorService = new ForkJoinPool(4)
  private val forkJoinExecutionContext: ExecutionContext = ExecutionContext.fromExecutorService(forkJoinPool)

  private val singleThread: Executor = Executors.newSingleThreadExecutor()
  private val singleThreadExecutionContext: ExecutionContext = ExecutionContext.fromExecutor(singleThread)

  private val globalExecutionContext: ExecutionContext = ExecutionContext.global
}

object FutureSample extends App {

  private val createMagicNumberF: Future[Int] = Future {
    createMagicNumber()
  }

  private val maxWaitTime: FiniteDuration = Duration(5, TimeUnit.SECONDS)

  println(Await.result(createMagicNumberF, maxWaitTime))
  createMagicNumberF.onComplete(printResult)
  createMagicNumberF.foreach(printSucceedResult)
  multiply(0).onComplete(printResult)
  multiply(1).onComplete(printResult)
  divide(0).onComplete(printResult)
  divide(1).onComplete(printResult)
  tryDivide(0).onComplete(printResult)
  tryDivide(1).onComplete(printResult)

  private def multiply(multiplier: Int): Future[Int] =
    if (multiplier == 0) {
      Future.successful(0)
    } else {
      Future(multiplier * createMagicNumber())
    }

  private def tryDivide(divider: Int): Future[Int] = Future.fromTry(Try {
    createMagicNumber() / divider
  })

  private def divide(divider: Int): Future[Int] =
    if (divider == 0) {
      Future.failed(new IllegalArgumentException("Don't divide by zero"))
    } else {
      Future(createMagicNumber() / divider)
    }

  private def printResult[A](result: Try[A]): Unit = result match {
    case Failure(exception) => println("Failed with: " + exception.getMessage)
    case Success(number)    => println("Succeed with: " + number)
  }

  private def printSucceedResult[A](result: A): Unit = println("Succeed: " + result)

  private def createMagicNumber(): Int = {
    Thread.sleep(1000L)
    val rand = new Random()
    rand.between(1, 100)
  }
}

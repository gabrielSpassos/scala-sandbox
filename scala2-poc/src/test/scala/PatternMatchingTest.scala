import org.scalatest.funsuite.AnyFunSuiteLike

class PatternMatchingTest extends AnyFunSuiteLike {

  test("should return email notification") {
    val patternMatching = new PatternMatching()
    val email = Email(sender = "Gabriel Passos", title = "Pattern Matching", body = "email body")

    val notification = patternMatching.sendNotification(email)

    assert("You received an email from Gabriel Passos with title Pattern Matching and the content is email body" == notification)
  }

  test("should return SMS notification") {
    val patternMatching = new PatternMatching()
    val sms = SMS(caller = "Gabriel Passos", message = "sms content")

    val notification = patternMatching.sendNotification(sms)

    assert("You received a SMS from Gabriel Passos with message sms content" == notification)
  }

  test("should return push notification") {
    val patternMatching = new PatternMatching()
    val push = Push("Gabriel Passos", "push content")

    val notification = patternMatching.sendNotification(push)

    assert("You received a push from Gabriel Passos with message push content" == notification)
  }

  test("should return urgent email notification") {
    val patternMatching = new PatternMatching()
    val email = Email(sender = "Gabriel Passos", title = "Pattern Matching", body = "real important email")
    val urgentPeopleTarget = Seq("Gabriel Passos", "Tom Brady")

    val notification = patternMatching.sendUrgentNotification(email, urgentPeopleTarget)

    assert("[Urgent Email] You received an email from Gabriel Passos with title Pattern Matching and the content is real important email" == notification)
  }

  test("should return urgent push notification") {
    val patternMatching = new PatternMatching()
    val push = Push("Gabriel Passos", "real important push content")
    val urgentPeopleTarget = Seq("Gabriel Passos", "Tom Brady")

    val notification = patternMatching.sendUrgentNotification(push, urgentPeopleTarget)

    assert("[Urgent Push] You received a push from Gabriel Passos with message real important push content" == notification)
  }

  test("should return urgent SMS notification") {
    val patternMatching = new PatternMatching()
    val sms = SMS(caller = "Gabriel Passos", message = "sms content")
    val urgentPeopleTarget = Seq("Gabriel Passos", "Tom Brady")

    val notification = patternMatching.sendUrgentNotification(sms, urgentPeopleTarget)

    assert("You received a SMS from Gabriel Passos with message sms content" == notification)
  }

  test("should mark email as read") {
    val patternMatching = new PatternMatching()
    val email = Email(sender = "Gabriel Passos", title = "Pattern Matching", body = "email body")

    patternMatching.checkNotification(email)

    assert(email.isRead)
  }

  test("should see sms message") {
    val patternMatching = new PatternMatching()
    val sms = SMS(caller = "Gabriel Passos", message = "sms content")

    patternMatching.checkNotification(sms)

    assert(sms.isSeen)
  }

  test("should dismiss push") {
    val patternMatching = new PatternMatching()
    val push = Push("Gabriel Passos", "push content")

    patternMatching.checkNotification(push)

    assert(push.isDismissed)
  }

}

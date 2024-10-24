sealed trait Notification

case class Email(sender: String, title: String, body: String) extends Notification {
  private var _isRead = false

  def read(): Unit = {
    _isRead = true
  }

  def isRead: Boolean = _isRead
}

case class SMS(caller: String, message: String) extends Notification {
  private var _isSeen = false

  def see(): Unit = {
    _isSeen = true
  }

  def isSeen: Boolean = _isSeen
}

case class Push(sender: String, message: String) extends Notification {
  private var _isDismissed = false

  def dismiss(): Unit = {
    _isDismissed = true
  }

  def isDismissed: Boolean = _isDismissed
}

class PatternMatching {

  def sendUrgentNotification(notification: Notification, urgentPeopleTarget: Seq[String]): String = {
    notification match {
      case Email(sender, _, _) if urgentPeopleTarget.contains(sender) =>
        "[Urgent Email] " + sendNotification(notification)
      case Push(sender, _) if urgentPeopleTarget.contains(sender) =>
        "[Urgent Push] ".concat(sendNotification(notification))
      case _ => sendNotification(notification)
    }
  }

  def sendNotification(notification: Notification): String = {
    notification match {
      case Email(sender, title, body) =>
        s"You received an email from $sender with title $title and the content is $body"
      case SMS(caller, message) =>
        s"You received a SMS from $caller with message $message"
      case Push(sender, message) =>
        s"You received a push from $sender with message $message"
    }
  }

  def checkNotification(notification: Notification): Unit = {
    notification match {
      case email: Email => email.read()
      case sms: SMS => sms.see()
      case push: Push => push.dismiss()
    }
  }

}

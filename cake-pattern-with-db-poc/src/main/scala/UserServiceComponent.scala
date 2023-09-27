trait UserServiceComponent {
  def userService: UserService

  trait UserService {
    def findAll: java.util.List[User]
    def save(user: User): Unit
  }
}

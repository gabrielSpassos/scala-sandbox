import javax.persistence.Persistence

object ApplicationLive {
  val userServiceComponent = new DefaultUserServiceComponent with UserRepositoryJPAComponent {   
    val em = Persistence.createEntityManagerFactory("cake.pattern").createEntityManager() 
  }
  val userService = userServiceComponent.userService
}

class CakeTestSpecification extends Specification with Mockito {

  trait MockEntitManager {
    val em = mock[EntityManager]

    def expect(f: (EntityManager) => Any): Unit = {
      f(em)
    }
  }

  "findAll should use the EntityManager's typed queries" in {
    val query = mock[TypedQuery[User]]
    val users: java.util.List[User] = new ArrayList[User]()

    val userService = new DefaultUserServiceComponent
                        with UserRepositoryJPAComponent
                        with MockEntitManager
    userService.expect { em=>
      em.createQuery("from User", classOf[User]) returns query
      query.getResultList returns users
    }

    userService.userService.findAll must_== users
  }
}

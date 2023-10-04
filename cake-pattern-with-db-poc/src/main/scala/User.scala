//import reflect.BeanProperty
import javax.persistence.{GeneratedValue, Entity, Id}
import com.sun.org.apache.xerces.internal.impl.Version

@Entity
class User() {

  //@BeanProperty
  @Id
  @GeneratedValue
  var id: java.lang.Long = _

  //@BeanProperty
  var firstName: String = _

  //@BeanProperty
  var lastName: String = _
}
package com.gabrielspassos
package domain

class User (val id: String, val login: String, val isActive: Boolean) {

  override def toString = s"User(id=$id, login=$login, isActive=$isActive)"

}

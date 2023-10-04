package com.gabrielspassos
package domain

class Account (val id: String, val number: Long, val isActive: Boolean) {
  
  override def toString = s"Account(id=$id, number=$number, isActive=$isActive)"
  
}

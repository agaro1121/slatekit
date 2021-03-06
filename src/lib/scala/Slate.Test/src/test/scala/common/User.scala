/**
<slate_header>
  author: Kishore Reddy
  url: https://github.com/kishorereddy/scala-slate
  copyright: 2015 Kishore Reddy
  license: https://github.com/kishorereddy/scala-slate/blob/master/LICENSE.md
  desc: a scala micro-framework
  usage: Please refer to license on github for more info.
</slate_header>
  */

package slate.test.common

import slate.common.{DateTime, Field}
import slate.entities.core._

class UserNormal1{
  var name = "slatekit"
}



class User(val id:Long) extends EntityWithId with IEntityUnique with EntityUpdatable[User] {

  def this() = {
    this(0)
  }


  @Field("",true, 50)
  var uniqueId = ""


  @Field("", true, -1)
  var createdAt  = DateTime.now()


  @Field("", true, -1)
  var createdBy  = 0


  @Field("", true, -1)
  var updatedAt  =  DateTime.now()


  @Field("", true, -1)
  var updatedBy  = 0


  @Field("", true, 30)
  var email = ""


  @Field("", true, 30)
  var firstName = ""


  @Field("", true, 30)
  var lastName = ""


  @Field("", true, 30)
  var isMale = false


  @Field("", true, 0)
  var age = 35


  @Field("", true, 10)
  var password = ""


  def init(first:String, last:String): User =
  {
    firstName = first
    lastName = last
    this
  }


  override def withId(id:Long): User = {
    val u = new User(id)
    u.age = this.age
    u.createdAt = this.createdAt
    u.createdBy = this.createdBy
    u.email = this.email
    u.firstName = this.firstName
    u.isMale = this.isMale
    u.lastName = this.lastName
    u.password = this.password
    u.uniqueId = this.uniqueId
    u.updatedAt = this.updatedAt
    u.updatedBy = this.updatedBy
    u
  }


  def fullname:String =
  {
    firstName + " " + lastName
  }


  override def toString():String =
  {
    email + ", " + firstName + ", " + lastName + ", " + isMale + ", " + age
  }
}


case class User2 (

  @Field("", true, 30)
   email :String = "",


  @Field("", true, 30)
   firstName :String = "",


  @Field("", true, 30)
   lastName :String = "",


  @Field("", true, 30)
   isMale :Boolean = false,


  @Field("", true, 0)
   age :Int = 35,


  id: Long = 0,


  @Field("",true, 50)
  uniqueId :String = "",


  @Field("", true, -1)
  createdAt: DateTime = DateTime.now(),


  @Field("", true, -1)
  createdBy: Int  = 0,


  @Field("", true, -1)
  updatedAt : DateTime =  DateTime.now(),


  @Field("", true, -1)
  updatedBy : Int  = 0

)
{

  def this(first:String, last:String) =
  {
    this(firstName = first, lastName = last)
  }


  def fullname:String =
  {
    firstName + " " + lastName
  }


  override def toString():String =
  {
    email + ", " + firstName + ", " + lastName + ", " + isMale + ", " + age
  }
}



case class Phone (

                   id: Long = 0,


                   @Field("",true, 50)
                   number :String = "",


                   @Field("",true, 50)
                   os :String = "",



                   @Field("",true, 50)
                   uniqueId :String = "",


                   @Field("", true, -1)
                   createdAt: DateTime = DateTime.now(),


                   @Field("", true, -1)
                   createdBy: Int  = 0,


                   @Field("", true, -1)
                   updatedAt : DateTime =  DateTime.now(),


                   @Field("", true, -1)
                   updatedBy : Int  = 0

                 ) extends EntityWithId
{
}

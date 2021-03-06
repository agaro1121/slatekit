/**
<slate_header>
  url: www.slatekit.com
  git: www.github.com/code-helix/slatekit
  org: www.codehelix.co
  author: Kishore Reddy
  copyright: 2016 CodeHelix Solutions Inc.
  license: refer to website and/or github
  about: A Scala utility library, tool-kit and server backend.
  mantra: Simplicity above all else
</slate_header>
  */

package slate.entities.repos

import slate.common.databases.Db
import slate.entities.core.{Entity, EntityMapper}

import scala.reflect.runtime.universe.Type


/**
  * Repository class specifically for MySql
  * @param entityType   : The data type of the entity/model
  * @param entityIdType : The data type of the primary key/identity field
  * @param entityMapper : The entity mapper that maps to/from entities / records
  * @param nameOfTable  : The name of the table ( defaults to entity name )
  * @param db
  * @tparam T
  */
class EntityRepoMySql [T >: Null <: Entity ](
                                               entityType  :Type,
                                               entityIdType:Option[Type]         = None,
                                               entityMapper:Option[EntityMapper] = None,
                                               nameOfTable :Option[String]       = None,
                                               val db:Db
                                             )
  extends EntityRepoSql[T](entityType, entityIdType, entityMapper, nameOfTable, db)
{

  override def top(count:Int, desc:Boolean ): List[T]  =
  {
    val orderBy = if(desc) " order by id desc" else " order by id asc"
    val sql = "select * from " + tableName + orderBy + " limit " + count
    val items = _db.mapMany(sql, _entityMapper).getOrElse(List[T]())
    items
  }


  override protected def scriptLastId(): String =
  {
    "SELECT LAST_INSERT_ID();"
  }


  override def tableName():String =
  {
    "`" + super.tableName() + "`"
  }
}

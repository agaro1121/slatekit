/**
  * <slate_header>
  * author: Kishore Reddy
  * url: https://github.com/kishorereddy/scala-slate
  * copyright: 2016 Kishore Reddy
  * license: https://github.com/kishorereddy/scala-slate/blob/master/LICENSE.md
  * desc: a scala micro-framework
  * usage: Please refer to license on github for more info.
  * </slate_header>
  */
package slate.examples

//<doc:import_required>

import slate.common.databases.DbConString
import slate.common.encrypt.Encryptor
import slate.common.envs.{Env, Envs, EnvItem}
import slate.core.common.Conf

//</doc:import_required>
//<doc:import_examples>
import slate.core.cmds.Cmd
import slate.common.results.ResultSupportIn
//</doc:import_examples>


class Example_Config extends Cmd("types") with ResultSupportIn {

  override protected def executeInternal(args: Any) : AnyRef = {

    //<doc:examples>
    // CASE 1: Load up config from application.conf in resources
    val conf = new Conf()
    println( "env.name: " + conf.getString("env.name") )
    println( "env.region: " + conf.getStringOrElse("env.region", "usa") )
    println( "db.enabled: " + conf.getBool("db.enabled") )
    println()


    // CASE 2: Get the environment selection ( env, dev, qa ) from conf or default
    val env = conf.env().getOrElse(new EnvItem("local", Env.DEV))
    println( s"${env.name}, ${env.env}, ${env.key}")
    println()


    // CASE 3: Inherit config from another config in resources folder
    // e.g. env.dev.conf ( dev environment ) can inherit from env.conf ( common )
    val confs1 = Conf.loadWithFallback("env.dev.conf", "env.conf")
    val dbConInherited = confs1.dbCon()
    printDbCon ( "db con - inherited : ", dbConInherited )


    // CASE 4: Override inherited config settings
    // e.g. env.loc.conf ( local environment ) overrides settings inherited from env.conf
    val confs2 = Conf.loadWithFallback("env.loc.conf", "env.conf")
    val dbConOverride = confs2.dbCon()
    printDbCon ( "db con - override : ", dbConOverride )


    // CASE 5: Multiple db settings, get 1 using a prefix
    // e.g. env.qa.conf ( qa environment ) with 2 db settings get one with "qa2" prefix.
    val confs3 = Conf.loadWithFallback("env.qa1.conf", "env.conf")
    val dbConMulti = confs3.dbCon("qa1")
    printDbCon ( "db con - multiple : ", dbConMulti )


    // CASE 6: File from user directory:
    // You can refer to a file path using a uri syntax:
    //
    // SYNTAX:
    // - "jars://"  refer to resources directory in the jar.
    // - "user://"  refer to user.home directory.
    // - "file://"  refer to an explicit path to the file
    // - "file://"  refer to a relative path to the file from working directory

    // EXAMPLES:
    // - jar://env.qa.conf
    // - user://slatekit/conf/env.qa.conf
    // - file://c:/slatekit/system/slate.shell/conf/env.qa.conf
    // - file://./conf/env.qa.conf
    //
    // CONFIG
    //
    // db {
    //   location: "user://slatekit/conf/db.conf"
    // }
    val confs4 = Conf.loadWithFallback("env.pro.conf", "env.conf")
    val dbConFile = confs4.dbCon( prefix = "db")
    printDbCon ( "db con - file ref: ", dbConFile )


    // CASE 7: Decryp encrypted strings in the config file
    // e.g.
    // db.user = "@{decrypt('8r4AbhQyvlzSeWnKsamowA')}"
    val encryptor = new Encryptor("wejklhviuxywehjk", "3214maslkdf03292")
    val confs5 = Conf.loadWithFallback("env.qa1.conf", "env.conf", enc = Some(encryptor) )
    println ( "db user decrypted : " + confs5.getStringEnc("db.user") )
    println ( "db pswd decrypted : " + confs5.getStringEnc("db.pswd") )
    println()
    //</doc:examples>
    ok()
  }


  private def printDbCon(desc:String, con:Option[DbConString]):Unit = {
    println(desc)
    println("driver: " + con.get.driver)
    println("url   : " + con.get.url)
    println("user  : " + con.get.user)
    println("pswd  : " + con.get.password)
    println()
  }


  /*
  //<doc:output>
```bat
 env.name: lc1
  db.enabled: true

  lc1, dev, dev : lc1


  db con - inherited :
  driver: com.mysql.jdbc.Driver
  url   : jdbc:mysql://localhost/db1
  user  : root
  pswd  : 123456789


  db con - override :
  driver: com.mysql.jdbc.Driver
  url   : jdbc:mysql://localhost/db1
  user  : root
  pswd  : 123456789


  db con - multiple :
  driver: com.mysql.jdbc.Driver
  url   : jdbc:mysql://localhost/db1
  user  : root
  pswd  : 123456789


  db con - file ref:
  driver: com.mysql.jdbc.Driver
  url   : jdbc:mysql://localhost/test1
  user  : root
  pswd  : t$123456789


  db user decrypted : root
  db pswd decrypted : 123456789
```
  //</doc:output>
  */
}

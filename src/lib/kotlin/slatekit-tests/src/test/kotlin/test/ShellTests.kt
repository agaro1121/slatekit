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

package slate.test

import org.junit.Test
import slatekit.apis.core.Api
import slatekit.apis.svcs.TokenAuth
import slatekit.common.ApiKey
import slatekit.common.Credentials
import slatekit.common.results.HELP
import slatekit.integration.apis.AppApi
import slatekit.integration.apis.CliApi
import slatekit.integration.apis.VersionApi
import slatekit.integration.common.AppEntContext


class ShellTests  {

  

    @Test fun can_execute_command() {
      val shell = getCli()
      val result = shell.onCommandExecute("sys.app.host")
      assert( result.value != null )
      assert( result.value!!.area == "sys" )
      assert( result.value!!.name == "app" )
      assert( result.value!!.action == "host" )
      assert( result.value!!.line == "sys.app.host" )
      assert( result.value!!.result!!.success)
    }
  

    @Test fun can_handle_help() {
      val shell = getCli()
      val result = shell.onCommandExecute("?")
      assert( result.value == null )
      assert( result.code == HELP )
      assert( result.msg  == "help")
    }


    @Test fun can_handle_help_for_area() {
      val shell = getCli()
      val result = shell.onCommandExecute("app ?")
      assert( result.value == null )
      assert( result.code == HELP )
      assert( result.msg  == "area ?")
    }


    @Test fun can_handle_help_for_area_api() {
      val shell = getCli()
      val result = shell.onCommandExecute("app.info ?")
      assert( result.value == null )
      assert( result.code == HELP )
      assert( result.msg  == "area.api ?")
    }


    @Test fun can_handle_help_for_area_api_action() {
      val shell = getCli()
      val result = shell.onCommandExecute("app.info.host ?")
      assert( result.value == null )
      assert( result.code == HELP )
      assert( result.msg  == "area.api.action ?")
    }
  


  private fun getCli(): CliApi {

    val ctx = AppEntContext.sample("id", "slate.tests", "slate unit tests", "slatekit")

    val apiKeys = listOf(
        ApiKey("user" , "7BF84B28FC8A41BBA3FDFA48D2B462DA", "user"                    ),
        ApiKey("po"   , "0F66CD55079C42FF85C001846472343C", "user,po"                 ),
        ApiKey("qa"   , "EB7EB37764AD4411A1763E6A593992BD", "user,po,qa"              ),
        ApiKey("dev"  , "3E35584A8DE0460BB28D6E0D32FB4CFD", "user,po,qa,dev"          ),
        ApiKey("ops"  , "5020F4A237A443B4BEDC37D8A08588A3", "user,po,qa,dev,ops"      ),
        ApiKey("admin", "54B1817194C1450B886404C6BEA81673", "user,po,qa,dev,ops,admin")
      )

    // 1. Get the user login info from .slate
    val creds = Credentials("1", "kishore", "kishore@abc.com", "3E35584A8DE0460BB28D6E0D32FB4CFD", "test", "ny")

    // 2. Register the apis using default mode ( uses permissions in annotations on class )
    val apis = listOf(
            Api(AppApi(ctx), declaredOnly = true, roles = "qa", protocol = "*"),
            Api(VersionApi(ctx), declaredOnly = true, roles = "qa", protocol = "*")
    )

    // 3. Build up the shell services that handles all the command line features.
    // And setup the api container to hold all the apis.
    val shell = CliApi(creds, ctx.toAppContext(), TokenAuth(apiKeys, null), apiItems = apis)
    return shell
  }
}
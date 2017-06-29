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

package slatekit.examples

//<doc:import_required>
import slatekit.common.Result
import slatekit.common.Success
import slatekit.common.results.ResultFuncs.conflict
import slatekit.common.results.ResultFuncs.failure
import slatekit.common.results.ResultFuncs.notAvailable
import slatekit.common.results.ResultFuncs.notFound
import slatekit.common.results.ResultFuncs.ok
import slatekit.common.results.ResultFuncs.success
import slatekit.common.results.ResultFuncs.unAuthorized
import slatekit.common.results.ResultFuncs.unexpectedError
import slatekit.common.results.SUCCESS

//</doc:import_required>

//<doc:import_examples>
import slatekit.core.cmds.Cmd
//</doc:import_examples>


class Example_Results : Cmd("results") {

  override fun executeInternal(args: Array<String>?) : Result<Any>
  {
    //<doc:examples>
    // CASE 1: The Result class is a container for the following
    // 1. success / failure  - flag
    // 2. message / error    - informative message
    // 3. data               - data being returned
    // 4. code               - matches http status codes
    // 5. ref                - tag for external id references
    // 6. exception          - error info
    // 7. tag                - for referencing/tracking purposes

    // NOTES: The result is in an alternative to Scala's Option[T]
    // and also the Try/Success/Failure. It provides a status code
    // as an integer that can be set to Http Status codes.
    // Also, the ResultSupportIn trait supports convenience functions
    // that can be used to easily build up Success or Failure Results
    // that match Http Status Codes ( see samples below ).
    val result = Success(
      data  = "userId:1234567890",
      code  = SUCCESS,
      msg   = "user created",
      tag   = "tag001",
      ref   = "XY123"
    )

    // NOTES: ResultSupport trait builds results that simulate Http Status codes
    // This allows easy construction of results/status from a server layer
    // instead of a controller/api layer

    // CASE 1: Success ( 200 )
    val res1 = success(123456, msg = "user created", tag= "promoCode:ny001")
    printResult(res1)


    // CASE 2: Failure ( 400 ) with message and ref tag
    val res2a = failure<String>(msg = "invalid email", tag = "23SKASDF23")
    printResult(res2a)


    // CASE 2: Failure ( 400 ) with data ( user ), message, and ref tag
    val res2b = failure<String>(msg = "invalid email", tag = "23SKASDF23")
    printResult(res2b)


    // CASE 4: Unauthorized ( 401 )
    val res3 = unAuthorized<String>(msg = "invalid email")
    printResult(res3)


    // CASE 5: Unexpected ( 500 )
    val res4 = unexpectedError<String>(msg = "invalid email")
    printResult(res4)


    // CASE 6: Conflict ( 409 )
    val res5 = conflict<String>(msg = "item already exists")
    printResult(res5)


    // CASE 7: Not found
    val res6 = notFound<String>(msg = "action not found")
    printResult(res6)


    // CASE 8: Not available
    val res7 = notAvailable<String>(msg = "operation currently unavailable")
    printResult(res7)
    //</doc:examples>

    return ok()
  }


  fun printResult(result:Result<Any>):Unit {
    println("success: " + result.success)
    println("message: " + result.msg)
    println("code   : " + result.code)
    println("data   : " + result.value)
    println("ref    : " + result.tag)
    println()
    println()
  }

  /*
  //<doc:output>
```bat
  success: true
  message: user created
  code   : 200
  data   : 123456
  ref    : promoCode:ny001


  success: false
  message: invalid email
  code   : 400
  data   : null
  ref    : 23SKASDF23


  success: false
  message: invalid email
  code   : 401
  data   : null
  ref    :


  success: false
  message: invalid email
  code   : 500
  data   : null
  ref    :


  success: false
  message: item already exists
  code   : 409
  data   : null
  ref    :


  success: false
  message: action not found
  code   : 404
  data   : null
  ref    :


  success: false
  message: operation currently unavailable
  code   : 503
  data   : null
  ref    :
```
  //</doc:output>
  */
}

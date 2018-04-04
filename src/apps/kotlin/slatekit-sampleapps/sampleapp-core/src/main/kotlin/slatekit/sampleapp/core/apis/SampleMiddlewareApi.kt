package slatekit.sampleapp.core.apis

import slatekit.apis.core.Action
import slatekit.apis.support.ApiWithMiddleware
import slatekit.common.Context
import slatekit.common.Request
import slatekit.common.Result
import slatekit.common.results.ResultFuncs
import slatekit.common.results.ResultFuncs.badRequest
import slatekit.sampleapp.core.models.User


open class SampleMiddlewareApi() : ApiWithMiddleware {


    // Used for demo/testing purposes
    var _user: User = User(0, "", "", "", "", "", "", "", "")
    var onBeforeHookCount = mutableListOf<Request>()
    var onAfterHookCount = mutableListOf<Request>()


    /**
     * hook for before the api call is made
     * @param ctx   : The application context
     * @param req   : The request
     * @param target: The target of the request
     * @param source: The originating source for this hook ( e.g. ApiContainer )
     * @param args  : Additional arguments supplied by the source
     */
    override fun onBefore(ctx: Context, req: Request, target: Action, source: Any, args: Map<String, Any>?): Unit {
        onBeforeHookCount.add(req)
    }


    /**
     * hook for after the api call is made
     * @param ctx   : The application context
     * @param req   : The request
     * @param target: The target of the request
     * @param source: The originating source for this hook ( e.g. ApiContainer )
     * @param args  : Additional arguments supplied by the source
     */
    override fun onAfter(ctx: Context, req: Request, target: Action, source: Any, args: Map<String, Any>?): Unit {
        onAfterHookCount.add(req)
    }


    /**
     * Filters the calls and returns a true/false indicating whether or not to proceed
     * @param ctx   : The application context
     * @param req   : The source to determine if it can be filtered
     * @param source: The originating source for this hook ( e.g. ApiContainer )
     * @param args  : Additional arguments supplied by the source
     */
    override fun onFilter(ctx: Context, req: Request, source: Any, args: Map<String, Any>?): Result<Any> {
        return if(req.action.startsWith("hi")) {
            badRequest<Boolean>("filtered out")
        } else {
            ResultFuncs.ok()
        }
    }


    fun hi(): String = "hi world"


    fun hello(): String = "hello world"
}
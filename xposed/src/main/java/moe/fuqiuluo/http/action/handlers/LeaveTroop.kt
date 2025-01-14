package moe.fuqiuluo.http.action.handlers

import com.tencent.common.app.AppInterface
import moe.fuqiuluo.http.action.ActionSession
import moe.fuqiuluo.http.action.IActionHandler
import moe.fuqiuluo.http.action.helper.TroopRequestHelper
import moe.fuqiuluo.http.entries.EmptyObject
import mqq.app.MobileQQ

internal object LeaveTroop: IActionHandler() {
    override suspend fun handle(session: ActionSession): String {
        if (!session.has("group_id")) {
            return noParam("group_id")
        }
        val groupId = session.getString("group_id")
        val runtime = MobileQQ.getMobileQQ().waitAppRuntime()
        if (runtime !is AppInterface)
            return logic("AppRuntime cannot cast to AppInterface")

        TroopRequestHelper.resignTroop(groupId.toLong())

        return ok(EmptyObject, "请求已提交")
    }

    override fun path(): String = "leave_group"
}
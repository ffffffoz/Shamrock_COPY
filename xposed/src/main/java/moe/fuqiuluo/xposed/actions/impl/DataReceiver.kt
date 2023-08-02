@file:OptIn(DelicateCoroutinesApi::class)
package moe.fuqiuluo.xposed.actions.impl

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import de.robv.android.xposed.XposedBridge
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moe.fuqiuluo.xposed.actions.IAction
import moe.fuqiuluo.xposed.helper.DynamicReceiver
import mqq.app.MobileQQ

lateinit var GlobalUi: Handler

class DataReceiver: IAction {
    override fun invoke(ctx: Context) {
        if (MobileQQ.getMobileQQ().qqProcessName != "com.tencent.mobileqq") return
        GlobalUi = Handler(ctx.mainLooper)
        GlobalScope.launch {
            val intentFilter = IntentFilter()
            intentFilter.addAction("moe.fuqiuluo.xqbot.dynamic")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                MobileQQ.getMobileQQ().registerReceiver(
                    DynamicReceiver, intentFilter,
                    Context.RECEIVER_EXPORTED
                )
            } else {
                MobileQQ.getMobileQQ().registerReceiver(DynamicReceiver, intentFilter)
            }
            XposedBridge.log("Register broadcast successfully.")
        }
    }
}

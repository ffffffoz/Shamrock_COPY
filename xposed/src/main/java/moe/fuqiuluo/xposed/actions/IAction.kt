package moe.fuqiuluo.xposed.actions

import android.content.Context

interface IAction {

    operator fun invoke(ctx: Context)

}
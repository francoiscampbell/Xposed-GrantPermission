package io.github.francoiscampbell.xposedgrantpermission

import android.Manifest
import android.content.pm.PackageManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by francois on 16-03-20.
 */
class Module : IXposedHookLoadPackage {
    val contextClassName = "android.app.ContextImpl"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            val contextClass = lpparam.classLoader.loadClass(contextClassName)
            XposedHelpers.findAndHookMethod(
                    contextClass,
                    "enforce",
                    String::class.java,
                    Int::class.java,
                    Boolean::class.java,
                    Int::class.java,
                    String::class.java,
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            super.beforeHookedMethod(param)
                            if (param.args[0].equals(Manifest.permission.INSTALL_LOCATION_PROVIDER)) {
                                param.args[1] = PackageManager.PERMISSION_GRANTED
                            }
                        }
                    }
            )
        } catch (e: ClassNotFoundException) {
            return
        }
    }
}
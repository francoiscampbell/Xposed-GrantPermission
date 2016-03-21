package io.github.francoiscampbell.xposedgrantpermission

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.test.ApplicationTestCase
import android.test.suitebuilder.annotation.SmallTest

/**
 * [Testing Fundamentals](http://d.android.com/tools/testing/testing_android.html)
 */
class ApplicationTest : ApplicationTestCase<Application>(Application::class.java) {

    //This actually doesn't work, the Xposed framework disables itself during instrumentation tests
    @SmallTest
    fun testPermission() {
        assertEquals(
                PackageManager.PERMISSION_GRANTED,
                context.checkPermission(
                        Manifest.permission.INSTALL_LOCATION_PROVIDER,
                        android.os.Process.myPid(),
                        android.os.Process.myUid()
                )
        )
    }
}
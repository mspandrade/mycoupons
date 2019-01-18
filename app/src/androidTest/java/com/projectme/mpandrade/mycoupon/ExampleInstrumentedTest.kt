package com.projectme.mpandrade.mycoupon

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    companion object {

        const val PACKAGE_NAME = "com.projectme.mpandrade.mycoupon"
    }

    @Test
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getInstrumentation().context

        assertEquals(PACKAGE_NAME, appContext.packageName)
    }
}

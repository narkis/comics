package com.narunas.simpledetailtest.base

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers



class Matchers {


    fun viewIsVisibleAndContainsText(@StringRes stringResource: Int) {
        Espresso.onView(ViewMatchers.withText(stringResource))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }


    fun viewContainsText(@IdRes viewId: Int, @StringRes stringResource: Int) {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(stringResource)))
    }

    fun viewIsVisible(@IdRes viewId: Int) {
        Espresso.onView(ViewMatchers.withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }


}
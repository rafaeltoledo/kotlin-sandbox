package net.rafaeltoledo.stak

import android.content.Intent
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.intent.matcher.UriMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule
import net.rafaeltoledo.stak.data.api.ApiCaller
import net.rafaeltoledo.stak.ui.UserViewHolder
import net.rafaeltoledo.stak.ui.activity.HomeActivity
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java, false, false)

    @Rule @JvmField
    val serverRule = InstrumentedTestRequestMatcherRule(InstrumentationRegistry.getContext())

    @Before
    fun setupMockServer() {
        ApiCaller.api = ApiCaller.build(serverRule.url("/").toString())
    }

    @Test
    fun shouldLoadUsersAndTriggerIntent() {
        serverRule
                .enqueueGET(200, "users.json")
                .assertPathIs("/2.2/users")
                .assertHasQuery("order", "desc")
                .assertHasQuery("sort", "reputation")
                .assertHasQuery("site", "stackoverflow")

        val expectedIntent = allOf(
                hasAction(Intent.ACTION_VIEW),
                hasCategories(hasItem(equalTo(Intent.CATEGORY_BROWSABLE))),
                hasData(UriMatchers.hasHost(equalTo("http://stackoverflow.com/users/157882/balusc"))))

        Intents.init();
        activityRule.launchActivity(Intent(Intent.ACTION_MAIN))

        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<UserViewHolder>(3))

        SystemClock.sleep(10000)

        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition<UserViewHolder>(2, click()));

        intended(expectedIntent);
        Intents.release();
    }
}

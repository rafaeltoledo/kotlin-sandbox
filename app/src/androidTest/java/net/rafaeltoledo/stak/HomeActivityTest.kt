package net.rafaeltoledo.stak

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.intent.matcher.UriMatchers.hasHost
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule
import net.rafaeltoledo.stak.data.api.ApiCaller
import net.rafaeltoledo.stak.ui.UserViewHolder
import net.rafaeltoledo.stak.ui.activity.HomeActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java, false, false)

    @Rule @JvmField
    val serverRule = InstrumentedTestRequestMatcherRule()

    @Before
    fun setupMockServer() {
        ApiCaller.api = ApiCaller.build(serverRule.url("/").toString())
    }

    @Test
    fun shouldLoadUsersAndTriggerIntent() {
        serverRule
                .addFixture(200, "users.json")
                .ifRequestMatches()
                .pathIs("/2.2/users")
                .queriesContain("order", "desc")
                .queriesContain("sort", "reputation")
                .queriesContain("site", "stackoverflow")

        Intents.init()
        activityRule.launchActivity(Intent(Intent.ACTION_MAIN))

        onView(withId(R.id.recyclerView))
                .perform(scrollToPosition<UserViewHolder>(3))

        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition<UserViewHolder>(2, click()))

        intended(allOf(hasAction(Intent.ACTION_VIEW),
                hasData(hasHost(equalTo("stackoverflow.com")))
        ))
        Intents.release()
    }
}

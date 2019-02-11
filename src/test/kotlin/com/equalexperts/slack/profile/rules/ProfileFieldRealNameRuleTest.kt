package com.equalexperts.slack.profile.rules

import com.equalexperts.slack.api.users.UsersForTesting
import com.equalexperts.slack.profile.UserProfilesForTesting
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ProfileFieldRealNameRuleTest {

    @Test
    fun `should return true for real name field presence`() {
        val userProfile = UserProfilesForTesting.testBot()
        val testUser = UsersForTesting.testBot(userProfile)

        val rule = ProfileFieldRealNameRule()

        val result = rule.checkProfile(testUser)
        assertTrue(result.result)
    }

    @Test
    fun `should return false for real name field absence`() {
        val userProfile = UserProfilesForTesting.testBot().copy(real_name = null)
        val testUser = UsersForTesting.testBot(userProfile)

        val rule = ProfileFieldRealNameRule()

        val result = rule.checkProfile(testUser)
        assertFalse(result.result)
    }
}
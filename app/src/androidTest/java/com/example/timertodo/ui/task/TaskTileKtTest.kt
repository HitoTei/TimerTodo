package com.example.timertodo.ui.task

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test


internal class TaskTileKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun existTaskText() {
        composeTestRule.setContent {
            TaskTile(text = "test", checked = false, onCheckedChange = {}, onClose = {})
        }
        composeTestRule.onNodeWithText("test").assertExists()
    }

    @Test
    fun existTimeLimitText() {
        composeTestRule.setContent {
            TaskTile(text = "test", timeLimit = "2021/10/10", checked = false, onCheckedChange = {}, onClose = {})
        }
        composeTestRule.onNodeWithText("期限：2021/10/10").assertExists()
    }

    @Test
    fun existTimeLeftText() {
        composeTestRule.setContent {
            TaskTile(text = "test", timeLeft = "timeLeft", checked = false, onCheckedChange = {}, onClose = {})
        }
        composeTestRule.onNodeWithText("残り：timeLeft").assertExists()
    }

    @Test
    fun timeLimitIsNullComponentShouldNotBeDisplayed(){
        composeTestRule.setContent {
            TaskTile(text = "test", checked = false, onCheckedChange = {}, onClose = {})
        }
        composeTestRule.onNodeWithText("期限：").assertDoesNotExist()
    }

    @Test
    fun timeLeftIsNullComponentShouldNotBeDisplayed(){
        composeTestRule.setContent {
            TaskTile(text = "test", checked = false, onCheckedChange = {}, onClose = {})
        }
        composeTestRule.onNodeWithText("残り：").assertDoesNotExist()
    }

    @Test
    fun onCheckedChangeIsCalledWhenCheckBoxIsClicked(){
        var checked: Boolean? = null
        composeTestRule.setContent {
            TaskTile(text = "test", checked = false, onCheckedChange = { checked = it }, onClose = {})
        }
        composeTestRule.onNodeWithTag("checkbox").assertExists().performClick()
        assert(checked == true)
    }

    @Test
    fun onCloseIsCalledWhenCloseButtonIsClicked(){
        var closed = false
        composeTestRule.setContent {
            TaskTile(text = "test", checked = false, onCheckedChange = {}, onClose = { closed = true })
        }
        composeTestRule.onNodeWithContentDescription("Delete").assertExists().performClick()
        assert(closed == true)
    }
}
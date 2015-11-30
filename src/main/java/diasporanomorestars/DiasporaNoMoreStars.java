package diasporanomorestars;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;

public class DiasporaNoMoreStars {

    public static ElementsCollection Messages = $$("#main_stream>div");

    @Step
    public static void logIn(String login, String password) {
        $(byText("Log In")).click();
        $("#user_username").sendKeys(login);
        $("#user_password").sendKeys(password);
        $(By.name("commit")).click();
    }

    public static void filterStream() {
        $(byText("Stream")).click();
    }

    public static void filterMyActivity() {
        $(byText("My activity")).click();
    }

    public static void filterMentions() {
        $(byText("@Mentions")).click();
    }

    public static void filterPublicActivity() {
        $(byText("Public activity")).click();
    }

    public static void assertFilterIs(String filterName) {
        $(".stream_title").shouldHave(exactText(filterName));
    }

    @Step
    public static void addMessage(String messageText) {
        $("#status_message_fake_text").sendKeys(messageText);
        $("#submit").click();
    }

    @Step
    public static void deleteMessage(String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .control-icons").hover().$(".delete .trash").click();
        switchTo().alert().accept();
    }

    @Step
    public static void deleteMessageByAuthor(String authorName) {
        deleteMessage(authorName);
    }

    @Step
    public static void likeMessage(String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .feedback .info").$(byText("Like")).click();
    }

    @Step
    public static void unlikeMessage(String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .feedback .info").$(byText("Unlike")).click();
    }

    @Step
    public static void addCommentToMessage(String commentText, String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .feedback .info").$(byText("Comment")).click();
        Messages.findBy(text(messageText)).$(".media .bd .comments .bd .new_comment .comment_box").sendKeys(commentText);
        Messages.findBy(text(messageText)).$(".media .bd .comments .bd .new_comment .submit_button .creation").click();
    }

    @Step
    public static void deleteCommentToMessage(String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .comments .comments .comment .bd .control-icons").hover().$(".delete .trash").click();
        switchTo().alert().accept();
    }

    @Step
    public static void reshareMessage(String originalAuthor) {
        Messages.findBy(text(originalAuthor)).$(".media .bd .feedback .info").$(byText("Reshare")).click();
        switchTo().alert().accept();
    }

    public static void assertMessagesByAuthorAre(String authorName, String ... messageTexts) {
        Messages.findBy(text(authorName)).$$(".media .bd .post-content .collapsible .markdown-content").shouldHave(exactTexts(messageTexts));
    }

    public static void assertAuthorResharedMessage(String authorName, String messageText) {
        Messages.findBy(text(authorName)).$(".media .bd .author-name.hovercardable").shouldHave(exactText(messageText));
    }

    public static void assertMessagesAreEmpty() {
        Messages.shouldHave(exactTexts(""));
    }

    public static void assertMessagesByAuthorAreEmpty(String authorName) {
        assertEquals(Messages.findBy(text(authorName)).$$(".media .bd .post-content .collapsible .markdown-content").isEmpty(), false);
    }

    public static void assertMessageHasLikes(String messageText, String messageLikes) {
        Messages.findBy(text(messageText)).$(".media .bd .likes").shouldHave(exactText(messageLikes));
    }

    public static void assertMessageHasNoLikes(String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .likes").shouldBe(Condition.empty);
    }

    public static void assertMessageHasComments(String messageText, String ... commentTexts) {
        Messages.findBy(text(messageText)).$$(".media .bd .comments .comment_stream .comments .comment .bd .comment-content").shouldHave(exactTexts(commentTexts));
    }

    public static void assertMessageHasNoComments(String messageText) {
        Messages.findBy(text(messageText)).$(".media .bd .comments .comment_stream .comment .bd .new_comment .comment_box").shouldBe(Condition.empty);
    }
}
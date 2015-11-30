package diasporanomorestars;

import org.junit.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static diasporanomorestars.DiasporaNoMoreStars.*;

public class DiasporaNoMoreStarsTest extends BaseTest {

    @Test
    public void testLogin() {
        $(".user-name").shouldHave(exactText("oksanaby@pod.nomorestars.com"));
        $("#aspect_stream_header").shouldHave(exactText("Stream"));
    }

    @Test
    public void testAddAndDeleteMessageAtFilterMyActivity() {

        filterMyActivity();
        assertFilterIs("My activity");

        addMessage("FilterMyActivityMessage");
        assertMessagesByAuthorAre("oksanaby@pod.nomorestars.com", "FilterMyActivityMessage");

        deleteMessage("FilterMyActivityMessage");
        assertMessagesAreEmpty();
    }

    @Test
    public void testLikeAndUnlikeMessageAtFilterMyActivity() {

        //Pre-conditions
        filterMyActivity();
        addMessage("FilterMyActivityMessage");

        likeMessage("FilterMyActivityMessage");
        assertMessageHasLikes("FilterMyActivityMessage", "1 Like");

        unlikeMessage("FilterMyActivityMessage");
        assertMessageHasNoLikes("FilterMyActivityMessage");

        //Post-conditions
        deleteMessage("FilterMyActivityMessage");
    }

    @Test
    public void testAddAndDeleteCommentAtFilterMyActivity() {

        //Pre-conditions
        filterMyActivity();
        addMessage("FilterMyActivityMessage");

        addCommentToMessage("Comment", "FilterMyActivityMessage");
        assertMessageHasComments("FilterMyActivityMessage", "Comment");

        deleteCommentToMessage("FilterMyActivityMessage");
        assertMessageHasNoComments("FilterMyActivityMessage");

        //Post-conditions
        deleteMessage("FilterMyActivityMessage");
    }

    @Test
    public void testAddAndDeleteMessageAtFilterMentions() {

        filterMentions();
        assertFilterIs("@Mentions");

        addMessage("FilterMentionsMessage");
        assertMessagesByAuthorAre("oksanaby@pod.nomorestars.com", "FilterMentionsMessage");

        deleteMessage("FilterMentionsMessage");
        assertMessagesAreEmpty();
    }

    @Test
    public void testAddAndDeleteMessageAtFilterStream() {

        addMessage("FilterStreamMessage");
        filterStream();
        assertMessagesByAuthorAre("oksanaby@pod.nomorestars.com", "FilterStreamMessage");

        deleteMessageByAuthor("oksanaby@pod.nomorestars.com");
        assertMessagesByAuthorAreEmpty("oksanaby@pod.nomorestars.com");
    }

    @Test
    public void testAddAndDeleteMessageAtFilterPublicActivity() {

        filterPublicActivity();
        assertFilterIs("Public activity");

        addMessage("FilterPublicActivity");
        assertMessagesByAuthorAre("oksanaby@pod.nomorestars.com", "FilterPublicActivity");

        deleteMessageByAuthor("oksanaby@pod.nomorestars.com");
        assertMessagesByAuthorAreEmpty("oksanaby@pod.nomorestars.com");
    }

    @Test
    public void testReshareMessageAtFilterStream() {

        reshareMessage("diaspora* HQ");
        assertAuthorResharedMessage("oksanaby@pod.nomorestars.com", "diaspora* HQ");

        // Post-conditions
        deleteMessageByAuthor("oksanaby@pod.nomorestars.com");
    }
}


package ar.edu.teclab;
import static org.assertj.core.api.Assertions.*;

import ar.edu.teclab.prueba.Comment;
import ar.edu.teclab.prueba.TicketService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TicketServiceTest {


    @Test
    public void ticketWithoutCommentsWhenNobodyHasCommentOn() {
        TicketService ticketService = new TicketService();
        ticketService.addTicket(1);
        List<Comment> comments = ticketService.getCommentsOfTicket(1);
        assertThat(comments).isEmpty();
    }

    @Test
    public void ticketCanHaveOneComment() {
        TicketService ticketService =  new TicketService();
        ticketService.addTicket(1);
        Comment aComment = new Comment("A comment");
        ticketService.commentOnTicket(1, aComment);
        List<Comment> comments = ticketService.getCommentsOfTicket(1);
        assertThat(comments).containsExactly(aComment);
    }

    @Test
    public void ticketCanHaveMultipleComments() {
        TicketService ticketService = new TicketService();
        ticketService.addTicket(1);
        Comment aFirstComment = new Comment("A comment");
        Comment aSecondComment = new Comment("A comment");
        ticketService.commentOnTicket(1, aFirstComment);
        ticketService.commentOnTicket(1, aSecondComment);
        List<Comment> comments = ticketService.getCommentsOfTicket(1);
        assertThat(comments).containsExactly(aFirstComment, aSecondComment);
    }

    @Test
    public void cannotCommentOnNonExistingTicket() {
        TicketService ticketService = new TicketService();
        Comment aComment = new Comment("A comment");

        assertThatThrownBy(() -> ticketService.commentOnTicket(10, aComment))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot comment on non-existent ticket");
    }

    @Test
    public void cannotGetCommentsOfNonExistingTicket() {
        TicketService ticketService = new TicketService();

        assertThatThrownBy(() -> ticketService.getCommentsOfTicket(10))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot get comments of non-existent tickets");
    }
}

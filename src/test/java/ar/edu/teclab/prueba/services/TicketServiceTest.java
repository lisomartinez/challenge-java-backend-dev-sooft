package ar.edu.teclab.prueba.services;

import ar.edu.teclab.prueba.dto.Comment;
import ar.edu.teclab.prueba.model.exceptions.DomainException;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TicketServiceTest {

    @Test
    public void ticketWithoutCommentsWhenNobodyHasCommentOn() {
        LocalTicketService ticketService = new LocalTicketService();
        ticketService.addTicket(1);
        List<Comment> comments = ticketService.getCommentsOfTicket(1);
        assertThat(comments).isEmpty();
    }

    @Test
    public void ticketCanHaveOneComment() {
        LocalTicketService ticketService =  new LocalTicketService();
        ticketService.addTicket(1);
        Comment aComment = createAComment(1L);
        ticketService.addCommentToTicket(1, aComment);
        List<Comment> comments = ticketService.getCommentsOfTicket(1);
        assertThat(comments).containsExactly(aComment);
    }

    private Comment createAComment(long id) {
        return new Comment(id, "A comment");
    }

    @Test
    public void ticketCanHaveMultipleComments() {
        LocalTicketService ticketService = new LocalTicketService();
        ticketService.addTicket(1);
        Comment aFirstComment = createAComment(1L);
        Comment aSecondComment = createAComment(2L);
        ticketService.addCommentToTicket(1, aFirstComment);
        ticketService.addCommentToTicket(1, aSecondComment);
        List<Comment> comments = ticketService.getCommentsOfTicket(1);
        assertThat(comments).containsExactly(aFirstComment, aSecondComment);
    }

    @Test
    public void cannotCommentOnNonExistingTicket() {
        LocalTicketService ticketService = new LocalTicketService();
        Comment aComment = createAComment(1L);

        assertThatThrownBy(() -> ticketService.addCommentToTicket(10, aComment))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot comment on non-existent ticket");
    }

    @Test
    public void cannotGetCommentsOfNonExistingTicket() {
        LocalTicketService ticketService = new LocalTicketService();

        assertThatThrownBy(() -> ticketService.getCommentsOfTicket(10))
                .isExactlyInstanceOf(DomainException.class)
                .hasMessage("Cannot get comments of non-existent tickets");
    }
}

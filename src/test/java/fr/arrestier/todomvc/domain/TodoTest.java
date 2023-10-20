package fr.arrestier.todomvc.domain;

import fr.arrestier.todomvc.domain.exception.InvalidData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TodoTest {
    Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo("id", "Title", false, 1);
    }

    @Test
    void setTitle_throwsInvalidDataException_whenNewTitleIsEmpty() {
        Assertions.assertThrows(InvalidData.class, () -> todo.setTitle(""));
    }

    @Test
    void setOrder_throwsInvalidDataException_whenNewOrderIsBelow1() {
        Assertions.assertThrows(InvalidData.class, () -> todo.setOrder(0));
    }
}
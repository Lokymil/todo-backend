package fr.arrestier.todomvc.domain;

import fr.arrestier.todomvc.domain.exception.AlreadyExisting;
import fr.arrestier.todomvc.domain.exception.InvalidData;
import fr.arrestier.todomvc.domain.exception.NotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    TodoService todoService;

    Todo todo;

    @BeforeEach
    void setUp() {
        todoService = new TodoService(todoRepository);
        todo = new Todo("id", "Title", false, 1);
    }

    @Test
    void getAll_retrieveAllTodosAsIs() {
        var expectedTodos = new ArrayList<Todo>();
        expectedTodos.add(todo);

        when(todoRepository.findAll()).thenReturn(expectedTodos);
        Assertions.assertEquals(todoService.getAll(), expectedTodos);
    }

    @Test
    void create_returnCreatedTodoAsIs_whenAllParamsAreValid() throws AlreadyExisting, InvalidData {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        Assertions.assertEquals(todoService.create("Title"), todo);
    }

    @Test
    void create_throwsInvalidParameterException_whenTitleIsEmpty() {
        Assertions.assertThrows(InvalidData.class, () -> todoService.create(""));
    }

    @Test
    void create_throwsAlreadyExistingException_whenTodoAlreadyExists() {
        when(todoRepository.save(any(Todo.class))).thenThrow(RuntimeException.class);

        Assertions.assertThrows(AlreadyExisting.class, () -> todoService.create("Title"));
    }

    @Test
    void deleteCompleted_callsDeletionForTodosWithCompletedFlagAsTrue() {
        todoService.deleteCompleted();

        ArgumentCaptor<Boolean> completedCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(todoRepository).deleteByCompleted(completedCaptor.capture());
        Assertions.assertEquals(true, completedCaptor.getValue());
    }

    @Test
    void updateById_returnsUpdatedTodo() throws InvalidData, AlreadyExisting, NotFound {
        when(todoRepository.findById(any(String.class))).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        Assertions.assertEquals(todoService.updateById("id", "Title", false, 1), todo);
    }

    @Test
    void updateById_throwsInvalidDataException_whenTitleIsEmpty() {
        when(todoRepository.findById(any(String.class))).thenReturn(Optional.of(todo));
        Assertions.assertThrows(InvalidData.class, () -> todoService.updateById("id", "", false, 1));
    }

    @Test
    void updateById_throwsInvalidDataException_whenOrderIsBelow1() {
        when(todoRepository.findById(any(String.class))).thenReturn(Optional.of(todo));
        Assertions.assertThrows(InvalidData.class, () -> todoService.updateById("id", "Title", false, 0));
    }

    @Test
    void updateById_throwsNotFoundException_whenNoTodoIsFoundWithId() {
        when(todoRepository.findById(any(String.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFound.class, () -> todoService.updateById("id", "Title", false, 1));
    }

    @Test
    void updateById_throwsAlreadyExistingException_whenTodoIsFoundWithNewTitle() {
        when(todoRepository.findByTitle(any(String.class))).thenReturn(Optional.of(todo));
        Assertions.assertThrows(AlreadyExisting.class, () -> todoService.updateById("id", "Title", false, 1));
    }

    @Test
    void deleteById_throwsNotFoundException_whenNotTodoIsFoundWithId() {
        when(todoRepository.findById(any(String.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFound.class, () -> todoService.deleteById("id"));
    }
}
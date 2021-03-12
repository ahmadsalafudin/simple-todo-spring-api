package com.alghibrany.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import com.alghibrany.springbootmongodb.exception.TodoCollectionException;
import com.alghibrany.springbootmongodb.model.TodoDTO;
import com.alghibrany.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepo.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.todoAllAlreadyExist());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todo.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
        if (optionalTodo.isPresent()) {
            return optionalTodo.get();
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoWithId = todoRepo.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());

        if (todoWithId.isPresent()) {
            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                throw new TodoCollectionException(TodoCollectionException.todoAllAlreadyExist());
            }

            TodoDTO todoToUpdate = todoWithId.get();

            todoToUpdate.setTodo(todo.getTodo());
            todoToUpdate.setDescription(todo.getDescription());
            todoToUpdate.setCompleted(todo.getCompleted());
            todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToUpdate);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }

    }

    @Override
    public void deleteTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) {
            todoRepo.deleteById(id);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }
}

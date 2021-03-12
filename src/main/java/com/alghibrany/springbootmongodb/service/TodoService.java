package com.alghibrany.springbootmongodb.service;

import java.util.List;

import javax.validation.ConstraintViolationException;
import com.alghibrany.springbootmongodb.exception.TodoCollectionException;
import com.alghibrany.springbootmongodb.model.TodoDTO;

public interface TodoService {
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;

    public List<TodoDTO> getAllTodos();

    public TodoDTO getSingleTodo(String id) throws TodoCollectionException;

    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;

    public void deleteTodo(String id) throws TodoCollectionException;
}

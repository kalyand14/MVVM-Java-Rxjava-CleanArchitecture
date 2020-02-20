package com.android.basics.data.source.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.android.basics.data.source.entity.TodoTbl;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TodoDao {

    @Query("INSERT INTO todo (userId, name, description, dueDate, isCompleted) VALUES (:userId, :name, :description, :dueDate, :isCompleted)")
    Completable insert(int userId, String name, String description, String dueDate, boolean isCompleted);

    @Query("DELETE FROM todo WHERE todoId =:todoId")
    Completable delete(int todoId);

    @Update
    Completable update(TodoTbl todoTbl);

    @Query("SELECT * from todo WHERE userId =:userId ORDER BY todoId DESC")
    Flowable<List<TodoTbl>> getAllTodo(int userId);

    @Query("SELECT * from todo WHERE todoId =:todoId")
    Flowable<TodoTbl> getTodo(int todoId);
}

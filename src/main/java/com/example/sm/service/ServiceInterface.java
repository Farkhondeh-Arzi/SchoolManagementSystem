package com.example.sm.service;

import java.util.List;

public interface ServiceInterface<T> {

	public List<T> getAll();

	public T add(T object);

	public T get(int Id);

	public T update(T object, int Id);

	public void delete(int Id);
}

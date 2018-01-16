package com.xd.flexible.aidl;

interface IBookManager{
List<Book> getBookList();
void addBook(in Book book);
}
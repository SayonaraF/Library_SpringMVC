package ru.broyaka.library.DAO;

import org.springframework.jdbc.core.RowMapper;
import ru.broyaka.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt(1));
        book.setName(resultSet.getString(2));
        book.setAuthor(resultSet.getString(3));
        book.setYear(resultSet.getInt(4));
        book.setPerson_id(resultSet.getInt(5));
        return book;
    }
}

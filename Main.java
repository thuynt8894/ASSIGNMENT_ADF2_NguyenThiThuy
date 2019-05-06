package Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
// tạo database URL
            String DB_URL = "jdbc:mysql://localhost:3306/asmadf2_th1804010";
            String DB_USER = "root";
            String DB_PASSWORD = "";
// connect db
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = conn.createStatement();
            System.out.println("1:Add new a book into db");
            System.out.println("2:Select a book by id");
            System.out.println("3:Search book by name");
            System.out.println("4:Get all book from db");
            System.out.println("5:Update a book");
            System.out.println("6:Remove a book");
            System.out.println("7:Export a book to txt file");
            System.out.println("8:Export list book to list txt file");

            System.out.println("Enter your choice:");


            Scanner sc = new Scanner(System.in);
            Scanner ac = new Scanner(System.in);
            int choice =sc.nextInt();

            while (choice >=1 && choice<=8) {
                switch (choice) {
                    case 1: {
                        System.out.println("Enter book's id");
                        int id = sc.nextInt();
                        System.out.println("Enter book's title");
                        String title = ac.nextLine();
                        System.out.println("Enter book's author");
                        String author = ac.nextLine();
                        System.out.println("Enter book's releasedate");
                        String releaseDate = ac.nextLine();
                        System.out.println("Enter book's content");
                        String content = ac.nextLine();

                        String addBook = "INSERT INTO books (ID, Title, Author, Release Date, Content) VALUES('" + id + "','" + title + "','" + author + "','" + releaseDate + "','" + content + "')";
                        statement.executeUpdate(addBook);
                        System.out.println("Success!");
                        break;
                    }
                    case 2: {
                        System.out.println("Enter the book's ID to search");
                        int id = sc.nextInt();
                        String sql = "SELECT* FROM books WHERE id =" + id;
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            System.out.println("ID: " + resultSet.getString("ID") + "Book's name: " + resultSet.getString("Title") + resultSet.getString("Author") + "Date" + resultSet.getDate("Release Date") + " Content: " + resultSet.getString("Content") + "Author: ");
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Enter the book's Name to search");
                        String name = sc.nextLine();
                        String sql = "SELECT * FROM books WHERE name=" + name;
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            System.out.println("ID: " + resultSet.getString("ID") + "Book's name: " + resultSet.getString("Title") + resultSet.getString("Author") + "Date" + resultSet.getDate("Release Date") + " Content: " + resultSet.getString("Content") + "Author: ");
                        }
                    }
                    case 4: {
                        String sql = "SELECT * FROM books ";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while (resultSet.next()) {
                            System.out.println("Book's name: " + resultSet.getString("title") + " Content: " + resultSet.getString("content") + "Author: " + resultSet.getString("author"));
                        }
                        break;
                    }
                    case 5: {
                        System.out.println("Enter book's id to update");
                        int id = sc.nextInt();
                        System.out.println("Title  book");
                        String name = ac.nextLine();
                        String update = "UPDATE books SET title = ? WHERE id = ?";
                        PreparedStatement preparedStmt = conn.prepareStatement(update);
                        preparedStmt.setString(1, name);
                        preparedStmt.setInt(2, id);
                        // execute the java preparedstatement
                        preparedStmt.executeUpdate();

                        conn.close();
                        System.out.println("Update successed!");
                        break;

                    }
                    case 6: {
                        System.out.println("Enter book's id to remove");
                        int id = sc.nextInt();
                        String sql = "DELETE FROM books WHERE id=" + id;
                        statement.execute(sql);
                        System.out.println("Remove successed!");
                        break;
                    }
                    case 7: {
                        System.out.println("Enter book's id to export to txt file");
                        int id = sc.nextInt();
                        statement = conn.createStatement();
                        String sql = "SELECT * FROM books WHERE id = " + id;
                        ResultSet rec = statement.executeQuery(sql);
                        String path = "D:\\ALAPTRINH\\Sem2\\src\\Assignment\\abook.txt";
                        FileWriter writer;
                        try {
                            File file = new File(path);
                            writer = new FileWriter(file, true);
                            while ((rec != null) && (rec.next())) {
                                writer.write(rec.getString("id"));
                                writer.write(",");
                                writer.write(rec.getString("title"));
                                writer.write(",");
                                writer.write(rec.getString("author"));
                                writer.write(",");
                                writer.write(rec.getString("releaseDate"));
                                writer.write(",");
                                writer.write(rec.getString("content"));
                                writer.write("\r\n");
                            }
                            writer.close();
                            System.out.println("Export to txt file success!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (conn != null) {
                                statement.close();
                                conn.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 8: {

                        statement = conn.createStatement();
                        String sql = "SELECT * FROM books ORDER BY id ASC";
                        ResultSet resultSet = statement.executeQuery(sql);
                        String path = "D:\\ALAPTRINH\\Sem2\\src\\Assignment\\allbook.txt";
                        FileWriter writer;
                        try {
                            File file = new File(path);
                            writer = new FileWriter(file, true);
                            while ((resultSet != null) && (resultSet.next())) {
                                writer.write(resultSet.getString("id"));
                                writer.write(",");
                                writer.write(resultSet.getString("title"));
                                writer.write(",");
                                writer.write(resultSet.getString("author"));
                                writer.write(",");
                                writer.write(resultSet.getString("releaseDate"));
                                writer.write(",");
                                writer.write(resultSet.getString("content"));
                                writer.write("\r\n");
                            }
                            writer.close();
                            System.out.println("Export to txt file successed!");
                        }

                        catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (conn !=null){
                                statement.close();
                                conn.close();
                            }
                        } catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                    break;

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
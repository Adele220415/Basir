import java.io.*;
import java.util.*;

class Book {
    String title, author;
    int id;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return id + " - " + title + " by " + author;
    }
}

public class LibraryManagement {
    static Scanner sc = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static final String FILE_NAME = "library.txt";

    public static void main(String[] args) {
        loadBooks();
        int choice;
        do {
            System.out.println("\n--- Library Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display Books");
            System.out.println("4. Save & Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addBook(); break;
                case 2: searchBook(); break;
                case 3: displayBooks(); break;
                case 4: saveBooks(); System.out.println("Books saved. Goodbye!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        books.add(new Book(id, title, author));
    }

    static void searchBook() {
        System.out.print("Enter title/author keyword: ");
        String keyword = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Book b : books) {
            if (b.title.toLowerCase().contains(keyword) || b.author.toLowerCase().contains(keyword)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No book found!");
    }

    static void displayBooks() {
        System.out.println("\n--- Book List ---");
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    static void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                bw.write(b.id + "," + b.title + "," + b.author);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    static void loadBooks() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    books.add(new Book(Integer.parseInt(data[0]), data[1], data[2]));
                }
            } catch (IOException e) {
                System.out.println("Error loading books: " + e.getMessage());
            }
        }
    }
}
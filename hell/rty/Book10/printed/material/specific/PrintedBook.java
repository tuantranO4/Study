package printed.material.specific;

import printed.material.Book;
import printed.material.CoverType;

public class PrintedBook extends Book {
    protected CoverType coverType;

    public PrintedBook(String author, String title, int pageCount, CoverType coverType) {
        super(author, title, pageCount + 6); //calls the Book parameterized struct
        this.coverType = coverType;
    }

    public PrintedBook(Book book) { //default constructor with HARDCOVER 
        this(book.getAuthor(), book.getTitle(), book.getPageCount(), CoverType.HARDCOVER);
    }

    public CoverType getCoverType() {
        return coverType;
    }

    @Override
    public int getPrice() {
        if (coverType == CoverType.HARDCOVER) {
            return super.getPrice() * 3;
        } else {
            return super.getPrice() * 2;
        }
    }

    public static PrintedBook decode(String text) {//J.K. Rowling : Harry Potter ; 500 (HARDCOVER)
        String[] split1 = text.split(":"); //split at ':'
        String[] split2 = split1[1].split(";"); //split at ';' (author = split1[0] and rest = split1[1])
        String[] split3 = split2[1].split("\\("); //split at '('
        String[] split4 = split3[1].split("\\)"); //split at ')'

        String author = split1[0].strip(); //strip: removes leading and trailing whitespaces ( clean up user input )
        String title = split2[0].strip();
        int pageCount = Integer.parseInt(split3[0].strip());
        if (split4[0].equals("HARDCOVER")) {
            return new PrintedBook(author, title, pageCount, CoverType.HARDCOVER);
        } else if (split4[0].equals("SOFTCOVER")) {
            return new PrintedBook(author, title, pageCount, CoverType.SOFTCOVER);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() { //J.K. Rowling : Harry Potter ; 500 (HARDCOVER)
        return super.toString() + " (" + coverType + ")";
    }
}

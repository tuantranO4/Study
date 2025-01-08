package printed.material;

public class Book {
    public static final String DEFAULT_AUTHOR = "John Steinbeck";
    public static final String DEFAULT_TITLE = "Of Mice and Men";
    public static final int DEFAULT_PAGE_COUNT = 107;

    private String author;
    private String title;
    protected int pageCount;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Book() { //no args constructor
        this(DEFAULT_AUTHOR, DEFAULT_TITLE, DEFAULT_PAGE_COUNT);
    }

    public Book(String author, String title, int pageCount) { //constructor
        checkInitData(author, title, pageCount); 
        initBook(author, title, pageCount);
    }

    private void checkInitData(String author, String title, int pageCount) {
        if (author.length() < 2 || title.length() < 4 || pageCount <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public String createReference(String articleTitle, int from, int to) {
        return getShortInfo() + "[" + from + "-" + to + "] referencedin article: " + articleTitle;
    }

    public static Book decode(String text) { //output: "Author: Title; PageCount"
        String[] split1 = text.split(":"); //Splits the input into two parts: Author and Title; PageCount
        String[] split2 = split1[1].split(";"); //Splits the input into two parts: Title and PageCount

        String author = split1[0].strip();
        String title = split2[0].strip();
        int pageCount = Integer.parseInt(split2[1].strip());

        return new Book(author, title, pageCount);
    }

    protected String getAuthorWithInitials() {
        String[] nameParts = author.split(" "); //split at ' '

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nameParts.length-1; i++) { //iterate through all parts of the name except the last one
            sb.append(nameParts[i].charAt(0) + ". "); //append first letter of each part of the name
        }
        // sb.append(nameParts[nameParts.length-1]); /Appends the last part of the name without any modification: "J. R. R. Tolkien"

        return sb.toString();
    }

    public int getPrice() {
        return pageCount;
    }

    public String getShortInfo() {
        return getAuthorWithInitials() + ": " + title.substring(0, 4) + "; " + pageCount; //substring(0, 4) returns the first 4 characters of the title
    }

    protected void initBook(String author, String title, int pageCount) { //initializes the book
        this.author = author;
        this.title = title;
        this.pageCount = pageCount;
    }

    @Override
    public String toString() { //toString method
        return author + ": " + title + "; " + pageCount;
    }
}

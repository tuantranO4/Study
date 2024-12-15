
-- Function to return the book's status
bookStatus :: Book -> String
bookStatus (Book t a _ True)  = t ++ " by " ++ a ++ " is available."
bookStatus (Book t a _ False) = t ++ " by " ++ a ++ " is checked out."

-- Function to determine if the book is a classic
isClassic :: Book -> Bool
isClassic (Book _ _ y _) = y < 1980

-- Example Books
book1 = Book "1984" "George Orwell" 1949 True
book2 = Book "Modern JavaScript" "John Doe" 2020 False

-- Main function to test the above implementations
main :: IO ()
main = do
    putStrLn $ bookStatus book1 -- Expected: "1984 by George Orwell is available."
    putStrLn $ bookStatus book2 -- Expected: "Modern JavaScript by John Doe is checked out."
    print $ isClassic book1     -- Expected: True
    print $ isClassic book2     -- Expected: False
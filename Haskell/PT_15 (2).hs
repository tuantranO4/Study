{- Progress Task  -}

{- Your neptun code : -}

{-
    Write a function paddingFirst which will get
        string - the string which contains one character used for the padding
        integer - how many copies will be added 
        string - the string to pad to the begginning
    
    If the integer is negative the function should return "Error".

    For example 
    paddingFirst "*" 5 "apple" -> "*****apple"
    apple is a original string. then you need to add "*" 5 times to the beginning of the string.
-}

paddingFirst :: String -> Int -> String -> String
paddingFirst _ n _ | n < 0 = "Error"
paddingFirst padChar 0 str = str
paddingFirst padding n str = padding ++ paddingFirst padding (n - 1) str


main = print(paddingFirst "*" 5 "apple") -- "*****apple"
-- main = print(paddingFirst "-" 0 "hello") -- "hello"
-- main = print(paddingFirst "W" 7 "") -- "WWWWWWW"
-- main = print(paddingFirst "o" (-9) "goodluck") -- "Error"

{- Progress Task 1 -}

{- Your neptun code : UG2F20 -}

{- 
    Create a function getSeason that takes an integer 
    (from 1 to 12) as input and returns the season in English. 
    - If the input is 12, 1, or 2, return "Winter".
    If the input is 3, 4, or 5, return "Spring".
    If the input is 6, 7, or 8, return "Summer".
    If the input is 9, 10, or 11, return "Autumn".
    If the input is anything else, return "Invalid month".
-}

getSeason :: Int -> String
getSeason a
    |a==12 || a==2 || a==1 = "Winter"
    |a>=3 && a<=5 = "Spring"
    |a>=6 && a<=8 = "Summer"
    |a>=8 && a<=11 = "Autumn"
    |otherwise ="Invalid month"

--main = print (getSeason 2)    -- "Winter"
--main = print (getSeason 4)    -- "Spring"
-- main = print (getSeason 7)    -- "Summer"
main = print (getSeason 10)   -- "Autumn"
--main = print (getSeason 13)   -- "Invalid month" 
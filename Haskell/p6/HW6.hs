import Data.Char (toUpper)
{-
    1.
    You are given a list of tuples where each tuple represents a person’s age and their income (in euros).
    Write a function that filters out tuples based on the following conditions:
    - Only include people who are at least 25 years old and earn at least €30,000.
    Once the list is filtered, apply a transformation:
    - For each remaining tuple, calculate a tax of 20% on the income and return the age and the after-tax income.
    - use `round` function to convert Double to Int
    Example:
    processPeople [(22, 25000), (30, 50000), (40, 60000), (26, 28000), (35, 32000)] -- Output: [(30, 40000), (40, 48000), (35, 25600)]
-}

--processPeople :: [(Int, Int)] -> [(Int, Int)]

-- main = print (processPeople [(22, 25000), (30, 50000), (40, 60000), (26, 28000), (35, 32000)]) -- [(30, 40000), (40, 48000), (35, 25600)]
-- main = print (processPeople [(20, 25000), (24, 29000), (18, 40000)]) -- []
-- main = print (processPeople [(25, 30000), (40, 70000), (50, 100000)]) -- [(25, 24000), (40, 56000), (50, 80000)]
-- main = print (processPeople [(30, 25000), (24, 35000), (45, 35000)]) -- [(45, 28000)]
-- main = print (processPeople [(27, 1000000), (35, 500000), (60, 2000000)]) -- [(27, 800000), (35, 400000), (60, 1600000)]

{-
    2.
    You are given the list of tuples of Int. Each sublist represents the highest and lowerst temperature in a day.
    Write a function that returns the day with the biggest difference between the highest and the lowest temperature. The index of the day should be returned and the index starts from 0. If there are multiple days with the same difference, return the first one.
    In case of an empty list, return -1.
    For example:
        dayWithMaxDifference [(30, 20), (35, 25), (40, 10), (33, 30)], returns 2
              max min    diff
        day0 (30, 20) -> 10
        day1 (35, 25) -> 10
        day2 (40, 10) -> 30 <- max diff day
        day3 (33, 30) -> 3       
-}

--dayWithMaxDifference :: [(Int, Int)] -> Int

-- main = print(dayWithMaxDifference [(30, 20), (35, 25), (40, 10), (33, 30)]) -- 2
-- main = print(dayWithMaxDifference [(17, 9), (16, 5), (18, 9)]) -- 1 
-- main = print(dayWithMaxDifference [(5, -5), (10, 0), (-10, -20)]) -- 0
-- main = print(dayWithMaxDifference []) -- -1



-- HW about generator
{-
    3.
    Create a function `isPrime` that takes an integer and returns a boolean.
    If the number is prime, return True, otherwise return False.
    If the number is less than 2 immediately return False.
-}
--isPrime :: Int -> Bool

-- main = print (isPrime 2) -- True
-- main = print (isPrime 7) -- True
-- main = print (isPrime 81) -- False
-- main = print (isPrime 109) -- True

{-
    4. Capitalize Words in a Sentence
    Write a function that capitalizes the first letter of every word in a given sentence.
    Example:
    capitalizeWords "hello world" -- Output: "Hello World"
-}

--capitalizeWords :: String -> String


-- main = print (capitalizeWords "hello world") -- "Hello World"
-- main = print (capitalizeWords "how are you") -- "How Are You"
-- main = print (capitalizeWords "apple banana orange kiwi grape") -- "Apple Banana Orange Kiwi Grape"


{-
Given a list of words, replace each word with a tuple containing the number of vowels and consonants in that word. 
Make sure to check for numbers and to not count them.

ex: ["Harry"] -> [(1,4)]
The word "Harry" has one vowl and four consonants

Hint:

You might want to use a list of vowels to check for vowels, make sure to cover bot lowercase and uppercase
-}

--hw7 :: [String] -> [(Int,Int)]


--main = print (hw7 ["Harry", "ooo", "pc", "Web"]) -- [(1,4),(3,0),(0,2),(1,2)]
--main = print (hw7 []) --[]
--main = print (hw7 ["FFAABB", "Clean"]) --[(2,4),(2,3)]
--main = print (hw7 ["12345", "he110", "W0r1D"]) --[(0,0),(1,1),(0,3)]


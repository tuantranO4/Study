---------------------------------------------------------------------------------------
---- Given a list of lists, for each list create a new list which has 2 numbers.
-- The first number represents the number of even numbers in that list.
-- The second number represents the sum of the even numbers in that list.

--countSumEvens :: [[Int]] -> [[Int]]

-- main = print (countSumEvens [[1,2,3,4],[71,43,42,92,3,8,1,8],[90,2,4,4],[]]) -- [[2,6],[4,150],[4,100],[0,0]]
-- main = print (countSumEvens [[1,3,5,6],[102,104,104,104],[2,2,2],[1,1,1]]) -- [[1,6],[4,414],[3,6],[0,0]]
-- main = print (countSumEvens [[]]) -- [[0,0]]
-- main = print (countSumEvens [[2,4,6],[1,1,1,1],[0,0,0,0]]) -- [[3,12],[0,0],[4,0]]

---------------------------------------------------------------------------------------
---- Given a list of lists and a character, for every list create a new list with two elements.
-- The first element is the number of occurrences of the given character.
-- The second element is the number of occurrences of every element except the given character.
-- Note that all the values will be lowercased

-- countChar :: [[Char]] -> Char -> [[Int]]

-- main = print (countChar [['a','b','c','d'],['d','d','e','l'],['a','p','x'],[]] 'd') -- [[1,3], [2,2],[0,3],[0,0]]
-- main = print (countChar [[]] 'c') -- [[0,0]]
-- main = print (countChar [['a','a'],['a','a'],['a','a'],['b','c','a']] 'a') -- [[2,0],[2,0],[2,0],[1,2]]
-- main = print (countChar [['x','x','x'],['y','y','y'],['z','z','z']] 'x') -- [[3,0],[0,3],[0,3]]

---------------------------------------------------------------------------------------
---- The replaceAll function takes a list and two values (old and new).
-- It replaces every occurrence of the old value with the new value.
-- If the old value is not found, the list remains unchanged.

-- replaceAll :: Int -> Int -> [Int] -> [Int]

-- main = print (replaceAll 3 9 [1, 2, 3, 4, 3, 5]) -- [1, 2, 9, 4, 9, 5]
-- main = print (replaceAll 7 0 [1, 2, 3, 4]) -- [1, 2, 3, 4] (no change)
-- main = print (replaceAll 0 1 [0, 0, 0]) -- [1, 1, 1]

---------------------------------------------------------------------------------------
---- The allEven function takes a list of integers and returns True if all elements are even. 
-- Otherwise, it returns False. If the list is empty, it returns True.

-- allEven :: [Int] -> Bool

-- main = print (allEven [2, 4, 6]) -- True
-- main = print (allEven [1, 2, 3]) -- False
-- main = print (allEven []) -- True

---------------------------------------------------------------------------------------
---- The consecutiveSums function takes a list of integers and returns True if the sum of 
-- any consecutive elements equals a given target sum. It should return False otherwise.
-- The list should have at least two elements.

-- consecutiveSums :: Int -> [Int] -> Bool

-- main = print (consecutiveSums 5 [1, 2, 3, 4, 5]) -- True (2 + 3 = 5)
-- main = print (consecutiveSums 10 [1, 2, 3, 4, 5]) -- False
-- main = print (consecutiveSums 7 [3, 4, 1, 6, 8]) -- True (3 + 4 = 7)
-- main = print (consecutiveSums 15 [1, 1, 1, 1]) -- False
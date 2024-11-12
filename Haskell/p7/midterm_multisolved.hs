 
-- TASK 1 
{-
1- Unique digits - 10 points
    Given an integer n, return the count of all unique digits of n.
    Input: n = 1232
    Output: 2 (only 1 and 3 are unique - appeared only once in n).
    Input: n = 1111
    Output: 0 (There is no unique digit in n.)
-}


toDigit :: Int -> [Int]
toDigit x
  | x < 10    = [x]
  | otherwise = toDigit (x `div` 10) ++ [x `mod` 10]

isUnique :: Int -> [Int] -> Bool
isUnique x ls = length (filter (== x) ls) == 1

countUniqueDigits :: Int -> Int
countUniqueDigits x = length (filter (== True) (map (\d -> isUnique d y) y))    
  where y = toDigit x


occur :: Int -> [Int] -> Int -> Int
occur num [] i = i
occur num (x:xs) i
  | num == x  = occur num xs (i + 1)
  | otherwise = occur num xs i

countUniqueDigits2 :: Int -> Int
countUniqueDigits2 x = length [num | num <- toDigit x, occur num (toDigit x) 0 == 1]


-- main = print (countUniqueDigits 1234) -- 4
-- main = print (countUniqueDigits 12325332)  -- 2
-- main = print (countUniqueDigits 111111)    -- 0
-- main = print (countUniqueDigits 1)         -- 1


-- TASK 2 
-- /*2- Count good lists - 10 points
--     Given a list of lists of integer numbers, count the good sublists in 
--     the given list. A list is considered to be good if the numbers at 
--     even positions are even and the numbers at odd positions are prime.    
--     Input:  [[2,2,4,5], [2,3,3,5]]
--     Output: 1 (Only the [2,2,4,5] sublist is good as the numbers at 
--     0th, 2nd (even) positions are even and the numbers at 1st, 3rd (odd) 
--     positions are prime.
-- */



isEven :: Int -> Bool
isEven x = x `mod` 2 == 0

isPrime :: Int -> Bool
isPrime n = n > 1 && null [i | i <- [2..(n - 1)], n `mod` i == 0]
--null [list] : check if list comprehension/list-generating expression yields empty list
isGood :: [Int] -> Bool
isGood [] = True
isGood (x:y:xs) = isEven x && isPrime y && isGood xs
--first: even, 2nd: prime = good list (recursion pattern)
isGood [x] = isEven x

count_good_lists :: [[Int]] -> Int
count_good_lists ls = length (filter isGood ls)
--filter predicate(bool) list

-- main = print (count_good_lists [[2,2,4,5], [2,3,3,5]]) -- 1
--main = print (count_good_lists [[2,23,22], [2,29,22,5], [1,2,3]]) --  2
-- main = print (count_good_lists [[2,2,4,5], [2,2,6,7,8,11,12,17], [12,23,4]]) --  3
-- main = print (count_good_lists []) --  0



-- TASK 3 
{-
    3- Increase by position - 10 points
    Given a list of real numbers, add the position of every number to the number.    
    Input:  [1.0,2.1,3.5,2.0]
    Output: [1.0,3.1,5.5,5.0] (the position of 1.0 is 0 -> 1.0 + 0 = 1.0  
                               the position of 2.1 is 1 -> 2.1 + 1 = 3.1 
                               the position of 3.5 is 2 -> 3.5 + 2 = 5.5
                               the position of 2.0 is 3 -> 2.0 + 3 = 5.0)
-}

increaseByPosition :: [Double] -> [Double]
increaseByPosition x = [el + fromIntegral i | (el, i) <- zip x [0..]]
--fromIntegral: convert to Double, we zip list [x] with [0..] aka position. returns all pair sum as new list


--main = print (increaseByPosition [1.0, 2.1, 3.5, 2.0])         --  [1.0, 3.1, 5.5, 5.0]
-- main = print (increaseByPosition [55.12, 22.45, 2.10, 15.1, 20.20]) -- [55.12, 23.45, 4.1, 18.1, 24.2]
-- main = print (increaseByPosition [])  -- []



-- TASK 4 
{-
    4- Reverse integers - 10 points
    Given a list of integer numbers, reverse every number in the list.
    Reversing a number means to write its digits in the reversed order. 
    Input:  [1,234,5677,43,0]
    Output: [1,432,7765,34,0] Reverse of 1 is 1    
       Reverse of 234: the digits of 234 in reversed order are 4,3 and 2, 
       and by combining these digits we get the number 432
    Note: reverse of e.g. 230 is 32 NOT 032  

-}

reverse_num :: Int -> Int
reverse_num x = read (reverse (show x)) :: Int
--show x : convert int to string
--read ... :: Int : convert back to Int (the type is after ::)
rev_nums :: [Int] -> [Int]
rev_nums [] = []
rev_nums (x:xs) = reverse_num x : rev_nums xs

rev_nums2 :: [Int] -> [Int]
rev_nums2 list = map reverse_num list
-- !!important: map function list: map SYNTAX


-- main = print (rev_nums [1,234,5677,43,0])            -- [1,432,7765,34,0]
-- main = print (rev_nums [1..5])                       -- [1,2,3,4,5]
-- main = print (rev_nums [222..240])                   -- [222,322,422,522,622,722,822,922,32,132,232,332,432,532,632,732,832,932,42]
-- main = print (rev_nums [])                           -- []


-- TASK 5 
{- 
    5- Passed students - 10 points
    Given a list of tuples and an integer number (let's call it x), where 
    the first element of the tuple represents a student's name and 
    the second element of the tuple represents the points of the student 
    that he/she got in a particular subject (its type is a list of real numbers).
    Return those students whose points have the following property:
    if the sum of the INTEGER parts of the points is greater than or equal 
    to the given number x.
    Input: [("Abdullah",[55.55,66.55,77.75,65.07,65.57]),("Mohammed",[27.55,20.55,10.75,30.07,20.57])] 320 
    Output: ["Abdullah"]
-}

-- Helper function to get the integer part of a real number
integerPart :: Double ->  Int
integerPart n = floor n

--round down to nearest int
-- Function to check which students passed based on integer part sum
passedStudents :: [(String, [Double])] -> Int -> [String]
passedStudents ls x = [name | (name, points) <- ls, sum (map integerPart points) >= x]
--(name, points) <- ls - extracts or deconstructs each element in ls (a list of tuples) into the components name and points.
-- <- : iterate symbol.
-- map: we 'floor' all elems in list then sum it. cond: compare to x


-- main = print (passedStudents [("Abdullah", [55.55,66.55,77.75,65.07,65.57]),("Mohammed",[27.55,20.55,10.75,30.07,20.57])] 320) 
-- ["Abdullah"]
-- main = print (passedStudents [("Sara" , [5.55,44.55,55.75,30.07,90.57]),("Rayan",[56.55,66.55,7.75,77.07,77.57]),("Ali",[1.55,6.55,66.75,6.07,7.57]),("Maria",[54.55,60.55,66.75,20.07,74.57])] 200) 
-- ["Sara","Rayan","Maria"]
-- main = print (passedStudents [] 100) --  []



--  TASK 6 
{-
    6- Eliminate - 10 points
    Given a list of numbers eliminate the first number of 
    every two numbers in the list, until only one number is left.  
    Input: a = [1, 2, 3, 4, 5, 6, 7, 8, 9]
           a = [2, 4, 6, 8]
           a = [4, 8]
           a = [8] 
-}

-- Auxiliary function to eliminate the first number of every two
aux :: [Int] -> Int -> [Int]
aux [] _ = []
aux list i
    | even i = aux (tail list) (i + 1)
    | otherwise = head list : aux (tail list) (i + 1)

-- Main function to perform elimination
eliminate :: [Int] -> [Int]
eliminate a
    | length a == 1 = a
    | otherwise = eliminate (aux a 0)

-- Alternative approach
eliminate2 :: [Int] -> [Int]
eliminate2 [] = []
eliminate2 [x] = [x]
eliminate2 ls = eliminate2 [x | (x, i) <- zip ls [1..], even i]
--eliminate2 ls = eliminate2 (filter (\(x, i) -> even i) (zip ls [1..]))
--LAMBDA SYNTAX: \parameter1 parameter2 ... -> expression

-- main = print (eliminate [1..9]) -- [8]
-- main = print (eliminate [1, 2, 3, 4]) -- [4]
-- main = print (eliminate [0]) -- [0]
-- main = print (eliminate []) --  []



-- TASK 7 
{-
    7- Delete third - 10 points
    Delete every third element from a list.
-}

-- Recursive approach to delete every third element
del3 :: [Int] -> [Int]
del3 [] = []
del3 [x] = [x]
del3 [x, y] = [x, y]
del3 (x:y:z:xs) = x : y : del3 xs

-- Alternative approach using list comprehension
del32 :: [Int] -> [Int]
del32 ls = [x | (x, i) <- zip ls [1..], i `mod` 3 /= 0]


-- main = print (del3 [1..7])  -- [1,2,4,5,7]
-- main = print (del3 [1..20]) --[1,2,4,5,7,8,10,11,13,14,16,17,19,20]
-- main = print (del3 [1..5])  -- [1,2,4,5]
-- main = print (del3 [])      --[]




--  TASK 8 
{-
    8- Fibonacci lists - 10 points
    Write a function that takes a list of integers and for every integer 
    returns a list of Fibonacci sequence less than or equal to the integer.
    Also, the length of generated list can not be greater than integer itself.
    A Fibonacci sequence is a sequence of numbers where each number is 
    the sum of the previous two numbers: 0, 1, 1, 2, 3, 5 ..... and so on
    Input: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    Output: [[0],[0,1],[0,1,1],[0,1,1,2],[0,1,1,2,3],[0,1,1,2,3,5],
             [0,1,1,2,3,5],[0,1,1,2,3,5,8],[0,1,1,2,3,5,8],[0,1,1,2,3,5,8]]
-}

-- Function to calculate the nth Fibonacci number
fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)
--fibonacci generator

-- Generate Fibonacci sequence less than or equal to n
genFib :: Int -> Int -> [Int]
genFib x n
    | n == 0 = [0]
    | x >= n     = [] --if index past n stop
    | fib x > n = [] --if fib at index x past n stop
    | otherwise = fib x : genFib (x + 1) n

-- Generate a list of Fibonacci sequences for each integer in the input list
fibList :: [Int] -> [[Int]]
fibList xs = map (\y -> genFib 0 y) xs
--For each integer y in xs, genFib 0 y is called to generate a list of Fibonacci numbers from 0 to y.

-- main = print (fibList [1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
-- [[0],[0,1],[0,1,1],[0,1,1,2],[0,1,1,2,3],[0,1,1,2,3,5],
--  [0,1,1,2,3,5],[0,1,1,2,3,5,8],[0,1,1,2,3,5,8],
--  [0,1,1,2,3,5,8]]

-- main = print (fibList [0, 45, 88, 87, 21])
-- [[0],[0,1,1,2,3,5,8,13,21,34],
--  [0,1,1,2,3,5,8,13,21,34,55],
--  [0,1,1,2,3,5,8,13,21,34,55],
--  [0,1,1,2,3,5,8,13,21]]

--main = print (fibList []) -- []



-- TASK 9 
{-
    9- Carry-less addition - 10 points
    Given 2 lists of integers of same length, perform carry-less addition 
    between 2 integers at the same position and return the list of integers.
    Carry-less addition: 9+7 results 6, not 16.
                        35+48 results 73, not 83                        
    Here, for the sake of simplicity, there will be only 1-digit integer.

-}


carrylessDigitAddition :: [Int] -> [Int] -> [Int]
carrylessDigitAddition list1 list2 = zipWith (\x y -> (x + y) `mod` 10) list1 list2
--zipWith: pairs zip1 and zip2, then apply function to each pair
--zipWith func pair1 pair2: zipWith Syntax


-- main = print (carrylessDigitAddition [9, 6, 3, 4, 3, 4, 5] [4, 8, 9, 0, 9, 6, 5]) -- [3, 4, 2, 4, 2, 0, 0]

-- main = print (carrylessDigitAddition [7, 6, 5, 9, 8, 7, 6] [9, 8, 7, 6, 3, 2, 9]) -- [6, 4, 2, 5, 1, 9, 5]

-- main = print (carrylessDigitAddition [7, 8, 3, 8] [6, 2, 7, 8])  -- [3, 0, 0, 6]

-- main = print (carrylessDigitAddition [2, 3, 4, 3, 2] [9, 8, -7, 6, 5]) -- [1, 1, -3, 9, 7]

carrylessDigitAddition' :: [Int] -> [Int] -> [Int]
carrylessDigitAddition' list1 list2 = [ (x + y) `mod` 10 | (x,y) <- zip list1 list2]

--main = print (carrylessDigitAddition' [7, 6, 5, 9, 8, 7, 6] [9, 8, 7, 6, 3, 2, 9]) -- [6, 4, 2, 5, 1, 9, 5]

carrylessDigitAddition'' :: [Int] -> [Int] -> [Int]
carrylessDigitAddition'' list1 list2 = map (\(x,y) -> (x + y) `mod` 10) (zip list1 list2)

--main = print (carrylessDigitAddition'' [7, 6, 5, 9, 8, 7, 6] [9, 8, 7, 6, 3, 2, 9]) -- [6, 4, 2, 5, 1, 9, 5]

--  TASK 10 
{-
    10- Zip with LCM - 10 points
    Write a function that takes two lists of integers and returns a 
    list of tuples where the first element of the tuple is an element of 
    the first list and the second element of the tuple is an element 
    of the second list at the same position and the third element is the 
    LCM of the first two elements (LCM - least common multiple of two numbers).
    LCM(2,3) = 6 LCM(3,4) = 12 LCM(12,15) = 60
    If the lists are of different lengths, the function should return a 
    list of tuples of the same length as the shorter list.
-}

-- Function to calculate the Least Common Multiple (LCM)
lcmAux :: Int -> Int -> Int
lcmAux x y = (x * y) `div` (gcd x y)
--math: LCM(x,y)= x*y/GCD(x,y)

zipWithLCM :: [Int] -> [Int] -> [(Int, Int, Int)]
zipWithLCM [] _ = []
zipWithLCM _ [] = []
zipWithLCM (x:xs) (y:ys) = (x, y, lcmAux x y) : zipWithLCM xs ys
--recursion, nothing much


-- main = print (zipWithLCM [12,14,22,57,66] [13,15,17,19,21]) --[(12,13,156),(14,15,210),(22,17,374),(57,19,171),(66,21,462)]

-- main = print (zipWithLCM [78,43,12,33,65] [32,77,21,11,9,43]) -- [(78,32,1248),(43,77,3311),(12,21,84),(33,11,33),(65,9,585)]

-- main = print (zipWithLCM [] [32,77,21,11,9,43]) -- []

-- main = print (zipWithLCM [78,43,12,33,65] []) -- []

-- main = print (zipWithLCM [] []) -- []



-- TASK 11 
{-
    11- Split number - 10 points
    Write a function that takes a number, splits in the middle and interchanges the 
    two halves. If digits' number is odd, the second half contains the middle digit.
    1234 -> 12 | 34 -> 34 | 12 -> 3412
    12345 -> 12 | 345 -> 345 | 12 -> 34512
-}

-- Convert a number to a list of its digits
numToList :: Int -> [Int]
numToList 0 = []
numToList n = numToList (n `div` 10) ++ [n `mod` 10] --append result of div to the begin, recursion 

-- Convert a list of digits back to a number
listToNum :: [Int] -> Int
listToNum [] = 0
listToNum xs = foldl (\acc x -> acc * 10 + x) 0 xs
--foldl/foldr syntax: foldl function initial value list

funNum :: Int -> Int
funNum n = listToNum (drop mid digits ++ take mid digits)
  where
    digits = numToList n
    mid = length digits `div` 2
--continue

funNum2 :: Int -> Int
funNum2 n = listToNum (rightHalf ++ leftHalf)
  where
    digits = numToList n
    mid = length digits `div` 2
    (leftHalf, rightHalf) = splitAt mid digits



-- main = print (funNum 0)      -- 0
--main = print (funNum 1234)   -- 3412
--main = print (funNum 12345)  -- 34512
--main = print (funNum 1234567) -- 456123



--   TASK 12 
{-
    12- Fold if true - 10 points
    Write function foldiftrue which reduces only those elements of a list which 
    satisfy a given predicate. There are 4 reduce options which are given in String:
    "max" return max number, "min" return min number, "*" return product, "+" return sum. 
-}



foldIfTrue :: (Int -> Bool) -> String -> [Int] -> Int
foldIfTrue pred func list
  | func == "+" = foldr (+) 0 (filter pred list)
  | func == "*" = foldr (*) 1 (filter pred list)
  | func == "max" = maximum (filter pred list)
  | func == "min" = minimum (filter pred list)
  | otherwise = error "not a valid reduce function"

--main  = print(foldIfTrue ((>) 5) "max" [6, 1, 2, 3]) -- 6
--main  = print(foldIfTrue ((>) 5) "min" [6, 1, 2, 3]) -- 1
-- main = print(foldIfTrue even "+" [6, 1, 2, 3, 233, 287]) -- 8
-- main = print(foldIfTrue even "*" [6, 1, 2, 3, 233, 287]) -- 12



-- TASK 13
{-

    13- Salary calculation - 30 points this task has 3 parts, each of 10 points 
    You are given list of tuples with employees' name, age and salaries, do some analysis. 
    Find about all the given queries using functions.
    1. What is the average salary of the employees?
    2. If the employer is to deduct 15% of the salaries of employees younger than 35 years old,
       how much money would he save?
    3. Give only the list of names where the employee is older than 35 but earns more than 300.
    [("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289), ("Doe", 22, 384), 
     ("Marie", 55, 573), ("Lucy", 37, 400)]
    1. 424.71...
    2. 87.6
    3. ["Bob", "Anna", "Marie", "Lucy"]
-}



-- Helper functions to access tuple elements
fst3 :: (a, b, c) -> a
fst3 (x, _, _) = x

snd3 :: (a, b, c) -> b
snd3 (_, y, _) = y

thd3 :: (a, b, c) -> c
thd3 (_, _, z) = z

-- 1. Calculate average salary
averageSalary :: [(String, Int, Int)] -> Double
averageSalary employees = totalSalary / fromIntegral count
  where
    totalSalary = sum [fromIntegral (thd3 emp) | emp <- employees]
    count = length employees
--------------emp <- employees:: iterates over each tuple elems in the employees (emp-tuple)

-- main = print (averageSalary [("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289), ("Doe", 22, 384), ("Marie", 55, 573), ("Lucy", 37, 400)])  
-- 424.7142857142857

-- 2. Calculate the saved money by deducting 15% from salaries of employees younger than 35
savedMoney :: [(String, Int, Int)] -> Double
savedMoney employees = sum [fromIntegral (thd3 emp) * 0.15 | emp <- employees, snd3 emp < 35]

-- main = print (savedMoney [("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289), ("Doe", 22, 384), ("Marie", 55, 573), ("Lucy", 37, 400)])  
-- 87.6

-- 3. Get names of employees older than 35 and earning more than 300
namesOlder35 :: [(String, Int, Int)] -> [String]
namesOlder35 employees = [fst3 emp | emp <- employees, snd3 emp > 35, thd3 emp > 300]


-- main = print (namesOlder35 [("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289), ("Doe", 22, 384), ("Marie", 55, 573), ("Lucy", 37, 400)])  
-- ["Bob", "Anna", "Marie", "Lucy"]













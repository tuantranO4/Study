--import Main (isPrime)
{-- WRITE
NAME 
AND 
NEPTUN CODE 
HERE by this YOU DECLARE this FILE is 
YOUR OWN SOLUTION for functional 
programming midterm 2024 November 14. --}
-- MIDTERM Thursday VERSION A


-- 1 ..............................
-- Write a function sumOfDigitsPower that takes two integers n and p and returns 
-- True if the sum of the digits of n raised to the power p equals n. 
-- Otherwise, return False.
-- sumOfDigitsPower 153 3 -- Output: True  (1^3 + 5^3 + 3^3 = 1+125+27 = 153)
-- sumOfDigitsPower 89 1 -- Output: False (8^1 + 9^1 = 17 not equal 89)
digital :: Int -> [Int]
digital 0 = []
digital x = digital (x `div` 10) ++ [x `mod` 10]
sumOfDigitsPower :: Int -> Int -> Bool
sumOfDigitsPower a n = sum (map (^n) (digital a))==a
--main = print(sumOfDigitsPower 89 3) -- False
--main = print(sumOfDigitsPower 153 3) -- True
--main = print(sumOfDigitsPower 123 2) -- False
--main = print(sumOfDigitsPower 9474 4) -- True


-- 2 ..............................
-- Find the n-th value of the Jacobsthal number sequence.
-- a(n) = a(n − 1) + (2 * a(n − 2)) for n >= 2, with a(0) = 0, a(1) = 1. 
-- Check if n is valid.

jacobsthal :: Int -> Int
jacobsthal 0 = 0
jacobsthal 1 = 1
jacobsthal n = jacobsthal(n-1) + (2*jacobsthal(n-2))
--main = print (jacobsthal 4) -- 5
--main = print (jacobsthal 16) -- 21845
--main = print (jacobsthal 0) -- 0
--main = print (jacobsthal 1) -- 1
--main = print (jacobsthal (-4)) -- "invalid argument"


-- 3 ..............................
-- Given a list of integers, write a function that processes the list as follows:
-- Remove all numbers that are divisible by 5.
-- For the remaining numbers, replace each number with the difference between 
-- the sum of its digits and the product of its digits.
-- Return the resulting list.
-- Example: 
-- For the list [10, 22, 35, 41, 55], after removing numbers divisible by 5, 
-- the list becomes [22, 41]. 
-- The difference between the sum and the product of the digits of 
-- the remaining numbers is 0 and 1.
-- for 22 : 2+2 = 4, 2*2 = 4 and 4-4 = 0
-- for 41 : 4+1 = 5 , 4*1 =4 and 5-4 = 1
-- so, the result is [0,1]
digits :: Int -> [Int]
digits 0 = []
digits n = digits (n `div` 10) ++ [n `mod` 10]

transformList :: [Int] -> [Int]
transformList [] = []
transformList xs = 
  [ sum (digits x) - foldr (*) 1 (digits x) | x <- xs, x `mod` 5 /= 0 ]
--main = print(transformList [10, 22, 35, 41, 55]) -- [0, 1]
--main = print(transformList [15, 23, 35, 45, 67]) -- [-1, -29]
--main = print(transformList [12, 30, 47, 55, 89]) -- [1, -17, -55]


-- 4 ..............................
-- Write a function which takes `a` as a first term, 
-- `d` as a common difference, and `n` as the number of terms 
-- in the arithmetic sequence, and returns the arithmetic sequence.
-- Example:
-- if a = 1, d = 2, and n = 5 the list is 1, 1+2, 1+2+2, 1+2+2+2, 1+2+2+2+2 
-- the function returns [1,3,5,7,9].

arithmeticSequence :: Int -> Int -> Int -> [Int]
arithmeticSequence a d n
  | n <= 0    = []                          
  | otherwise = a : arithmeticSequence (a + d) d (n - 1) -- Add the current term and recurse

--main = print (arithmeticSequence 1 2 5) -- [1,3,5,7,9]
--main = print (arithmeticSequence 2 3 4) -- [2,5,8,11]
--main = print (arithmeticSequence 0 (-10) 20) 
-- [0,-10,-20,-30,-40,-50,-60,-70,-80,-90,-100,-110,-120,-130,-140,-150,-160,-170,-180,-190]


-- 5 ..............................
-- Write a function, that counts the number of vowels 
-- (a, e, i, o, u) in a given string.
-- The function should be case insensitive.

countVowels :: String -> Int
countVowels str1 = length [c | c<- str1, c `elem` "aieouAIEOU"]
--main = print(countVowels "Hello World")      -- 3
--main = print(countVowels "Functional")       -- 4
--main = print(countVowels "Haskell")          -- 2
--main = print(countVowels "xyz")              -- 0


-- 6 ..............................
-- Write a function which calculates the variance of a list of floats.
-- First, sum all the data of the list (sigma x). 
-- Then square the data values of the list and add these up (sigma x^2). 
-- Divide (sigma x^2) by n (number of data values in the list), 
-- and take away the square of (sigma x divided by n).

var :: [Float] -> Float
var [] = error "Empty list"
var xs = (sum [x^2 | x<-xs] / fromIntegral (length xs)) - ((sum xs)/fromIntegral((length xs)))^2
--main = print $ var [1.0, 2.0, 3.0, 4.0] -- 1.25
--main = print $ var [5.0, 5.0, 5.0, 5.0] -- 0.0
--main = print $ var  [6.0, 7.0, 3.0, 9.0, 10.0, 15.0]  -- 3.888893
--main = print $ var [-1.0, 0.0, 1.0] -- 0.6666667
--main = print $ var [10.0] -- 0.0
--main = print $ var [10.0, 20.0, 30.0, 40.0, 50.0] -- 200.0
--main = print $ var [] -- "Empty list is not allowed!"


-- 7 ..............................
-- Write a function that takes a list of tuples, 
-- each containing two elements of the same type,
-- and returns a single tuple that contains two lists: 
-- one for the first elements and one for the second elements.

mergeTuples :: [(a, b)] -> ([a], [b])
mergeTuples [] = ([],[])
mergeTuples ((x,y):xs) = (x : fst rest, y:snd rest)
 where
    rest = mergeTuples xs
--main = print (mergeTuples [(1, "apple"), (2, "banana"), (3, "cherry")]) 
-- ([1, 2, 3], ["apple", "banana", "cherry"])
-- main = print (mergeTuples [(1, "a"), (2, "b"), (3, "c"), (4, "d")])     
-- ([1, 2, 3, 4], ["a", "b", "c", "d"])
-- main = print (mergeTuples [(10, "x"), (20, "y")])                        
-- ([10, 20], ["x", "y"])
-- main = print (mergeTuples [])   -- []


-- 8 ..............................
-- Write a function that takes a list of integer lists and an integer n.
-- Sublists with size less than n should be removed,
-- inside sublists the numbers smaller than n should also be removed,
-- the numbers greater than n should be subtracted from n.
-- Example:
-- input: [[6,2,4,5], [8,1], [6,6,7], [5,2,1,5],[1,1,1,1]] 4
-- n is 4, so all sublists of length smaller than 4 should be removed
-- [[6,2,4,5], [5,2,1,5], [1,1,1,1]]
-- numbers smaller than 4 should be removed
-- [[6,4,5], [5,5], []]
-- remaining numbers should be subtracted from 4
-- output: [[-2,0,-1], [-1,-1], []]

f2 :: [[Int]] -> Int -> [[Int]]
f2 [[]] _ = [[]]
f2 xss n = [[ 4-y | y<-xs, y>=4 ] | xs<-xss, length xs >=4]
--main = print (f2 [[6,2,4,5], [8,1], [6,6,7],[5,2,1,5],[1,1,1,1]] 4) 
-- [[-2,0,-1], [-1,-1], []] 
--main = print(f2 [[1..5], [1,3..10],[1]] 2) 
-- [[0,-1,-2,-3],[-1,-3,-5,-7]]


-- 9 ..............................
-- Given two lists of integers, return a list containing 
-- only the elements that are present in the first list 
-- but not in the second list.

difference :: [Int] -> [Int] -> [Int]
difference [] [] = []
difference xs ys = [a | a<-xs, a `notElem` ys]
--main = print (difference [1, 2, 3, 4] [3, 4, 5, 6]) -- [1, 2]
--main = print (difference [] [1, 2, 3])              -- []
--main = print (difference [1, 2, 3] [])              -- [1, 2, 3]
--main = print (difference [7, 8, 9] [1, 2, 3])       -- [7, 8, 9]
--main = print (difference [5, 6, 7, 8] [6, 7, 9])    -- [5, 8]


-- 10 ..............................
-- A prime triplet consists of three numbers that are either 
-- (p, p+2, p+6) or (p, p+4, p+6), where p is a prime number, 
-- and p+2, p+4, and p+6 are also prime numbers.
-- Write a function that takes a positive integer n and returns a 
-- list of tuples of prime triplets for all p less than or equal to n.
-- Example:
-- n=20, the function returns [(5,7,11), (7,11,13), (11,13,17), (13,17,19)].
isPrimed :: Int -> Bool
isPrimed p
  | p < 2     = False 
  | otherwise = null [n | n <- [2..floor (sqrt (fromIntegral p))], p `mod` n == 0]

primeTriplets :: Int -> [(Int, Int, Int)]
primeTriplets n = 
  [(p, p + 2, p + 6) | p <- [2..n], isPrimed p, isPrimed (p + 2), isPrimed (p + 6)] ++
  [(p, p + 4, p + 6) | p <- [2..n], isPrimed p, isPrimed (p + 4), isPrimed (p + 6)]

--main = print (primeTriplets 20)
-- [(5,7,11),(7,11,13),(11,13,17),(13,17,19)]
--main = print (primeTriplets 50) 
-- [(5,7,11),(7,11,13),(11,13,17),(13,17,19),(17,19,23),(37,41,43),(41,43,47)]
--main = print (primeTriplets 100) 
-- [(5,7,11),(7,11,13),(11,13,17),(13,17,19),(17,19,23),(37,41,43),(41,43,47),(67,71,73)]


-- 11 ..............................
-- Multiply each element in a sublist by the minimum of that sublist.
-- For each sublist, multiply all its elements by the minimum value 
-- of that sublist. If a sublist is empty, it remains unchanged.
-- You must use higher-order functions.
minFind :: [Int] -> Int
minFind [] = 0
minFind [x]=x
minFind (x:y:xs)
    |x<y = minFind(x:xs)
    |otherwise = minFind(y:xs)
multiplyWithMin :: [[Int]] -> [[Int]]
multiplyWithMin xss = [[x*minFind xs | x<-xs] | xs<-xss]
--main = print(multiplyWithMin [[1, 2, 3], [4, 6], [5, 7, 8]]) 
-- [[1,2,3],[16,24],[25, 35, 40]]
--main = print(multiplyWithMin [[-100, 2], [3, -4, 5], [], [7, 8]]) 
-- [[10000, -200], [-12, 16, -20], [], [49, 56]]
--main = print(multiplyWithMin [[]]) -- [[]]


-- 12 ..............................
-- Write a function that takes a list of tuples, where each tuple contains 
-- the name of a product (a String) and its price (a Double).
-- The function should perform the following tasks:
-- Filter out products that have a price less than or equal to zero 
-- (since these are considered invalid).
-- Calculate the total price of the remaining valid products.
-- Count how many valid products there are.
-- Return a tuple containing the total price and the count of valid products. 
-- If there are no valid products, return (0.0, 0).

analyzePrices :: [(String, Double)] -> (Double, Int)
analyzePrices [] = (0.0, 0)
analyzePrices ((n, p) :xs)
    | p<=0 = analyzePrices xs
    |otherwise = (p + total, count + 1)
  where
    (total, count) = analyzePrices xs
--main = print(analyzePrices [("Laptop", 999.99), ("Mouse", 25.0), ("Keyboard", -10.0), ("Monitor", 199.99)]) 
-- (1225.98, 3)
--main = print(analyzePrices [("Book", -5.0), ("Pen", 0.0)]) 
-- (0.0, 0)
--main = print(analyzePrices [("Phone", 699.99), ("Charger", 19.99), ("Case", 0.0)]) 
-- (719.98, 2)
--main = print(analyzePrices []) -- (0.0, 0)
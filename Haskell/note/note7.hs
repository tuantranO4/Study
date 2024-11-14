---IMPORTANT---
--map: map function list
----LAMBDA: \parameter1 parameter2 ... -> expression
--zipWith: zipWith func pair1 pair2
--foldl/foldr syntax: foldl function initialValue list
--[expression (x,y,int...) | pattern <- list, conditions]
--filter :: f(a -> Bool) -> [a] -> [a]
--composition: (f . g) x 
-- factorial :: Int -> Int
-- factorial 0 = 1                            -- Base case: 0! = 1
-- factorial n = n * factorial (n - 1)         -- Recursive case: n! = n * (n-1)!
-- !!! [x:xs] - head of x and tails of xs
-- !!! (x:xs) - pattern matching, list comprehension

--CONVERSION
--realToFrac (3.14 :: Float) :: Double : converts between any two fractional types such as Float to Double
--fromIntegral 42 --42.0 : convert to Double
--show 123     -- "123" : convert int to string
--read "123" -- 123 : convert to Int (the type is after ::)
-- round 3.5     -- 4
-- ceiling 3.1   -- 4
-- floor 3.9     -- 3
-- truncate 3.9  -- 3
-- toEnum 65 :: Char     -- 'A'
-- fromEnum 'A'          -- 65
------------------------------------------------------------------------
-- Basic functions
double :: Int -> Int
double x = x * 2

increment :: Int -> Int
increment x = x + 1

result1 = (double . increment) 3  -- Applies increment first, then double

carrylessDigitAddition' :: [Int] -> [Int] -> [Int]
carrylessDigitAddition' list1 list2 = [ (x + y) `mod` 10 | (x,y) <- zip list1 list2]
--main = print (carrylessDigitAddition' [7, 6, 5, 9, 8, 7, 6] [9, 8, 7, 6, 3, 2, 9]) -- [6, 4, 2, 5, 1, 9, 5]

carrylessDigitAddition'' :: [Int] -> [Int] -> [Int]
carrylessDigitAddition'' list1 list2 = map (\(x,y) -> (x + y) `mod` 10) (zip list1 list2)
--main = print (carrylessDigitAddition'' [7, 6, 5, 9, 8, 7, 6] [9, 8, 7, 6, 3, 2, 9]) -- [6, 4, 2, 5, 1, 9, 5]


numToList :: Int -> [Int]
numToList 0 = []
numToList n = numToList (n `div` 10) ++ [n `mod` 10]

listToNum :: [Int] -> Int
listToNum [] = 0
listToNum xs = foldl (\acc x -> acc * 10 + x) 0 xs

funNum :: Int -> Int
funNum n = listToNum (drop mid digits ++ take mid digits)
  where
    digits = numToList n
    mid = length digits `div` 2
--main = print (funNum 1234)   -- 3412    

--helper
fst3 :: (a, b, c) -> a
fst3 (x, _, _) = x
snd3 :: (a, b, c) -> b
snd3 (_, y, _) = y
thd3 :: (a, b, c) -> c
thd3 (_, _, z) = z

averageSalary :: [(String, Int, Int)] -> Double
averageSalary employees = totalSalary / fromIntegral count
  where
    totalSalary = sum [fromIntegral (thd3 emp) | emp <- employees]
    count = length employees

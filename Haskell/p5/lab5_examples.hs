import Data.List
---- Higher order functions

-- Earlier exemples rewritten with higher order functions: map, foldr, filter, takeWhile. 
-- Operations with lists: write functions for the followings


-- 1.Keep the head of every sublist (assume sublists are not empty).
-- e.g. [[1, 2, 3], [3, 4], [5, 7, 8, 9]] -> [1, 3, 5]

heads :: [[Int]] -> [Int]
heads x = map head x

--main = print(heads [[1, 2, 3], [3, 4], [5, 7, 8, 9]]) -- [1,3,5]


-- 2. Keep the tails of a list.
-- e.g. [[1, 2, 3], [3, 4], [5, 7, 8, 9]] -> [[2, 3], [4], [7, 8, 9]] 

tails :: [[Int]] -> [[Int]]
tails x = map tail x

--main = print(tails [[1, 2, 3], [3, 4], [5, 7, 8, 9]]) -- [[2, 3], [4], [7, 8, 9]] 


-- 3. Add 100 to the numbers of a list.

g :: Int -> Int
g x = x + 100

add100 :: [Int] -> [Int]
add100 x = map g x

add100' :: [Int] -> [Int]
add100' x = map (+ 100) x    

--main = print(add100 [1..8]) -- [101,102,103,104,105,106,107,108]
--main = print(add100' [1..8]) -- [101,102,103,104,105,106,107,108]


-- 4. Triple the elements of a list.

triples :: [Int] -> [Int]
triples x = map (\x -> 3*x) x

--main = print(triples [1..5]) -- [3,6,9,12,15]

triples2 :: [Int] -> [Int]
triples2 x = map (* 3) x

--main = print(triples2 [1..5]) -- [3,6,9,12,15]


-- 5. Check if the numbers of a list are odd.

isoddnrs :: [Int] -> [Bool]
isoddnrs x = map odd x

isoddnrs2 :: [Int] -> [Bool]
isoddnrs2 x = map (\x -> rem x 2 == 1) x

--main = print(isoddnrs [1..5]) -- [True,False,True,False,True]
--main = print(isoddnrs2 [1..5]) -- [True,False,True,False,True]

-- 7. Collect in a list the last digits of the numbers of a list.

lastdigits :: [Int] -> [Int]
lastdigits x = map (\x -> rem x 10) x

--main = print(lastdigits [1..35])
-- [1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5]


-- 9. Reverse every sublist of a list.

revsub :: [[Int]] ->  [[Int]]
revsub x = map reverse x

--main = print(revsub [[1,2,3],[5,6],[],[7,8,9,10]]) -- [[3,2,1],[6,5],[],[10,9,8,7]]


-- 10. Keep the last elements of the sublists of a list in one list 
-- (assume the sublists are not empty).
-- E.g. [[1,2,3],[5,6],[1],[7,8,9,10]] -> [3,6,1,10]

lasts :: [[Int]] -> [Int]
lasts x = map last x

--main = print(lasts [[1,2,3],[5,6],[1],[7,8,9,10]]) -- [3,6,1,10]

-- 15.  Add the numbers from 1..N (N positive and not 0) using foldr.

addn :: Int -> Int
addn n = foldr (+) 0 [1..n]

--main = print(addn 5) -- 15
--main = print(addn 0) -- 0
--main = print(addn (-2))  -- 0
--main = print(addn 10) --55

-- 21. Filter the elements smaller then n, e.g. n=3 [1,5,3,2,1,6,4,3,2,1] -> [1,2,1,2,1]

f7 :: Int -> [Int] -> [Int]
--f7 n x = filter ((>) n) x 
f7 n x = filter (< n) x  -- both are good

--main = print(f7 3 [1,5,3,2,1,6,4,3,2,1])  -- [1,2,1,2,1]

-- 24. Write a function that keeps the integers of a list up to the first 0 encounterred 
-- and then divides by 2 every element [1, 2, -2, 3, 0, -4] -> [0, 1, -1, 1]
-- hints: use takeWhile then map

f3 :: [Int] -> [Int]
f3 x = map (\x -> x `div` 2) (takeWhile ((/=) 0) x) -- (/=) 0 x      0 /= x

--main = print(f3 [1, 2, (-2), 3, 0, (-4)]) -- [0, 1, -1, 1]

-- 26. Write a function that checks if each elements in the list appear even times.
-- E.g. checkEven [1,1,2,2,2,2,3,5,3,5] = True  

checkAux :: [Int] -> Int -> Bool
checkAux list number = even (length (filter ((==) number) list)) 

checkEven :: [Int] -> Bool
checkEven list = and (map (checkAux list) list)

--main = print(checkEven [1,1,2,2,2,2,3,5,3,5]) -- True
--main = print(checkEven [1,1,2,2,1]) -- False
--main = print(checkEven []) -- True

checkEven' :: [Int] -> Bool
checkEven' x = foldr (\x acc -> acc && even (length x)) True (group (sort x))

main = print(checkEven' [1,1,2,2,2,2,3,5,3,5]) -- True
--main = print(checkEven' [1,1,2,2,1]) -- False
--main = print(checkEven' []) -- True

--[1,1,2,2,2,2,3,5,3,5]
--sort: [1 1 2 2 2 2 3 3 5 5]
-- ->[[11] [2222] [33] [55]]
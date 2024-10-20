import Data.List (group, sort)

------------------------MAP------------------------
{--map is a higher-order function that applies a given function to each element of a list and returns a new list with the results.
map (\x -> x * 3) [1, 2, 3]  -- Result: [3, 6, 9]

map _ [] = []                           -- If the list is empty, return an empty list
map f (x:xs) = f x : map f xs           -- Apply the function to the head (x) and recurse on the tail (xs)
-}


-- 3. Add 100 to the numbers of a list.
g :: Int -> Int
g x = x + 100

add100 :: [Int] -> [Int]
add100 x = map g x
--main = print(add100 [1..8]) -- [101,102,103,104,105,106,107,108]

triples :: [Int] -> [Int]
triples x = map (\x -> 3*x) x
--main = print(triples [1..5]) -- [3,6,9,12,15]


------------------------FOLDR/FOLDL------------------------
{-
foldr :: f(a -> b -> b) -> b -> [a] -> b
foldr (+) 0 [1, 2, 3]  -- Result: 6

foldl :: f(b -> a -> b) -> b -> [a] -> b
foldl (+) 0 [1, 2, 3]  -- Result: 6

foldr :: (a -> b -> b) -> b -> [a] -> b: Right fold, starting from the rightmost element of the list.
foldl :: (b -> a -> b) -> b -> [a] -> b: Left fold, starting from the leftmost element of the list.
-}

addn :: Int -> Int
addn n = foldr (+) 0 [1..n]
--main = print(addn 5) -- 15

--Write a function that checks if each elements in the list appear even times.
checkEven' :: [Int] -> Bool
checkEven' x = foldr (\x acc -> acc && even (length x)) True (group(sort x)) --import group, sort
--main = print(checkEven' [1,1,2,2,2,2,3,5,3,5]) -- True

--HOW IT WORKS: 
--[1,1,2,2,2,2,3,5,3,5]
--sort: [1 1 2 2 2 2 3 3 5 5]
-- ->[[11] [2222] [33] [55]]

------------------------FILTER------------------------
{-
filter :: f(a -> Bool) -> [a] -> [a]
-}
main = print(filter (\x -> length x >= 5) ["apple", "fig", "banana", "kiwi", "cherry"]) --["apple", "banana", "cherry"]
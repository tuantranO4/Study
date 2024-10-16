-- quiz between 9.00-9.06 in Canvas, this is the attendance
-- {-# options_ghc -fwarn-incomplete-patterns #-}

import Prelude hiding (not, head, tail, take, drop, reverse, sum, length, map, (++), takeWhile, dropWhile)

-- import Prelude
-- qualified name: Prelude.not

-- before list functions, let's see the definition of not

not :: Bool -> Bool
-- not b = if b then False else True

-- Bool is a datatype, it has two constructors: True, False
-- pattern matching definition
-- not True  = False
-- not False = True

-- evaluation example: not False = True
-- evaluation example: not (1 == 3) = not False = True

-- if-then-else
ite :: Bool -> a -> a -> a
ite True  t f = t
ite False t f = f

not b = ite b False True

-- nicest definition
-- (&&) :: Bool -> Bool -> Bool
-- (&&) True  b = b     -- lazy version
-- (&&) False b = False
-- instead of the last line we could write

-- this is even more efficient than the nicest one, but the
--   second equation does not hold all the time
-- (&&) True  b = b     -- lazy version
-- (&&) a b = False

-- this is also a bit paranoid
-- (&&) True True = True   -- strict version
-- (&&) a b = False

-- this is the paranoid version, inefficient
-- (&&) True True = True   -- strict version
-- (&&) True False = False
-- (&&) False True = False
-- (&&) False False = False

-- (&&) True (100 == 100) = (&&) True True = True


-- undefined :: a
tt1 :: Int
tt1 = 1 + undefined
tt2 :: [[Int]]
tt2 = [[1,2,3], [1,undefined,3], undefined]

-- try in ghci:   :type (:)
--                :info (:)
-- [1,2,3] = 1 : (2 : (3 : [])) = 1 : 2 : 3 : []
-- Bool has constructors True, False
-- [a] has construction [], (:)
-- [] is called empty, (:) is called cons

-- Lets look into some of the built-in functions
-- rewriting built-in functions, names should not overlap
head :: [Int] -> Int
head (i:is) = i
head [] = undefined
-- i  :: Int
-- is :: [Int]

-- main = print (head [1..5]) -- 1

tail :: [Int] -> [Int]
tail (i:is) = is
tail [] = undefined

-- main = print (tail [1..5]) -- [2,3,4,5]

-- quiz:

-- head (head [[1],[2],[3]]) = 
-- head [1] =
-- 1

-- head (head [[1],[2],[3]]) = 
-- head (head ((1:[]):(2:[]):(3:[]):[])) =
-- head (head ((1:[]):((2:[]):(3:[]):[]))) =
--             \____/ \________________/
-- head (head (  i   :   is             )) =
-- head i =
-- head (1:[]) =
--       i:is  =
-- i
-- 1




take :: Int -> [a] -> [a]
take n [] = []
take n (a:as) | n > 0 = a : take (n-1) as
              | otherwise = []

-- main = print (take 2 [1..5])  -- [1,2]
-- main = print (take 2 [1..10]) -- [1,2]
-- main = print (take 0 [1..5])  -- []
-- main = print (take 2 [])      -- []
-- main = print (take (-2) [])   -- []


{-
take 2 [1..5] =

n = 2  a = 1   as = [2,3,4,5]    1 : take 1 [2,3,4,5]
n = 1  a = 2   as = [3,4,5]      1 : 2 : take 0 [3,4,5]
n = 0                            1 : 2 : [] = [1,2]
-}


drop :: Int -> [a] -> [a]
drop n [] = []
drop n (x : xs)
 | n < 1 = (x : xs)
 | otherwise = drop (n-1) xs

-- main = print (drop 5 [1 ,2 ,3])        -- []
-- main = print (drop ([1..5]!!2) [1..5]) -- [4,5]
-- main = print (drop 0 [1..5])           -- [1,2,3,4,5]

{-
drop 2 [1..5] =
drop 2 (1:2:3:4:5:[]) =
drop 1 (2:3:4:5:[]) =
drop 0 (3:4:5:[]) =
3:4:5:[] =
[3,4,5]
-}

(++) :: [a] -> [a] -> [a]
[]     ++ ys = ys
(x:xs) ++ ys = x : (xs ++ ys)

{-
[1,2]++[3..100000] =
(1:[2])++[3..100000] =
(x:xs )++ys =
1 : ([2] ++ [3..100000]) =
1 : ((2:[]) ++ [3..100000]) =
     (x:xs) ++ ys 
1 : (x : (xs ++ ys)) =
1 : (2 : ([] ++ [3..100000])) =
1 : (2 : [3..100000]) =
[1..100000]
-}


-- [1,2,3] ++ [4,5] = [1,2,3,4,5]

reverse :: [a] -> [a]
reverse [] = []
reverse (x : xs) = reverse xs ++ [ x ]

-- main = print (reverse []:: [Int]) -- []
-- main = print (reverse [1,2,3]) -- [3,2,1]

{-
reverse [1..3]
(reverse [2 ,3]) ++ [1]
(reverse [3] ) ++ [2] ++ [1]
(reverse [] ) ++ [3] ++ [2] ++ [1]
[] ++ [3] ++ [2] ++ [1] = [3 ,2 ,1]
-}

-- some more list patterns
-- the input is expected in a set format = pattern 
triplesum :: [Int] -> Int
{-
triplesum [] = undefined -- matches on empty list
triplesum (x:[]) = undefined -- matches on singleton list = length 1
triplesum (x:y:z:[]) = undefined -- matches on list with exactly length 3
triplesum (x:x':xs) = undefined -- matches on list with at least length 2
-}
triplesum [x, y, z] = x + y + z

-- main = print (triplesum [1,2,4])  -- 7  
-- main = print (triplesum [1,2,3,4])  -- error, Non-exhaustive patterns in function triplesum

-- omitting values
f :: Int -> Int -> Int
f _ x = x
-- main = print (f 4 5) -- 5


-- patterns with list constructor - a list with min 2 elements
g :: [Int] -> Int
g (x:y:zs)  = x + y
-- main = print ( g [1 , 2, 3, 4, 5]) -- 3

-- patterns + recursively applied functions
-- lastof :: [a] -> a
-- lastof xs = head (drop (length xs - 1) xs)
lastof [x] = x   -- not defined for []
lastof (x:xs) = lastof xs
-- main = print (lastof [1..10]) -- 10


-- recursive functions on lists - calling built-in functions
-- sum buil-in to sum up all the elements of a list

sum :: [Int] -> Int
sum xs
  | xs == [] = 0
  | otherwise = head xs + sum (tail xs)
-- recursive functions on lists - using [head : tail] pattern

sum2 :: [Int] -> Int
sum2 [] = 0
sum2 (first : rest) = first + sum2 rest
-- main =  print (sum1 [1..5]) -- 15 the same for sum2

-- recursive function with any element pattern
length [] = 0
length (_ : rest) = 1 + length rest
-- main =  print (length1 [1..10]) -- 10

-- warm-up
{-
Evaluate the following expressions:
1. (take 3 [1..10] ) ++ (drop 3 [1..10] )
2. length (concat [[1 ,2] , [3] , [4 , 5, 6, 7] , [8 , 9]] )
3. elem (length [1..5] ) [7..10]
4. [1..5] ++ [0] ++ reverse [1..5]

try it on paper and check with ghc!
-}

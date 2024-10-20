-- Basic list functions - review

{-
        Overview of lists:
            1. Homogeneity: All elements of list must be of the same type.
            2. Basic functions:
                1. "head list" retrieves the first element.
                2. "tail list" returns all but the first element.
            3. Use "++" to combine two lists.
            4. "length list" gives the number of elements of list
            5. Nested Lists: Lists can contain other lists.
            6. Indexing: Access elements with (list !! index).
            for more information check: https://www.haskelltutorials.com/guides/haskell-lists-ultimate-guide.html

--main = print (head [101, 20, 33, 43, 51] )    -- 101 first element of the list

--main = print (head [ [101, 20], [33, 43, 51]] )  -- [101,20]

--main = print (head [[[101, 20]], [[33]],[[ 43, 51], [1,2,3]]] )  -- [[101,20]]

--main = print (tail [10, 22, 32, 43, 58] )  -- [22,32,43,58] everything except first element as a list

--main = print (drop 5 [1, 2, 3, 4, 5, 6, 7] ) -- [6,7] delete first 5 elements

--main = print (take 4 [1, 2, 3, 4, 5] ) -- [1,2,3,4] take first 4 elements

--main = print ([1..10] ++ [8, 88] )  -- [1,2,3,4,5,6,7,8,9,10,8,88] concatenation, appends the second to the first

--main = print (reverse [1..8])  -- [8,7,6,5,4,3,2,1] reverses a list

--main = print (length [1..100] )   -- 100 number of elements

--main = print (last [100, 200, 300])   -- 300 last list element

--main = print (init [100, 200, 300])  -- [100,200] all except the last          

--main = print ( elem 2 [0..22] ) -- True, check membership

--main = print ( elem 5 [10..20]) -- False

--main = print (concat [[1,2,3,4], [5], [6,7,8]] ) -- [1,2,3,4,5,6,7,8] flattens a list of lists
-}
-----------------recursion-----------------
digitSum :: Int -> Int
digitSum 0 = 0
digitSum a = (a `mod`10 ) + digitSum (a `div` 10) --div: it returns remainder AND get the result as well
--main = print(digitSum 1234) -- 10

fx :: Int -> Int
fx 0 = 0
fx i = 2*i*(2*i+1) +f(i-1)
--main = print( f 3 ) -- 68

f :: Int -> Int
f 0 = 0
f n = (3*n + 4) * (n + 5) + f (n - 1)
-- main = print (f 3) -- 216

-----------------working with list-----------------
concatTails :: [a] -> [a] -> [a]
concatTails x y
   | length x < 2 || length y < 2 = error "input too short"
concatTails xs ys = tail xs ++ tail ys
--main = print (concatTails [1, 2, 3] [4, 5, 6]) -- [2, 3, 5, 6]


averageList :: [Int] -> Float
averageList [] = 0
averageList xs = fromIntegral (sum xs) / fromIntegral (length xs)
--main = print (averageList [1, 2, 3, 4, 5]) -- 3.0


not_five :: [Int] -> [Int]
not_five [] = []
not_five (x:xs)
  | x == 5    = not_five xs --if x==5 then not in list (notice how there is no x : ... in this line)
  | otherwise = x : not_five xs
--main = print (not_five [5, 4, 5, 4, 3]) -- [4, 4, 3]

not_five2 :: [Int] -> [Int] 
not_five2 [] = []
not_five2 (5:xs) = not_five2 xs --another way for not_five, here we mention 5 in the pattern matching arg
not_five2 (x:xs) = x : not_five2 xs
-- main = print (not_five2 [5, 4, 5, 4, 3]) -- [4, 4, 3]

insertZero :: [[Int]] -> [[Int]] --list of Int list
insertZero [] = []
insertZero (x:xs) = (0:x) : insertZero xs --append 0 before the head(x)
--main = print (insertZero [[1, 2], [3, 4], [5]]) -- [[0,1,2],[0,3,4],[0,5]]
-- 1 ----------------------------------------------------------------------
-- Write a recursive function that computes the n-th multiple of an x plus 10 (n*x+10).
f1 :: Int -> Int -> Int
f1 0 x = 10
f1 n x = x + f1 (n - 1) x
-- main = print (f1 5 2) -- 20 = 5x2+10 ->2+2+2+2+2 +10
--main = print (f1 3 4) 
-- 2 ----------------------------------------------------------------------
-- Add 2 to every odd number of a list, and subtract 2 from every even number.
f2 :: [Int] -> [Int]
f2 [] = []
f2 (x : xs)
    | odd x = (x + 2) : f2 xs
    | otherwise = (x - 2) : f2 xs

-- main = print (f2 [1..5]) -- [3,0,5,2,7]

-- 3 ----------------------------------------------------------------------
-- Write a function for the square, the cube, and so on up to the n-th power of a number,
-- so that increasing powers of a number are obtained in a list.
f5 :: Int -> Int -> [Int]
f5 1 x = []
--f5 n x = f5 (n - 1) x ++ 
--f5 n x = [x ^ n] ++ f5 (n - 1) x
f5 n x = (x ^ n) : f5 (n - 1) x
--main = print (f5 5 2)  -- [4,8,16,32]

-- 4 ----------------------------------------------------------------------
-- Replicate n>0 times a list.
f6 :: Int -> [Int] -> [[Int]]
f6 0 x = []
f6 n x = x : f6 (n - 1) x
--f6 n x = [x] ++ f6 (n - 1) x

--main = print (f6 3 [1..5]) -- [[1,2,3,4,5],[1,2,3,4,5],[1,2,3,4,5]]


-- 5 ----------------------------------------------------------------------
-- Insert 0 at the middle of each sublist.
f7 :: [[Int]] -> [[Int]]
f7 [] = []
f7 (x : xs) = (take (length x `div` 2) x ++ [0] ++ drop (length x `div` 2) x) : f7 xs --eg: list length=10: (take 5 x ++ [0] ++ drop 5 x) : f7 xs (prepend rest of list)  

--f7 (x : xs) = (insert0 x) : f7 xs
--insert0 :: [Int] -> [Int]
--insert0 l = take m l ++ [0] ++ drop m l  
--  where m = length l `div` 2

--main = print (f7 [[1..10], [1..11], [], [1], [1,2]]) 
-- [[1,2,3,4,5,0,6,7,8,9,10],[1,2,3,4,5,0,6,7,8,9,10,11],[0],[0,1],[1,0,2]]

-- 6 ----------------------------------------------------------------------
-- Extract the elements smaller then the head element of a list. Assume that the list is not empty.
f8 :: [Int] -> [Int]
f8 [] = []
f8 (h : t) = f8a h t

f8a :: Int -> [Int] -> [Int] --helper function
f8a h [] = []
f8a h (x : xs)
    | x < h = x : f8a h xs --if x<h: prepend x
    | otherwise = f8a h xs -- else: skip x and process xs

--main = print (f8 [5,1,2,3,4,5,3,6,7,1,8]) -- [1,2,3,4,3,1]

-- 7 ----------------------------------------------------------------------
-- Eliminate in a list, the sublists that are longer or equal to 10.
cond9 :: [Int] -> Bool
cond9 x = length x < 10 --predicate function
f9 :: [[Int]] -> [[Int]]
f9 [] = []
f9 (x : xs)
    | cond9 x = x : f9 xs
    | otherwise = f9 xs

-- main = print (f9 [[1..10], [1..11], [1..5], []]) -- [[1,2,3,4,5],[]]

-- 8 ----------------------------------------------------------------------
-- Compute the greatest common divisor in a recursive function.
f10 :: Int -> Int -> Int
f10 a b
    | a > b = f10 (a - b) b
    | b > a = f10 a (b - a)
    | otherwise = a

--f10 :: Int -> Int -> Int
--f10 a 0 = a
--f10 0 b = b
--f10 a b
--    | a > b = f10 (rem a b) b
--    | b > a = f10 a (rem b a)
--    | otherwise = a

--main = print (f10 24 12) -- 12
--main = print (f10 270 192) -- 6

-- 9 ----------------------------------------------------------------------
-- Given a list of Ints, remove the element at the given position.
remElemAt :: Int -> [Int] -> [Int]
remElemAt i list = take i list ++ drop (i + 1) list

-- main = print (remElemAt 6 [1..7]) -- [1,2,3,4,5,6] 
-- main = print (remElemAt 2 [1..7]) -- [1,2,4,5,6,7]<-[1,2] ++ [4,5,6,7]
-- main = print (remElemAt 9 [1..7]) -- [1,2,3,4,5,6,7]


-- 10 ----------------------------------------------------------------------
-- Switch places the first and last element of a 3 element list.

reorder :: [String] -> [String]
reorder [t, b, h] = [h, b, t] -- reorder (t:b:h:[]) = h:b:t:[] 3 elems list

-- main = print (reorder ["tail", "body", "head"])              -- ["head", "body", "tail"]
-- main = print (reorder ["tails", "body", "heads"] )           -- ["heads", "body", "tails"]
-- main = print (reorder ["ground", "rainbow", "sky"])          -- ["sky", "rainbow", "ground"]

-- 11 ----------------------------------------------------------------------
-- Write a function to convert a list of a person's names into initials (first letter sepparated by a '.').

initials :: [String] -> String
initials [] = []
initials (x : xs) = head x : '.' : initials xs --In Haskell, a string is actually a list of characters ([Char]), so the head function works the same way it works on lists
-- main = print (initials ["Sam", "Harris"]) -- "S.H."
-- main = print (initials ["Howard", "Phillips", "Lovecraft"]) -- "H.P.L."

-- 12 ----------------------------------------------------------------------
-- Given a list of integers, find the minimum of a list (assume the list is not empty).
minimum1 :: [Int] -> Int
minimum1 [x] = x
minimum1 (x : y : xs) --pattern match lists with at least two elements
    | x < y = minimum1 (x : xs) 
    | otherwise = minimum1 (y : xs)

-- [1,0,3,4,5]
-- minimum1 [0,3,4,5]
-- minimum1 [0,4,5]
-- minimum1 [0,5]
-- minimum1 [0]

-- main = print (minimum1 [1..5])        -- 1
-- main = print (minimum1 [10,9,8,7,6])  -- 6
-- main = print (minimum1 [8,6,4,10,12]) -- 4

minimum2 :: [Int] -> Int
minimum2 [x] = x
minimum2 (x : xs) = min x (minimum2 xs) --min function

-- minimum2 [1,2,0,-1] -> min 1 (min 2 (min 0 (-1)))
-- minimum2 [1,2,0,-1] -> min 1 (min 2 -1)
-- minimum2 [1,2,0,-1] -> min 1 -1
-- minimum2 [1,2,0,-1] -> -1

-- main = print (minimum2 [1..5])        -- 1
-- main = print (minimum2 [10,9,8,7,6])  -- 6
-- main = print (minimum2 [8,6,4,10,12]) -- 4

minimum3 :: [Int] -> Int
minimum3 x = minimum x

-- main = print (minimum3 [1..5]) -- 1

-- 13 ----------------------------------------------------------------------
-- Print the max and min number of a string

maxmin :: [Int] -> String
maxmin l = auxMaxMin (tail l) (head l) (head l) --calling auxMaxMin with 3 params: [tail]; head(max) head(min)

auxMaxMin :: [Int] -> Int -> Int -> String
auxMaxMin [] max min = "max = " ++ show max ++ ", min = " ++ show min
auxMaxMin (x : xs) max min
    | x > max = auxMaxMin xs x min --update [] as xd, x as max
    | x < min = auxMaxMin xs max x --update [] as xd, x as min
    | otherwise = auxMaxMin xs max min

-- main = print (maxmin [4,6,2,1,9,63,-134,566]) -- "max = 566, min = -134"
-- main = print (maxmin [-52, 56, 30, 29, -54, 0, -110]) -- "max = 56, min = -110"
-- main = print (maxmin [5]) -- "max = 5, min = 5"



-- P1 ----------------------------------------------------------------------
-- Compute the triple of the negative elements of a list up to the first positive number.

f3 :: [Int] -> [Int]
f3 [] = []
f3 (x:xs)
    | x < 0     = (3 * x) : f3 xs  
    | x >= 0    = []               
--main = print (f3 [-1,-3,-5,-5,2,-4,-5]) -- [-3, -9, -15, -15]

-- P2 ----------------------------------------------------------------------
-- Write a function that keeps the non-zero elements of a list and then multiply by 2 every element.

f4 :: [Int] -> [Int]
f4 [] = []
f4 (x:xs)
    |x==0  = f4 xs 
    |x/=0 = (x*2) : f4 xs

--main = print (f4 [1,2,3,0,5,0,6,0,0,0,0]) -- [2,4,6,10,12]


-- P3 ----------------------------------------------------------------------
-- Check if a list of Booleans contains a True value.
ifOneTrue :: [Bool] -> Bool
ifOneTrue [] = False                 -- If the list is empty, return False
ifOneTrue (x:xs)
    | x == True  = True              -- If the current element is True, return True
    | x == False = ifOneTrue xs      -- If it's False, continue checking the rest of the list
--  False || False || False || False

--main = print (ifOneTrue [False, False, False]) -- False

ifOneTrue' :: [Bool] -> Bool
ifOneTrue' = or   -- or returns True if any value in the list is True, otherwise False
--main = print (ifOneTrue' [False, False, False]) -- False

-- P4 ----------------------------------------------------------------------
-- Check if all elements of a list of Booleans are True.

ifAllTrue :: [Bool] -> Bool
ifAllTrue [] = True        --vacously true         
ifAllTrue (x:xs)
    | x == False =False          
    | x == True = ifAllTrue xs  
--  True && False && True && True

--main = print (ifAllTrue [True, False, True]) -- False

ifAllTrue' :: [Bool] -> Bool -- (use the 'and' function)
ifAllTrue' = and
--main = print (ifAllTrue' [True, False, True]) -- False



-- P5 ----------------------------------------------------------------------
-- Write a function that checks if at least one of the elements in a list is even.

isOneEven :: [Int] -> Bool
isOneEven [] = False
isOneEven (x:xs)
    |x `mod` 2 ==0 = True
    |x `mod` 2 == 1 = isOneEven xs 
--main = print (isOneEven [1,1,3])   -- False
--main = print (isOneEven [1..9])    -- True
-- main = print (isOneEven [2,4..14]) -- True
--main = print (isOneEven [])        -- False


-- P6 ----------------------------------------------------------------------
-- Write a function that checks if all of the elements in a list are even.

allEven :: [Int] -> Bool
allEven [] = True
allEven (x:xs)
    |x `mod` 2 ==1 = False
    |x `mod` 2 == 0 = isOneEven xs
--main = print (allEven [2,4,6])   -- True -- [2,4,6] -> even 2 && even 4 && even 6 && True
--main = print (allEven [1..9])    -- False
-- main = print (allEven [2,4..14]) -- True
-- main = print (allEven [])        -- True



-- P7 ----------------------------------------------------------------------
-- Collect the divisors of a number in a list.

divisors :: Int -> [Int] -- (use a list to accumulate the values)
divisors x = [d | d <- [1..x], x `mod` d == 0] --LIST COMPREHENSION

--main = print (divisors 18) -- [1,2,3,6,9,18]

divisors2 :: Int -> [Int] -- (build a list recursively)
divisors2 0 = [0]
divisors2 1 = [1]
divisors2 x = helper x 1 []            
  where
    helper :: Int -> Int -> [Int] -> [Int]
    helper n d acc
      | d > n                 = reverse acc  
      | n `mod` d == 0        = helper n (d + 1) (d : acc)  
      | otherwise  = helper n (d + 1) acc

-- main = print (divisors2 18) -- [1,2,3,6,9,18]



-- P8 ----------------------------------------------------------------------
-- Delete every second element from a list.
del2 :: [Int] -> [Int]
del2 [] = []
del2 [x] = [x]
del2 (x:y:xs)= x : del2 xs


main = print (del2 [1..10]) -- [1,3,5,7,9]
-- main = print (del2 [1..11]) -- [1,3,5,7,9,11]

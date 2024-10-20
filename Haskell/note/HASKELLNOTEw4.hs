--------------REVISION OF PAST WEEKS--------------

-- Write a recursive function that computes the n-th multiple of an x plus 10 (n*x+10).
f1 :: Int -> Int -> Int
f1 0 x = 10
f1 n x = x + f1 (n - 1) x
-- main = print (f1 5 2) -- 20 = 5x2+10 ->2+2+2+2+2 +10

-- Add 2 to every odd number of a list, and subtract 2 from every even number.
f2 :: [Int] -> [Int]
f2 [] = []
f2 (x : xs)
    | odd x = (x + 2) : f2 xs
    | otherwise = (x - 2) : f2 xs
-- main = print (f2 [1..5]) -- [3,0,5,2,7]


-------------------MISC-------------------
-- Extract the elements smaller then the head element of a list. Assume that the list is not empty.
f8 :: [Int] -> [Int]
f8 [] = []
f8 (h : t) = f8a h t
f8a :: Int -> [Int] -> [Int] --helper function
f8a h [] = []
f8a h (x : xs)
    | x < h = x : f8a h xs --if x<h: prepend x
    | otherwise = f8a h xs -- else: skip x and process xs


-- Eliminate in a list, the sublists that are longer or equal to 10.
cond9 :: [Int] -> Bool
cond9 x = length x < 10 --predicate function
f9 :: [[Int]] -> [[Int]]
f9 [] = []
f9 (x : xs)
    | cond9 x = x : f9 xs
    | otherwise = f9 xs
-- main = print (f9 [[1..10], [1..11], [1..5], []]) -- [[1,2,3,4,5],[]]

-- Given a list of Ints, remove the element at the given position.
remElemAt :: Int -> [Int] -> [Int]
remElemAt i list = take i list ++ drop (i + 1) list
-- main = print (remElemAt 6 [1..7]) -- [1,2,3,4,5,6]

reorder :: [String] -> [String]
reorder [t, b, h] = [h, b, t] -- reorder (t:b:h:[]) = (h:b:t:[]) 3 elems list
-- main = print (reorder ["tail", "body", "head"])   -- ["head", "body", "tail"]

-- Write a function to convert a list of a person's names into initials (first letter sepparated by a '.').
initials :: [String] -> String
initials [] = []
initials (x : xs) = head x : '.' : initials xs --In Haskell, a string is actually a list of characters ([Char]), so the head function works the same way it works on lists
-- main = print (initials ["Sam", "Harris"]) -- "S.H."

minimum1 :: [Int] -> Int
minimum1 [x] = x
minimum1 (x : y : xs) --pattern matching of >=2 elems list
    | x < y = minimum1 (x : xs) 
    | otherwise = minimum1 (y : xs)
-- main = print (minimum1 [1..5])   -- 1

-- Collect the divisors of a number in a list.
divisors :: Int -> [Int] -- (use a list to accumulate the values)
divisors x = [d | d <- [1..x], x `mod` d == 0] --LIST COMPREHENSION
--main = print (divisors 18) -- [1,2,3,6,9,18]


--main = print (divisors 18) -- [1,2,3,6,9,18]


divisors2 :: Int -> [Int] -- (build a list recursively)
divisors2 0 = [0]
divisors2 1 = [1]
divisors2 x = helper x 1 []            
  where
    helper :: Int -> Int -> [Int] -> [Int]
    helper n d acc --accumulate
      | d > n                 = reverse acc  
      | n `mod` d == 0        = helper n (d + 1) (d : acc)  
      | otherwise  = helper n (d + 1) acc
-- main = print (divisors2 18) -- [1,2,3,6,9,18]

-- 3. Given a list of alphabet letters and a list of integers, write a function that pairs them up.
t1 :: [Int] -> [Char] -> [(Int, Char)]
t1 x y = zip x y
--main = print(t1 [1..] ['a'..'z']) -- [(1,'a'),(2,'b'),(3,'c'),...,(26,'z')]
--makes a list of tuples, each tuple conteining elements of both lists occuring at the same position


-- 20. Compute the sum of the list of tuples [(1,1), (2,2), (3,3)] -> (6,6)
sumtup :: [(Int, Int)] -> (Int, Int)
sumtup x = (sum a, sum b)
    where (a, b) = unzip x
 --unzip: separates a list of tuples into one with all the first elements and one with all the second elements.
--main = print(sumtup [(1,1), (2,2), (3,3)])

------------------------------LIST COMPREHENSION------------------------------
--[expression (x,y,int...) | pattern <- list, conditions]

-- 1. Generate a list with 10 times of 13: [13,13,13,13,13,13,13,13,13,13]
l1 :: [Int]
l1 = [ 13 | x <- [1..10] ]
--l1_alt = replicate 10 13
---list comprehension, but instead of x (print out 1..10), we replace it with 13. So it prints 13 times 
--main = print(l1)

-- 3. Generate the following list [(1,3),(1,2),(1,1),(2,3),(2,2),(2,1),(3,3),(3,2),(3,1)]
l3 :: [(Int, Int)]
l3 = [ (x, y) | x <- [1..3], y <- [3,2,1] ]

--main = print(l3)

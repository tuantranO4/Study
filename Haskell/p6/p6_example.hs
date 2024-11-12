-- 1. Given a tuple of two, write a function that swaps the elements of the tuple.
swap :: (a, b) -> (b, a)
swap (a, b) = (b, a)

--main = print(swap (1, 2)) -- (2,1)

-- 3. Given a list of alphabet letters and a list of integers, write a function that pairs them up.
t1 :: [Int] -> [Char] -> [(Int, Char)]
t1 x y = zip x y

--main = print(t1 [1..] ['a'..'z']) -- [(1,'a'),(2,'b'),(3,'c'),...,(26,'z')]

-- 1. Generate a list with 10 times of 13: [13,13,13,13,13,13,13,13,13,13]
l1 :: [Int]
l1 = [ 13 | x <- [1..10] ]
--l1_alt = replicate 10 13

--main = print(l1)

-- 3. Generate the following list [(1,3),(1,2),(1,1),(2,3),(2,2),(2,1),(3,3),(3,2),(3,1)]
l3 :: [(Int, Int)]
l3 = [ (x, y) | x <- [1..3], y <- [3,2,1] ]

--main = print(l3)

-- 8. Generate 100 even numbers using list comprehensions
l8 :: [Int]
l8 = [ x*2 | x <- [1..100] ]

l82 :: [Int]
l82 = take 100 [ x | x <- [1..] , even x ]

--main = print(l8)
--main = print(l82)


-- 15. Is 123457 a prime number? A number is prime if only 1 and the number divides it.
l15 :: Bool
l15 = (length [ x | x <- [1..123457], mod 123457 x == 0 ] ) == 2 -- =2 because 1 and 123457 (mod x y - where x is dividend and y is divisor)

--main = print(l15)

-- 16. Generate the list [(0,10),(1,9),(2,8),...(10,0)]
l16 :: [(Int, Int)]
l16 = [ (x,y) | x <- [0..10], y <- [0..10] , x+y == 10 ]

--main = print(l16)

-- 17. Generate a list that contains all (hour, minute) pairs in a day.
l17 :: [(Int, Int)]
l17 = [ (hour, minute) | hour <- [0..23], minute <- [0..59] ]

--main = print(l17)

-- 20. Compute the sum of the list of tuples [(1,1), (2,2), (3,3)] -> (6,6)
sumtup :: [(Int, Int)] -> (Int, Int)
sumtup x = (sum a, sum b)
    where (a, b) = unzip x
 --unzip: separates a list of tuples into one with all the first elements and one with all the second elements.

--main = print(sumtup [(1,1), (2,2), (3,3)])

-- 24. Given a list of tuples form a list of triple tuples with the original numbers and their sum
triplesum :: [(Int, Int)] -> [(Int, Int, Int)]
triplesum x = [ (fst a, snd a, fst a + snd a) | a <- x ]
--fst/snd a: retrieve first/second elem
--main = print(triplesum [(1,2),(2,3),(3,4),(4,5),(5,6)])

-- 30. Generate a list that contains all (month, day) pairs in a 365-day year.
-- you can consider the number of days in the following way
-- Jan -> 31 days, Feb -> 28 days (ignore leap year), Mar -> 31 days, Apr -> 30 days, 
-- May -> 31 days, Jun -> 30 days, Jul -> 31 days, Aug -> 31 days, Sep -> 30 days, 
-- Oct -> 31 days, Nov -> 30 days, Dec -> 31 days.
l30 :: [(Int, Int)]
l30 = [(m, d) | (m, ds) <- pairs, d <- [1..ds]]
    where pairs = zip [1..12] [31,28,31,30,31,30,31,31,30,31,30,31]
--pairs: zip months with respective date, then d <- [1..ds] iterates throughout that month
--main = print(l30)
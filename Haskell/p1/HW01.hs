-- 1. Find the difference between the product and the sum of two values.
diffps :: Int -> Int -> Int
diffps x y = x*y - (x+y)
--main = print (diffps 10 20)  -- 170
--main = print (diffps 100 50) -- 4850

-- 2. Change the sign of a value and compute the cube of it.
chsc :: Int -> Int
chsc x = (-x)^3
-- main = print (chsc 2) -- -8
--main = print (chsc (-10)) -- 1000

-- 3. Check if both a and b have the same remainder when divided by c.
sameRem :: Int -> Int -> Int -> Bool
sameRem a b c = (a `mod` c) == (b `mod` c)
--main = print (sameRem 12 4 4) -- True
--main = print (sameRem 12 4 3) -- False
-- main = print (sameRem 13 4 3) -- True

-- 4. Check if the first integer is even, the second divisible by 13 and the boolean value is True.
check :: Int -> Int -> Bool -> Bool
check x y b = x `mod` 2 == 0 && mod y 13 == 0 && b == True
-- main = print (check 4 26 True) -- True
--main = print (check 5 26 True) -- False
-- main = print (check 5 23 True) -- False
-- main = print (check 4 26 False) -- False
-- main = print (check 6 26 False) -- False
--main = print (check 6 25 True) --false

-- 5. Check if a number is the sum of two other given numbers, in any order.
issum :: Int -> Int -> Int -> Bool
issum a b c = a==b+c
-- main = print (issum 10 6 3)  -- False
--main = print (issum 10 6 4)  -- True

-- 6. Compute the 5th power of a value.
power5 :: Int -> Int
power5 x = x^5
--main = print (power5 2)  -- 32
main = print (power5 10) -- 100000
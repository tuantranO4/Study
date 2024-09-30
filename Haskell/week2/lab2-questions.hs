
{-
    When you want to test your function:
        1. open your terminal (right click the folder name in the left bar, then click "open in integrated terminal")
        2. run the complier
            2.1 the first way to run you test :
                uncomment the "main" line under the function then 
                input "runghc <filename>" to the terminal, for example : "runghc Ex01_.hs"
            2.2 the second way is using ghci
                input ":l <filename>" to load your file
                then test any functions you want to try. like f 2, or main
                2.2.1
                    GHCi Reminder:
                    - :l <file> - loads the file into GHCi
                    - :r - reloads loaded files
                    - :bro <module> - short for browse, displays the contents of a module
                    - :t <expression> - tells the type of an expression
                    - :i <name> - displays the information of a fv/type/etc (binding strength, where it is defined, etc.)
                    - :set <flag> - turns on a flag (eg -Wincomplete-patterns)
                    - :q - exit
-}

-- Hello world!
greeting :: String -> String -> String -> String
greeting s1 s2 s3 = s1++s2++s3

--main = print( greeting "hello" " " "world" ) -- "hello world"

---------------------------------------------------------------------------------------
---- Define a function maxi with two arguments that delivers the maximum of the two.

maxi :: Int -> Int -> Int
maxi a b =
    if a-b > 0 
        then a
        else b
maxi2 :: Int -> Int -> Int
maxi2 a b
    | a-b > 0 = a
    | otherwise =b
--main = print( maxi 34 56 ) -- 56
--main = print(maxi2 32 23)

---------------------------------------------------------------------------------------
---- Triple a number.

triple :: Int -> Int
triple a = a*3
--main = print( triple 5 ) -- 15
---------------------------------------------------------------------------------------
---- Compute the cube of a number.

cube :: Int -> Int
cube a = a^3
--main = print( cube 4 )  -- 64
-- main = print( cube 8  ) -- 512

---------------------------------------------------------------------------------------
---- Check if a number is the sum of two other given numbers in any order.
    
issum :: Int -> Int -> Int -> Bool
issum a b c
 |a == b+c = True
 |otherwise = False
--main = print( issum 10 6 3 )  -- False
-- main = print( issum 10 6 4 )  -- True

---------------------------------------------------------------------------------------
---- Check if a number is odd. -- odd or even

isoddnr :: Int -> Bool
isoddnr a
 |a`mod`2 == 1 = True
 |otherwise = False
--main = print(isoddnr 5) -- True
--main = print(isoddnr 6) -- False

---------------------------------------------------------------------------------------
---- Check if a number is multiple of 10.

ismult10 :: Int -> Bool

ismult10 x =
 if x `mod` 10 == 0
        then True
        else False

--main = print( ismult10 20 ) -- True
--main = print( ismult10 201 ) -- False

---------------------------------------------------------------------------------------
---- Write a function which returns true if a is divisible by b. 
divBy :: Int -> Int -> Bool
divBy a b =
    if b == 0
    then error ("Division by 0") -- Handling division by 0
    else if a `mod` b == 0
        then True
        else False
--main = print(divBy 10 2) -- True
--main = print (divBy 10 3) -- False
--main = print (divBy 10 0) -- "Dvision by 0"

-- Difference between mod and rem
-- (-7) mod 3 returns 2 because the result takes the sign of the divisor (3), which is positive.
-- 3 * (-3) = - 9 ((-9) - (-7) == -2 , then take the sign of 3)
-- (-7) rem 3 returns -1 because the result takes the sign of the dividend (-7), which is negative.

---------------------------------------------------------------------------------------
---- Write a function which returns true if a is divisible by b or vice versa. Fill in the blanks

divAny :: Int -> Int -> Bool

divAny a b = a `mod` b == 0 || b `mod` a == 0 --return True or False
--main = print(divAny 2 10)

---------------------------------------------------------------------------------------
---- Given three integer numbers a, b and c, check if both a and b have the same remainder when divided by c.

sameRem :: Int -> Int -> Int -> Bool
sameRem a b c=
    if (a `mod` c) == (b `mod` c) 
        then True
        else False
--main = print( sameRem 12 4 4) -- True
-- main = print( sameRem 12 4 3) -- False
-- main = print( sameRem 13 4 3) -- True

---------------------------------------------------------------------------------------
---- Given two integers and a boolean value, check if the first integer is even, the second divisible by 13 and the boolean value is True.
-- Fill in the missing boolean operators.

check :: Int -> Int -> Bool -> Bool
check a b b1 =
    if a`mod`2 == 0 && b `mod` 13 == 0 && b1==True
        then True
        else False
--main = print( check 4 26 True) -- True
--main = print( check 5 26 True) -- False
--main = print( check 5 23 True) -- False

--Recursion is a function that calls itself. We are trying to break questions into small parts.
---------------------------------------------------------------------------------------
---- Write a function that takes two arguments, say n and x, and computes their power,
-- in 2 versions - with recursion and without recursion.

power :: Int -> Int -> Int
power a b= a^b
--main = print( power 2 5 ) -- 32

powerrec :: Int -> Int -> Int
powerrec a b | b==0 = 1             -- Any number to the power of 0 is 1
powerrec a b | b > 0 = a * power a (b - 1)  -- Recursive case
powerrec a b |b<0 = error "Negative exponent not supported"  -- Handle negative exponents if needed

--main = print (powerrec 2 5)  -- Output: 32

--main = print( powerrec 2 0) --  1
--main = print( powerrec 2 4 ) -- 16

---------------------------------------------------------------------------------------
---- Write a function which calculates the sum of the digits of a number.
digitSum :: Int -> Int
digitSum 0 = 0
digitSum a = (a `mod`10 ) + digitSum (a `div` 10) --div: it returns remainder AND get the result as well
--main = print(digitSum 1234) -- 10

---------------------------------------------------------------------------------------
---- Write a function multiplyUntilOne that takes an integer n and returns the result of multiplying 
-- n by itself n times, then decreasing n by 1 after each multiplication until n reaches 1.
sumpowers :: Int -> Int
sumpowers a 
    | a < 0     = error "Negative number"  
    | a == 0    = 0                         
    | a == 1    = 1                         
    | otherwise  = a ^ a + sumpowers (a - 1) 

--main = print(sumpowers 5) -- 3413
--main = print(sumpowers (-34)) -- Negative number
-- main = print(sumpowers 9) -- 405071317

---------------------------------------------------------------------------------------
---- Sum of squares
-- Compute the sum of the squares of numbers from 1 to n.
squareSum :: Int -> Int
squareSum a
    squareSum 0 = 0
    squareSum a = a^2 + squareSum(a-1)
-- Examples
-- main = print(squareSum 5) -- 55
-- main = print(squareSum 0) -- 0
-- main = print(squareSum 100) -- 338350

---------------------------------------------------------------------------------------
---- Given a positive integer, find the sum of the odd numbers up to that number starting from 1.

--sumOdd :: Int -> Int

-- sumOdd 5 // 9 
-- sumOdd 21 // 121
-- sumOdd 10 // 25 = 9+7+5+3+1
-- sumOdd -13 // n has to be positive

---------------------------------------------------------------------------------------
---- Compute for a given positive n the sum of 2i*(2i+1), for i from 1 to n. E.g. for n=3 the sum is 68.

--f :: Int -> Int

-- main = print( f 0 ) -- 0
-- main = print( f 3 ) -- 68

---------------------------------------------------------------------------------------
---- Write GetLastPositive function
-- Returns the number decreased by the last digit if positive, otherwise returns -1.

--getLastPositive :: Int -> Int

-- main = print( getLastPositive 5856)   -- 5850  
-- main = print( getLastPositive 689255) -- 689250
-- main = print( getLastPositive 0)      -- 0
-- main = print( getLastPositive 8)      -- 0
-- main = print( getLastPositive (-8554)) -- -1

---------------------------------------------------------------------------------------
---- Convert digit to string
-- Convert an integer from 0 to 3 into a word, otherwise return "Not less or equal to 5".

--digitToString :: Int -> String

-- main = print( digitToString 3) -- "Three"
-- main = print( digitToString 8) -- "Not less or equal to 5"
-- main = print( digitToString (-1)) -- "Not less or equal to 5"

---------------------------------------------------------------------------------------
---- Average of 5 numbers
-- Compute the average of 5 numbers.

--av5 :: Int -> Int -> Int -> Int -> Int -> Double

-- main = print( av5 1 2 3 4 5) -- 3.0
-- main = print(av5 3 5 7 9 10) -- 6.8

---------------------------------------------------------------------------------------
---- Odd-even operation
-- Return the product if both numbers are odd, sum if both are even, otherwise return 0.

--oddEven :: Int -> Int -> Int


-- main = print(oddEven 474 8983) -- 0
-- main = print(oddEven 6 6) -- 12
-- main = print(oddEven 7 7) -- 49

---------------------------------------------------------------------------------------
---- Are numbers sorted?
-- Check if 5 numbers are sorted in increasing order.

--isSorted :: Int -> Int -> Int -> Int -> Int -> Bool

-- main = print(isSorted 1 1 1 1 1) -- True
-- main = print(isSorted 1 2 3 4 5) -- True
-- main = print(isSorted 4 3 2 1 0) -- False

---------------------------------------------------------------------------------------
---- Transform days into years, weeks, and days.
-- Convert the number of days into a string of years, weeks, and days.

--transform :: Int -> String

-- main = print(transform 375) -- "1 year 1 week 3 days"
-- main = print(transform 365) -- "1 year 0 week 0 days"
-- main = print(transform 1050) -- "2 year 45 week 5 days"
-- main = print(transform 2500) -- "6 year 44 week 2 days"

---------------------------------------------------------------------------------------
---- Armstrong number
--  If sum of cubes of each digit of the number is equal to the number itself, then the number is called an   Armstrong number.
--  153 = 1^3 + 5^3 + 3^3
--  Given a positive integer number, write a function to determine whether it is an Armstrong number or not.

--armstrong :: Int -> Bool

-- main = print( armstrong 153) -- True
-- main = print( armstrong 370) -- True
-- main = print( armstrong 0) -- True
-- main = print( armstrong 12) -- False

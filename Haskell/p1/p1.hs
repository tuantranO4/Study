main = do
  --E1
  print (add 3 5 == 8) 
  print ((add 3 5) == 8)
  --E2
  print (subt 10 4 == 6)
  --E3
  print (multiply 3 4 == 12)
  --E4
  print (divide 10.0 2.0 == 5.0)
  --E5
  print (modulus 10 3 == 1)
  --E6
  print (power 2 3 == 8)
  --E7
  print ((logicalAnd True False) == False)
  --E8
  print (logicalOr True False == True) 
  --E9
  print (isEqual 5 5 == True)
  --E10
  print (isNotEqual 5 3 == True)
  --E11
  print(double 42 == 84)
  print(double(double 42) == 168)
  --E12
  print (halve 5.0 == 2.5)
  print (halve 5.0 == 2.5)
  --E13
  print (ne True == False)
  --E14
  print (st3 "Hello" "World" "!" == "HelloWorld!")
  --E15
  print (cid 'a' == 'a') 
  
  --P1
  print (f3 5 == 15) 
  --P2
  print (dist 12 == 249)
  --P3
  print (sq 8 == 64)
  --P4
  print (expression 1 2 3 4 == 21) 
  print (expression (-1) (-2) (-3) (-4) == 3) 
  --P5
  print (granpa 20000 5 10 1 == 12500) 
  print (granpa 20000 10 5 2 == 11000)

--------Example-------- 
{- 
Basic Types
                Bool    False | True
                Char    '~','c', ...
                Integer 123456, ...
                Float/Double  0.0000001, ...
                String "Hello World!"
-}

{-
Learning materials
https://learnyouahaskell.com/chapters
https://hoogle.haskell.org/
-}

{-
Install
https://www.haskell.org/ghc/download.html
https://www.haskell.org/ghc/download_ghc_9_6_6.html
Download, Unzip, Add the bin in the path.

Mac
curl --proto '=https' --tlsv1.2 -sSf https://get-ghcup.haskell.org | sh
-}

-- 1. Addition
add :: Int -> Int -> Int
add x y = x + y

-- 2. Subtraction
subt :: Int -> Int -> Int
subt x y = x - y

-- 3. Multiplication
multiply :: Int -> Int -> Int
multiply x y = x * y

-- 4. Division
divide :: Float -> Float -> Float
divide x y = x / y

-- 5. Modulus
modulus :: Int -> Int -> Int
modulus x y = x `mod` y

-- 6. Exponentiation
power :: Int -> Int -> Int
power x y = x ^ y

-- 7. Logical AND
logicalAnd :: Bool -> Bool -> Bool
logicalAnd x y = x && y

-- 8. Logical OR
logicalOr :: Bool -> Bool -> Bool
logicalOr x y = x || y

-- 9. Equality
isEqual :: Int -> Int -> Bool
isEqual x y = x == y

-- 10. Inequality
isNotEqual :: Int -> Int -> Bool
isNotEqual x y = x /= y

-- 11. Double an integer
double :: Int -> Int
double x = x * 2

-- 12. Halve a real number
halve :: Double -> Double
halve x = x / 2.0

-- 13. Negate a Boolean
ne :: Bool -> Bool
ne x = not x

-- 14. Concatenation of three strings
st3 :: String -> String -> String -> String
st3 a b c = a ++ b ++ c

-- 15. Function that returns the same character
cid :: Char -> Char
cid x = x


--------Practice--------
-- P1 Triple an integer
f3 :: Int -> Int
f3 x = x * 3

-- P2 Ming climbs from height k1 to height m1, then x more meters. Calculate the total distance he covered.
m1 :: Int
m1 = 8848
k1 :: Int
k1 = 8611

dist :: Int -> Int
dist x = x + m1 - k1 

-- P3 Write a function that squares its argument.
sq :: Int -> Int
sq x = x * x 

-- P4 Write a function to compute a to the power of 3 plus b to the power of 3 plus c multiplied by d.
expression :: Int -> Int -> Int -> Int -> Int
expression a b c d = a^3 + b^3 + c*d
-- main = 
-- main = print (expression (-1) (-2) (-3) (-4)) -- 3

{- P5
    Granpa went shopping with 20000 Ft.
    He bought m milks each 300, c croissants 400 each, and f flower for 2000.
    How much money left after shopping? 
-}
granpa :: Int -> Int -> Int -> Int -> Int
granpa original m c f = original - m*300 - c*400 - f*2000
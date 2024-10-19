import Prelude hiding (map, takeWhile, dropWhile, null, replicate, repeat)

-- main :: IO ()

{-  Lists - review basics
-- empty list is []
-- every list has a type, the type of the contained elements
-- singleton list with one element [False], [[1,2,3]]
-- special constructor is 1:[2,3,4] is equivalent to [1,2,3,4]
-- [1,2,3] is equivalent to 1:2:3:[]
-- 1 : 2 : 3 : [] == 1 : (2 : (3 : [])) == [1, 2, 3]
-}

-- Dot-dot generator: finite and infinite list generator

--main = print [0,2..20]      -- [0,2,4,6,8,10,12,14,16,18,20]
--main = print [1000..]     -- [1000,1001,1002,..] and so on, infinite
--main = print [1000, 900..] 
--The elements need not be constants, used expressions must be of the same type

lfloats :: [Float]
lfloats = [sqrt 2, 5/2, max 5.0 55.0 ] 
--main = print lfloats   -- [1.4142135,2.5,55.0]

------ Basic built-in functions - review

--main = print (head [1, 2, 3, 4, 5] )    -- 1 first element of the list
--main = print (tail [1, 2, 3, 4, 5] )  -- [2,3,4,5] everything except first element as a list
--main = print (drop 2 [1, 2, 3, 4, 5] ) -- [3,4,5] delete first n elements
--main = print (take 2 [1, 2, 3, 4, 5] ) -- [1,2] take first n elements
--main = print ([1, 2, 3] ++ [4, 5, 6] ++ [10]) -- [1,2,3,4,5,6,10] concatenation, appends the lists
--main = print (reverse [1, 2, 3])  -- [3,2,1] reverses a list
--main = print (length [1, 2, 3, 4] )   -- 4 number of elements
--main = print (last [1, 2, 3])   -- 3 last list element     
--main = print (init [1, 2, 3])  -- [1,2] all except the last          
--main = print (elem 2 [1, 2, 3]) -- True, check membership
--main = print (elem 5 [1, 2, 3]) -- False
--main = print (concat [[1,2], [3, 4, 5], [6, 7]] ) -- [1,2,3,4,5,6,7] flattens a list of lists


-- More tests

--main = print (take 2 ([]::[Int]))   -- [] empty, it can not take any from []
--main = print (drop 5 [1,2,3])  -- [] empty, it can not drop 5 out of 3       
--main = print (take 2 [1 .. 10])  -- [1,2]       
--main = print (drop ([1..5]!!2) [1..5]) -- [4,5] the !! is list index (subscript) operator, starting from 0, shows n+1-th element
     
--main = print (reverse [5,4 .. -5])  -- [-5,-4,-3,-2,-1,0,1,2,3,4,5]      
--main = print (elem 0 [])  -- False             
--main = print (elem (-1) [1..10])     -- False     
--main = print (elem ([1..5]!!1) [1..5])  -- True, extracts an element and checks if is member -> true
-- New functions to practice and learn

-- Logical list functions

-- null - checks if a list is empty or not 
null :: [a] -> Bool
-- null xs = length xs == 0
null []     = True
null (x:xs) = False

e1 :: Bool
e1 = null []
--main = print e1 -- True

e2 :: Bool
e2 = null [1..]
--main = print e2 -- False


-- and - returns the conjunction of a list of Bools
-- does it work for infinite lists?
-- and :: [Bool] -> Bool    -- concat  -- or
-- &&                       -- ++      -- ||

a1 = and []
a2 = and [True]
a3 = and [False]
a4 = and [True, False, True, False]
--main = print ([a1,a2,a3,a4]) --[True,True,False,False]


-- or -  returns the disjunction of a list of Bools, does it work for infinite lists?
-- or :: [Bool] -> Bool

o1 = or []
o2 = or [True]
o3 = or [False]
o4 = or [True, False, True, False]
--main = print ([o1,o2,o3,o4]) --[False,True,False,True]


-- Membership functions
-- elem - answers the question  - does the element occur in the list?
-- elem :: a -> [a] -> Bool
el1 = 3 `elem` [] -- False  ` backtick or accent grave
el2 = 3 `elem` [1,2] -- False
el3 = 3 `elem` [1,2,3,4,5] -- True 
el4 = 3 `elem` [1..] -- True
--main = print ([el1,el2,el3,el4]) --[False,False,True,True]


-- can be written in prefix way first operator then arguments
el11 = elem 3 [] -- False
el22 = elem 3 [1,2] -- False
el33 = elem 3 [1,2,3,4,5] -- True 
el44 = elem 3 [1..] -- True
--main = print ([el11,el22,el33,el44]) --[False,False,True,True]


-- notElem is the negation of elem
-- notElem :: a -> [a] -> Bool 
ne1 = 3 `notElem` [] -- True
ne2 = 3 `notElem` [1,2] -- True
ne3 = 3 `notElem` [1,2,3,4,5] -- False
-- For infinite structures, notElem terminates if the value exists at a finite distance from the left side of the structure:
ne4 = 3 `notElem` [1..] -- False
ne5 = 3 `notElem` ([4..] ++ [3]) -- hangs forever
--main = print ([ne1,ne2,ne3,ne4]) -- [True,True,False,False]


-- can written in prefix way
ne11 = notElem 3 [] -- True
ne22 = notElem 3 [1,2] -- True
ne33 = notElem 3 [1,2,3,4,5] -- False
ne44 = notElem 3 [1..] -- False
--main = print ([ne11,ne22,ne33,ne44]) -- [True,True,False,False]

-- 3 `elem` [1,1..] -- runs infinitely, should return False, but it doesn't
-- 3 `notElem` [1,1..] -- runs infinitely, should return True, but it doesn't
-- 1 `elem` [1,1..] -- return immeditely with True
-- 1 `notElem` [1,1..] -- return immeditely with False

-- Repetition style list functions

-- repeat x - is an infinite list, with x the value of every element
-- repeat :: a -> [a]   -- repeats the value x infinitely many times

l4 :: [Int]
l4 = take 5 ( repeat 8 )
--main = print l4 -- [8,8,8,8,8]


-- replicate - generates a list of length n with x the value of every element
replicate :: Int -> a -> [a]
replicate i x = take i $ repeat x


l5 = replicate 5 8
--main = print l4 -- [8,8,8,8,8]

-- cycle - ties a finite list into a circular one
-- cycle :: [a] -> [a] -- is the infinite repetition of the original list

l6 = take 10 (cycle [42]) 
--main = print l6 -- [42,42,42,42,42,42,42,42,42,42]

l7 = take 10 (cycle [2, 5, 8])
--main = print l7 -- [2,5,8,2,5,8,2,5,8,2]

-- repeat is a special case of cycle:
repeat x = cycle [x]

-- Functions on strings
-- type String = [Char] -- String is an alias for a list of characters.
-- String constants in Haskell are values of type String. 
-- That means if you write a string literal like "hello world", 
-- it will have the type [Char], which is the same as String.

-- length "hello" -- same as:
-- length ['h', 'e', 'l', 'l', 'o']

-- putStr "hello\n\n\n"

-- lines - splits the argument into a list of lines stripped 
--  of their terminating \n characters. 
-- The \n terminator is optional in a final non-empty line of the argument string.
-- lines :: String -> [String]

line1 = lines ""           -- empty input contains no lines []
line2 = lines "\n"         -- single empty line [""]
line3 = lines "one"        -- single unterminated line ["one"]
line4 = lines "one\n"      -- single non-empty line ["one"]
line5 = lines "one\n\n"    -- second line is empty ["one",""]
line6 = lines "one\ntwo"   -- second line is unterminated ["one","two"]
line7 = lines "one\ntwo\n" -- two non-empty lines ["one","two"]
-- main = print [line1, line2, line3, line4, line5, line6, line7] 
-- [[],[""],["one"],["one"],["one",""],["one","two"],["one","two"]]

-- unlines - appends a \n character to each input string, then concatenates the results.
-- unlines :: [String] -> String

u1 = unlines ["Hello", "World", "!"] 
-- main = print u1 -- "Hello\nWorld\n!\n"

-- lines $ unlines ss == ss -- not always true:
-- lines $ unlines ["a\nb", "c"] == ["a","b","c"] /= ["a\nb", "c"]
-- unlines $ lines s == s -- not always true:
-- unlines $ lines "hello" /= "hello\n"

{-
                          -- for a list of length 5
f [x,y,z] = x : f [y,z]   -- 1 step
f xs = xs

f [] = []                 -- 4 steps
f [x] = [x]
f [x,y] = [x,y]
f [x:y:zs] = [x:y:zs]

f [] = []                 -- 5 steps, in general it takes as many steps as the length of the list
f (x:xs) = x : f xs

f [1,2,3,4,5] =
f (1:[2,3,4,5]) =
1 : f [2,3,4,5] =
1 : f (2:[3,4,5]) =
1 : 2 : f [3,4,5] =
1 : 2 : f (3:[4,5]) =
1 : 2 : 3 : f [4,5] =
1 : 2 : 3 : f (4:[5]) =
1 : 2 : 3 : 4 : f [5] =
1 : 2 : 3 : 4 : f (5:[]) =
1 : 2 : 3 : 4 : 5 : f [] =
1 : 2 : 3 : 4 : 5 : []
 

f xs = xs                  FASTEST



take 3 (take 4 [1..10]) =
take 3 [1,2,3,4] =
[1,2,3]
-}


-- words - breaks a string up into a list of words, which were delimited by white space 
-- This function trims any white spaces at the beginning and at the end.
-- words :: String -> [String]

w1 = words "Lorem ipsum dolor" -- ["Lorem","ipsum","dolor"]
w2 = words " foo bar " -- ["foo","bar"]
-- main = print [w1, w2] -- [["Lorem","ipsum","dolor"],["foo","bar"]]


-- unwords - joins words with separating spaces
-- unwords :: [String] -> String

uw1 = unwords ["Lorem", "ipsum", "dolor"] -- "Lorem ipsum dolor"
uw2 = unwords ["foo", "bar", "", "bang"] -- "foo bar  bang"
--main = print [uw1, uw2] -- ["Lorem ipsum dolor","foo bar  bang"]

-- unwords $ words str == str NOT TRUE ALL THE TIME:
-- unwords $ words "a  b" =
-- unwords ["a", "b"] =
-- "a b"

-- words $ unwords strs == strs NOT TRUE ALL THE TIME:
-- words $ unwords ["a b", "c"] == ["a", "b", "c"] /= ["a b", "c"]


-- Converting to String
-- show - conversion of values to readable Strings
-- show :: a -> String
bs :: Bool
bs = False
as :: Int
as = 12345
fs :: Float
fs = 3.1428
--main = print (show bs ++"  "++ show as ++"  "++ show fs)  -- "False  12345  3.1428"



-- we can show Bool, Int, [Int], [[String]], ...
-- but we cannot show functions, e.g. Bool -> Bool
-- what are the different Bool->Bool functions?
tt, tf, ft, ff, g1,g2,g3,g4 :: Bool -> Bool
tt True  = True
tt False = True
tf True  = True
tf False = False
ft True  = False
ft False = True
ff True  = False
ff False = False

g1 a = a -- this behaves the same as tf
g2 a = True -- this behaves the same as tt

-- is there a function which behaves different from tt,tf,ft,ff??

g3 = g3 -- this is different from all of these
g4 True = g4 True
g4 False = True
-- ... 


-- Higher order functions

-- map - applies a function f to each element of a list
-- map f [x1, x2, ..., xn] == [f x1, f x2, ..., f xn]
-- map f [x1, x2, ...] == [f x1, f x2, ...]
-- map :: (a->b) -> [a] -> [b]

map                     :: (a->b) -> [a] -> [b]
map f  []               =  []
map f (x:xs)            =  f x : map f xs

-- map ((+) 2a) [1,2,3] =
-- [(+) 2 1,(+) 2 2,(+) 2 3] =
-- [2+1, 2+2, 2+3] =
-- [3,4,5]

-- map null [[],"hello", "", [], "a"] = [True,False,True,True,False]
--   null :: String -> Bool
--   [[],"hello", "", [], "a"] :: [String]
--   map null [[],"hello", "", [], "a"] :: [Bool]


fm :: Int -> Int
fm x = 2*x
--main = print (map fm [1..10]) --[2,4,6,8,10,12,14,16,18,20]

fm2 :: Int -> Float
fm2 x = fromIntegral x / 2
--main = print (map fm2 [1..10]) -- [0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0]


-- Lambda expressions

-- An anonymous function is a function without a name. 
-- It is a lambda abstraction and might look like this: \x -> x + 1
-- \ is symbolic way writing the lambda letter

f5',f5'' :: Int -> Int
f5'      x  = 2*(x+1)
f5'' = \ x -> 2*(x+1)
-- no difference between the above two functions

-- this is a nameless function which increments its parameter x
la = (\x -> x + 1) 4
--main = print (la) -- 5

-- this a nameless function of two parameters, x and y
la2 = (\x y -> x + y) 3 5
--main = print (la2) -- 8

-- this a nameless function to add 10 to the argument x
la3 = map (\x -> 10 + x) [1..5]
--main = print (la3) -- [11,12,13,14,15]

-- the above can be rewritten in "pointfree style" or with partial parameters
-- (\x -> 10 + x) is equivalent to  (10+) 
la4 = map (10+) [1..5] 
--main = print (la4) -- [11,12,13,14,15]


lm1 = map (+1) [1, 2, 3] -- [2,3,4]
lm2 = map id [1, 2, 3] -- [1,2,3] -- id x = x
lm3 = map (\n -> 3 * n + 1) [1, 2, 3] --[4,7,10]
--main = print ([lm1, lm2, lm3]) -- [[2,3,4],[1,2,3],[4,7,10]]

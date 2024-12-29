averageScores :: [(String, [Int])] -> [(String, Double)]
averageScores [] = [] 
averageScores ((name, scores):xs) =
  let avg = fromIntegral (sum scores) / fromIntegral (length scores)
  in (name, avg) : averageScores xs

--main = print (averageScores [("Alice", [10, 20, 30]), ("Bob", [20, 30, 40])]) 
------------------------
--let <bindings> in <expression> (aka let var_declare in main_func)
--result = let x = 5
--          in let y = x * 2
--             in y + 3
---------------------------------
filterTupleSum :: Int -> [(Int, Int)] -> [(Int, Int)]
filterTupleSum _ [] = []
filterTupleSum n xs = [(a,b) | (a,b)<-xs, a+b<n]
--list comprehesnion
---------------------------------
nums :: Int -> [Int]
nums 0 = []
nums n = nums (n`div` 10) ++ [n `mod` 10]
sumOfSquaresOfDigits :: Int -> Int
sumOfSquaresOfDigits n = sum [num^2 | num <- nums (abs n)]
--from number to arrays
---------------------------------
f1 :: String -> String
f1 str = [if c `elem` "aeiouAEIOU" then 'O' else c | c<-str] --`notElem` check if not element
--string already array of characters
---------------------------------
difference :: [Int] -> [Int] -> [Int]
difference [] [] = []
difference xs ys = [a | a<-xs, a `notElem` ys] --notElem
---------------------------------
countOddGap :: [Int] -> Int -> Int
countOddGap [] _ = 0
countOddGap [_] _ = 0
countOddGap [_,_] _ = 0
countOddGap (x:y:z:xs) a
  | x == a && odd z = 1 + countOddGap (y:z:xs) a
  | otherwise = countOddGap (y:z:xs) a
--multiple elements checking
---------------------------------
convertBin :: Int -> [Int]
convertBin 0 = [0]
convertBin x = reverse (helper x)
 where
  helper 0 = []
  helper x = (x `mod` 2 ) : helper (x `div` 2) -- :  cons operator, append to left side 
{--append right:
    helper 0 acc = acc
    helper x acc = helper (x `div` 2) (acc ++ [x `mod` 2])
--}
--BINARY CONVERT
isAllOnes :: Int -> Bool
isAllOnes n = length (filter (==1) $ convertBin n) ==length (convertBin n) 
---------------------------------
selectiveMap :: (a -> Bool) -> (a -> a) -> [a] -> [a]
selectiveMap _ _ [] = []
selectiveMap p f (x:xs) --remember, [x:xs doesnt work], we need (x:xs)
  |p x = f x : selectiveMap p f xs --p bool check x
  |otherwise = x : selectiveMap p f xs
--main = print $ selectiveMap odd (\x -> 6000) [2,4,6,8,10] -- [2,4,6,8,10]
---------------------------------
analyzePrices :: [(String, Double)] -> (Double, Int)
analyzePrices [] = (0.0, 0)
analyzePrices ((n, p) :xs)
    | p<=0 = analyzePrices xs
    |otherwise = (p + total, count + 1)
  where
    (total, count) = analyzePrices xs
--where: Define variables, helper functions, or intermediate values used in the main expression
---------------------------------
f2 :: [[Int]] -> Int -> [[Int]]
f2 [[]] _ = [[]]
f2 xss n = [[ 4-y | y<-xs, y>=4 ] | xs<-xss, length xs >=4]
--list comprehension INSIDE another lc
---------------------------------
mergeTuples :: [(a, b)] -> ([a], [b])
mergeTuples [] = ([],[])
mergeTuples ((x,y):xs) = (x : fst rest, y:snd rest)
 where
    rest = mergeTuples xs
---------------------------------
digits :: Int -> [Int]
digits 0 = []
digits n = digits (n `div` 10) ++ [n `mod` 10]

transformList :: [Int] -> [Int]
transformList [] = []
transformList xs = 
  [ sum (digits x) - foldr (*) 1 (digits x) | x <- xs, x `mod` 5 /= 0 ]
--SYNTAX
--foldr (+) 0 [1, 2, 3] --OUTPUT: 6
--filter (== 1) [1, 2, 1, 3, 1] -- Output: [1, 1, 1]
--length [1, 2, 3, 4] -- Output: 4
--map (\\x -> x * 2) [1, 2, 3] -- Output: [2, 4, 6]
--[x * 2 | x <- [1, 2, 3, 4], x > 2] -- Output: [6, 8]
--filter (\\x -> x `mod` 2 == 0) [1, 2, 3, 4] -- Output: [2, 4]
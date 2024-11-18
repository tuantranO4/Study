{-# LANGUAGE ParallelListComp #-}

e1 :: [(Int,Int)]
e1 = [(x,y) | x <- [1, 2], y <- [3, 4]]
--main = print(e1) --[(1,3),(1,4),(2,3),(2,4)]

e2 :: [(Int,Int)]
e2 = [(x,y) | x <- [1, 2] | y <- [3, 4]]
--main = print(e2) --[(1,3),(2,4)]

e3 :: [Int]
e3 = [x + y | x <- [1, 2], y <- [3, 4]]
--main = print(e3) --[4,5,5,6]

e4 :: [Int]
e4 = [x + y | x <- [1, 2] | y <- [3, 4]]
--main = print(e4) --[4,6]


--Task 1: Multiply Corresponding Elements
--Given two lists of numbers, compute a list where each element is the product of the corresponding elements of the two lists.
multiplyElements :: [Int] -> [Int] -> [Int]
multiplyElements [] [] = [0,0]
multiplyElements xs ys = [x*y | (x,ix)<- zip xs [1..],(y,iy)<- zip ys [1..],  ix==iy]
--zip:
multiplyElements' :: [Int] -> [Int] -> [Int]
multiplyElements' [] [] = [0,0]
multiplyElements' xs ys = [x*y | (x,y)<- zip xs ys]
--parallel list comprehension:
multiplyElements'' :: [Int] -> [Int] -> [Int]
multiplyElements'' xs ys = [x*y | x<-xs|y<-ys]
--main = print (multiplyElements [2, 3, 4] [5, 6, 7]) -- [10, 18, 28]

--Task 2: Combine Names and Scores 
--Given two lists—one of names and one of scores—combine them into a list of strings with the format "Name: Score".
toString :: [Int] -> [String]
toString [] = []
toString ss = [show s | s<-ss]
combineNamesAndScores :: [String] -> [Int] -> [String]
combineNamesAndScores [] [] = ["null"]
combineNamesAndScores ns ss = [(n++": "++s) | (n,s)<-zip ns (toString ss)]
--main = print (combineNamesAndScores ["Alice", "Bob", "Charlie"] [85, 92, 78])
-- Output: ["Alice: 85", "Bob: 92", "Charlie: 78"]

--Task 3: Compute Squares and Cubes 
--Given two lists, compute a list of tuples where the first element is the square of the number from the first list and the second element is the cube of the number from the second list.
computeSquaresAndCubes :: [Int] -> [Int] -> [(Int, Int)]
computeSquaresAndCubes s c = [(ss^2,cc^3)| ss<-s| cc<-c]
--main = print (computeSquaresAndCubes [1, 2, 3] [4, 5, 6])
-- Output: [(1, 64), (4, 125), (9, 216)]

--Task 4: Filtered Pairing
--Given two lists, create pairs of elements (x, y) where both elements satisfy a condition: x must be even, and y must be odd.
filteredPairs :: [Int] -> [Int] -> [(Int, Int)]
filteredPairs xs ys = [(x,y) | x<-xs, even x | y<-ys, odd y]--parallel traverse, condition also need to be parallel
--main = print (filteredPairs [1, 2, 3, 4] [5, 6, 7, 8])
-- Output: [(2, 5), (4, 7)]

--Task 5: Weighted Sum of Two Lists  
--Given two lists of numbers, compute their weighted sum using a given list of weights. Each weight applies to both corresponding elements.
weightedSum :: [Double] -> [Double] -> [Double] -> [Double]
weightedSum w1 w2 mul = [(w1+w2)*mul | w1<-w1| w2<-w2| mul<-mul]
main = print (weightedSum [1.0, 2.0, 3.0] [4.0, 5.0, 6.0] [0.5, 1.5, 2.0])
-- Output: [2.5, 10.5, 18.0]



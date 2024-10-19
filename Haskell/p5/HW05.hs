import Data.Char
{- 1
	Given a list of string, remove the string that does not start with the vowel and convert the rest of the string into UPPERCASE.
	If the string is empty, then it must be removed as it does not start with the vowel.
-}

--isVowel :: Char -> Bool

--vowelUpper :: [String] -> [String]

--main = print(vowelUpper ["apple", "banana", "orange", "umbrella", "grape", "avocado"]) -- ["APPLE","ORANGE","UMBRELLA","AVOCADO"]
--main = print(vowelUpper ["", "apple", "banana"]) -- "APPLE"
--main = print(vowelUpper []) -- []


{- 2
 Write a function called "transformer" which takes 2 arguments; 
 the first argument is a string from ["add", "multiply", "power"] and the second argument called 'n' is the integer.
 
 The main function "transformer" should return the function that take 1 integer argument called 'x' and that return the integer (Int -> Int).
 
 The returned function performs the operation based on the given string upon 2 opearands, 'x' and 'n' ('x' is the first operand).
 
 If the furst argument of "transformer" is unknown (which means it is not from ["add", "multiply", "power"], abort the function.
 
-}

--transformer :: String -> Int -> (Int -> Int)

-- do not delete these: part of the test cases
adder5 = transformer "add" 5
multiplier3 = transformer "multiply" 3
power2 = transformer "power" 2

--main = print(adder5 3) -- 8
--main = print(multiplier3 6) -- 18
--main = print(power2 10) -- 100




{- 3.
	Write a function called "logicChain" which takes the list of boolean functions (Int -> Bool) and a string ["and", "or"];
	
	The main function "logicChain" should return a function (Int -> Bool) which takes an integer 
	and apply all the boolean functions and reduce the result using "and" or "or" according to the given string.
	
	For example: the argument for the "logicChain" is [is_even, is_positive, less_than_10] and "and",
	then it should return a function taking integer and returning a single boolean value (Int -> Bool).
	That returned function applies all the boolean functions from the list and reduce it into a single boolean.
	
	If the string argument from the "logicChain" is not from the ["and", "or"], abort the function.
	Look at the test cases and you will understand.
-}

--logicChain :: [Int -> Bool] -> String -> (Int -> Bool)


-- do not delete these: part of the test cases
isPositive x = x > 0
isLessThan10 x = x < 10

andChain = logicChain [even, isPositive, isLessThan10] "and"
orChain = logicChain [even, isPositive] "or"

--main = print(andChain 4) -- True
--main = print(andChain 11) -- False
--main = print(orChain (-2)) -- True
--main = print(orChain (-5)) -- False



{- 4
	Write a function that takes a list of lists (representing a matrix) and a boolean function (Int -> Bool) and returns a new matrix that is the transpose of the input matrix, 
	along with the sum of each row in the original matrix. 
	
	The sum of each row is restricted with the boolean function (only integer that is True with the given boolean function has to be included in the sum).
	
	The result of the sum has to be concatenated into the transpose matrix.
	
	For example: transposeAndSum [[1,2,3], [4,5,6]] isEven
				The transpose matrix: [[1,4],[2,5],[3,6]]
				The sum of each row based on condition in the original matrix: [2,10]
				
				Result: [[1,4],[2,5],[3,6],[2,10]]
				
	The input matrix will be transposable.
-}

--transpose :: [[Int]] -> [[Int]]

--transposeAndSum :: [[Int]] -> (Int -> Bool) -> [[Int]]

-- do not delete these: part of the test cases
isPos x = x > 0

--main = print(transposeAndSum [[1, 2, 3], [4, 5, 6], [7, 8, 9]] even) -- [[1,4,7],[2,5,8],[3,6,9],[2,10,8]]
--main = print(transposeAndSum [[1, 2], [2, 2], [3, 3]] odd) -- [[1,2,3],[2,2,3],[1,0,6]]
--main = print(transposeAndSum [[(-1), 2, 3], [4, 5, 6], [(-2), (-3), 4]] isPos) -- [[-1,4,-2],[2,5,-3],[3,6,4],[5,15,4]]


{- 5
	Write a function that takes a list of words and returns the list of longest word(s).
-}

--maxLengthWords :: [String] -> [String]

--maxHelper :: Int -> String -> Int
  

--main = print(maxLengthWords ["hello", "world", "haskell", "programming"]) -- ["programming"]
--main = print(maxLengthWords ["sunshine", "moonlight", "starlight", "night"]) -- ["moonlight","starlight"]
--main = print(maxLengthWords ["cat", "dog", "bat"]) -- ["cat","dog","bat"]
--main = print(maxLengthWords []) -- []

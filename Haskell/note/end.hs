{-# LANGUAGE ParallelListComp #-}
--main :: IO ()
data Team = Team {teamName :: String, teamID :: Int} deriving (Show)
data Player = Player {playerID :: Int, playerName :: String, playerTeam :: Team, playerScores :: [Int]} deriving (Show)
data Univ = Univ {uniName :: String, uniID :: Int} deriving (Show)
data Student = Student {neptunID :: Int, studentName :: String, uni :: Univ, grades :: [Int]}
team1 = Team {teamName = "Team A", teamID = 1}
team2 = Team {teamName = "Team B", teamID = 2}
team3 = Team {teamName = "Team C", teamID = 3}
team0 = Team {teamName = "Team D", teamID = 0}
player1 = Player {playerID = 1, playerName = "Alice", playerTeam = team1, playerScores = [10, 20, 30]}
player2 = Player {playerID = 2, playerName = "Bob", playerTeam = team1, playerScores = [15, 25, 35]}
player3 = Player {playerID = 3, playerName = "Charlie", playerTeam = team2, playerScores = [12, 22, 32]}
player4 = Player {playerID = 4, playerName = "David", playerTeam = team2, playerScores = [18, 28, 38]}
player5 = Player {playerID = 5, playerName = "Eve", playerTeam = team3, playerScores = [14, 24, 34]}
player6 = Player {playerID = 6, playerName = "Frank", playerTeam = team3, playerScores = [16, 26, 36]}
univ1 = Univ {uniName = "University A", uniID = 1}
univ2 = Univ {uniName = "University B", uniID = 2}
univ3 = Univ {uniName = "University C", uniID = 3}
student1 = Student {neptunID = 101, studentName = "Alice", uni = univ1, grades = [85, 90, 78]}
student2 = Student {neptunID = 102, studentName = "Bob", uni = univ1, grades = [88, 92, 80]}
student3 = Student {neptunID = 103, studentName = "Charlie", uni = univ2, grades = [75, 85, 82]}
student4 = Student {neptunID = 104, studentName = "David", uni = univ2, grades = [90, 87, 91]}
student5 = Student {neptunID = 105, studentName = "Eve", uni = univ3, grades = [80, 83, 88]}
student6 = Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [85, 89, 84]}
student7 = Student {neptunID = 103, studentName = "Charlie1", uni = univ2, grades = [75, 59, 82]}
student8 = Student {neptunID = 105, studentName = "Eve1", uni = univ3, grades = [80, 9, 8]}
 -------------------------------------------------------
{- 1. 10p Write a function which takes 2 lists of integers of the same length, and calculates the dot product of these two vectors. A dot product is the sum of the products of the corresponding  entries of the two lists. Parallel list comprehension must be used. Non-parallel solutions are also accepted but then max is 8 points. -}
--dotProd :: [Int] -> [Int] -> Int
--main = print $ dotProd [1, 2, 3] [4, 5, 6] -- 32 --main = print $ dotProd [-1, -2, -3] [4, -5, 6] -- -12 --main = print $ dotProd [0, 0, 0] [1, 2, 3] -- 0 --main = print $ dotProd [7] [3] -- 21 --main = print $ dotProd [1, 2, 3, 4, 5] [6, 7, 8, 9, 10] -- 130
 -------------------------------------------------------
{- 2. 10p We have a bulb, which has 2 switches connected to it serially. The switch is controlled by a stream of signals, where 1 means on, and 0 means off. The bulb is on if both switches are on. Write a function which takes in 2 streams of signals (string of '0's and '1's) and output the list of states of the bulb over the time ("On" or "Off"). Parallel list comprehension must be used. Non-parallel solutions are also accepted but then max is 8 points. -}
--lightStates :: String -> String -> [String]
--main = print $ lightStates "1100" "1010" -- ["On", "Off", "Off", "Off"] --main = print $ lightStates "1111" "1111" -- ["On", "On", "On", "On"] --main = print $ lightStates"0000" "1111" -- ["Off", "Off", "Off", "Off"] --main = print $ lightStates "1010" "0101" -- ["Off", "Off", "Off", "Off"]
 -------------------------------------------------------
{- 3. 10p Filter out the list of students who failed at least one course. A student fails a course if the grade is lower than 60. Return a list of Student name and their failing scores. -}
--failingStudents :: [Student] -> [(String, [Int])]
--main = print $ failingStudents [student1, student2, student7, student4, student8, student6]  -- [("Charlie1",[59]),("Eve1",[9,8])] --main = print $ failingStudents [student1, student2, student3] -- [] --main = print $ failingStudents [] -- []
 -------------------------------------------------------
{- 4. 30p Find the Player with the Highest Score in a Team. Write a function that takes a Team and a list of Player records, and finds the players with the highest total score in that team. If there are no players in the given team return empty list. To obtain the solution for this task, you will need: a. Filter players belonging to the given team. - 5pts b. Find the highest total score among players. - 5pts c. Calculate the maximum score from a list of players. - 5pts  d. Select players with the highest score. - 5pts e. Calculate the total score of a player. - 5pts  f. Put together, finding players with top scores. - 5pts -}
--topScorersInTeam :: Team -> [Player] -> [Player]
--main = print (topScorersInTeam team1 [player1, player2, player3, player4, player5, player6])  -- [Player {playerID = 2, playerName = "Bob", playerTeam = Team {teamName = "Team A", teamID = 1}, playerScores = [15,25,35]}] --main = print (topScorersInTeam team2 [player1, player2, player3, player4, player5, player6])  -- [Player {playerID = 4, playerName = "David", playerTeam = Team {teamName = "Team B", teamID = 2}, playerScores = [18,28,38]}] --main = print (topScorersInTeam team3 [player1, player2, player3, player4, player5, player6])  -- [Player {playerID = 6, playerName = "Frank", playerTeam = Team {teamName = "Team C", teamID = 3}, playerScores = [16,26,36]}] --main = print (topScorersInTeam team0 [])  -- []
-------------------------------------------------------
data Tree a = Leaf | Node a (Tree a) (Tree a) deriving (Show)
treeExample1 :: Tree Int 
treeExample1 = Node 5 (Node 3 (Node 7 Leaf Leaf) (Node 2 Leaf Leaf)) (Node 10 (Node 6 Leaf Leaf) (Node 1 Leaf Leaf))
treeExample2 :: Tree Int 
treeExample2 = Node 8 (Node 3 (Node 12 Leaf Leaf) (Node 2 Leaf Leaf)) (Node 15 (Node 5 Leaf Leaf) (Node 20 Leaf Leaf))
 -------------------------------------------------------
{- 5. 10p Write a function that takes a tree of integers and returns  a tuple of two integers. The first integer is the sum of the  even values in the tree and the second integer is the sum of  the odd values in the tree. Leaf counts as 0. -}
--evenSumOddSum :: Tree Int -> (Int, Int)
--main = print $ evenSumOddSum Leaf -- (0,0) --main = print $ evenSumOddSum (Node 11 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf)) -- (0,21) --main = print $ evenSumOddSum treeExample1 -- (18,16) --main = print $ evenSumOddSum treeExample2 --(42,23) --main = print $ evenSumOddSum (Node 10 (Node 8 (Node 7 (Node 11 Leaf Leaf) Leaf) (Node 6 Leaf Leaf)) (Node 5 Leaf (Node 7 Leaf Leaf))) -- (24,30) --main = print $ evenSumOddSum (Node 1 (Node 2 (Node 2 Leaf Leaf) (Node 2 Leaf Leaf)) (Node 5 (Node 3 (Node 1 Leaf Leaf) Leaf) (Node 4 Leaf Leaf))) -- (10,10)
 -------------------------------------------------------
{- 6. 10p Given an integer and a binary tree, write a function replaceNodes  that modifies the nodes in a binary tree with the following rules: Replace the value with -1 if it is divisible by n. Replace the value with 0 if it is greater than n. Retain the original value otherwise. -}
--replaceNodes :: Int -> Tree Int -> Tree Int
--main = print $ replaceNodes 5 treeExample1 -- Node (-1) (Node 3 (Node 0 Leaf Leaf) (Node 2 Leaf Leaf)) (Node (-1) (Node 0 Leaf Leaf) (Node 1 Leaf Leaf)) --main = print $ replaceNodes 5 treeExample2 -- Node 0 (Node 3 (Node 0 Leaf Leaf) (Node 2 Leaf Leaf)) (Node (-1) (Node (-1) Leaf Leaf) (Node (-1) Leaf Leaf))
 -------------------------------------------------------
{- 7. 10p Implement a custom Show instance for the Student record, which prints an introduction for the student in the format of: "I am <studentName> from <uni>. My average score is <average of grades>." If his grades > 90, add the sentence: "I am a very good student.". -}
-- Implementing the Show instance for Student

--main = print Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [95, 99, 84]}  -- I am Frank from University C. My average score is 92.66666666666667. I am a very good student. --main = print Student {neptunID = 106, studentName = "Bob", uni = univ3, grades = [85, 89, 84]}  -- I am Bob from University C. My average score is 86.0.
 -------------------------------------------------------
{- 8. 10p Implement the Eq instance for Product. Two products are considered equal if: they have the same name, the same price, and the same quantity in stock.
Implement the Eq instance for Customer. Two customers are considered equal if: They have the same name, the same contact email, and the same order history. -}
-- product1 = Product "Laptop" 999.99 10 
-- product2 = Product "Smartphone" 499.99 20 
-- product3 = Product "Headphones" 199.99 50
-- customer1 = Customer "Alice" "alice@example.com" [product1, product2] 
-- customer2 = Customer "Bob" "bob@example.com" [product2, product3] 
-- customer3 = Customer "Alice" "alice@example.com" [product1, product2]
-- data Product = Product { productName :: String, price :: Double, quantity :: Int }
-- data Customer = Customer { customerName :: String, contactEmail :: String, orderHistory :: [Product] }
-- Implementing the Eq instance for Product
--main = print (product1 == product2) -- Expected output: False --main = print (product1 == product1) -- Expected output: True
-- Implementing the Eq instance for Customer
--main = print (customer1 == customer2) -- Expected output: False --main = print (customer1 == customer3) -- Expected output: True -------------------------------------------------------

{-# LANGUAGE ParallelListComp #-}
--main :: IO ()
--data Team = Team {teamName :: String, teamID :: Int}
--data Player = Player {playerID :: Int, playerName :: String, playerTeam :: Team, playerScores :: [Int]}
-- team1 = Team {teamName = "Team A", teamID = 1}
-- team2 = Team {teamName = "Team B", teamID = 2}
-- team3 = Team {teamName = "Team C", teamID = 3}
-- team0 = Team {teamName = "Team C", teamID = 3}
-- player1 = Player {playerID = 1, playerName = "Alice", playerTeam = team1, playerScores = [10, 20, 30]}
-- player2 = Player {playerID = 2, playerName = "Bob", playerTeam = team1, playerScores = [15, 25, 35]}
-- player3 = Player {playerID = 3, playerName = "Charlie", playerTeam = team2, playerScores = [12, 22, 32]}
-- player4 = Player {playerID = 4, playerName = "David", playerTeam = team2, playerScores = [18, 28, 38]}
-- player5 = Player {playerID = 5, playerName = "Eve", playerTeam = team3, playerScores = [14, 24, 34]}
-- player6 = Player {playerID = 6, playerName = "Frank", playerTeam = team3, playerScores = [16, 26, 36]}
-- player7 = Player {playerID = 7, playerName = "John", playerTeam = team2, playerScores = [12, 22, 32]}
-- player8 = Player {playerID = 8, playerName = "Peter", playerTeam = team2, playerScores = [10, 5, 5]}
-- 1. 10p ***************************************
--You are given a number n. --Use a parallel generator to pair up the first n matching  --Uppercase and Lowercase letters. --Parallel list comprehension must be used. --Non-parallel solutions are also accepted but then max is 8 points. --Example: -- gen1 3 = [('A','a'),('B','b'),('C','c')]
--gen1 :: Int -> [(Char, Char)]
--main = print (gen1 0) -- [] --main = print (gen1 3) -- [('A','a'),('B','b'),('C','c')] --main = print (gen1 8) -- [('A','a'),('B','b'),('C','c'),('D','d'),('E','e'),('F','f'),('G','g'),('H','h')]
 -- 2. 10p ***************************************
--Write a function which takes a list of points (x, y) tuple --and a list of motion vector (dx, dy) tuple, --and shift all the points by the corresponding motion vector. --Parallel list comprehension must be used. --Non-parallel solutions are also accepted but then max is 8 points.
--shiftPoints :: [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)]
--main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(1, 1), (2, 2), (3, 3)]  -- Expected output: [(2, 3), (5, 6), (8, 9)] --main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(-1, -1), (-2, -2), (-3, -3)]  -- Expected output: [(0, 1), (1, 2), (2, 3)] --main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(0, 0), (0, 0), (0, 0)]  -- Expected output: [(1, 2), (3, 4), (5, 6)] --main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(1, -1), (-2, 2), (3, -3)]  -- Expected output: [(2, 1), (1, 6), (8, 3)] --main = print $ shiftPoints [(1, 2), (3, 4)] [(1, 1)]  -- Expected output: [(2, 3)]
 -- 3. 20p ***************************************
--Giving a list of players, find the team with the top total score. --Return the id of the top team.
--topTeam :: [Player] -> Int
--main = print $ topTeam [player1, player2, player3, player4, player5, player6] -- 3 --main = print $ topTeam [player1, player2, player3, player4] -- 2 --main = print $ topTeam [player1] -- 1 --main = print $ topTeam [] -- "No Team!"
 -- 4. 10p ***************************************
--Given a binary tree of Player records, write a function to extract  --all players who belong to a specific Team. --Return a list of tuples that contain the player name and ID.
--data Tree a = Node a (Tree a) (Tree a) | Leaf deriving (Show)
tree :: Tree Player 
tree = Node player1 (Node player2 Leaf (Node player3 Leaf Leaf)) (Node player4 (Node player5 Leaf Leaf) (Node player6 Leaf Leaf))
--playersFromTeam :: Team -> Tree Player -> [(Int, String)]
--main = print (playersFromTeam team1 tree) -- [(1,"Alice"),(2,"Bob")] --main = print (playersFromTeam team2 tree) -- [(3,"Charlie"),(4,"David")] --main = print (playersFromTeam team3 tree) -- [(5,"Eve"),(6,"Frank")]  --main = print (playersFromTeam team0 tree) -- [(5,"Eve"),(6,"Frank")]
 -- 5. 10p ***************************************
--We would like to keep the height of trees under 3.

 --Write a function, which takes a tree of integers, and --trim out part the branches that grows beyond the depth of 3. --(Leaf does not count as branch). -- e.g. Before trim -- j -- / \ -- b l -- / / \ -- d e c -- / \ -- m f -- / \ -- o g -- / -- n -- After trim -- j -- / \ -- b l -- / / \ -- d e c
--trim :: Tree Integer -> Tree Integer
--main = print $ trim (Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 (Node 7 Leaf Leaf) Leaf))(Node 3(Node 6(Node 8(Node 9 Leaf Leaf) Leaf) Leaf) Leaf)) -- -- Expected output: Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 (Node 6 Leaf Leaf) Leaf) --main = print $ trim (Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 (Node 6 Leaf Leaf) Leaf)) -- -- Expected output: Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 (Node 6 Leaf Leaf) Leaf) --main = print $ trim (Node 1 (Node 2 Leaf Leaf) (Node 3 Leaf Leaf)) -- -- Expected output: Node 1 (Node 2 Leaf Leaf) (Node 3 Leaf Leaf) --main = print $ trim (Node 1 Leaf Leaf) -- -- Expected output: Node 1 Leaf Leaf --main = print $ trim Leaf -- Expected output: Leaf
 -- 6. 10p ***************************************
--You are given a tree of char. You want to check if the tree is a palindrome. --In order to check it, you need to convert the tree to a string traversing inorder  --and check if the string is a palindrome. Leaf is an empty string. --For example, the below tree is a palindrome when reading inorder forward  --results in the String: "madam" and it is the same as reading backward: "madam". -- -- d -- / \ -- m a -- / \ / \ -- L a L m -- / \ / \ -- L L L L
--isPaliTree :: Tree Char -> Bool
--main = print $ isPaliTree (Node 'd' (Node 'm' Leaf (Node 'a' Leaf Leaf)) (Node 'a' Leaf (Node 'm' Leaf Leaf))) -- True --main = print $ isPaliTree (Node 'a' (Node 'a' (Node 'b' Leaf Leaf) (Node 'n' Leaf Leaf)) (Node 'n' Leaf (Node 'a' Leaf Leaf))) -- False --main = print $ isPaliTree (Node 'e' (Node 'a' (Node 'r' Leaf Leaf) (Node 'c' Leaf Leaf)) (Node 'a' (Node 'c' Leaf Leaf) (Node 'r' Leaf Leaf))) -- True --main = print $ isPaliTree (Node 'c' (Node 'b' (Node 'a' Leaf Leaf) Leaf) (Node 'd' (Node 'c' Leaf Leaf) (Node 'a' Leaf Leaf))) -- False
 -- 7. 10p ***************************************
-- Define a binary tree BTree type of BNode and BLeaf -- and create a custom Show instance that outputs  -- the tree in a specific format. The format is as follows: -- The output for each leaf node should be Leaf: value. -- The output for each node should be Node: (left subtree) -> value -> (right subtree). -- You should format the tree such that: -- Leaf nodes display only their value, prefixed with Leaf:. -- Node values should display the left and right subtree recursively,  -- using the format described above.
-- writing BTree
-- instancing show
--t1, t2 :: BTree Int --t1 = BNode (BNode (BLeaf 1) 2 (BLeaf 3)) 4 (BLeaf 5) --t2 = BNode (BNode (BLeaf 6) 20 (BLeaf 5)) 10 (BNode (BLeaf 1) 8 (BLeaf 2))
--main = print t1 --Node: (Node: (Leaf: 1) -> 2 -> (Leaf: 3)) -> 4 -> (Leaf: 5) --main = print (BNode (BLeaf 1) 10 (BLeaf 2)) -- Node: (Leaf: 1) -> 10 -> (Leaf: 2) --main = print t2 -- Node: (Node: (Leaf: 6) -> 20 -> (Leaf: 5)) -> 10 -> (Node: (Leaf: 1) -> 8 -> (Leaf: 2)) --main = print (BLeaf 3) -- Leaf: 3
 -- 8. 10p ***************************************
--Implement a custom Show instance for the Player record, --where it prints the player in the format of: --I am <playerName> from <playerTeam>, my average score is <average of scores> which is <x>. --x = "bad" if average < 20, otherwise "quite good".
-- instancing show for players
--main = print player1 -- I am Alice from Team A. My average score is 20.0, which is quite good --main = print [player1, player2, player3] --main = print [player7, player8] -- [I am John from Team B, my average score is 22.0 which is quite good, -- I am Peter from Team B, my average score is 6.666666666666667 which is bad]
 -- 9. 10p ***************************************
--This question has two parts, 5p each. --Part 1 - Write an instance Eq to determine whether two players are equal.

 --Two players are equal if they have the same total score, and they are --in the same team. --Part 2 - Write an instance Eq to determine whether two teams are equal.  --Two teams are equal if they have the same id and name.
-- Part 1: Eq instancing for players
--main = print $ player1 == player2 -- False --main = print $ player3 == player7 -- True
-- Part 2: Eq instance for Student
--main = print $ team3 == team0 -- True --main = print $ team1 == team2 -- False
 -- **********************************************
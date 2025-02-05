--main ::IO()
{-
1. 
a. Create Person type that has a name of type string and age of type int.
b. Create a custom show instance that formats the Person type as "Name: [name], Age: [age]".
c. Implement the Eq instance for the Person type. Two Person instances are equal if both their name and age fields are equal.
-}

data Person = Person { name :: String, age :: Int }

instance Show Person where
    show (Person n a) = "Name: " ++ n ++ ", Age: " ++ show a

instance Eq Person where
    (Person n1 a1) == (Person n2 a2) = n1 == n2 && a1 == a2

-- main = print (Person "Alice" 25) -- Name: Alice, Age: 25
-- main = print (Person "Alice" 30) == (Person "Alice" 30) -- True
-- main = print (Person "Alice" 30) == (Person "Bob" 25) -- False

--2. Define a recursive custom list type that has data of anytype
data MyList a = Empty | Cons a (MyList a)

{-
3.
a. Create a custom Show instance that prints the custom list in the following format: el1:el2:[]
ex: 1 : 2 : 3 : []
b. Implement the Eq instance for the MyList type. Two custom lists are equal if they have the same elements in the same order.
-}
instance Show a => Show (MyList a) where
    show Empty = "[]"
    show (Cons x xs) = show x ++ " : " ++ show xs


instance Eq a => Eq (MyList a) where
    Empty == Empty = True
    (Cons x xs) == (Cons y ys) = x == y && xs == ys
    _ == _ = False
-- main = print (Cons 1 (Cons 2 (Cons 3 Empty))) -- "1 : 2 : 3 : []"
-- main = print (Cons 1 (Cons 2 (Cons 3 Empty))) == (Cons 1 (Cons 2 (Cons 3 Empty))) -- True
-- main = print (Cons 1 (Cons 2 Empty)) == (Cons 1 (Cons 3 Empty)) -- False

--4. Display a binary tree with formatting for its structure.
--a. Define a Binary Tree type
data BTree a = BLeaf a | BNode (BTree a) (BTree a)

--b. Custom Show instance
instance Show a => Show (BTree a) where
    show (BLeaf x) = "Leaf " ++ show x
    show (BNode left right) = "Node (" ++ show left ++ ") (" ++ show right ++ ")"
-- main = print (Node (Leaf 1) (Node (Leaf 2) (Leaf 3))) -- "Node (Leaf 1) (Node (Leaf 2) (Leaf 3))"

-- c. Implement the Eq instance for the Tree type. Two trees are equal if their structures and all their elements are equal.
instance Eq a => Eq (BTree a) where
    (BLeaf x) == (BLeaf y) = x == y
    (BNode l1 r1) == (BNode l2 r2) = l1 == l2 && r1 == r2
    _ == _ = False

-- main = print (Leaf 1) == (Leaf 1) -- True
-- main = print (Node (Leaf 1) (Leaf 2)) == (Node (Leaf 1) (Leaf 3)) -- False

-- 5. Define an employee type that has empName as a string empAge as int and empAddress of Address type.
-- The address type has a city of types string and a postalCode of type string as well.
data Address = Address { city :: String, postalCode :: String }
data Employee = Employee { empName :: String, empAge :: Int, empAddress :: Address }

-- 6. 
-- a. create a custom Show instance that converts an employee into a string matching the following format:
-- Name, Age: Value, Address: City (postalCode)
-- ex: Bob, Age: 40, Address: London (SW1A 1AA)
-- b.  Implement the Eq instance for Address and Employee types. Two addresses are equal if their city and postalCode are the same. Two employees are equal if all their fields are equal.
instance Show Address where
    show (Address c p) = c ++ " (" ++ p ++ ")"

instance Show Employee where
    show (Employee n a addr) = n ++ ", Age: " ++ show a ++ ", Address: " ++ show addr

-- Implement the Eq instance for Address
instance Eq Address where
    (Address c1 p1) == (Address c2 p2) = c1 == c2 && p1 == p2

-- Implement the Eq instance for Employee
instance Eq Employee where
    (Employee n1 a1 addr1) == (Employee n2 a2 addr2) = n1 == n2 && a1 == a2 && addr1 == addr2

-- main = print ((Employee "Alice" 30 (Address "Paris" "75000")) == (Employee "Alice" 30 (Address "Paris" "75000"))) -- True
-- main = print ((Employee "Alice" 30 (Address "Paris" "75000")) == (Employee "Bob" 25 (Address "London" "SW1A"))) -- False
-- main = print (Employee "Bob" 40 (Address "London" "SW1A 1AA"))

-- 7. Define an enumerated type TrafficLight that has the following values: Red, Yellow and Green.
data TrafficLight = Red | Yellow | Green

--8. 
-- a. Create a  custom show instance that converts a TrafficLight into a string according to the following: 
-- Red -> "Red Light - Stop"
-- Yellow -> "Yellow Light - Caution"
-- Green -> "Green Light - Go"
-- b.  Implement the Eq instance for the TrafficLight type. Two traffic lights are equal if they represent the same state.

instance Show TrafficLight where
    show Red = "Red Light - Stop"
    show Yellow = "Yellow Light - Caution"
    show Green = "Green Light - Go"

instance Eq TrafficLight where
    Red == Red = True
    Yellow == Yellow = True
    Green == Green = True
    _ == _ = False

-- main =  print Red -- Red Light - Stop
-- main = print (Red == Red) -- True
-- main = print (Red == Green) -- False



-- Q type for rational values and its instance operations
-- rational numbers: numerator and denominator

data Q = Q { num :: Int , denom :: Int } -- deriving Show

-- simplify makes the numerator and denominator relative primes and
-- the denominator positive 
-- there is only one such representation of a rational number

simplify (Q { num , denom }) =
  Q { num = sgn * abs num `div` n , denom = abs denom `div` n }
  where
    n = gcd (abs num) (abs denom)
    sgn | num>0 && denom>0 || num<0 && denom<0 = 1
        | num<0 && denom>0 || num>0 && denom<0 = -1
        | num == 0 && denom /= 0               = 0
        | denom == 0                           = error "denominator cannot be 0"

-- to make printing work, we need to write "deriving Show" above, or
-- define our own Show instance

instance Show Q where
  show :: Q -> String
  show r = show (num r) ++ "/" ++ show (denom r)

--main = print $ Q 4 6 -- 4/6
--main = print $ simplify $ Q 4 6 -- 2/3
--main = print $ Q (-1) 2 -- -1/2
--main = print $ Q 2 3 -- 2/3
--main = print $ Q 2 8 -- 2/8



-- Num typeclass

-- ghci> :i Num

instance Num Q where
  (+) :: Q -> Q -> Q
  Q a b + Q c d = simplify $ Q (a*d + c*b) (b*d)
  (*) :: Q -> Q -> Q
  Q a b * Q c d = simplify $ Q (a*c) (b*d)
  abs :: Q -> Q
  abs (Q a b)   = Q (abs a) (abs b)
  fromInteger :: Integer -> Q
  fromInteger a = Q (fromInteger a) 1
  negate :: Q -> Q
  negate (Q a b) = simplify $ Q (-a) b
  signum :: Q -> Q
  signum         = flip Q 1 . signum . num . simplify
  
--main = print $ Q 1 3 + Q 4 3 -- 5/3
--main = print $ Q 5 3 * Q 2 3 -- 10/9
--main = print $ abs $ Q 1 3 -- 1/3
--main = print $ abs $ Q 1 (-3) -- 1/3
--main = print (fromInteger 3 :: Q) -- 3/1
--main = print $ negate (Q 1 2) -- -1/2
--main = print $ Q 1 3 - Q 2 3 -- -1/3
--main = print $ signum $ Q 1 3 -- 1/1
--main = print $ signum $ Q (-1) 3 -- -1/1
--main = print $ signum $ Q 0 3 -- 0/1


-- framp
-- Implement the fmap function for MyList.
instance Functor MyList where
    fmap _ Empty = Empty
    fmap f (Cons x xs) = Cons (f x) (fmap f xs)

-- Test cases
-- main = do
--     print (fmap (*2) (Cons 1 (Cons 2 (Cons 3 Empty))))  
--     -- 2 : 4 : 6 : []
--     print (fmap (++ "!") (Cons "Hi" (Cons "Hello" Empty)))  
--     -- "Hi!" : "Hello!" : []

-- Implement the fmap function for tree type.
instance Functor BTree where
    fmap f (BLeaf x) = BLeaf (f x)
    fmap f (BNode l r) = BNode (fmap f l) (fmap f r)

-- Test cases
-- main = do
--     let tree = Node (Leaf 1) (Node (Leaf 2) (Leaf 3))
--     print (fmap (*2) tree)  
    -- Should print: Node (Leaf 2) (Node (Leaf 4) (Leaf 6))
    -- print (fmap (++ " tree") (Node (Leaf "small") (Leaf "big")))  
    -- Should print: Node (Leaf "small tree") (Leaf "big tree")


-- Implement the fmap function for TrafficLight2
data TrafficLightWithValue a = Red1 a | Yellow1 a | Green1 a
  deriving (Show)

instance Functor TrafficLightWithValue where
    fmap f (Red1 x) = Red1 (f x)
    fmap f (Yellow1 x) = Yellow1 (f x)
    fmap f (Green1 x) = Green1 (f x)

-- Test cases
-- main = do
--     print (fmap (*10) (Red1 3))    -- Should print: Red 30
--     print (fmap (++ " caution") (Yellow1 "Proceed with"))  
    -- Should print: Yellow "Proceed with caution"

-- more trees 


{-
//Task 1

You are given a tree and your task will be to represent it in a string format. To read it easier
we will use different types of parantheses, in the next order "()" "<>" "{}" "[]". So, for
each level of the tree, different parantheses are used. Once we run out, the parantheses cycle
repeats. Instead of the leaves, insert "|".

Example: tree_to_paranth = Node (Node (Node (Node (Node Leaf Leaf) Leaf) Leaf) Leaf) Leaf -> "(<{[(||)|]|}|>|)"
		                     ^     ^     ^     ^     ^ 
						     |     |     |     |     |
		                    ()    <>    {}    []    ()
-}

-- Parentheses for levels
o = "(<{["
c = ")}>]"

data TreeB = NodeB TreeB TreeB | LeafB

-- Recursive function to traverse the tree and build the string
treeToPara :: TreeB -> Int -> String
treeToPara LeafB _ = "|"
treeToPara (NodeB l r) n =
    [o !! len] ++ treeToPara l (n + 1) ++ treeToPara r (n + 1) ++ [c !! len]
  where
    len = n `mod` 4

-- Wrapper function to start with level 0
tree_to_paranth :: TreeB -> String
tree_to_paranth t = treeToPara t 0

-- main = do
--     let tree1 = NodeB (NodeB LeafB LeafB) LeafB
--     print $ tree_to_paranth tree1 -- Output: "(<||>|)"
    
--     let tree2 = NodeB (NodeB (NodeB (NodeB (NodeB LeafB LeafB) LeafB) LeafB) LeafB) LeafB
--     print $ tree_to_paranth tree2 -- Output: "(<{[(||)|]|>|}|)"


{-
 You are given a binary tree. Implement the
 function that will cut nodes that have
 a depth greater or equal than the given one.
 For example, if you have a tree:

 1 depth-0

 / \ 

 2 3 depth-1

 / \

 4 5 depth-2

 / \ / \

 L L L L

 And you cut it on 2, the result will be:

 1 depth-0

 / \ 

 2 3 depth-1

 / \ / \

 L L L L

 NOTE: "L" represents Leaf

 -}

data Tree a = Node a (Tree a) (Tree a) | Leaf
  deriving (Show, Eq)

tree1 :: Tree Int
tree1 = (Node 4 (Node 10 (Node 6 Leaf Leaf)(Node 11 Leaf Leaf)) (Node 20 (Node 12 Leaf Leaf) Leaf))


tree2 :: Tree Int
tree2 = (Node 5 (Node 10 (Node 31 (Node 1 Leaf Leaf) Leaf) Leaf) (Node 17 (Node 31 (Node 14 (Node 12 Leaf Leaf) Leaf) Leaf) (Node 11 Leaf Leaf) ))

tree3 :: Tree Int
tree3 = (Node 12 (Node 11 (Node 11 (Node 32 Leaf Leaf) Leaf) Leaf) (Node 4 (Node 17 (Node 5 (Node 7 Leaf Leaf) Leaf) Leaf) (Node 3 Leaf (Node 4 Leaf Leaf)) ))

tree4 :: Tree Int
tree4 = (Node 7 (Node 11 tree1 tree2) (Node 5 tree3 tree2))

tree5 :: Tree Int
tree5 = Node 1 tree3 tree4



cutTheTree :: Tree a -> Int -> Tree a
cutTheTree Leaf _ = Leaf
cutTheTree (Node value left right) 0 = Leaf
cutTheTree (Node value left right) depth =
  Node value (cutTheTree left (depth - 1)) (cutTheTree right (depth - 1))


-- main = print( cutTheTree tree1 2) -- (Node 4 (Node 10 Leaf Leaf) (Node 20 Leaf Leaf))

-- main = print( cutTheTree tree1 1) -- (Node 4 Leaf Leaf)

-- main = print(cutTheTree tree1 0) -- Leaf

-- main = print(cutTheTree tree1 100) -- (Node 4 (Node 10 (Node 6 Leaf Leaf) (Node 11 Leaf Leaf)) (Node 20 (Node 12 Leaf Leaf) Leaf))

-- main = print(cutTheTree tree5 5 ) --  (Node 1 (Node 12 (Node 11 (Node 11 (Node 32 Leaf Leaf) Leaf) Leaf) (Node 4 (Node 17 (Node 5 Leaf Leaf) Leaf) 

-- // (Node 3 Leaf (Node 4 Leaf Leaf)))) (Node 7 (Node 11 (Node 4 (Node 10 Leaf Leaf) (Node 20 Leaf Leaf)) (Node 5 

-- // (Node 10 Leaf Leaf) (Node 17 Leaf Leaf))) (Node 5 (Node 12 (Node 11 Leaf Leaf) (Node 4 Leaf Leaf)) (Node 5 (Node 10 Leaf Leaf) (Node 17 Leaf Leaf)))))

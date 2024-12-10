--module Main where

import Data.List (sort)

--main :: IO ()

-- Tree definition
data Tree a = Node a (Tree a) (Tree a) | Leaf
    deriving (Show)

-- Test trees
tree1 :: Tree Int
tree1 = Node 4 (Node 10 (Node 6 Leaf Leaf) (Node 11 Leaf Leaf)) 
              (Node 20 (Node 12 Leaf Leaf) Leaf)

tree2 :: Tree Int
tree2 = Node 5 (Node 10 (Node 31 (Node 1 Leaf Leaf) Leaf) Leaf) 
              (Node 17 (Node 31 (Node 14 (Node 12 Leaf Leaf) Leaf) Leaf) (Node 11 Leaf Leaf))

tree3 :: Tree Int
tree3 = Node 12 (Node 11 (Node 11 (Node 32 Leaf Leaf) Leaf) Leaf) 
               (Node 4 (Node 17 (Node 5 (Node 7 Leaf Leaf) Leaf) Leaf) (Node 3 Leaf (Node 4 Leaf Leaf)))

tree4 :: Tree Int
tree4 = Node 7 (Node 11 tree1 tree2) (Node 5 tree3 tree2)

tree5 :: Tree Int
tree5 = Node 1 tree3 tree4

{-
    Task 0 :
    Function to create mirror image of a tree.

    Example:
          1                         1
        /   \     reverse        /    \
      2      3    ------->     3      2
     / \    / \               / \    / \ 
    4  5   6  Leaf           Leaf 6 5   4
    Flip all the left and right subtrees.
    
-}

--reverseTree :: Tree a -> Tree a


{-
	Task 1 :
    
    Given a key determine in which level it is stored in the Tree Int.
	
	
		 07           <- Level 0
	   /   \          
	 02	    20        <- Level 1
	 /\	    / \ 
	01 04  10 30 	  <- Level 2 

	If the value is not in the tree then return -1
-}


--getLevel :: Int -> Tree Int -> Int

-- main = do
--     -- Tree level tests
--     print $ getLevel 5 tree2  -- 0
--     print $ getLevel 10 tree1 -- 1
--     print $ getLevel 55 tree1 -- -1
--     print $ getLevel 31 tree2 -- 2

{- 
    Task 2: 
    Count nodes with exactly 3 grandchildren

    Ex.:  1
        /   \
      2      3
     / \    / \
    4 5   6  Leaf
    The first node (1) has exactly 3 grandchildrens(4,5,6), so it's a 'good' node.
-}


--countTripleParents :: Tree Int -> Int

-- main = do    
--     -- Triple parents tests
--     print $ countTripleParents tree1 -- 1
--     print $ countTripleParents tree2 -- 1
--     print $ countTripleParents tree3 -- 1
--     print $ countTripleParents tree4 -- 4
--     print $ countTripleParents tree5 -- 5

{- Task 3. 
    Count nodes at specific level 
    Example:

        Tree2
                5       --level 1 has 1 element  --  print $ nodeCountLevel tree2 1 --1
            10    17    --level 2 has 2 elements --  print $ nodeCountLevel tree2 2 --2
        31  L  31  11   --level 3 has 3 elements --  print $ nodeCountLevel tree2 3 --3
      1 L    14 L L L   --level 4 has 2 elements --  print $ nodeCountLevel tree2 4 --2
    L L     L L

-}

--nodeCountLevel :: Tree a -> Int -> Int

-- main = do
--     print $ nodeCountLevel tree1 5  -- 0
--     print $ nodeCountLevel tree2 4  -- 2
--     print $ nodeCountLevel tree3 3  -- 3
--     print $ nodeCountLevel tree4 1  -- 1
--     print $ nodeCountLevel tree5 2  -- 2


-- Item type
data Item = Item {
    key :: String,
    value :: Int
} deriving (Show, Eq)

-- Sample Item tree
t1 :: Tree Item
t1 = Node (Item "a" 5) 
         (Node (Item "b" 2) 
              (Node (Item "x" 10) 
                   (Node (Item "h" 3) Leaf Leaf) 
                   Leaf)
              (Node (Item "y" 7) Leaf Leaf))
         (Node (Item "d" 8) 
              (Node (Item "e" 15) 
                   (Node (Item "g" 12) Leaf Leaf) 
                   Leaf)
              (Node (Item "f" 9) Leaf Leaf))

{-  
    Task a.
    Given a tree of Item. Find the key of the item that has the maximum value.
	All the key inside tree are unique.
-}


--searchMaxKey :: Tree Item -> String

-- main = do
    
--     putStrLn "Testing searchMaxKey:"
--     print $ searchMaxKey t1 --"e"
    
{-
    Task b:  
    Union of two item lists

    Union means taking all the items from both lists, if items has the same key, taking the larger value.
	The final result should be distinct in term of key and can be any order.
-} 

-- Sample items for testing
i1 = Item "abc" 13
i2 = Item "def" 25
i3 = Item "ghi" 8
i4 = Item "jkl" 15
i5 = Item "mno" 20
it1 = Item "abc" 4
it2 = Item "def" 30
it3 = Item "pqr" 23
list1 = [i1,i2,i3,i4,i5]
list2 = [it1,it2,it3]


--unionItems :: [Item] -> [Item] -> [Item]

-- main = do
--     putStrLn "\nTesting unionItems:"
--     print $ unionItems list1 list2


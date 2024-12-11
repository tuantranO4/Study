{--data TypeName = Constructor1 Type1 Type2 ...
              | Constructor2 Type3 Type4 ...
              | ...

------------------------
EG:
data Shape = Circle Double | Rectangle Double Double
myCircle :: Shape
myCircle = Circle 5.0

myRectangle :: Shape
myRectangle = Rectangle 4.0 6.0

area :: Shape -> Double
area (Circle r) = pi * r * r
area (Rectangle w h) = w * h
------------------------

--}

data Tree a = Node a (Tree a) (Tree a) --constructor1 with param Tree a
            | Leaf                  --constructor2 with no param
            deriving (Show)
        

tree1 :: Tree Int
tree1 = Node 7 Leaf Leaf
-- Tree1 see link: http://graphonline.ru/en/?graph=RDODcKkbEjpzIbIh
tree2 :: Tree Int
tree2 = Node 0 
            (Node 1 
                (Node 3 Leaf Leaf) 
                (Node 4 Leaf Leaf))
            (Node 2 
                (Node 5 Leaf Leaf) 
                (Node 6 Leaf Leaf))

-- Tree2 see link: http://graphonline.ru/en/?graph=apYgfCbqYeaQRHNL

sizeT :: Tree a -> Int
sizeT Leaf = 0
sizeT (Node x le ri) = 1 + sizeT le + sizeT ri
--main = print (sizeT tree2) --7

depthT :: Tree a -> Int
depthT Leaf = 0
depthT (Node _ le ri) = 1 + max (depthT le) (depthT ri) 
--main = print (depthT tree2) --3

sumT :: Tree Int -> Int
sumT Leaf = 0
sumT (Node x left right) = x + sumT left + sumT right
--main = print (sumT tree2) --21

-- a. Inorder traversal: Left, Root, Right
inorder :: Tree a -> [a]
inorder Leaf = []
inorder (Node x left right) = inorder left ++ [x] ++ inorder right
--main = print $ inorder tree2 --[3,1,4,0,5,2,6]

preorder :: Tree a -> [a]
preorder Leaf = []
preorder (Node x left right) = [x] ++ preorder left ++ preorder right
--main = print $ preorder tree2 --[0,1,3,4,2,5,6]

-- Example tree with five 3's
tree3fiveTimes :: Tree Int
tree3fiveTimes = Node 3 (Node 3 Leaf (Node 3 Leaf (Node 2 Leaf Leaf))) 
                       (Node 3 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf))

-- Two implementations
task2 :: Tree Int -> Int -> Int
task2 Leaf _ = 0
task2 (Node x left right) a
  | x == a = 1 + task2 left x + task2 right x
  | otherwise = task2 left x + task2 right x
--main =print $ task2 tree3fiveTimes 3 -- 5

-- 6. Create list of tuples (node, left child, right child) for odd numbers

extractN :: Tree Int -> Int
extractN Leaf = -1
extractN (Node x _ _) = x

task3 :: Tree Int -> [(Int, Int, Int)]
task3 Leaf = []
task3 (Node x l r)
    |odd x = (x, extractN l, extractN r)  : task3 l ++ task3 r -- : is the list "cons" operator. It adds an element (the tuple for the current node) to the front of a list.
    |otherwise  = task3 l ++ task3 r
--main = print $ task3 tree2 --[(1,3,4),(3,-1,-1),(5,-1,-1)]

searchT :: Tree Int -> Int -> Bool
searchT Leaf _ = False
searchT (Node x l r) a
    | x == a = True 
    | otherwise = searchT l a || searchT r a
--main = print $ searchT tree2 10 --False

exNode :: Tree Int -> Int
exNode Leaf = 0
exNode (Node x _ _) = x -- It's similar to extractN, but leaf is counted as 0.

f8 :: Tree Int -> Int -> Int
f8 Leaf _ = 0
f8 (Node x l r) a
    |x==a = exNode l + exNode r
    |otherwise = 0
--main =  print $ f8 (Node 2 Leaf Leaf) 3  -- 0
--main =  print $ f8 (Node 3 (Node 1 Leaf Leaf) (Node 1 Leaf Leaf)) 3  -- 2

replace :: Int -> Tree Int -> Tree Int
replace a Leaf = Leaf
replace a (Node x l r) 
    |x==a = Node 0 (replace a l) (replace a r)
    |otherwise = Node x (replace a l) (replace a r)

treec :: Tree Int
treec = Node 4 (Node 3 (Node 1 Leaf Leaf) (Node 3 Leaf Leaf)) 
              (Node 6 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf))

--main =  print $ replace 3 treec 


data Person = Person {
    name :: String,
    birthday :: (Int, Int, Int)
} deriving (Show)

over18 :: (Int, Int, Int) -> Bool
over18 (year, _, _)
    | year >= 2006 = False
    | otherwise = True

addString :: Person -> Person
addString p = p { name = name p ++ "_over18" }

updateName :: Tree Person -> Tree Person
updateName Leaf = Leaf
updateName (Node p l r)
    | over18 (birthday p) = Node (addString p) (updateName l) (updateName r) --birthday p: access birthday elem in data | over18 (elem p): apply function to elem
    | otherwise           = Node p (updateName l) (updateName r)

--main = print $ updateName t1
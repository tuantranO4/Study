{-
    Write a function replaceWithOccurrence that takes a list of integers and returns a new list
    where each element is replaced with the number of times it occurs in the list.
-}


replaceWithOccurrence :: [Int] -> [Int]
replaceWithOccurrence xs = map (\x -> count x xs) xs
  where
    count x = length . filter (== x)
main = print (replaceWithOccurrence [1, 2, 2, 3, 4, 4, 5])  -- [1,2,2,1,2,2,1]
-- main = print (replaceWithOccurrence [1, 1, 1])  -- [3,3,3]
-- main = print (replaceWithOccurrence [])  -- []
-- main = print (replaceWithOccurrence [5, 6, 5, 6, 7, 8])  -- [2,2,2,2,1,1]
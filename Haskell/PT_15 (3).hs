{- Progress Task -}

{- Your neptun code : -}

{-
    Given a list of integers, return True if the list is symmetrical after removing all numbers 
    that are divisible by 5; otherwise, return False.

    Example:
    Input: [5, 1, 3, 5, 3, 1, 5]
    Output: True
-}
checkSymmetryAfterRemoving5s :: [Int] -> Bool
checkSymmetryAfterRemoving5s xs = filteredList == reverse filteredList
  where
    filteredList = [x | x <- xs, x `mod` 5 /= 0]


--main = print(checkSymmetryAfterRemoving5s [5, 1, 3, 5, 3, 1, 5]) -- True
--main = print(checkSymmetryAfterRemoving5s [10, 2, 3, 5, 2, 10]) -- True
-- main = print(checkSymmetryAfterRemoving5s [1, 2, 5, 4, 3, 2, 1]) -- False
-- main = print(checkSymmetryAfterRemoving5s [5, 5, 5, 5, 5, 5, 5, 1, 2]) -- False
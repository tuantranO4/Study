concatHeads :: [a] -> [a] -> [a]
concatHeads xs ys
    | null xs || null ys = error "Null list"
concatHeads xs ys = head xs ++ head ys
main = print (concatHeads [1, 2, 3] [4, 5, 6]) -- [1, 4]

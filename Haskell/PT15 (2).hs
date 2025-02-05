{-
 Generate Triple tuples of integers with the below conditions.
 Given three lists of integers. generate triplets (x, y, z) such that:
 x is from the first list, y is from the second list and z is from the third list.
 x, y and z can form a right angled triangle and x < y < z should be satisfied.
 If the one of the sides is negative or zero, you cannot form a triangle.
-}

f :: [Int] -> [Int] -> [Int] -> [(Int,Int , Int)]
f [] [] [] = [(0,0,0)]
f xs ys zs = [(x,y,z) | x<-xs, y<-ys, z<-zs, x^2+y^2==z^2,x<=y]
-- Test cases
main = print $ f [1..10] [1..10] [1..10] -- [(3,4,5),(6,8,10)]
-- main = print $ f [-10,-9..10] [-10,-9..10] [-10,-9..10] -- [(3,4,5),(6,8,10)]
-- main = print $ f [1..100] [1..100] [1..100] -- [(3,4,5),(5,12,13),(6,8,10),(7,24,25),(8,15,17),(9,12,15),(9,40,41),(10,24,26),(11,60,61),(12,16,20),(12,35,37),(13,84,85),(14,48,50),(15,20,25),(15,36,39),(16,30,34),(16,63,65),(18,24,30),(18,80,82),(20,21,29),(20,48,52),(21,28,35),(21,72,75),(24,32,40),(24,45,51),(24,70,74),(25,60,65),(27,36,45),(28,45,53),(28,96,100),(30,40,50),(30,72,78),(32,60,68),(33,44,55),(33,56,65),(35,84,91),(36,48,60),(36,77,85),(39,52,65),(39,80,89),(40,42,58),(40,75,85),(42,56,70),(45,60,75),(48,55,73),(48,64,80),(51,68,85),(54,72,90),(57,76,95),(60,63,87),(60,80,100),(65,72,97)]
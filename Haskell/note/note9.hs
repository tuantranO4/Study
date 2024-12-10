-- 1. Create a Employee Record, which has fields EmployeeID :: Int, companyName :: String, and programmer :: Bool

data Employee = Employee {
    employeeID :: Int,
    companyName :: String,
    programmer :: Bool
} deriving (Show)
--custom data set
--deriving (Show): allows you to print Employee records as strings using show

emp1 :: Employee
emp1 = Employee 123 "Twitter" True

isProgrammer :: Employee -> String
isProgrammer emp 
    | programmer emp = "Yes"
    | otherwise = "No"
--main = print (employeeID emp1)  -- Accesses the employeeID field
--main = print $ isProgrammer emp1 -- "Yes" --$ is just a separator ()

changeCompanyName :: Employee -> Employee
changeCompanyName emp
    | programmer emp && companyName emp == "Twitter" = emp {companyName = "X"} --True && company==Twitter
    | otherwise = emp

----------------------------------------------
data Student = Student {
    neptunID :: Int,
    uni :: String,
    grades :: [Int]
} deriving (Show)

students :: [Student]
students = [Student {neptunID=1,uni="Elte",grades=[]}, Student {neptunID=2,uni="BME",grades=[5,5,5]}, Student {neptunID=3,uni="Corvinus",grades=[5,5,5,5]}]

moreThanNGrades :: [Student] -> Int -> [(Int, String)]
moreThanNGrades list n = [ (neptunID x, uni x) | x<-list, length (grades x) > n ] 
--also x: is a tuple extract from list -> neptunID x = neptunID Student

-- main = print $ moreThanNGrades students 1 -- [(2,"BME"),(3,"Corvinus")]

getAvgGrade :: Student -> Float
getAvgGrade st 
    | null (grades st) = 0
    | otherwise = fromIntegral (sum (grades st)) / fromIntegral (length (grades st))
--main = print $ getAvgGrade (students!!2) --!! used to index a list. (list !! n) gives you the element at index n

getBestStudents :: [Student] -> [Student]
getBestStudents students = [ x | x<-students, getAvgGrade x == highestAvgGrade ]
    where
        highestAvgGrade = maximum [getAvgGrade x | x<-students]

---------------------------------------------------------------------
data Country = Country {
    countryName :: String,
    capital :: String
} deriving (Show)
 
data Continent = Continent {
    contName :: String,
    countries :: [Country]
} deriving (Show)

-- Sample countries
macedonia = Country "Macedonia" "Skopje"
hungary = Country "Hungary" "Budapest"
spain = Country "Spain" "Madrid"
brazil = Country "Brazil" "Brasilia"
chile = Country "Chile" "Santiago"
argentina = Country "Argentina" "Buenos Aires"
china = Country "China" "Beijing"
india = Country "India" "New Delhi"
--Sample continents
europe = Continent "Europe" [macedonia, hungary, spain]
asia = Continent "Asia" [china, india]
southAmerica = Continent "South America" [argentina, brazil, chile]
 

-- Helper functions
isPrime :: Int -> Bool
isPrime x = length [y | y <- [1..x], x `mod` y == 0] == 2 --prime number: when the amount of divisor is 2
 
checkI :: Country -> Bool
checkI c = isPrime (length $ filter (=='i') $ capital c) -- $ just a parenthesis
--checkI c = isPrime (length (filter (=='i') (capital c)))
 
-------------
--evens :: [Int] -> [Int]
-- evens xs = filter even xs
--Usage:
-- main = print (evens [1, 2, 3, 4, 5, 6])  -- Output: [2, 4, 6]
-------------

checkCount :: Continent -> Bool
checkCount c = any checkI (countries c) --if at least one element in a list satisfies a given condition (predicate) -> true
--countries c is a field of Continent. we check if exist Prime number of 'i' in capital name

-- Main function
continentsPrimeI :: [Continent] -> [String]
continentsPrimeI = map contName . filter checkCount --map f . g : we do g first (aka check count), then f (aka extract contName).
--continentsPrimeI continents = map contName (filter checkCount continents)
main = print $ continentsPrimeI [europe, asia] -- ["Asia"]

--------------------------
data APlayer = APlayer {
    playerName :: String,
    skillLevel :: Int
}
shouldWePlay :: [APlayer] -> APlayer -> Bool
shouldWePlay players (APlayer gkName gkSkill) =
    let scoredPenalties = length [APlayer | (APlayer _ skill) <- players, skill >= gkSkill]
    in scoredPenalties >= 3 --true/false


------------------SYNTAX------------------

{--
data TypeName = ConstructorName {
    field :: Type1,
    field :: Type2,
    ...
}deriving (Show, Eq, Ord)
--}

--LIST OF CUSTOM DATA TYPE--
-- students :: [Student]
-- students = [Student {neptunID=1,uni="Elte",grades=[]}, Student {neptunID=2,uni="BME",grades=[5,5,5]}, Student {neptunID=3,uni="Corvinus",grades=[5,5,5,5]}]

-- moreThanNGrades :: [Student] -> Int -> [(Int, String)]
-- moreThanNGrades list n = [ (neptunID x, uni x) | x<-list, length (grades x) > n]
--extract elem from list of datatype with (field TypeName1, field TypeName2)
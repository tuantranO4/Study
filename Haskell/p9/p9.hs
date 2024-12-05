import GHC.Exts.Heap (GenClosure(key))
-- 1. Create a Employee Record, which has fields EmployeeID :: Int, companyName :: String, and programmer :: Bool

data Employee = Employee {
    employeeID :: Int,
    companyName :: String,
    programmer :: Bool
} deriving (Show)

-- a) Create two employees, using different ways
    
emp1 :: Employee
emp1 = Employee 123 "Facebook" True

emp2 :: Employee
emp2 = Employee {employeeID = 333, companyName = "Twitter", programmer = False}

-- main = print $ emp1
-- main = print $ emp2 


-- b) Create a method which checks whether an Employee is a programmer. If he is an employee print "Yes", and "No" otherwise
isProgrammer :: Employee -> String
isProgrammer emp 
    | programmer emp = "Yes"
    | otherwise = "No"

-- main = print $ isProgrammer emp1 -- "Yes"
-- main = print $ isProgrammer emp2 -- "No"


-- c) Twitter is being renamed to X. Change the companyName of employees working in X to Twitter, only if they are programmers.
changeCompanyName :: Employee -> Employee
changeCompanyName emp
    | programmer emp && companyName emp == "Twitter" = emp {companyName = "X"}
    | otherwise = emp

-- main = print $ changeCompanyName emp2 -- Employee {employeeID = 333, companyName = "Twitter", programmer = False}
-- main = print $ changeCompanyName (Employee 111 "Twitter" True) -- Employee {employeeID = 111, companyName = "X", programmer = True}

{-- 2.
    Define the Student record type of neptunID, university and list of grades.
   
    a. Select from a list of students the ones that have more then n grades,
    and return a list of (neptunID,uni) pairs of such students.
-}

data Student = Student {
    neptunID :: Int,
    uni :: String,
    grades :: [Int]
} deriving (Show)

students :: [Student]
students = [Student {neptunID=1,uni="Elte",grades=[]}, Student {neptunID=2,uni="BME",grades=[5,5,5]}, Student {neptunID=3,uni="Corvinus",grades=[5,5,5,5]}]

moreThanNGrades :: [Student] -> Int -> [(Int, String)]
moreThanNGrades list n = [ (neptunID x, uni x) | x<-list, length (grades x) > n ]

-- main = print $ moreThanNGrades students 1 -- [(2,"BME"),(3,"Corvinus")]
-- main = print $ moreThanNGrades students 3 -- [(3,"Corvinus")]



-- b. Write a function which returns the students with the highest average grade.

getAvgGrade :: Student -> Float
getAvgGrade st 
    | null (grades st) = 0
    | otherwise = fromIntegral (sum (grades st)) / fromIntegral (length (grades st))

-- main = print $ getAvgGrade (students!!0)

getBestStudents :: [Student] -> [Student]
getBestStudents students = [ x | x<-students, getAvgGrade x == highestAvgGrade ]
    where
        highestAvgGrade = maximum [getAvgGrade x | x<-students]

-- main = print $ getBestStudents students -- [Student {neptunID = 2, uni = "BME", grades = [5,5,5]},Student {neptunID = 3, uni = "Corvinus", grades = [5,5,5,5]}]
-- main = print $ getBestStudents [ Student 99 "ELTE" [1,2,3] , Student 101 "BGE" [90,0,45], Student 202 "Corvinus" [50,60], Student 4 "ELTE" [0,25,50,75,100]] -- [Student {neptunID = 202, uni = "Corvinus", grades = [50,60]}]

{-- 3.
    Given a list of continents, give back the names of the continents that have 
    at least one country whose capital has prime number of 'i' in it.
-}

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
 
-- Sample continents
europe = Continent "Europe" [macedonia, hungary, spain]
asia = Continent "Asia" [china, india]
southAmerica = Continent "South America" [argentina, brazil, chile]
 
-- Helper functions
isPrime :: Int -> Bool
isPrime x = length [y | y <- [1..x], x `mod` y == 0] == 2
 
checkI :: Country -> Bool
checkI c = isPrime (length $ filter (=='i') $ capital c) -- $ just a parenthesis
--checkI c = isPrime (length (filter (=='i') (capital c)))


checkCount :: Continent -> Bool
checkCount c = any checkI (countries c) --if at least one element in a list satisfies a given condition (predicate) -> true
--countries c is a field of Continent. 

-- Main function
continentsPrimeI :: [Continent] -> [String]
continentsPrimeI = map contName . filter checkCount --map f g : we do g first (aka check count), then f (aka extract contName).
 
--main = print $ continentsPrimeI [europe, asia] -- ["Asia"]
-- main = print $ continentsPrimeI [europe]  -- []
--main = print $ continentsPrimeI [europe, southAmerica, asia] -- ["South America","Asia"]

-- 1. Define the Q type as a record representing rational values, and  
-- implement all the possible operations.

data Q  = Q { nom :: Int, den :: Int}  deriving Show

simplify :: Q -> Q
simplify Q {nom=n,den=d}
   | d == 0 = error " denominator is 0"
   | d < 0 = Q { nom = (-n) `div` g, den = (-d) `div` g}
   | otherwise = Q { nom = n `div` g, den = d `div` g}
      where g = gcd n d

mkQ :: Int -> Int -> Q
mkQ n d = simplify (Q n d)
--NOTE: data constructor pattern matching: Q n d -> Extract the nom and den fields from Q value, bind them to n1 and d1

equalQ :: Q -> Q -> Bool
equalQ (Q n1 d1) (Q n2 d2) = n1 * d2 == n2 * d1 

smallerQ :: Q -> Q -> Bool
smallerQ (Q n1 d1) (Q n2 d2) = n1 * d2 < n2 * d1

plusQ :: Q -> Q -> Q
plusQ (Q n1 d1) (Q n2 d2) = Q (newNom `div` g) (newDen `div` g)
  where
    k = lcm d1 d2                      
    newNom = (n1 * (k `div` d1)) + (n2 * (k `div` d2)) 
    newDen = k                         
    g = gcd newNom newDen              

decrementQ :: Q -> Q -> Q
decrementQ (Q n1 d1) (Q n2 d2) = Q (newNom `div` g) (newDen `div` g)
  where
    k = lcm d1 d2                      
    newNom = (n1 * (k `div` d1)) - (n2 * (k `div` d2)) 
    newDen = k                         
    g = gcd newNom newDen

timesQ :: Q -> Q -> Q
timesQ (Q n1 d1) (Q n2 d2) = Q (newNom `div` g) (newDen `div` g)
    where
    newNom = n1*n2
    newDen=d1*d2
    g=gcd newNom newDen

divideQ :: Q -> Q -> Q
divideQ (Q n1 d1) (Q n2 d2) = Q (newNom `div` g) (newDen `div` g)
    where
    newNom = n1 `div` n2
    newDen=d1 `div` d2
    g=gcd newNom newDen

absoluteQ :: Q -> Q
absoluteQ (Q n d) = Q (abs n) (abs d)

signOfQ :: Q -> Int
signOfQ (Q n d)
    |n*d == 0 && d == 0 = error "denom is 0"
    |n*d >= 0 = 1
    |n*d < 0 = -1
--main = print (signOfQ (Q (-3) 4)) -- Output: -1

--negateQ :: Q -> Q

integerToQ :: Int -> Q
integerToQ 0 = Q 0 0
integerToQ a = Q a 1
--main = print (integerToQ 2)

rationaltoInt :: Q -> Int
rationaltoInt (Q 0 d) = 0
rationaltoInt (Q n 0) = error "cave diving"
rationaltoInt (Q n d) = n `div` d
--main = print (rationaltoInt (Q 2 0))

isIntQ :: Q -> Bool
isIntQ (Q n d)
    |n `mod` d ==0 = True
    |otherwise = False
rationaltoReal :: Q -> Float
rationaltoReal (Q n d) = fromIntegral n / fromIntegral d
 
q0 = Q { nom = 0, den = 1 }
q1 = Q { nom = 1, den = 1 }
q2 = Q { nom = 1, den = 2 }
q3 = Q { nom = 3, den = 4 }

--main = print $ simplify (mkQ 81 90) -- Q {nom = 9, den = 10}
--main = print $ mkQ 81 90 -- Q {nom = 9, den = 10}
--main = print $ equalQ (mkQ 9 10) (mkQ 81 90) -- True
--main = print $ smallerQ q2 q3 -- True
--main = print $ plusQ q2 q0 -- Q {nom = 0, den = 1}--
--main = print $ decrementQ q0 q3 -- Q {nom = 0, den = 1}--
--main = print $ timesQ q0 q2 -- Q {nom = 0, den = 1}--
--main = print $ divideQ q1 q2 -- Q {nom = 2, den = 1}--
--main = print $ absoluteQ q2 -- Q {nom = 1, den = 2}
--main = print $ signOfQ q2 -- 1
--main = print $ negateQ q2 -- Q {nom = -1, den = 2} --
--main = print $ integerToQ 4 -- Q {nom = 4, den = 1}
--main = print $ rationaltoInt q2 -- 0
--main = print $ isIntQ q1 -- True
--main = print $ rationaltoReal q2 -- 0.5

-- 2. Define the point type.
-- Test about 3 points if they are visible and can form a right-angled triangle.

data Point = Point 
            { x       ::  Float
            , y       ::  Float
            , visible ::  Bool
            } deriving Show

origo = Point {x = 0.0, y = 0.0, visible = True}
point1 = Point {x = 0.0, y = 3.0, visible = True}
point2 = Point {x = 2.0, y = 0.0, visible = True}

--isTriangle :: Point -> Point -> Point -> Bool

-- main = print $ isTriangle origo point1 point2 -- True

{-- 3. 
    a. Define a Person record which contains name and height two fields,
	    with type of String and Double respectively. 
      
    b. Write a function which takes a person
	    and a certain height, if the person is taller than 1.70, subtract their height by 1%
-}

data Person = Person {
    name :: String,
    tall :: Double
} deriving (Show)

-- Sample persons
john :: Person
john = Person {name = "John", tall = 1.78}

mike :: Person
mike = Person {name = "Mike", tall = 1.58}

lily :: Person
lily = Person {name = "Lily", tall = 1.85}

--changeHeight :: Person -> Person

-- main =    print $ changeHeight john  -- Person1 "John" 1.7622
-- main =    print $ changeHeight mike  -- Person1 "Mike" 1.58
-- main =    print $ changeHeight lily  -- Person1 "Lily" 1.8315000000000001


{-- 4.
    Me and my friends went to play football in the streets, and the game ended as tie, 
    so we were discussing if we should go for penalties or not. Help me to decide that.
    You will get in a list each one of my team member skill Level and name, and you will 
    get the name of the other team's goalkeeper and his/her level of skill.
    If the skill of the player is greater or equal than the skill of the goalkeeper, 
    then the penalty will count as scored.
    The team would win this virtual game, if at least 3 or more penalties could be 
    scored against the given goalkeeper.
-}

data APlayer = APlayer {
    playerName :: String,
    skillLevel :: Int
}

--shouldWePlay :: [APlayer] -> APlayer -> Bool

-- main = print $  shouldWePlay [ APlayer {playerName = "kareem", skillLevel = 4}, APlayer {playerName = "Tarek", skillLevel = 3}, APlayer {playerName = "Ali", skillLevel = 3},APlayer {playerName="Hussien", skillLevel=2}, APlayer {playerName="Ziad", skillLevel=4}] (APlayer {playerName="Gemy", skillLevel=4}) -- False
-- main = print $  shouldWePlay [ APlayer {playerName = "kareem", skillLevel = 5}, APlayer {playerName = "Tarek", skillLevel = 4}, APlayer {playerName = "Ali", skillLevel = 3},APlayer {playerName="Hussien", skillLevel=2}, APlayer {playerName="Ziad", skillLevel=4}] (APlayer {playerName="Gemy", skillLevel=4}) -- True


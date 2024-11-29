-- Define two data types:
-- 1. City with fields for name (String) and population (Int).
-- 2. Citizen with fields for name (String), age (Int), and city (City).
-- Create examples:
-- - City: Name "Metropolis", Population 1000000
-- - Citizen: Name "John", Age 45, City is Metropolis.
-- Hint: Use `deriving (Show)` to make your records printable.
--
-- Write a function to rename a city to a new name if its population is below a specific threshold.
-- The specific threshold and new name are passed as the first and second arguments, respectively.

data City = City
  { cityName :: String
  , cityPopulation :: Int
  } deriving (Show)

data Citizen = Citizen
  { citizenName :: String
  , citizenAge :: Int
  , citizenCity :: City
  } deriving (Show)
renameCities :: Int -> String -> [Citizen] -> [Citizen]
renameCities threshold newName citizens = map updateCitizen citizens
  where
    updateCitizen (Citizen name age (City cName cPopulation))
      | cPopulation < threshold = Citizen name age (City newName cPopulation)
      | otherwise = Citizen name age (City cName cPopulation)

--main = print $ renameCities 500000 "Rural Area" [Citizen "Alice" 30 (City "Smallville" 400000), Citizen "Bob" 40 (City "Metropolis" 1000000)] -- [Citizen {citizenName = "Alice", citizenAge = 30, citizenCity = City {cityName = "Rural Area", cityPopulation = 400000}},Citizen {citizenName = "Bob", citizenAge = 40, citizenCity = City {cityName = "Metropolis", cityPopulation = 1000000}}]
-- main = print $ renameCities 200000 "Tiny Town" [Citizen "Charlie" 25 (City "Smallville" 400000)] -- [Citizen {citizenName = "Charlie", citizenAge = 25, citizenCity = City {cityName = "Smallville", cityPopulation = 400000}}]
main = print $ renameCities 800000 "Villageton" [] -- []

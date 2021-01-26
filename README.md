# PracticeREST
Small Spring Boot REST app

Create a recipes and ingredients REST API:
    - Recipes and Ingredients are the main models (save Ingredients in one file and Recipes in another)
    - Recipes have Ingredients and Notes (number, description), time of creation(timestamp), author, price   
    - Recipes have a list of all it's ingredients (ID of ingredients) 
    - Ingredients have name, origin 
    - Notes are created when creating a recipe and saved in the same file
    - Override equals and hashcode methods for Recipes and Ingredients
    
    - Override toString methods and save Recipes(with Notes and int list of Ingredient ID's) 
    - Save CRUD data to two files (Recipes and Ingredients)
    
    - Add exception handler
    - Validate Input creation most important fields and throw a informative error
    
    - Analyze which are the most used ingredients (possibly some sort of streaming filter on recipes?)
    - Add Person class and possibility to Order most expensive recipe (person will have cash)
    - If he does not have enough money return an error. 
    
Add two REST CRUD Controllers for Recipes and Ingredients
JSON format for request and responses
    - GET
    - POST
    - PUT
    - DELETE


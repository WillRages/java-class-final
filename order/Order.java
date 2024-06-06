package order;

import order.Ingredient.Type;

import java.util.List;

import static order.Order.MenuItem.*;

public class Order {
    public enum MenuItem {
        Burger(List.of(
                new Ingredient(0.25, Type.BeefLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(0.25, Type.ButterLbs)
        ), 1.99),
        Cheeseburger(List.of(
                new Ingredient(0.25, Type.BeefLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(1, Type.CheeseSlice),
                new Ingredient(0.25, Type.ButterLbs)
        ), 2.57),
        SupersizeBurger(List.of(
                new Ingredient(0.5, Type.BeefLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(0.25, Type.ButterLbs)
        ), 3.99),
        HeartAttackBurger(List.of(
                new Ingredient(1, Type.BeefLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(0.25, Type.ButterLbs)
        ), 7.99),
        HeartAttackCheeseburger(List.of(
                new Ingredient(1, Type.BeefLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(1, Type.CheeseSlice),
                new Ingredient(0.25, Type.ButterLbs)
        ), 8.57),
        SupersizeHeartAttackCheeseburger(List.of(
                new Ingredient(2, Type.BeefLbs),
                new Ingredient(3, Type.Buns),
                new Ingredient(5, Type.CheeseSlice),
                new Ingredient(0.37, Type.ButterLbs)
        ), 16.57),
        DoubleSupersizeHeartAttackCheeseburger(List.of(
                new Ingredient(4, Type.BeefLbs),
                new Ingredient(5, Type.Buns),
                new Ingredient(10, Type.CheeseSlice),
                new Ingredient(0.62, Type.ButterLbs)
        ), 32.99),
        QuintupleSupersizeHeartAttackCheeseburger(List.of(
                new Ingredient(10, Type.BeefLbs),
                new Ingredient(5, Type.Buns),
                new Ingredient(10, Type.CheeseSlice),
                new Ingredient(0.62, Type.ButterLbs)
        ), 82.99),
        DoubleSupersizeHeartAttackBurger(List.of(
                new Ingredient(4, Type.BeefLbs),
                new Ingredient(5, Type.Buns),
                new Ingredient(0.62, Type.ButterLbs)
        ), 32.42),
        QuintupleSupersizeHeartAttackBurger(List.of(
                new Ingredient(10, Type.BeefLbs),
                new Ingredient(5, Type.Buns),
                new Ingredient(0.62, Type.ButterLbs)
        ), 82.42),
        THE_CHUCK_NORRIS_BURGER(List.of(
                new Ingredient(50, Type.BeefLbs),
                new Ingredient(25, Type.Buns),
                new Ingredient(5, Type.ButterLbs)
        ), 411.99),
        Fry(List.of(
                new Ingredient(0.1, Type.Potato),
                new Ingredient(0.01, Type.ButterLbs)
        ), 0.05),
        SmallFries(List.of(
                new Ingredient(1, Type.Potato),
                new Ingredient(0.1, Type.ButterLbs)
        ), 0.99),
        MediumFries(List.of(
                new Ingredient(2, Type.Potato),
                new Ingredient(0.1, Type.ButterLbs)
        ), 1.99),
        LargeFries(List.of(
                new Ingredient(3, Type.Potato),
                new Ingredient(0.1, Type.ButterLbs)
        ), 3.99),
        XLFries(List.of(
                new Ingredient(4.5, Type.Potato),
                new Ingredient(0.1, Type.ButterLbs)
        ), 6.99),
        SupersizeFries(List.of(
                new Ingredient(8, Type.Potato),
                new Ingredient(0.2, Type.ButterLbs)
        ), 8.99),
        ChickenSandwich(List.of(
                new Ingredient(0.25, Type.ChickenLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(0.25, Type.ButterLbs)
        ), 1.99),
        ChickenCheeseSandwich(List.of(
                new Ingredient(0.25, Type.ChickenLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(1, Type.CheeseSlice),
                new Ingredient(0.25, Type.ButterLbs)
        ), 2.57),
        DoubleChickenSandwich(List.of(
                new Ingredient(0.5, Type.ChickenLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(0.25, Type.ButterLbs)
        ), 3.99),
        DoubleChickenCheeseSandwich(List.of(
                new Ingredient(0.5, Type.ChickenLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(1, Type.CheeseSlice),
                new Ingredient(0.25, Type.ButterLbs)
        ), 4.57),
        QuintupleChickenSandwich(List.of(
                new Ingredient(1.25, Type.ChickenLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(0.25, Type.ButterLbs)
        ), 9.99),
        QuintupleChickenCheeseSandwich(List.of(
                new Ingredient(1.25, Type.ChickenLbs),
                new Ingredient(2, Type.Buns),
                new Ingredient(1, Type.CheeseSlice),
                new Ingredient(0.25, Type.ButterLbs)
        ), 10.57),
        SmallDrink(List.of(
                new Ingredient(0.2, Type.StyrofoamLbs)
        ), 0.99),
        MediumDrink(List.of(
                new Ingredient(0.5, Type.StyrofoamLbs)
        ), 1.99),
        LargeDrink(List.of(
                new Ingredient(0.75, Type.StyrofoamLbs)
        ), 3.99),
        XLDrink(List.of(
                new Ingredient(0.95, Type.StyrofoamLbs)
        ), 5.99),
        THE_CHONKER_DRINK(List.of(
                new Ingredient(1.2, Type.StyrofoamLbs)
        ), 9.99),
        Onion(List.of(), 0.50),
        Pickle(List.of(), 0.50),
        Tomato(List.of(), 0.50),
        Lettuce(List.of(), 0.50),
        ThousandIsland(List.of(
                new Ingredient(1, Type.Egg),
                new Ingredient(2, Type.MustardSeeds),
                new Ingredient(0.1, Type.Spices),
                new Ingredient(0.2, Type.Lemons),
                new Ingredient(0.5, Type.SourCream),
                new Ingredient(0.25, Type.VinegarLbs),
                new Ingredient(0.25, Type.SugarLbs),
                new Ingredient(0.1, Type.GarlicLbs),
                new Ingredient(0.35, Type.SaltLbs)
        ), 4.99),
        Spinach(List.of(), 0.50),
        PeanutButter(List.of(
                new Ingredient(100, Type.Peanuts)
        ), 0.50),
        GoatCheese(List.of(), 0.50),
        Ketchup(List.of(
                new Ingredient(0.75, Type.Tomato)
        ), 0.50),
        Mustard(List.of(
                new Ingredient(2, Type.MustardSeeds),
                new Ingredient(0.1, Type.Spices),
                new Ingredient(0.2, Type.Lemons)
        ), 0.75),
        Medidip(List.of(
                new Ingredient(0.25, Type.Milk),
                new Ingredient(0.2, Type.Lemons),
                new Ingredient(0.2, Type.VinegarLbs),
                new Ingredient(0.2, Type.SugarLbs),
                new Ingredient(0.2, Type.SaltLbs),
                new Ingredient(1, Type.Anchovy),
                new Ingredient(0.1, Type.Onions)
        ), 1.99),
        SourCream(List.of(
                new Ingredient(0.25, Type.Milk),
                new Ingredient(0.2, Type.Lemons)
        ), 0.50),
        WorcestershireSauce(List.of(
                new Ingredient(0.2, Type.VinegarLbs),
                new Ingredient(0.2, Type.SugarLbs),
                new Ingredient(0.2, Type.SaltLbs),
                new Ingredient(1, Type.Anchovy),
                new Ingredient(0.1, Type.Onions)
        ), 1.32),
        Salt(List.of(), 0.50),
        Pepper(List.of(), 0.50),
        Parmesan(List.of(), 0.50),
        Mayo(List.of(
                new Ingredient(2, Type.MustardSeeds),
                new Ingredient(0.1, Type.Spices),
                new Ingredient(0.4, Type.Lemons),
                new Ingredient(0.2, Type.OliveOil)
        ), 0.99);

        private final List<Ingredient> ingredients;
        private final double price;

        MenuItem(List<Ingredient> ingredients, double price) {
            this.ingredients = ingredients;
            this.price = price;
        }

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public double getPrice() {
            return price;
        }
    }

    // burgers fries sandwiches drinks additions
    public static MenuItem[] burgers = new MenuItem[]{
            Burger,
            Cheeseburger,
            SupersizeBurger,
            HeartAttackBurger,
            HeartAttackCheeseburger,
            SupersizeHeartAttackCheeseburger,
            DoubleSupersizeHeartAttackCheeseburger,
            QuintupleSupersizeHeartAttackCheeseburger,
            DoubleSupersizeHeartAttackBurger,
            QuintupleSupersizeHeartAttackBurger,
            THE_CHUCK_NORRIS_BURGER,
    };

    public static MenuItem[] fries = new MenuItem[]{
            Fry,
            SmallFries,
            MediumFries,
            LargeFries,
            XLFries,
            SupersizeFries,
    };

    public static MenuItem[] sandwiches = new MenuItem[]{
            ChickenSandwich,
            ChickenCheeseSandwich,
            DoubleChickenSandwich,
            DoubleChickenCheeseSandwich,
            QuintupleChickenSandwich,
            QuintupleChickenCheeseSandwich,
    };

    public static MenuItem[] drinks = new MenuItem[]{
            SmallDrink,
            MediumDrink,
            LargeDrink,
            XLDrink,
            THE_CHONKER_DRINK,
    };

    public static MenuItem[] additions = new MenuItem[]{
            Onion,
            Pickle,
            Tomato,
            Lettuce,
            ThousandIsland,
            Spinach,
            PeanutButter,
            GoatCheese,
            Ketchup,
            Mustard,
            Medidip,
            SourCream,
            WorcestershireSauce,
            Salt,
            Pepper,
            Parmesan,
            Mayo,
    };
}

package order;

public record Ingredient(double amount, Type type) {
    public enum Type {
        Anchovy,
        BeefLbs,
        Buns,
        ButterLbs,
        CheeseSlice,
        ChickenLbs,
        Egg,
        GarlicLbs,
        Lemons,
        Milk,
        MustardSeeds,
        OliveOil,
        Onions,
        Peanuts,
        Potato,
        SaltLbs,
        SourCream,
        Spices,
        StyrofoamLbs,
        SugarLbs,
        Tomato,
        VinegarLbs,
    }
}

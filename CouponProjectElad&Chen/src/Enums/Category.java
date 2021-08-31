package Enums;

//https://stackoverflow.com/questions/604424/how-to-get-an-enum-value-from-a-string-value-in-java?rq=1

/**
 * this class contain all the coupons  categories and how to convert them to int value
 */
public enum Category {
    attractions (1),
    clothes (2),
    electricity(3),
    flights(4),
    food(5),
    movies(6),
    resorts(7),
    restaurant(8),
    vacation(9);

    private int num;


    /**
     * Con that help us get an num in order to peack a category num.
     * @param num the number value of each category.
     */
    Category(int num) {
        this.num = num;
    }

    public int getText() {
        return this.num;
    }

    /**
     * this method checking if the int value it gets,is equal to any of the values that is on category array
     * @param num
     * @return value of category's name
     */
    public static Category getCategoryStringFromInt(int num) {
        for (Category item : Category.values()) {
            if (item.num==num) {
                return item;
            }
        }
        return null;
    }
}
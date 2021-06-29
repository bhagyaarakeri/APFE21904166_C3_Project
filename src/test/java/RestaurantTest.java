import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class RestaurantTest {
    Restaurant restaurant;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void init(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Noodles", 170);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        doReturn(LocalTime.parse("11:30:00")).when(restaurant).getCurrentTime();
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        doReturn(LocalTime.parse("09:30:00")).when(restaurant).getCurrentTime();
        assertFalse(restaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>GET TOTAL PRICE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_total_amount_calculated_by_the_system_is_same_as_total_price(){
        List<String> selectItems = new ArrayList<String>();
        selectItems.add("Sweet corn soup");
        selectItems.add("Noodles");
        assertEquals(289, restaurant.calculateTotalAmt(selectItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<GET TOTAL PRICE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}

package mate.academy.internetshop;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {
    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static UserService userService;

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User user1 = new User("Name 1");
        User user2 = new User("Name 2");
        userService.create(user1);
        userService.create(user2);

        Bucket bucket1 = new Bucket();
        bucketService.create(bucket1);

        user1.setBucket(bucket1);
        System.out.println(Storage.users);

        Item item1 = new Item("Item 1", 1.0);
        Item item2 = new Item("Item 2", 2.0);
        Item item3 = new Item("Item 3", 3.0);
        itemService.create(item1);
        itemService.create(item2);
        itemService.create(item3);
        System.out.println(Storage.items);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        System.out.println(items);

        bucketService.addItem(bucket1, item1);
        bucketService.addItem(bucket1, item2);
        System.out.println(bucketService.getAllItems(bucket1));
        System.out.println(Storage.buckets);

        orderService.completeOrder(bucketService.getAllItems(bucket1), user1);
        System.out.println(Storage.orders);
    }
}

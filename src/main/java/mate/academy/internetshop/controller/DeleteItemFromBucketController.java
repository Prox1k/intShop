package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteItemFromBucketController extends HttpServlet {
    private static final Long USER_ID = 1L;

    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        Item item = itemService.get(Long.valueOf(itemId));
        Bucket bucket = bucketService.getByUserId(USER_ID);
        bucketService.deleteItem(bucket, item);
        resp.sendRedirect(req.getContextPath() + "/servlet/showBucket");
    }
}
